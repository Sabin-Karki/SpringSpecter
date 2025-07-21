package com.scanner.Scanner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ScannerCLI implements CommandLineRunner {

    private final ApplicationArguments arguments;
    private final PropertiesFileParser propertiesFileParser;
    List<SecurityRule> securityRules;

    public ScannerCLI(ApplicationArguments arguments,PropertiesFileParser propertiesFileParser ,List<SecurityRule> securityRules) {
        this.arguments = arguments;
        this.propertiesFileParser=propertiesFileParser;
        this.securityRules=securityRules;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> nonOptionArgs = this.arguments.getNonOptionArgs();
        if (nonOptionArgs.isEmpty()) {
            System.err.println("Project path not specified ");

        } else {
            String projectPath = nonOptionArgs.get(0);
            System.out.println(projectPath);


            Path currentPath = Paths.get(projectPath);//getting path of user

            if (Files.exists(currentPath)) {
                if (Files.isDirectory(currentPath)) {
                try{
                    //logic is first get the path object and use walk method which returns all files/folder structire inside the path specified
                    //filter and look for only files and not folder of project path
                    //then look for certain extension
                    //at the end use collector to terminate and append all in list !
                    List<Path> propertiesFiles = Files.walk(currentPath)
                            .filter(Files::isRegularFile)
                            .filter(path->path.getFileName().toString().equals("application.properties"))
                            .collect(Collectors.toList());
                    if(propertiesFiles.isEmpty()){
                        System.err.println("No application file found in the project");
                    }else{

                        for(Path file : propertiesFiles){
                            System.out.println(" ->  Found  file : " + file);
                            try {
                                Properties parsedProperties= this.propertiesFileParser.parse(file);
                                // this i believe will now parse the file whifh stores the properties file , , although i am a little confused about this line...like why did i intilaise parsedProperties variable with Properties class gaain...and the right side whole this.propertiesfileparser.parse(),i knwo this is calling method byt why use of this and why not just propertiesfileeparser.parse()..
                                System.out.println(" -> parsing the file " + file.getFileName());

                                List<String> vulnerablities = new ArrayList<>(); // this lsit is created to hold content/vulnerablitie sof the file we jsut parsed a step above
                                for(SecurityRule rule : this.securityRules){
                                    String result = rule.execute(parsedProperties);
                                    if(result!=null){
                                        vulnerablities.add(result);
                                    }
                                }
                                //now after checking the rule
                                if(vulnerablities.isEmpty()){
                                    System.out.println(" [PASS] NO VULNERABLITIES DETECTED"+file.getFileName());
                                }else{
                                    System.err.println(" [FAIL] VULNERABILTIIES DETECTED " + file.getFileName());
                                    for(String vulnerability : vulnerablities){
                                        System.err.println("  " + vulnerability);
                                    }
                                }
                            }catch(IOException e){
                                System.out.println("Can not parse the file" + e.getMessage());
                            }
                    }

                }}catch(IOException e){
                    System.err.println("Exception thrown" + e.getMessage());
                }

                    System.out.println(" Scanning Directory :  " + projectPath);
                } else {
                    System.err.println("Project path exist but it is not a directory");
                }

            } else {
                System.err.println("Project path is not specified");
            }

        }
    }
}
