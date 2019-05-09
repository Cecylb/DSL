package launch;

import builder.ConnBuilder;
import builder.ObjBuilder;
import builder.PropBuilder;
import data.Connection;
import builder.Crutch;
import data.Objects;
import data.Property;
import generate.Elements;
import generate.Properties;
import generate.TeXfields;
import io.github.therealmone.tdf4j.generator.impl.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.lexer.LexerFactory;
import io.github.therealmone.tdf4j.lexer.config.AbstractLexerModule;
import io.github.therealmone.tdf4j.parser.Parser;
import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;
import io.github.therealmone.tdf4j.parser.model.ast.AST;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class Launch {

    Queue<Property> property = new LinkedList<>();// построю во вставках
    Queue<Objects> objects = new LinkedList<>(); // заполню во вставках
    Queue<Connection> connections = new LinkedList<>(); // заполню во вставках
    PropBuilder propBuilder = new PropBuilder();
    ObjBuilder objBuilder = new ObjBuilder();
    ConnBuilder connBuilder = new ConnBuilder();
    AST ast;

    Crutch crutch = new Crutch(); // Ужасный и страшный костыль, за который мне очень стыдно v_v

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
            tokenize("CUR").pattern("^n$").priority(2);
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
                                   "builder.ConnBuilder",
                                   "builder.Crutch",
                                   "data.Property")
                    .dependencies(
                            dependency(
                                    Queue.class, "property", property
                            ),
                            dependency(
                                    Queue.class, "objects", objects
                            ),
                            dependency(
                                    Queue.class, "connections", connections
                            ),
                            dependency(
                                    PropBuilder.class, "propBuilder", propBuilder
                            ),
                            dependency(
                                    ObjBuilder.class, "objBuilder", objBuilder
                            ),
                            dependency(
                                    ConnBuilder.class, "connBuilder", connBuilder
                            ),
                            dependency(
                                    Crutch.class, "crutch", crutch
                            )
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
                                    "crutch.setX(0);\n" +
                                          "crutch.setY(0);\n" +
                                          "crutch.setN(0);\n" +
                                          "crutch.setSize(\"\");\n"
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
                                            "case \"POS\": objBuilder.setLabelKW(crutch.getSize());\n" +
                                            "break;\n" +
                                            "case \"coordinates\": objBuilder.setLabelCRD(crutch.getX(), crutch.getY());\n" +
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
                                            "case \"SCL\": objBuilder.setSizeKW(crutch.getSize());\n" +
                                            "break;\n" +
                                            "case \"coordinates\": objBuilder.setSizeCRD(crutch.getX(), crutch.getY());\n" +
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
                                    "crutch.setN(Double.parseDouble(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value()));\n" +
                                          "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                            ),
                            t("CRD"),
                            inline(
                                    "switch(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value()){\n" +
                                        "case \"x\": crutch.setX(crutch.getN());\n" +
                                        "break;\n" +
                                        "case \"y\": crutch.setY(crutch.getN());\n" +
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
                            repeat(t("LET")),
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
                            repeat(t("LET")),
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
                            t("DIV")
                            /*inline(
                                    "ConnBuilder conn = new ConnBuilder();\n" +
                                            "connections.add(conn.getConnections(ast.onCursor().asNode));\n"
                                            )*/
                    );
            //5
            prod("property")
                    .is(
                            t("GRD"),
                            oneOf(t("IDN"), t("NUM"), t("CUR"))
                    );
        }
    });
    @Test
    public void Test() {
        ast = parser.parse(lexer.stream("a4, landscape; new TTr{ size: 1.0x, 1.0y; label: 0.5x, 0.5y, \"TtR #n\"; inputs:  10; amount: 1; spacing: 4; }connections{ }"));
        File teXcode = new File("/etc/home/cecylb/Documents/TeX/Test/Test.tex");
        StringBuilder sb = new StringBuilder();
        TeXfields fields = new TeXfields(); // поля, необходимые для TeX, в которых нет значений
        //GATHERING PROPERTIES
        Properties prop = new Properties();
        sb.append(prop.teXproperty(property.peek()));
        sb.append(fields.teXmakeAletter());
        //GATHERING OBJECT INFORMATION
        for(Objects object: objects) {
            Elements elem = new Elements(object);
            sb.append(elem.teXobjN());
            sb.append(elem.teXsize());
            sb.append(fields.teXborder());
            sb.append(elem.teXlabelC());
            sb.append(elem.teXport());
            sb.append(fields.teXbackgroundpath());
            sb.append(elem.teXportN());
            sb.append(fields.teXcloseP());
            sb.append(elem.teXportL());
            sb.append(fields.teXendG());
        }
        sb.append(fields.teXaddFont());
        sb.append(fields.teXfontSize());
        sb.append(fields.teXmakeAtother());
        sb.append(fields.teXbegin());

        for(Objects object : objects){
            Elements elem = new Elements(object);
            sb.append(elem.teXamount());
            sb.append(elem.teXforEach());
            sb.append(elem.teXspacing());
        }
        /*for(Connection connection: connections){
            Connections conn = new Connections(connection);
            sb.append(c)
         }*/
        sb.append(fields.teXend());
        System.out.println(sb.toString());
    }
}