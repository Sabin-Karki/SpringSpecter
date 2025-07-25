package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component      // this annotation is used of course as this class would need its object created by spring container as it needs to be injected
public class ParentRuleCheck  implements BuildRule{
    public String execute(Map<String,String> flattenedPom){
        // the logic for me is  -> get the version from the .xml file which I will parse and then check the version on whether it shall be used or not
        String parentVersion  = flattenedPom.get("parent.version");
        if(parentVersion!=null && (parentVersion.startsWith("1.") || parentVersion.startsWith("2."))){
            return  " VULNERABILITY FOUND : Outdated version , suggest upgrading";
        }else{
            return null;
        }
    }
}
