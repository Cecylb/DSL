package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface SpacingTemplate extends Collectible {

    String labelName();

    String spacing();

    class Builder extends ImmutableSpacingTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_SPACING.template()
                .add("labelName", labelName())
                .add("spacing", spacing())
                .render()
        );
    }
}
