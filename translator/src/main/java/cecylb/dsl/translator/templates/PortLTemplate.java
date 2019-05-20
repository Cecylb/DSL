package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface PortLTemplate extends Collectible {

    String objName();

    String name();

    String lor();

    String label();

    class Builder extends ImmutablePortLTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_PORT_L.template()
                .add("objName", objName())
                .add("name", name())
                .add("lor", lor())
                .add("label", label())
                .render()
        );
    }
}
