package grammarTest.propTest;

import io.github.therealmone.tdf4j.generator.impl.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.lexer.LexerFactory;
import io.github.therealmone.tdf4j.lexer.config.AbstractLexerModule;
import io.github.therealmone.tdf4j.parser.Parser;
import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;
import org.junit.Test;

public class SizeTest{
    Lexer lexer = new LexerFactory().withModule(new AbstractLexerModule() {
        public void configure() {
            tokenize("BSL").pattern("^\\[$").priority(1); // int max
            tokenize("BSR").pattern("^\\]$").priority(1); // priority-  необзяательный параметр
            tokenize("BFL").pattern("^\\{$").priority(1); // По умолчанию приоритет = 0
            tokenize("BFR").pattern("^\\}$").priority(1);
            tokenize("BRL").pattern("^\\($").priority(1);
            tokenize("BRR").pattern("^\\)$").priority(1);
            tokenize("COL").pattern("^\\:$").priority(1);
            tokenize("QUT").pattern("^\"$").priority(1);
            tokenize("GRD").pattern("^\\#$").priority(1);
            tokenize("DIV").pattern("^\\;$").priority(1);
            tokenize("CMA").pattern("^\\,$").priority(1);
            tokenize("DOT").pattern("^\\.$").priority(1);
            tokenize("EQL").pattern("^\\=$").priority(1);
            tokenize("COM").pattern("^\\//$").priority(1);

            tokenize("LIN").pattern("^--$").priority(1);
            tokenize("INV").pattern("^(o-)|(-o)$").priority(1);
            tokenize("ARW").pattern("^(->)|(<-)$").priority(1);
            tokenize("DTL").pattern("^(\\*-)|(-\\*)$").priority(1);

            tokenize("OBJ").pattern("^(AC|ARSTr|CTTr|DC|DTr|LDC|MX|PDC|RDC|SC|ShRSTr|SRSTr|TTr|UJKTr)$").priority(2);

            tokenize("NEW").pattern("^new$").priority(2);
            tokenize("SIZ").pattern("^size$").priority(2);
            tokenize("LAB").pattern("^label$").priority(2);
            tokenize("FON").pattern("^font$").priority(2);
            tokenize("PRT").pattern("^inputs$").priority(2);
            tokenize("SPC").pattern("^spacing$").priority(2);
            tokenize("AMT").pattern("^amount").priority(2);
            tokenize("MTH").pattern("^[\\+\\-\\/\\*]$").priority(2);
            tokenize("COP").pattern("^[<>]|(<=)|(>=)|(==)|(!=)$").priority(2);
            tokenize("CON").pattern("^connections$").priority(2);
            tokenize("LET").pattern("^([a-z]+[0-1]*)|([A-Z]+[0-1]*)$").priority(1);
            tokenize("NUM").pattern("^(0|([1-9][0-9]*))$").priority(2);
            tokenize("DBL").pattern("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$").priority(1);
            tokenize("SCL").pattern("^(small|medium|large)$").priority(2);
            tokenize("POS").pattern("^(top|middle|bottom)$").priority(2);
            tokenize("CRD").pattern("^(x|y)$").priority(2);
            tokenize("CUR").pattern("^n$").priority(2);
            tokenize("IDN").pattern("^id$").priority(2);

            tokenize("single_line_comment").pattern("//.*(\n|\r|\r\n|\n\r)").hidden(true);
            tokenize("multi_line_comment").pattern("/\\*[^(\\*/)]*\\*/").hidden(true);

            tokenize("WS").pattern("\\s|\\n|\\r").priority(Integer.MAX_VALUE).hidden(true);
        }
    });
    Parser parser = new ParserGenerator().generate(new AbstractParserModule() {
        public void configure() {
            //1
            prod("lang") // identifire - the name of production
                    .is(
                            repeat(nt("object"))
                    );
            //3
            prod("object")
                    .is(
                            t("NEW"),
                            t("OBJ"),
                            t("BFL"),
                            optional(nt("size")),
                            t("BFR")
                    );
            //4
            prod("size")
                    .is(
                            t("SIZ"),
                            t("COL"),
                            or(t("SCL"), nt("coordinates")),
                            t("DIV")
                    );
            //5
            prod("coordinates")
                    .is(
                            oneOf(t("SCL"), t("POS"), nt("coordinate")),
                            t("CMA"),
                            oneOf(t("SCL"), t("POS"), nt("coordinate")),
                            optional(t("CMA"))
                    );
            //6
            prod("coordinate")
                    .is(
                            t("DBL"),
                            t("CRD")
                    );
            //5
            prod("ports")
                    .is(
                            t("BSL"),
                            repeat(nt("port")),
                            t("BSR"),
                            t("DIV")
                    );
            //6
            prod("port")
                    .is(
                            t("NUM"),
                            optional(t("CMA"))
                    );
        }
    });
    @Test
    public void Test(){
        System.out.printf("Test 1: ");
        System.out.println(parser.parse(lexer.stream( "new DC{ size: 1.0x, 1.0y;} new ARST { size: 1.0x, 1.0y;}")));
        System.out.printf("Test 2: ");
        System.out.println(parser.parse(lexer.stream( "new DC{ size: 1.0x, 1.0y;} new ARST { size: large;}")));
        System.out.printf("Test 3: ");
        System.out.println(parser.parse(lexer.stream( "new DC{ size: 1.0x, 1.0y;} new ARST { size: 100.09x, 0.99y;}")));

    }
}
