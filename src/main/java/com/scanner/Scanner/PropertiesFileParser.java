package com.scanner.Scanner;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Component // this creates instance of the class and makes it able to be injected in another class
public class PropertiesFileParser {
    //instead of manual parsing code ,will try to use Properties class which essentially provides key value pair form application.properties file
    //properties.load(InputStream): This is the main method. It reads from an input stream (which we can get from our Path) and parses all the key=value pairs, loading them into the Properties object.
    //properties.getProperty("some.key"): This method lets you retrieve the value for a given key.

    // my logic is to first use inputstream to get the bytes from the file and then use properties methods to split the data from the file into key and value ??
    public Properties parse(Path filePath) throws IOException{
        Properties property = new Properties();
       try(InputStream input = Files.newInputStream(filePath)){
             property.load(input);
       };
         return property;

    }
}
