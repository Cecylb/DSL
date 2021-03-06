package cecylb.dsl.translator;

import cecylb.dsl.translator.impl.TemplateProcessorImpl;
import cecylb.dsl.translator.impl.TranslatorImpl;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MultipleElementTest{

    @Test
    public void andInlineTest() throws Exception {
        System.out.println("[MX, DC, AND T E S T . . .]\n" +
                "Note: на 4 инпутах и более крашится. Расширить индексацию !!!");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream(("a4, landscape; " +
                        "new MX{ " +
                            "position: 0.0x, 15.0y; " +
                            "size: 0.5x, 0.5y; " +
                            "label: 1.5x, 1.5y, \"MX #n\"; " +
                            "inputs: 3;" +
                            "amount: 1; " +
                            "spacing: 4; " +
                        "}" +
                        "new DC{ " +
                            "position: 2.5x, 10.0y; " +
                            "size: 0.5x, 0.5y; " +
                            "label: 1.5x, 1.5y, \"DC #n\"; " +
                            "inputs: 3;" +
                            "amount: 1; " +
                            "spacing: 4; " +
                        "}" +
                        "new AND{ " +
                            "position: 10.0x, 12.5y; " +
                            "size: 0.5x, 0.5y; " +
                            "label: 1.5x, 1.5y, \"AND #n\"; " +
                            "amount: 2; " +
                            "spacing: 4; " +
                        "}" +
                        "connections{ " +
                            "(MX)Q -- (AND)A; " +
                            "(DC)oe -- (AND)B;" +
                            "(AND)Q -- (AND)B;" +
                        "};").getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    private InputStream resource(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}