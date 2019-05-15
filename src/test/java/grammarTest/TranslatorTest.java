package grammarTest;

import org.junit.Test;
import translator.LexerModule;
import translator.ParserModule;
import translator.Translator;
import translator.impl.TranslatorImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TranslatorTest {

   /* @Test
    public void test1() throws Exception {
        final Translator translator = new TranslatorImpl(new LexerModule(), new ParserModule());
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new TTr{ size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"TtR #n\"; inputs:  10; amount: 1; spacing: 4; }connections{ (TtR)Qn o- (TtR)S; } code: tikzpicture, \" \\def\\p{0} \\foreach \\min{0,...,\\N} { \\draw [*-] ($ (Tt R\\m.Qn) + (5mm,-0.8mm) $) -- ($ (Tt R\\m.Qn) + (5mm,2.9) $) -- ($ (Tt R\\m.R) + (-5mm,1.5) $) -- ($ (Tt R\\m.R) + (-5mm,0) $) --(Tt R\\m.R); \\global\\let\\p\\m}\";".getBytes()),
                new ByteArrayOutputStream()
        );
    }*/
    @Test
    public void test2() throws Exception {
        final Translator translator = new TranslatorImpl(new LexerModule(), new ParserModule());
        translator.translate(
                new ByteArrayInputStream("a4, landscape; new DC{ size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"Dc #n\"; inputs:  3; amount: 1; spacing: 4; }connections{};".getBytes()),
                new ByteArrayOutputStream()
        );
    }
}
