package com.hodor.apimail.service;

import com.hodor.apimail.dto.EmailDto;
import com.hodor.apimail.entity.Email;
import com.hodor.apimail.enumaration.EmailStatus;
import com.hodor.apimail.mapper.EmailMapper;
import com.hodor.apimail.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;
    private final KafkaTemplate<Long, Long> kafkaTemplate;

    public void sendEmail(EmailDto emailDto) {
        log.info("Sending email : {}", emailDto.toString());
        Email entity = EmailMapper.INSTANCE.toEntity(emailDto);
        entity.setStatus(EmailStatus.PENDING);

        emailRepository.save(entity);
        kafkaTemplate.send("emailMessageTopicLow",entity.getId());

    }
}
