package cecylb.dsl.translator;

import cecylb.dsl.translator.impl.TemplateProcessorImpl;
import cecylb.dsl.translator.impl.TranslatorImpl;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class LogicGatesTest{

    @Test
    public void andInlineTest() throws Exception {
        System.out.println("[AND T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape;\n new AND{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"AND #n\"; amount: 3; spacing: 4; }connections{ (AND)ab o- (AND)a; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void orTest() throws Exception {
        System.out.println("[OR T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new OR{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"OR #n\"; amount: 3; spacing: 4; }connections{ (OR)q o- (OR)a; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void notInlineTest() throws Exception {
        System.out.println("[NOT T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new NOT{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"NOT #n\"; amount: 3; spacing: 4; }connections{ (NOT)q o- (NOT)a; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void nandInlineTest() throws Exception {
        System.out.println("[NAND T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new NAND{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"NAND #n\"; amount: 3; spacing: 4; }connections{ (NAND)q o- (NAND)a; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void norInlineTest() throws Exception {
        System.out.println("[NOR T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new NOR{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"NOR #n\"; amount: 3; spacing: 4; }connections{ (NOR)q o- (NOR)a; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void exorInlineTest() throws Exception {
        System.out.println("[EXOR T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new EXOR{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"EXOR #n\"; amount: 3; spacing: 4; }connections{ (EXOR)q o- (EXOR)a; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }
    
    private InputStream resource(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}