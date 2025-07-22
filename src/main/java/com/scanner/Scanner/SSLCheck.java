package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component // to tell spring to create instance of this class since this will be injected somewhere else
public class SSLCheck implements  SecurityRule {
    // so the logic is literally very simple
    // get the value from the key and store it in a variable.use if condition to check further and return vulnerability or return null
    //Firstly  ?What is ssl ? it is simply a protocol to secure communicaiton occuring across network between sender and reciever ...and it encrypts the data travelled across this network

    @Override
    public String execute(Properties properties){
        String value = properties.getProperty("server.ssl.enabled");
        if(value != null && value.equals("false")){
            return "VULNERABILITY FOUND : SSL ENCRYPTION IS DISABLED !";
        }else{
         return null;
        }
    }
}
 // so now my goal is to inject this in scannerCLI class ,,, and return some string output in terminal / / wait perhaps i need not do this , because i have already actually enforced SecurityRule in teh scannerCLI class and created a vulnerability variable with list<String> type  ...and oh yeah this might be collected there too
