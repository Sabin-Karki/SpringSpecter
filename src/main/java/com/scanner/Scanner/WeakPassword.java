package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Component
public class WeakPassword implements  SecurityRule {
 @Override
    public String execute(Map<String,String> properties){
     String data  = properties.get("spring.security.user.password");
     if(data != null && data.equals("password")){
     return "VULNERABILITY : DEFAULT PASSWORD \"password\" is used for password  ";
     }else{
         return null;
     }
 }
}
