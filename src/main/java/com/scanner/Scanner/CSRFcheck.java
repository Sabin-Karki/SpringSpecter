package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CSRFcheck implements  SecurityRule{
    @Override
    public String execute (Properties properties){
        String value = properties.getProperty("spring.security.csrf.enabled");
        if(value != null && value.equals("false")){
            return " VULNERABILITY : CSRF PROTECTION IS DISABLED ! ";
        }else{
            return null;
        }
    }
}
