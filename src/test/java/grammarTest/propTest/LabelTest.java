package grammarTest.propTest;

import io.github.therealmone.tdf4j.generator.LexerGenerator;
import io.github.therealmone.tdf4j.generator.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.lexer.config.AbstractLexerModule;
import io.github.therealmone.tdf4j.parser.Parser;
import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;
import org.junit.Test;

public class LabelTest{
    Lexer lexer = LexerGenerator.newInstance().generate(new AbstractLexerModule() {
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


    Parser parser = ParserGenerator.newInstance().generate(new AbstractParserModule() {
        public void configure() {
            //1
            prod("lang") // identifire - the name of production
                    .is( // here will be all the methods
                            repeat(nt("object"))
                    );
            //3
            prod("object")
                    .is(
                            t("NEW"),
                            t("OBJ"),
                            t("BFL"),
                            optional(nt("label")),
                            t("BFR")
                    );
            //4
            prod("label")
                    .is(
                            t("LAB"),
                            t("COL"),
                            optional(or(t("POS"), nt("coordinates"))),
                            t("CMA"),
                            t("QUT"),
                            repeat(t("LET")),
                            optional(nt("property")),
                            t("QUT"),
                            t("DIV")
                    );
            //5
            prod("coordinates")
                    .is(
                            oneOf(t("SCL"), t("POS"), nt("coordinate")),
                            t("CMA"),
                            oneOf(t("SCL"), t("POS"), nt("coordinate"))
                    );
            //6
            prod("coordinate")
                    .is(
                            t("DBL"),
                            t("CRD")
                    );
            //5
            prod("property")
                    .is(
                            t("GRD"),
                            oneOf(t("IDN"), t("NUM"), t("CUR")) // U N F I N I S H E D
                    );
        }
    });
    @Test
    public void Test(){
        System.out.println("Test 1: ");
        System.out.println(parser.parse(lexer.stream( "new DC{label: 0.5x, 0.5y, \"DC #n\";}")));
        System.out.println("Test 2: ");
        System.out.println(parser.parse(lexer.stream( "new DC{label: middle, \"DC #n\";}")));
        System.out.println("Test 3: ");
        System.out.println(parser.parse(lexer.stream( "new DC{label: 0.5x, 0.5y, \"DC #n\";}")));

    }
}
