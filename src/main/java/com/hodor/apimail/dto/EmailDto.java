package com.hodor.apimail.dto;

import com.hodor.apimail.enumaration.EmailPriority;
import lombok.Data;

@Data
public class EmailDto {

    private String from;
    private String to;
    private String subject;
    private String body;
    private EmailPriority priority;

}
