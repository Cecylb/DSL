package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface OverLineTemplate extends Collectible {

    String name();

    class Builder extends ImmutableOverLineTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_OVER_LINE.template()
                .add("name", name())
                .render()
        );
    }
}
