package com.example.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.apache.tika.sax.ToTextContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * test tika
 *
 */
@Slf4j
public class App 
{
    private static final String inputFilePath = "/tmp/input.pdf";

    public static void main( String[] args ) throws Exception
    {
        System.out.println( "=== Test Tika ===" );

        log.info("inputFilePath = "+inputFilePath);
        InputStream is = new FileInputStream(inputFilePath);
        Metadata metadata = new Metadata();
        TikaInputStream tikaStream = TikaInputStream.get(is);

        ContentHandler handler = new BodyContentHandler(100*1024*1024);
        handler = new ToXMLContentHandler();

        Parser parser = new AutoDetectParser();
        ParseContext context = new ParseContext();
        try {
            parser.parse(tikaStream, handler, metadata, context);
            System.out.println("handler = "+handler.toString());
            System.out.println("metadata = "+metadata.toString());
        } catch (Exception e) {
            System.out.println("Failed to parse contents: "+e.getMessage());
        } finally {
            try {
                tikaStream.close();
                is.close();
            } catch (Exception ie) {
                // ignore
                System.out.println("failed to close tika stream: "+ ie.getMessage());
            }
        }
    }
}
