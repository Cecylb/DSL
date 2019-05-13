package translator;

import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;

public class ParserModule extends AbstractParserModule {

    @Override
    public void configure() {
        environment()
                .packages(
                        "builder.PropBuilder",
                        "builder.ObjBuilder",
                        "builder.ConnBuilder"
                )
                .code(
                        "PropBuilder propBuilder = new PropBuilder();\n" +
                        "ObjBuilder objBuilder = new ObjBuilder();\n" +
                        "ConnBuilder connBuilder = new ConnBuilder();\n" +
                        "double x;\n" +
                        "double y;\n" +
                        "double n;\n" +
                        "String name;\n" +
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
                        repeat(nt("object")),
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
                                "this.context.getObjects().add(objBuilder.getResult(name));\n"
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
                                "objBuilder.setLabelN(sb.toString());"
                        ),
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
