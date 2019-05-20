package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface SizeTemplate extends Collectible {

    String x();

    String y();

    class Builder extends ImmutableSizeTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_SIZE.template()
                .add("x", x())
                .add("y", y())
                .render()
        );
    }
}
