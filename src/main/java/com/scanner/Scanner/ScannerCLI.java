package com.scanner.Scanner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ScannerCLI implements CommandLineRunner {

    private final ApplicationArguments arguments;
    private final PropertiesFileParser propertiesFileParser;
    List<SecurityRule> securityRules;
    private final YamlFileParser yamlFileParser;
    List<BuildRule> buildRule;
    private final PomXmlParser xmlParser;
    private final ConsoleReporter consoleReporter;

    public ScannerCLI(ApplicationArguments arguments,PropertiesFileParser propertiesFileParser ,List<SecurityRule> securityRules,YamlFileParser yamlFileParser,List<BuildRule> buildRule , PomXmlParser xmlParser , ConsoleReporter consoleReporter) {
        this.arguments = arguments;
        this.propertiesFileParser=propertiesFileParser;
        this.securityRules=securityRules;
        this.yamlFileParser=yamlFileParser;
        this.buildRule=buildRule;
        this.xmlParser=xmlParser;
        this.consoleReporter=consoleReporter;
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
                            .filter(path -> !path.toString().contains("/target/"))
                            .filter(Files::isRegularFile)
                            .filter(path->{
                               String fileName = path.getFileName().toString();
                               return fileName.equals("application.properties")
                                       || fileName.equals("application.yml")
                                        || fileName.equals("application.yaml")
                                       || fileName.equals("pom.xml");
                            }) // using 1 filter to implement multiple logical OR
                            .collect(Collectors.toList());
                    if(propertiesFiles.isEmpty()){
                        System.err.println("No application file found in the project");
                    }else{

                        for(Path file : propertiesFiles){
                            System.out.println(" ->  Found  file : " + file);
                            try {
                                // Okay so we will need a variable to store the flattened config now of type map interface
                                Map<String, String> flatConfig = new HashMap<>();
                                String fileName = file.getFileName().toString();

                                if (fileName.endsWith(".properties") || fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {

                                    if (fileName.endsWith(".properties")) {
                                        // okay inside the block ..now what..logically...now i know .if my file ends with .properties...
                                        Properties parsedProperties = this.propertiesFileParser.parse(file);
                                        // now loop through all the key in properties object and get the key but only the string ,since thats what will be in the final format
                                        for (String key : parsedProperties.stringPropertyNames()) {
                                            flatConfig.put(key, parsedProperties.getProperty(key)); // the latter method here ofcourse is use to give value of the given key
                                        }
                                    } else {
                                        flatConfig = this.yamlFileParser.parse(file);
                                    }

                                    // this lsit is created to hold content/vulnerablitie sof the file we jsut parsed a step above
                                    List<String> vulnerablities = new ArrayList<>();
                                    for (SecurityRule rule : this.securityRules) {
                                        String result = rule.execute(flatConfig);
                                        if (result != null) {
                                            vulnerablities.add(result);
                                        }
                                    }

                                    //now after checking the rule
                                    if (vulnerablities.isEmpty()) {
                                        this.consoleReporter.printPass(file, "NO VULNERABLITIES DETECTED : ");
                                    } else {
                                        this.consoleReporter.printReport(file,vulnerablities   );
                                        for (String vulnerability : vulnerablities) {
                                            System.err.println(" - " + vulnerability);
                                        }
                                    }

                                } else if (fileName.equals("pom.xml")) {

                                    flatConfig = this.xmlParser.parsePom(file);

                                    List<String> pomVulnerabilities = new ArrayList<>();
                                    for (BuildRule rule : this.buildRule) {
                                        String result = rule.execute(flatConfig);
                                        if (result != null) {
                                            pomVulnerabilities.add(result);
                                        }
                                    }

                                    if (pomVulnerabilities.isEmpty()) {
                                        this.consoleReporter.printPass(file, "  NO VULNERABILITIES DETECTED : " );
                                    } else {
                                        this.consoleReporter.printReport(file,pomVulnerabilities);
                                    }
                                }
                            } catch (Exception e) {
                                System.err.println("Can not process the file " + file.getFileName() + ": " + e.getMessage());
                                e.printStackTrace(); // This is very helpful for debugging parser errors.
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
