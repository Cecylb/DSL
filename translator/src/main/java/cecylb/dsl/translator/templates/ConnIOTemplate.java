package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface ConnIOTemplate extends Collectible {

    String eorw();

    String port();

    String lineType();

    String space();

    String objName();

    String index();

    class Builder extends ImmutableConnIOTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_CONN_IO.template()
                .add("eorw", eorw())
                .add("port", port())
                .add("linetype", lineType())
                .add("space", space())
                .add("objName", objName())
                .add("index", index())
                .render()
        );
    }
}
