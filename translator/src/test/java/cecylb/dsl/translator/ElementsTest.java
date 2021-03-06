package cecylb.dsl.translator;

import cecylb.dsl.translator.impl.TemplateProcessorImpl;
import cecylb.dsl.translator.impl.TranslatorImpl;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ElementsTest {

    @Test
    public void ttrTest() throws Exception {
        System.out.println("[TTr T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new TTr{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.8x, 1.5y, \"ttr #n\"; amount: 1; spacing: 4; }connections{ (ttr)Q -- (ttr #self)T; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }
    @Test
    public void dcTest() throws Exception {
        System.out.println("[DC T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new DC{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"Dc #n\"; inputs:  3; amount: 2; spacing: 4; }connections{(Dc)oa -- (Dc)ia; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }
    @Test
    public void ujktrTest() throws Exception {
        System.out.println("[UJKTr T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new UJKTr{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"JK #n\"; inputs:  3; amount: 2; spacing: 4; }connections{(JK)Q -- (JK)S; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }
    @Test
    public void udvtrTest() throws Exception {
        System.out.println("[UDVTr T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new UDVTr{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DV #n\"; amount: 2; spacing: 4; }connections{(DV)Q -- (DV)S; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void dtrTest() throws Exception {
        System.out.println("[DTr T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new DTr{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DT #n\"; inputs:  3; amount: 2; spacing: 4; }connections{(DT)Q -- (DT)S; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void dtrSelfTest() throws Exception {
        System.out.println("[DTr SELF T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new DTr{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"DT #n\"; inputs:  3; amount: 2; spacing: 4; }connections{(DT)Qn -- (DT #self)S; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void mxSelfTest() throws Exception {
        System.out.println("[MX SELF T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new MX{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"Mx #n\"; inputs:  3; amount: 2; spacing: 4; }connections{(Mx)Q -- (Mx #self)sa; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void mxTest() throws Exception {
        System.out.println("[MX T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new MX{ position: 0.0x, 0.0y; size: 0.5x, 0.6y; label: 1.5x, 1.5y, \"Mx #n\"; inputs:  3; amount: 2; spacing: 4; }connections{(Mx)Q -- (Mx)sa; };".getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    private InputStream resource(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}
