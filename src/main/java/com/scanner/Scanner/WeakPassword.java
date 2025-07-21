package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class WeakPassword implements  SecurityRule {
 @Override
    public String execute(Properties properties){
     String data  = properties.getProperty("spring.security.user.password");
     if(data != null && data.equals("password")){
     return "VULNERABILITY : DEFAULT PASSWORD \"password\" is used for password  ";
     }else{
         return null;
     }
 }
}
