package com.scanner.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class PomXmlParser {
    public Map<String,String> parsePom(Path filePath) throws IOException {
        // the goal is to basically flatten the pom.xml file ..into key value pair like how i want. . but i am looking to not flatten entire pom.xml ,but instead only <parent> and <dependencies> and i guess check for their version and scan the issue? is that what i shall do... hmm
//storing result in map
        Map<String, String> flattenedPom = new HashMap<>();
        try (InputStream input = Files.newInputStream(filePath)) {
            Document doc = Jsoup.parse(input, "UTF-8", "", Parser.xmlParser());
            Element versionElement = doc.selectFirst("parent > version"); //"In the whole document, find the first <parent> tag. Immediately inside it, find a <version> tag. Then give me the text content of that <version> tag. btw using element returns the entire XML strucutre type text ,not just the text
            if (versionElement != null) {
                String parentVersion = versionElement.text(); // this actualy gets the text content
                System.out.println(" VERSION : " + parentVersion);
                flattenedPom.put("parent.version", parentVersion);
            }
            Elements dependencyElement = doc.select("dependencies > dependency"); //An Elements object is essentially a List of Element objects. You can loop through it with a standard for-each loop.
            for (Element elements : dependencyElement) {
                String groupId = elements.selectFirst("groupId").text();
                String artifactId = elements.selectFirst("artifactId").text();
                String version = null;
                Element versionElementt = elements.selectFirst("version");

                if (versionElementt != null) {
                    version = versionElementt.text();
                }
                String mapKey = "dependency." + groupId + ":" + artifactId;
                flattenedPom.put(mapKey,version);
            }
        }

return flattenedPom;
    }
}
