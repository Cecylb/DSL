package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface PortTemplate extends Collectible {

    String name();

    String x();

    String y();

    class Builder extends ImmutablePortTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_PORT.template()
                .add("name", name())
                .add("x", x())
                .add("y", y())
                .render()
        );
    }
}
