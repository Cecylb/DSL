package grammarTest;

import org.junit.Test;
import translator.LexerModule;
import translator.ParserModule;
import translator.Translator;
import translator.impl.TranslatorImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TranslatorTest {

    @Test
    public void test() throws Exception {
        final Translator translator = new TranslatorImpl(new LexerModule(), new ParserModule());
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new TTr{ size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"TtR #n\"; inputs:  10; amount: 1; spacing: 4; }connections{ (TTr)Qn o- (TTr)S; }".getBytes()),
                new ByteArrayOutputStream()
        );
    }
}
