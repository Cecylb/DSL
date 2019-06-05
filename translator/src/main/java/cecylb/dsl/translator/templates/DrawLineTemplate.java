package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface DrawLineTemplate extends Collectible {

    String lineType();

    class Builder extends ImmutableDrawLineTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_DRAW_LINE.template()
                .add("linetype", lineType())
                .render()
        );
    }

}
