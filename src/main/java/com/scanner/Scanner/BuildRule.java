package com.scanner.Scanner;

import java.util.Map;

public interface BuildRule {
    public String execute(Map<String,String> flattenedPom); // this method when called will be expecting a .xml // pom file which is flatenned and in a key value pair

}
