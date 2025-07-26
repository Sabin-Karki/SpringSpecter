package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Log4JVulnerable implements  BuildRule {
    public String execute(Map<String,String> flattenedPom){
        String version = flattenedPom.get("dependency.org.apache.logging.log4j:log4j-core");
        if(version!=null && !version.startsWith("2.17")){
            return " LOG4J vulnerability found . Please upgrade the version ! ";
        }else {
            return null;
        }
    }
}
