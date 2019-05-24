package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface ConnCTemplate extends Collectible {

    String objName();

    //String index();

    String port();

    String x();

    String y();

    String let();

    class Builder extends ImmutableConnCTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_CONN_C.template()
                .add("objName", objName())
                //.add("index", index())
                .add("port", port())
                .add("x", x())
                .add("y", y())
                .add("let", let())
                .render()
        );
    }
}
