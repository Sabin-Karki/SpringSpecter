package com.scanner.Scanner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

//only job is to print report
@Component
public class ConsoleReporter {
    public void printReport(Path file, List<String> vulnerabilities){
      // loop through vulnerability list and print the vulnerability
            System.out.println("\u001B[31m" + " [FAIL] VULNERABILITIES DETECTED IN: " + file.getFileName() + "\\u001B[0m"  );
     for ( String vulnerability : vulnerabilities){
         System.err.println(" - " + vulnerability);
     }

    }
    public void printPass(Path file , String message){
            // Print the green PASS header.
            System.out.println("\u001B[32m" + "[PASS] " + message + ": " + file.getFileName() + "\u001B[0m");
        }
    }

