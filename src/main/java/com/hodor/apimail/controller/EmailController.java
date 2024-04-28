package com.hodor.apimail.controller;

import com.hodor.apimail.dto.BulkEmailDto;
import com.hodor.apimail.dto.EmailDto;
import com.hodor.apimail.service.BulkEmailService;
import com.hodor.apimail.service.EmailService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final BulkEmailService bulkEmailService;

    @PostMapping
    public void create(@RequestBody @NonNull @Validated EmailDto dto) {
        emailService.sendEmail(dto);
    }

    @PostMapping("/bulk")
    public void create(@RequestBody @NonNull @Validated BulkEmailDto dto) {
        bulkEmailService.sendEmail(dto);
    }

}
