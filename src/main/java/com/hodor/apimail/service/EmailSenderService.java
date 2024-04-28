package com.hodor.apimail.service;

import com.hodor.apimail.configuration.EmailConfig;
import com.hodor.apimail.dto.EmailDto;
import com.hodor.apimail.entity.Email;
import com.hodor.apimail.exception.EmailSenderException;
import com.hodor.apimail.mapper.EmailMapper;
import com.hodor.apimail.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final EmailConfig emailConfig;

    public void sendEmail(EmailDto emailDto) throws EmailSenderException {

        try {

            Properties props = new Properties();
            props.putAll(emailConfig.getProperties());

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailConfig.getAuth().getUser(), emailConfig.getAuth().getPassword());
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailDto.getFrom()));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(emailDto.getTo()));
            message.setSubject(emailDto.getSubject());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailDto.getBody(), "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            throw new EmailSenderException(e.getMessage(), e);
        }
    }
}
