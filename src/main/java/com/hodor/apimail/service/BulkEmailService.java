package com.hodor.apimail.service;

import com.hodor.apimail.dto.BulkEmailDto;
import com.hodor.apimail.dto.EmailDto;
import com.hodor.apimail.entity.BulkEmail;
import com.hodor.apimail.entity.Email;
import com.hodor.apimail.enumaration.EmailStatus;
import com.hodor.apimail.mapper.BulkEmailMapper;
import com.hodor.apimail.mapper.EmailMapper;
import com.hodor.apimail.repository.BulkEmailRepository;
import com.hodor.apimail.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkEmailService {

    private final BulkEmailRepository bulkEmailRepository;
    private final EmailService emailService;

    public void sendEmail(BulkEmailDto bulkEmailDto) {
        log.info("Sending email : {}", bulkEmailDto.toString());
        BulkEmail entity = BulkEmailMapper.INSTANCE.toEntity(bulkEmailDto);

        bulkEmailRepository.save(entity);

        bulkEmailDto.getTo().forEach(to -> {
            EmailDto dto = new EmailDto();
            dto.setTo(to);
            dto.setBody(bulkEmailDto.getBody());
            dto.setFrom(bulkEmailDto.getFrom());
            dto.setSubject(bulkEmailDto.getSubject());
            dto.setPriority(bulkEmailDto.getPriority());

            emailService.sendEmail(dto);
        });

    }
}
