package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface PropertyTemplate extends Collectible {

    String sheetSize();

    String orientation();

    class Builder extends ImmutablePropertyTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_PROPERTY.template()
                .add("sheetSize", sheetSize())
                .add("orientation", orientation())
                .render()
        );
    }
}