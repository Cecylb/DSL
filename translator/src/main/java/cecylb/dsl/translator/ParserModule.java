package cecylb.dsl.translator;

import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;

public class ParserModule extends AbstractParserModule {

    @Override
    public void configure() {
        environment()
                .packages(
                        "cecylb.dsl.translator.builder.*",
                        "cecylb.dsl.model.*",
                        "cecylb.dsl.model.Objects"
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
                                "switch (name) {\n" +
                                        "            case \"AC\":\n" +
                                        "                break;\n" +
                                        "            case \"ARSTr\":\n" +
                                        "                break;\n" +
                                        "            case \"CTTr\":\n" +
                                        "                break;\n" +
                                        "            case \"DC\":\n" +
                                        "DCBuilder dc = new DCBuilder(size.getSizeX(), size.getSizeY(), inputsN.getInputsN());\n" +
                                        "                break;\n" +
                                        "            case \"DTr\":\n" +
                                        "                break;\n" +
                                        "            case \"LDC\":\n" +
                                        "                break;\n" +
                                        "            case \"MX\":\n" +
                                        "                break;\n" +
                                        "            case \"RDC\":\n" +
                                        "                break;\n" +
                                        "            case \"PDC\":\n" +
                                        "                break;\n" +
                                        "            case \"SC\":\n" +
                                        "                break;\n" +
                                        "            case \"ShRSTr\":\n" +
                                        "                break;\n" +
                                        "            case \"SRSTr\":\n" +
                                        "                break;\n" +
                                        "            case \"TTr\":\n" +
                                        "TTrBuilder ttr = new TTrBuilder(size.getSizeX(), size.getSizeY());\n" +
                                        "                break;\n" +
                                        "            case \"JKTr\":\n" +
                                        "                break;\n" +
                                        "        }\n" +
        "this.context.getObjects().add(new Objects.Builder()" +
                                        ".objName(name)\n" +
                                        ".sizeX(size.getSizeX())\n" +
                                        ".sizeY(size.getSizeY())\n" +
                                        ".labelX(label.getLabelX())\n" +
                                        ".labelY(label.getLabelY())\n" +
                                        ".labelN(labelN.getLabelN())\n" +
                                        ".inputs(inputsL)\n" +
                                        ".outputs(outputsL)\n" +
                                        ".amount(amount.getAmount())\n" +
                                        ".spacing(spacing.getSpacing())\n" +
                                        ".rectangles(rectangles)\n" +
                                        ".build());\n"
                        ));
        //4
        prod("label")
                .is(
                        t("LAB"),
                        t("COL"),
                        optional(or(t("POS"), nt("coordinates"))),
                        inline(
                                "switch(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().tag().value()){\n" +
                                        "case \"POS\": label = new Label(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                        "break;\n" +
                                        "case \"COL\": label = new Label(x, y);\n" +
                                        "break; \n" +
                                        "}\n" +
                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                        ),
                        optional(t("CMA"),
                        t("QUT"),
                        inline(
                                "StringBuilder sb = new StringBuilder();"
                        ),
                        repeat(t("LET"), inline(
                                "sb.append(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());" +
                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                                )
                        ),
                        optional(nt("property")),
                        t("QUT"),
                        inline(
                                "labelN = new LabelN(sb.toString());"
                        )),
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
                                        "case \"SCL\": size = new Size(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                        "break;\n" +
                                        "case \"COL\": size = new Size(x, y);\n" +
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
                                "amount = new Amount(Integer.parseInt(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value()));\n" +
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
                                "spacing = new Spacing(Integer.parseInt(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value()));\n" +
                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                        ),
                        t("DIV")
                );
        //4
        prod("inputs")
                .is(
                        t("PRT"),
                        t("COL"),
                        t("NUM"), //or(t("NUM"), nt("ports")),
                        inline(
                                "inputsN = new InputsN(Integer.parseInt(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value()));\n" +
                                        "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                        ),
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
                        or(
                                group(
                                        repeat(
                                                t("LET"),
                                                inline(
                                                        "sb.append(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());" +
                                                                "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                                                )
                                        ),
                                        inline(
                                                "connBuilder.setObjName1(sb.toString());\n" +
                                                        "sb = new StringBuilder();\n"
                                        )
                                ),
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
                        t("QUT"),
                        inline(
                                "StringBuilder sb = new StringBuilder();"
                        ),
                        repeat(
                                oneOf(
                                        t("LET"),
                                        t("NUM"),
                                        t("DBL"),
                                        t("MTH"),
                                        t("CMA"),
                                        t("INV"),
                                        t("ARW"),
                                        t("LIN"),
                                        t("DOT"),
                                        t("SLA"),
                                        t("BRR"),
                                        t("BRL"),
                                        t("BFR"),
                                        t("BFL"),
                                        t("BSR"),
                                        t("BSL"),
                                        t("BRL"),
                                        t("BRL"),
                                        t("LIN"),
                                        t("ARW"),
                                        t("INV"),
                                        t("DTL"),
                                        t("DLR"),
                                        t("EQL"),
                                        t("DIV")
                                ),
                                inline(
                                        "sb.append(ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());" +
                                                "ast.moveCursor(AST.Movement.TO_PARENT);\n"
                                )
                        ),
                        t("QUT"),
                        t("DIV"),
                        inline(
                                "this.context.getInline().put(position, sb.toString());\n"
                        )
                );
    }
}
