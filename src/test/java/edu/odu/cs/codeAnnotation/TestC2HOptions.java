package edu.odu.cs.codeAnnotation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;


public class TestC2HOptions {
    
    Path testResultsDir = Paths.get("build", "testData");
    Path testInputFile = testResultsDir.resolve("test.cpp");
    Path testOutputFile = testResultsDir.resolve("test.cpp.html");
    Path testOutputCSSFile = testResultsDir.resolve("code2html.css");

    @BeforeEach
    public void setup() throws IOException {
        if (Files.exists(testResultsDir)) {
            Files.walk(testResultsDir)
              .sorted(Comparator.reverseOrder())
              .map(Path::toFile)
              .forEach(File::delete);
        }
        Files.createDirectories(testResultsDir);
        Files.copy(Paths.get("src", "test", "data", "test.cpp"), testInputFile);
    }

    public String load(Path fileName) throws IOException {
        StringBuffer results = new StringBuffer();
        BufferedReader in = new BufferedReader(new FileReader(fileName.toFile()));
        String line = in.readLine();
        while (line != null) {
            results.append(line);
            results.append("\n");
            line = in.readLine();
        }
        in.close();
        return results.toString();
    }

    @Test
    public void testMain() throws Exception {
        String[] args = {testInputFile.toString(), testOutputFile.toString()};
        Code2HTML.main(args);
        String out = load(testOutputFile);
        assertTrue (out.contains("a line started by 8 blanks"));
        assertTrue (out.contains("<style"));
        assertTrue (out.contains("<title>test.cpp</title>"));
        assertTrue (out.contains("<h1>test.cpp</h1>"));
    }
    
    @Test
    public void testCSS() throws Exception {
        String[] args = {"-css", "other.css", testInputFile.toString(), testOutputFile.toString()};
        Code2HTML.main(args);
        String out = load(testOutputFile);
        assertTrue (out.contains("a line started by 8 blanks"));
        assertTrue (out.contains("other.css"));
    }

    @Test
    public void testEmbedCSS() throws Exception {
        String[] args = {"-css", Paths.get("src", "test", "data", "code2html.css").toString(), "-embedcss", testInputFile.toString(), testOutputFile.toString()};
        Code2HTML.main(args);
        String out = load(testOutputFile);
        assertTrue (out.contains("a line started by 8 blanks"));
        assertTrue (out.contains("monospace"));
        assertTrue (out.contains("blue"));
    }

    @Test
    public void testEmbedCSSDefault() throws Exception {
        String[] args = {"-embedcss", testInputFile.toString(), testOutputFile.toString()};
        Code2HTML.main(args);
        String out = load(testOutputFile);
        assertTrue (out.contains("a line started by 8 blanks"));
        assertTrue (out.contains("italic"));
        assertFalse (out.contains("blue"));
    }
    
    @Test
    public void testFooter() throws Exception {
        String[] args = {"-footer", "<p>something at the bottom</p>", testInputFile.toString(), testOutputFile.toString()};
        Code2HTML.main(args);
        String out = load(testOutputFile);
        assertTrue (out.contains("a line started by 8 blanks"));
        assertTrue (out.contains("<p>something at the bottom</p>"));
    }
    
    @Test
    public void testTitle() throws Exception {
        String[] args = {"-title", "alternate Title", testInputFile.toString(), testOutputFile.toString()};
        Code2HTML.main(args);
        String out = load(testOutputFile);
        assertTrue (out.contains("a line started by 8 blanks"));
        assertTrue (out.contains("<title>alternate Title</title>"));
        assertTrue (out.contains("<h1>alternate Title</h1>"));
    }
    
}
