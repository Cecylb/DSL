package cecylb.dsl.translator;

import cecylb.dsl.model.builders.Builder;
import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;
import io.github.therealmone.tdf4j.parser.model.ast.AST;

public class ParserModule extends AbstractParserModule {

    @Override
    public void configure() {
        environment()
                .packages(
                        "cecylb.dsl.translator.builder.*",
                        "cecylb.dsl.model.*",
                        "cecylb.dsl.model.Objects",
                        "cecylb.dsl.model.rectangles.*"
                )
                .code(
                        "PropBuilder propBuilder = new PropBuilder();\n" +
                        "Amount amount = new Amount();\n" +
                        "InputsN inputsN = new InputsN();\n" +
                        "IO io = new IO();\n" +
                        "IOD iod = new IOD();\n" +
                        "Label label = new Label();\n" +
                        "LabelN labelN = new LabelN();\n" +
                        "Size size = new Size();\n" +
                        "Spacing spacing = new Spacing();\n" +
                        "ConnBuilder connBuilder = new ConnBuilder();\n" +
                        "double x;\n" +
                        "double y;\n" +
                        "double n;\n" +
                        "String name;\n" +
                        "List<Rectangles> rectangles = new ArrayList<>();\n" +
                        "List<Ports> inputsL = new ArrayList<>();\n" +
                        "List<Ports> outputsL = new ArrayList<>();\n" +
                        "\n" +
                        "Context context;" +
                        "\n" +
                        "@Override\n" +
                        "public Context getContext() {\n" +
                        "   return this.context == null \n" +
                        "           ? new Context()\n" +
                        "           : this.context;\n" +
                        "}\n" +
                                "public void getNode() {\n" +
                                "return  AST.Movement.TO_LAST_ADDED_NODE;\n" +
                                "ast.moveCursor(AST.Movement.TO_PARENT);\n" +
                                "}\n"
        );
        //1
        prod("lang")
                .is( // here will be all the methods
                        inline("this.context = new Context();\n"),
                        optional(nt("desc")),
                        repeat(or(nt("object"), nt("code"))),
                        nt("connections"),
                        optional(repeat(nt("code")))
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
                                "this.context.getProperties().add(propBuilder.getProps());"
                        )
                );
        //3
        prod("object")
                .is(
                        t("NEW"),
                        t("OBJ"),
                        t("BFL"),
                        optional(nt("size")),
                        optional(nt("label")),
                        optional(nt("inputs")),
                        optional(nt("amount")),
                        optional(nt("spacing")),
                        t("BFR"),
                        inline(
                                "Здесь отправить дерево в "
                        )
                );
        //4
        prod("label")
                .is(
                        t("LAB"),
                        t("COL"),
                        optional(or(t("POS"), nt("coordinates"))),
                        optional(t("CMA")),
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
                        t("CRD") // Есть возможность увидеть нетерминал, если обратиться к последнему ноду
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
                        t("NUM"), //or(t("NUM"), nt("ports")),
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
                        inline(
                                "StringBuilder sb = new StringBuilder();"
                        ),
                        or(repeat(t("LET")),
                                group(
                                        t("OBJ"),
                                        inline(
                                                "String name = ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value();\n" +
                                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                                        )
                                )
                        ),
                        optional(nt("property")),
                        t("BRR"),
                        repeat(
                                or(
                                        t("LET"),
                                        t("NUM")
                                ),
                                inline(
                                        "sb.append(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());" +
                                                "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                                )
                        ),
                        inline(
                                "connBuilder.setPort1(sb.toString());\n" +
                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n" +
                                        "sb = new StringBuilder();\n"
                        ),
                        oneOf(t("ARW"), t("LIN"), t("INV"), t("DTL")),
                        inline(
                                "connBuilder.setLineType(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                        ),
                        t("BRL"),
                        repeat(
                                t("LET"),
                                inline(
                                        "sb.append(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());" +
                                                "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                                )
                        ),
                        inline(
                                "connBuilder.setObjName2(sb.toString());\n" +
                                        "sb = new StringBuilder();\n"
                        ),
                        optional(nt("property")),
                        t("BRR"),
                        repeat(
                                or(
                                        t("LET"),
                                        t("NUM")
                                ),
                                inline(
                                        "sb.append(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());" +
                                                "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                                )
                        ),
                        inline(
                                "connBuilder.setPort2(sb.toString());\n" +
                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n" +
                                        "sb = new StringBuilder();\n"
                        ),
                        t("DIV"),
                        inline(
                                "this.context.getConnections().add(connBuilder.getConnection(this.context.getObjects()));\n"
                        )
                );
        //5
        prod("property")
                .is(
                        oneOf(t("IDN"), t("NUM"), t("CUR"))
                );
        prod("code")
                .is(
                        t("COD"),
                        t("COL"),
                        t("KOD"),
                        inline(
                                "String position = ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value();\n" +
                                      "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                        ),
                        t("CMA"),
                        t("QOT"),
                        t("IOL"),
                        inline(
                                "??????????????????????????????"
                        ),
                        t("QOT"),
                        t("DIV")
                );
    }
}
