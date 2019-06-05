package cecylb.dsl.translator;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public enum Template {
    TEX_PROPERTY("teXproperty"),
    TEX_MAKE_A_LETTER("teXmakeAletter"),
    TEX_OBJ_N("teXobjN"),
    TEX_SIZE("teXsize"),
    TEX_BORDER("teXborder"),
    TEX_LABEL_C("teXlabelC"),
    TEX_PORT("teXport"),
    TEX_BACKGROUND_PATH("teXbackgroundpath"),
    TEX_REC("teXrec"),
    TEX_CLOSE_P("teXcloseP"),
    TEX_PORT_L("teXportL"),
    TEX_END_G("teXendG"),
    TEX_ADD_FONT("teXaddFont"),
    TEX_FONT_SIZE("teXfontSize"),
    TEX_MAKE_A_TOTHER("teXmakeAtother"),
    TEX_BEGIN("teXbegin"),
    TEX_DEF("teXdef"),
    TEX_SPACING("teXspacing"),
    TEX_DRAW_LINE("teXdrawLine"),
    TEX_CONN_1("teXconn1"),
    TEX_CONN_C("teXconnC"),
    TEX_CONN_2("teXconn2"),
    TEX_CONN_IO("teXconnIO"),
    TEX_END("teXend"),
    TEX_FOR_EACH("teXforEach"),
    TEX_LET("teXlet"),
    TEX_PUT("teXput"),
    TEX_OVER_LINE("teXoverLine"),
    TEX_BRACKET_L("teXbracketL"),
    TEX_BRACKET_R("teXbracketR");

    private static final STGroup TEMPLATE = new STGroupFile("template.stg");

    static {
        TEMPLATE.load();
    }

    private final String template;

    Template(final String template) {
        this.template = template;
    }

    public ST template() {
        return TEMPLATE.getInstanceOf(template);
    }

    public String render() {
        return TEMPLATE.getInstanceOf(template).render();
    }
}
