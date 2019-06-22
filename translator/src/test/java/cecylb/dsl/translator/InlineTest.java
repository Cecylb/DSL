package cecylb.dsl.translator;

import cecylb.dsl.translator.impl.TemplateProcessorImpl;
import cecylb.dsl.translator.impl.TranslatorImpl;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class InlineTest {

    @Test
    public void documentclassInlineTest() throws Exception {
        System.out.println("[DOCUMENTCLASS INLINE T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream(("a4, landscape; new TTr{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"TtR #n\"; amount: 3; spacing: 4; }connections{ (TtR)Qn o- (TtR)S; } code: documentclass, ' \\def\\p{0} \\foreach \\min{0,...,\\N} { \\draw [*-] ($ (Tt R\\m.Qn) + (5mm,-0.8mm) $) -- ($ (Tt R\\m.Qn) + (5mm,2.9) $) -- ($ (Tt R\\m.R) + (-5mm,1.5) $) -- ($ (Tt R\\m.R) + (-5mm,0) $) --(Tt R\\m.R); \\global\\let\\p\\m} ';").getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void declareshapeInlineTest() throws Exception {
        System.out.println("[DECLARESHAPE INLINE T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream(("a4, landscape; new TTr{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"TtR #n\"; amount: 3; spacing: 4; }connections{ (TtR)Qn o- (TtR)S; } " +
                        "code: declareshape, ' \\pgfdeclareshape{DV}{\n" +
                        "\\savedanchor\\northeast{\n" +
                        "\\pgfmathsetlength\\pgf@x{\\pgfshapeminwidth}\n" +
                        "\\pgfmathsetlength\\pgf@y{\\pgfshapeminheight}\n" +
                        "\\pgf@x=0.5\\pgf@x\n" +
                        "\\pgf@y=0.6\\pgf@y\n" +
                        "}\n" +
                        "\\savedanchor\\southwest{\n" +
                        "\\pgfmathsetlength\\pgf@x{\\pgfshapeminwidth}\n" +
                        "\\pgfmathsetlength\\pgf@y{\\pgfshapeminheight}\n" +
                        "\\pgf@x=-0.5\\pgf@x\n" +
                        "\\pgf@y=-0.6\\pgf@y\n" +
                        "}\n" +
                        "\\inheritanchorborder[from=rectangle]\n" +
                        "\\anchor{center}{\\pgfpointorigin}\n" +
                        "\\anchor{north}{\\northeast \\pgf@x=0pt}\n" +
                        "\\anchor{east}{\\northeast \\pgf@y=0pt}\n" +
                        "\\anchor{south}{\\southwest \\pgf@x=0pt}\n" +
                        "\\anchor{west}{\\southwest \\pgf@y=0pt}\n" +
                        "\\anchor{north east}{\\northeast \\pgf@x=0pt}\n" +
                        "\\anchor{north west}{\\northeast \\pgf@x=-\\pgf@x}\n" +
                        "\\anchor{south west}{\\southwest}\n" +
                        "\\anchor{south east}{\\southwest \\pgf@x=-\\pgf@x}\n" +
                        "\\anchor{text}{\n" +
                        "\\northeast\n" +
                        "\\advance\\pgf@x by -1.5\\wd\\pgfnodeparttextbox\n" +
                        "\\advance\\pgf@y by -1.5\\ht\\pgfnodeparttextbox\n" +
                        "\\advance\\pgf@y by +.5\\dp\\pgfnodeparttextbox\n" +
                        "}\n" +
                        "\\anchor{D}{\n" +
                        "\\pgf@process{\\northeast}\n" +
                        "\\pgf@x=-1.0\\pgf@x\n" +
                        "\\pgf@y=0.6\\pgf@y\n" +
                        "}\n" +
                        "\\anchor{V}{\n" +
                        "\\pgf@process{\\northeast}\n" +
                        "\\pgf@x=-1.0\\pgf@x\n" +
                        "\\pgf@y=-0.5999999999999999\\pgf@y\n" +
                        "}\n" +
                        "\\anchor{Q}{\n" +
                        "\\pgf@process{\\northeast}\n" +
                        "\\pgf@x=1.0\\pgf@x\n" +
                        "\\pgf@y=0.4\\pgf@y\n" +
                        "}\n" +
                        "\\anchor{Qn}{\n" +
                        "\\pgf@process{\\northeast}\n" +
                        "\\pgf@x=1.0\\pgf@x\n" +
                        "\\pgf@y=-0.3999999999999999\\pgf@y\n" +
                        "}\n" +
                        "\\backgroundpath{\n" +
                        "\\pgfpathrectanglecorners{\\southwest \\pgf@x=1.0\\pgf@x \\pgf@y=1.2\\pgf@y}{\\northeast \\pgf@x=1.0\\pgf@x \\pgf@y=1.2\\pgf@y}\n" +
                        "\\pgfpathrectanglecorners{\\southwest \\pgf@x=0.5\\pgf@x \\pgf@y=1.2\\pgf@y}{\\northeast \\pgf@x=0.5\\pgf@x \\pgf@y=1.2\\pgf@y}\n" +
                        "\\pgfclosepath\n" +
                        "\\begingroup\n" +
                        "\\tikzset{flip flop/port labels}\n" +
                        "\\tikz@textfont\n" +
                        "\\pgf@anchor@DV@D\n" +
                        "\\pgftext[left,base,at={\\pgfpoint{\\pgf@x}{\\pgf@y}},x=\\pgfshapeinnerxsep]{\\raisebox{-0.75ex}{D}\n" +
                        "}\n" +
                        "\\pgf@anchor@DV@V\n" +
                        "\\pgftext[left,base,at={\\pgfpoint{\\pgf@x}{\\pgf@y}},x=\\pgfshapeinnerxsep]{\\raisebox{-0.75ex}{V}\n" +
                        "}\n" +
                        "\\pgf@anchor@DV@Q\n" +
                        "\\pgftext[right,base,at={\\pgfpoint{\\pgf@x}{\\pgf@y}},x=\\pgfshapeinnerxsep]{\\raisebox{-0.75ex}{Q }\n" +
                        "}\n" +
                        "\\pgf@anchor@DV@Qn\n" +
                        "\\pgftext[right,base,at={\\pgfpoint{\\pgf@x}{\\pgf@y}},x=\\pgfshapeinnerxsep]{\\raisebox{-0.75ex}{Qn }\n" +
                        "}\n" +
                        "\\endgroup\n" +
                        "}\n" +
                        "}\n" +
                        "\\tikzset{add font/.code={\\expandafter\\def\\expandafter\\tikz@textfont\\expandafter{\\tikz@textfont#1}\n" +
                        "}\n" +
                        "}\n" +
                        "\\tikzset{flip flop/port labels/.style={font=\\sffamily\\scriptsize}}\n" +
                        "\\tikzset{every DV node/.style={draw,minimum width=2cm,minimum\n" +
                        "height=2.828427125cm,very thick,inner sep=1mm,outer sep=0pt,cap=squar,add\n" +
                        "font=\\sffamily\n" +
                        "}\n" +
                        "}';").getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    @Test
    public void tikzpictureInlineTest() throws Exception {
        System.out.println("[TIKZPICTURE INLINE T E S T . . .]");
        final Translator translator = new TranslatorImpl(resource("grammar.tdf"), new TemplateProcessorImpl());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        translator.translate(
                new ByteArrayInputStream(("a4, landscape; new TTr{ position: 0.0x, 0.0y; size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"TtR #n\"; amount: 3; spacing: 4; }connections{ (TtR)Qn o- (TtR)S; } " +
                        "code: tikzpicture, ' \\def\\p{0} \\foreach \\min{0,...,\\N} { \\draw [*-] ($ (Tt R\\m.Qn) + (5mm,-0.8mm) $) -- ($ (Tt R\\m.Qn) + (5mm,2.9) $) -- ($ (Tt R\\m.R) + (-5mm,1.5) $) -- ($ (Tt R\\m.R) + (-5mm,0) $) --(Tt R\\m.R); \\global\\let\\p\\m}';").getBytes()),
                byteArrayOutputStream
        );
        System.out.println(byteArrayOutputStream.toString());
    }

    private InputStream resource(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}
