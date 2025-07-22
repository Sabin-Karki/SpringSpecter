package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Component
public class CSRFcheck implements  SecurityRule{
    @Override
    public String execute (Map<String,String> properties){
        String value = properties.get("spring.security.csrf.enabled");
        if(value != null && value.equals("false")){
            return " VULNERABILITY : CSRF PROTECTION IS DISABLED ! ";
        }else{
            return null;
        }
    }
}
