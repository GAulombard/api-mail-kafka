package com.hodor.apimail.dto;

import com.hodor.apimail.enumaration.EmailPriority;
import lombok.Data;

import java.util.List;

@Data
public class BulkEmailDto {

    private String from;
    private List<String> to;
    private String subject;
    private String body;
    private EmailPriority priority;

}
