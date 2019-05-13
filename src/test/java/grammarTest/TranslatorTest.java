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
                new ByteArrayInputStream("a4, landscape; new TTr{ size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"TtR #n\"; inputs:  10; amount: 1; spacing: 4; }connections{ (TTr)Qn o- (TTr)S; } code: group, \" \\def\\p{0} \\foreach \\min{0,...,\\N} { \\draw [*-] ($ (TTS\\m.Qn) + (5mm,-0.8mm) $) -- ($ (TTS\\m.Qn) + (5mm,2.9) $) -- ($ (TTS\\m.D) + (-5mm,1.5) $) -- ($ (TTS\\m.D) + (-5mm,0) $) --(TTS\\m.D); \\global\\let\\p\\m}\";".getBytes()),
                new ByteArrayOutputStream()
        );
    }
}
