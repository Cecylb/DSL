package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface FontSizeTemplate extends Collectible {

    String objName();

    class Builder extends ImmutableFontSizeTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_FONT_SIZE.template()
                .add("objName", objName())
                .render()
        );
    }
}
