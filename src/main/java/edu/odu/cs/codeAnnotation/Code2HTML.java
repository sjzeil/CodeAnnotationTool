/**
 * 
 */
package edu.odu.cs.codeAnnotation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * main driver for code2html program.
 * 
 * @author zeil
 *
 */
public class Code2HTML {

    private static BufferedReader input;
    private static BufferedWriter output;
    private static String graphicsPath;
    private static String cssFile;
    private static boolean embedcss;
    private static String footer;
    private static String title;
    
    private static String defaultCSS = 
            ".cppcode      {font-size: 12pt; font-family: monospace; white-space: nowrap; background: #C0C0C0}\n\n" +
            ".directive    {font-weight: bold; color: #7F0000; }\n\n" +
            ".quote        {font-style: italic; color: #00007F;}\n\n" +
            ".comment      {font-style: italic; color: #007F00;}\n\n" +
            ".highlighted1  {background: #ffff99}\n\n" +
            ".highlighted2  {background: #66ccff}\n\n" +
            ".highlighted3  {background: #cc33ff}\n\n" +
            ".keyword      {font-weight: bold;}";

    
    
    private static void processArgs(String[] args) throws IOException
    {
        int argsNum = 0;
        String inputFileName = null;
        String outputFileName = null;
        title = null;
        footer = "";
        embedcss = false;
        graphicsPath = "./";
        cssFile = null;
        while (argsNum < args.length) {
           if (args[argsNum].equals("-css")) {
              ++argsNum;
              cssFile = args[argsNum];
              ++argsNum;
           }
           else if (args[argsNum].equals("-embedcss")) {
              ++argsNum;
              embedcss = true;
           }
           else if (args[argsNum].equals("-graphics")) {
              ++argsNum;
              graphicsPath = args[argsNum];
              ++argsNum;
           }
           else if (args[argsNum].equals("-footer")) {
              ++argsNum;
              footer = args[argsNum];
              ++argsNum;
           }
           else if (args[argsNum].equals("-title")) {
              ++argsNum;
              title = args[argsNum];
              ++argsNum;
           }
           else if (inputFileName == null) {
              inputFileName = args[argsNum];
              if (title == null) {
                 title = inputFileName;
                 if (title.contains("/")) {
                     int k = title.lastIndexOf('/');
                     title = title.substring(k+1);
                 }
              }
              ++argsNum;
           } 
           else {
              outputFileName = args[argsNum];
              ++argsNum;
           }
        }
        if (outputFileName == null) {
           outputFileName = inputFileName + ".html";
        }

        input = new BufferedReader(new FileReader(inputFileName));
        output = new BufferedWriter(new FileWriter(outputFileName)); 

    }
    
    
    public static void generateHTML() throws IOException
    {
        CppJavaScanner scanner = new CppJavaScanner(input);
        scanner.output = output;
        scanner.graphicsPath = graphicsPath;
        output.write ("<html><head>\n");
        output.write("<title>" + title + "</title>\n");
        if (embedcss || cssFile == null) {
            output.write ("<style type=\"text/css\">\n<!--");
            if (cssFile == null) {
                output.write(defaultCSS);
            } else {
                BufferedReader in = new BufferedReader(new FileReader(cssFile));
                String line = in.readLine();
                while (line != null) {
                    output.write(line);
                    output.write("\n");
                    line = in.readLine();
                }
                in.close();
            }
            output.write ("\n-->\n</style>\n");
        } else {
            output.write ("<link REL='stylesheet' href='" + cssFile + "' type='text/css'>\n");
        }
        output.write ("</head><body>\n");
        output.write("<h1>" + title + "</h1>\n");
        output.write ("<div class='cppcode'>\n");
        scanner.yylex();
        output.write ("</div>\n");
        output.write (footer);
        output.write ("</body></html>\n");
        output.close();
    }
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        processArgs(args);
        generateHTML();
    }

}
