package org.mcclone.tika;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.*;

/**
 * @author McClone
 */
public class TiKaUtil {

    public static String parseFile(File file) {
        Parser parser = new AutoDetectParser();
        InputStream input = null;
        try {
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            input = new FileInputStream(file);
            ContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);
            parser.parse(input, handler, metadata, context);
            for (String name : metadata.names()) {
                System.out.println(name + ":" + metadata.get(name));
            }
            System.out.println(handler.toString());
            return handler.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public static void main(String argt0[]) throws Exception {
        parseFile(new File("C:\\Users\\McClone\\Documents\\郑思达-Java开发工程师.doc"));
    }
}
