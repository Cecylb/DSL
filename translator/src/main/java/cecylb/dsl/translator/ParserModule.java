package cecylb.dsl.translator;

import io.github.therealmone.tdf4j.module.parser.AbstractParserModule;

public class ParserModule extends AbstractParserModule {

    @Override
    public void configure() {
        environment()
                .packages(
                        "cecylb.dsl.modelv2.builders.objects.*",
                        "cecylb.dsl.modelv2.builders.*",
                        "cecylb.dsl.model.*",
                        "cecylb.dsl.modelv2.tmp.TexObject",
                        "cecylb.dsl.model.rectangles.*"
                )
                .code(
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
                        t("CMA"),
                        t("ORI"),
                        t("DIV"),
                        inline(
                                "PropertyBuilder builder = new PropertyBuilder();\n" +
                                        "this.context.getProperties().add(builder.build(ast.onCursor()));"
                        )
                );
        //3
        prod("object")
                .is(
                        t("NEW"),
                        t("OBJ"),
                        inline(
                                "ObjectBuilder builder = ObjectBuilder.byName(ast.moveCursor(ASTCursor.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value());\n" +
                                        "ast.moveCursor(ASTCursor.Movement.TO_PARENT);\n"
                        ),
                        t("BFL"),
                        optional(nt("size")),
                        optional(nt("label")),
                        optional(nt("inputs")),
                        optional(nt("amount")),
                        optional(nt("spacing")),
                        t("BFR"),
                        inline(
                                "this.context.getTexObject().add(builder.build(ast.onCursor()));\n"
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
                        nt("x_coordinate"),
                        t("CMA"),
                        nt("y_coordinate")
                );
        //6
        prod("x_coordinate")
                .is(
                        t("DBL"),
                        t("CDX") // Есть возможность увидеть нетерминал, если обратиться к последнему ноду
                );
        //6
        prod("y_coordinate")
                .is(
                        t("DBL"),
                        t("CDY") // Есть возможность увидеть нетерминал, если обратиться к последнему ноду
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
                        nt("obj1"),
                        t("BRR"),
                        nt("port1"),
                        oneOf(t("ARW"), t("LIN"), t("INV"), t("DTL")),
                        t("BRL"),
                        nt("obj2"),
                        t("BRR"),
                        nt("port2"),
                        t("DIV"),
                        inline(
                                "ConnectionBuilder builder = new ConnectionBuilder();\n" +
                                        "this.context.getConnections().add(builder.build(ast.onCursor()));\n"
                        )
                );
        prod("obj1")
                .is(
                        repeat(t("LET")),
                        optional(nt("property"))
                );
        prod("obj2")
                .is(
                        repeat(t("LET")),
                        optional(nt("property"))
                        );
        prod("port1")
                .is(
                        repeat(or(t("LET"), t("NUM")))
                );
        prod("port2")
                .is(
                        repeat(or(t("LET"), t("NUM")))
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
                                "String position = ast.moveCursor(ASTCursor.Movement.TO_LAST_ADDED_LEAF).onCursor().asLeaf().token().value();\n" +
                                      "ast.moveCursor(ASTCursor.Movement.TO_PARENT);\n"
                        ),
                        t("CMA"),
                        t("QOT"),
                        t("IOL"),
                        //inline(
                        //        "??????????????????????????????"
                        //),
                        t("QOT"),
                        t("DIV")
                );
    }
}
