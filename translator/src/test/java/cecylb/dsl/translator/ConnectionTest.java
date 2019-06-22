package cecylb.dsl.translator;

import cecylb.dsl.translator.impl.TemplateProcessorImpl;
import cecylb.dsl.translator.impl.TranslatorImpl;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ConnectionTest {

    @Test
    public void connection1Test() throws Exception {
        System.out.println("[CONNECTION 1 T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new UDVTr{ position: 0.0x, 15.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DV #n\"; amount: 1; spacing: 4;} new DTr{ position: 10.0x, 5.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DT #n\"; amount: 1; spacing: 4; }connections{(DT)Q -- (DV)D; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void connection2Test() throws Exception {
        System.out.println("[CONNECTION 2 T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new DTr{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DT #n\"; inputs:  3; amount: 2; spacing: 4; }connections{(DT)Qn -- (DT #self)S; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }


    @Test
    public void connection3Test() throws Exception {
        System.out.println("[CONNECTION 3 T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new UDVTr{ position: 0.0x, 15.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DV #n\"; inputs:  3; amount: 2; spacing: 4;} new DTr{ position: 10.0x, 15.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DT #n\"; amount: 2; spacing: 4; }connections{(DV)Q -- (DT)S; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void connection4Test() throws Exception {
        System.out.println("[CONNECTION 4 T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new UDVTr{ position: 0.0x, 10.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DV #n\"; amount: 1; spacing: 4;} new DTr{ position: 10.0x, 15.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DT #n\"; amount: 1; spacing: 4; }connections{(DT)Q -- (DV)D; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    private InputStream resource(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}