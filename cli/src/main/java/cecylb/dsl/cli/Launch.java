package cecylb.dsl.cli;

import cecylb.dsl.translator.impl.TemplateProcessorImpl;
import cecylb.dsl.translator.impl.TranslatorImpl;

import java.io.*;

public class Launch {

    public static void main(String[] args) throws Exception {
        File from = new File(args[0]);
        File to = new File(args[1]);
        if(!to.exists()){
            to.createNewFile();
        }
        try (
                final InputStream input = new BufferedInputStream(new FileInputStream(from));
                final OutputStream output = new BufferedOutputStream(new FileOutputStream(to))
        ){
            new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl()).translate(input, output);
            output.flush();
        }
    }
    private static InputStream resource(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}
