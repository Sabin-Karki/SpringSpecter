package com.scanner.Scanner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

//only job is to print report
@Component
public class ConsoleReporter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public void printReport(Path file, List<String> vulnerabilities){
      // loop through vulnerability list and print the vulnerability
            System.out.println(ANSI_RED + " [FAIL] VULNERABILITIES DETECTED IN: " + file.getFileName() + ANSI_RESET  );
     for ( String vulnerability : vulnerabilities){
         System.err.println(" - " + vulnerability);
     }

    }
    public void printPass(Path file , String message){
            // Print the green PASS header.
            System.out.println(ANSI_GREEN + "[PASS] " + message + ": " + file.getFileName() + ANSI_RESET);
        }
    }

