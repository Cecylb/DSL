package cecylb.dsl.translator;

import io.github.therealmone.tdf4j.generator.LexerGenerator;
import io.github.therealmone.tdf4j.generator.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.lexer.config.AbstractLexerModule;
import io.github.therealmone.tdf4j.parser.Parser;
import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;
import io.github.therealmone.tdf4j.parser.model.ast.AST;
import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;
import io.github.therealmone.tdf4j.parser.model.ast.ASTRoot;
import org.junit.Test;

public class GrammarTest{
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
                            optional(nt("desc")),
                            repeat(nt("object")),
                            nt("connections")
                    ); // Recieves the String, it can be a code that will be completed after the top part

            environment().packages().dependencies(); // Prohibited: left recursion, {[...]} (endless loop)
            //2
            prod("desc")
                    .is(
                            nt("format"),
                            t("CMA"),
                            repeat(t("LET")),
                            t("DIV")
                    );
            //5
            prod("format")
                    .is(
                            repeat(or(t("LET"), t("NUM")))
                    );
            //3
            prod("object")
                    .is(
                            inline(
                                    "int x = 0;"
                            ),
                            t("NEW"),
                            t("OBJ"),
                            t("BFL"),
                            optional(nt("size")),
                            optional(nt("label")),
                            optional(nt("inputs")),
                            optional(nt("amount")),
                            optional(nt("spacing")),
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
                            or(t("OBJ"), repeat(t("LET"))),
                            optional(nt("property")),
                            t("QUT"),
                            t("DIV"),
                            inline("System.out.println(\"X = \" + x);")
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
                            inline(
                                    "x = 1;"
                            ),
                            t("CMA"),
                            oneOf(t("SCL"), t("POS"), nt("coordinate"))
                    );
            //6
            prod("coordinate")
                    .is(
                            t("DBL"),
                            t("CRD")
                    );
            //4
            prod("amount")
                    .is(
                            t("AMT"),
                            t("COL"),
                            t("NUM"),
                            t("DIV")
                    );
            //4
            prod("spacing")
                    .is(
                            t("SPC"),
                            t("COL"),
                            t("NUM"),
                            t("DIV")
                    );
            //4
            prod("inputs")
                    .is(
                            t("PRT"),
                            t("COL"),
                            or(t("NUM"), nt("ports")),  // H E R E I S T H E P R O B L E M
                            optional(t("DIV"))
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
            //3
            prod("connections")
                    .is(
                            t("CON"),
                            t("BFL"),
                            repeat(nt("connection")),
                            t("BFR")
                    );
            //4
            prod("connection")
                    .is(
                            t("BRL"),
                            t("OBJ"),
                            optional(nt("property")),
                            t("BRR"),
                            or(repeat(t("LET")),t("NUM")),
                            oneOf(t("ARW"), t("LIN"), t("INV"), t("DTL")),
                            t("BRL"),
                            t("OBJ"),
                            optional(nt("property")),
                            t("BRR"),
                            or(repeat(t("LET")),t("NUM")),
                            t("DIV")
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
        System.out.println(parser.parse(lexer.stream( "a4, horizontal; new DC{ size: 1.0x, 1.0y; label: 0.5x, 0.5y, \"DC #n\"; inputs:  10; amount: 1; spacing: 4; } new ARSTr { size: medium; label: 0.5x, 0.5y, \"ARSTr #n\"; amount: 1; spacing: 3; } connections{ (DC)0 -o (ARSTr)C; (DC)15 -> (ARSTr)S; }")));

        AST ast = parser.parse(lexer.stream( "a4, horizontal; new DC{ size: 1.0x, 1.0y; label: 0.5x, 0.5y, \"DC #n\"; inputs:  10; amount: 1; spacing: 4; } new ARSTr { size: medium; label: 0.5x, 0.5y, \"ARSTr #n\"; amount: 1; spacing: 3; } connections{ (DC)0 -o (ARSTr)C; (DC)15 -> (ARSTr)S; }"));
        final ASTElement element = ast.onCursor();
        final ASTRoot root = ast.getRoot();
        for (final ASTElement child : root.children()) {
            if (child.isNode()) {
                child.asNode().children();
            } else if (child.isLeaf()) {
                child.asLeaf().token();
                System.out.println(child.asLeaf().token());
            }
        }
        System.out.println("--------------------------------------------");
        ast.moveCursor(cursor -> {
            cursor.setValue(cursor.asRoot().children().get(0));
            System.out.println("1" + cursor.getValue());
        }).onCursor();
        System.out.println("----------------------------------------------");
        System.out.println(ast.onCursor().asNode().children().get(0).asNode().children().get(1).asLeaf());
    }
}

/*        Parser parser = new ParserGenerator().cecylb.dsl.translator.generate(new AbstractParserModule() {
            public void configure() {
                prod("new") // identifire - the name of production
                        .is( // here will be all the methods
                                optional(t("name")), // recieves the Element
                                repeat(), //
                                repetition(t("A"), 1), // It's necessary to say how many times it's repeating
                                or(t("A"), t("B")), // Recieves only 2 cecylb.dsl.translator.builder.objects.elements, it's necessary to describe both
                                oneOf(),
                                group(),
                                t("A"),
                                nt("A"),
                                except() // UNFINISHED, can accept anything, but "A"
                        ).inline(); // Recieves the String, it can be a code that will be completed after the top part
                environment().packages().dependencies(); // Prohibited: left recursion, {[...]} (endless loop)
            }
        }); */