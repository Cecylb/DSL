package launch;

import data.Connection;
import data.Objects;
import data.Property;
import io.github.therealmone.tdf4j.generator.impl.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.lexer.LexerFactory;
import io.github.therealmone.tdf4j.lexer.config.AbstractLexerModule;
import io.github.therealmone.tdf4j.parser.Parser;
import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;

import java.util.LinkedList;
import java.util.Queue;

public class Grammar {

    Queue<Property> property = new LinkedList<>();
    Queue<Objects> objects = new LinkedList<>();
    Queue<Connection> connections = new LinkedList<>();

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
            //tokenize("GRD").pattern("^\\#$").priority(1);
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
            tokenize("SHS").pattern("^(a1|a2|a3|a4|a5)$").priority(2);
            tokenize("ORI").pattern("^(landscape|portrait)$").priority(2);

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
            tokenize("CUR").pattern("^#n$").priority(2);
            tokenize("IDN").pattern("^id$").priority(2);

            tokenize("single_line_comment").pattern("//.*(\n|\r|\r\n|\n\r)").hidden(true);
            tokenize("multi_line_comment").pattern("/\\*[^(\\*/)]*\\*/").hidden(true);

            tokenize("WS").pattern("\\s|\\n|\\r").priority(Integer.MAX_VALUE).hidden(true);
        }
    });

    Parser parser = new ParserGenerator().generate(new AbstractParserModule() {
        public void configure() {
            environment().packages("builder.PropBuilder",
                    "builder.ObjBuilder",
                    "builder.ConnBuilder")
                    .dependencies(
                            dependency(
                                    Queue.class, "property", property
                            ),
                            dependency(
                                    Queue.class, "objects", objects
                            ),
                            dependency(
                                    Queue.class, "connections", connections
                            )
                    ).code(
                    "PropBuilder propBuilder = new PropBuilder();\n" +
                            "ObjBuilder objBuilder = new ObjBuilder();\n" +
                            "ConnBuilder connBuilder = new ConnBuilder();\n" +
                            "double x;\n" +
                            "double y;\n" +
                            "double n;\n" +
                            "String name;\n"
            );
            //1
            prod("lang")
                    .is( // here will be all the methods
                            optional(nt("desc")),
                            repeat(nt("object")),
                            nt("connections")
                    );
            //2
            prod("desc")
                    .is(
                            t("SHS"),
                            inline(
                                    "propBuilder.setSheetSize(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("CMA"),
                            t("ORI"),
                            inline(
                                    "propBuilder.setOrientation(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("DIV"),
                            inline(
                                    "property.add(propBuilder.getProps());"
                            )
                    );
            //3
            prod("object")
                    .is(
                            inline(
                                    "x=0;\n" +
                                            "y=0;\n" +
                                            "n=0;\n" +
                                            "name=\"\";\n"
                            ),
                            t("NEW"),
                            t("OBJ"),
                            inline(
                                    "String name = ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value();\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("BFL"),
                            optional(nt("size")),
                            optional(nt("label")),
                            optional(nt("inputs")),
                            optional(nt("amount")),
                            optional(nt("spacing")),
                            t("BFR"),
                            inline(
                                    "objects.add(objBuilder.getResult(name));\n"
                            ));
            //4
            prod("label")
                    .is(
                            t("LAB"),
                            t("COL"),
                            optional(or(t("POS"), nt("coordinates"))),
                            inline(
                                    "switch(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().tag().value()){\n" +
                                            "case \"POS\": objBuilder.setLabelKW(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "break;\n" +
                                            "case \"COL\": objBuilder.setLabelCRD(x, y);\n" +
                                            "break; \n" +
                                            "}\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("CMA"),
                            t("QUT"),
                            repeat(t("LET")),
                            optional(nt("property")),
                            t("QUT"),
                            t("DIV")
                    );
            //4
            prod("size")
                    .is(
                            t("SIZ"),
                            t("COL"),
                            or(t("SCL"), nt("coordinates")),
                            inline(
                                    "switch(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().tag().value()){\n" +
                                            "case \"SCL\": objBuilder.setSizeKW(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "break;\n" +
                                            "case \"COL\": objBuilder.setSizeCRD(x, y);\n" +
                                            "break; \n" +
                                            "}\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
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
                            inline(
                                    "n = Double.parseDouble(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("CRD"), // Есть возможность увидеть нетерминал, если обратиться к последнему ноду
                            inline(
                                    "switch(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value()){\n" +
                                            "case \"x\": x = n;\n" +
                                            "break;\n" +
                                            "case \"y\": y = n;\n" +
                                            "break;\n" +
                                            "}\n"+
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            )
                    );
            //4
            prod("amount")
                    .is(
                            t("AMT"),
                            t("COL"),
                            t("NUM"),
                            inline(
                                    "objBuilder.setAmount(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("DIV")
                    );
            //4
            prod("spacing")
                    .is(
                            t("SPC"),
                            t("COL"),
                            t("NUM"),
                            inline(
                                    "objBuilder.setSpacing(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("DIV")
                    );
            //4
            prod("inputs")
                    .is(
                            t("PRT"),
                            t("COL"),
                            or(t("NUM"), nt("ports")),
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
                            inline(
                                    "connBuilder.setObjName1(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            optional(nt("property")),
                            t("BRR"),
                            or(repeat(t("LET")),t("NUM")),
                            inline(
                                    "connBuilder.setPort1(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            oneOf(t("ARW"), t("LIN"), t("INV"), t("DTL")),
                            inline(
                                    "connBuilder.setLineType(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("BRL"),
                            t("OBJ"),
                            inline(
                                    "connBuilder.setObjName2(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            optional(nt("property")),
                            t("BRR"),
                            or(repeat(t("LET")),t("NUM")),
                            inline(
                                    "connBuilder.setPort2(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                            "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("DIV"),
                            inline(
                                    "connections.add(connBuilder.getConnection());\n"
                            )
                    );
            //5
            prod("property")
                    .is(
                            oneOf(t("IDN"), t("NUM"), t("CUR"))
                    );
        }
    });

    public  Lexer getLexer(){
        return lexer;
    }
    public Parser getParser(){
        return parser;
    }
    public Queue<Property> getProperty(){
        return property;
    }
    public Queue<Objects> getObjects(){
        return objects;
    }
    public Queue<Connection> getConnections(){
        return connections;
    }
}
