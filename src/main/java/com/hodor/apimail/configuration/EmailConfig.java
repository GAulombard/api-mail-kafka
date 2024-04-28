package com.hodor.apimail.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "email.provider")
@Setter
@Getter
public class EmailConfig {

    private Map<String,String> properties;
    private Auth auth;

    @Setter
    @Getter
    public static class Auth {
        private String user;
        private String password;
    }
}
