package com.hodor.apimail.scheduler;

import com.hodor.apimail.dto.EmailDto;
import com.hodor.apimail.entity.Email;
import com.hodor.apimail.enumaration.EmailStatus;
import com.hodor.apimail.mapper.EmailMapper;
import com.hodor.apimail.repository.EmailRepository;
import com.hodor.apimail.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderKafkaScheduler {

    private final EmailRepository emailRepository;
    private final EmailSenderService emailSenderService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final Semaphore semaphoreLow = new Semaphore(30);
    private final Semaphore semaphoreNormal = new Semaphore(100);
    private final Semaphore semaphoreHigh = new Semaphore(3000);

    @KafkaListener(topics = "emailMessageTopicLow", groupId = "emailMessageTopic")
    public void emailMessageTopicLow(Long messageId) throws InterruptedException {
        semaphoreLow.acquire();
        sendEmailMessage(messageId, semaphoreLow);
    }

    @KafkaListener(topics = "emailMessageTopicNormal", groupId = "emailMessageTopic")
    public void emailMessageTopicNormal(Long messageId) throws InterruptedException {
        semaphoreNormal.acquire();
        sendEmailMessage(messageId, semaphoreNormal);
    }

    @KafkaListener(topics = "emailMessageTopicHigh", groupId = "emailMessageTopic")
    public void emailMessageTopicHigh(Long messageId) throws InterruptedException {
        semaphoreHigh.acquire();
        sendEmailMessage(messageId, semaphoreHigh);
    }

    private void sendEmailMessage(Long messageId, Semaphore semaphore) throws InterruptedException {

        Email entity = emailRepository.findById(messageId).orElseThrow();
        entity.setStatus(EmailStatus.SENDING);
        emailRepository.save(entity);
        EmailDto dto = EmailMapper.INSTANCE.toDto(entity);

        executorService.submit(() -> {
            try {
                emailSenderService.sendEmail(dto);
                entity.setStatus(EmailStatus.SENT);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                entity.setFailedReason(e.getMessage());
                entity.setStatus(EmailStatus.FAILED);
            } finally {
                emailRepository.save(entity);
                semaphore.release();
            }
        });
    }
}
