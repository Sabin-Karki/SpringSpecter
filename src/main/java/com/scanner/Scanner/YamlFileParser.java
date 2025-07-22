
package com.scanner.Scanner;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class YamlFileParser {
 public Map<String, String> parse(Path filePath) throws IOException {
  Map<String, String> flattenedMap = new HashMap<>(); // the final map which will be returned in key-value pair
  // the reason the type in signature is MAP<String,String> is cause the return will be in the key-value where both will be string...after we flatten the yml which is in more tree like structure

  Yaml yaml = new Yaml();
  try (InputStream inputStream = Files.newInputStream(filePath)) {
   Map<String, Object> rawData = yaml.load(inputStream); // store it in a variable right? ?

   if (rawData != null) {
    flattenMap("", rawData, flattenedMap);
   }
  }
  return flattenedMap;
 }

 private void flattenMap(String prefix, Map<String, Object> source, Map<String, String> destination) {
  for (Map.Entry<String, Object> entry : source.entrySet()) {
   String key = entry.getKey();
   Object value = entry.getValue();
   String newPrefix = prefix.isEmpty() ? key : prefix + "." + key;

   if (value instanceof Map) {
    flattenMap(newPrefix, (Map<String, Object>) value, destination);
   } else {
    destination.put(newPrefix, value.toString());
   }
  }
 }
}

