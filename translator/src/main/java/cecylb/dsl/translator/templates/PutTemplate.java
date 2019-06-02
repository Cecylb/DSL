package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface PutTemplate extends Collectible {

    String posX();

    String posY();

    class Builder extends ImmutablePutTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_PUT.template()
                .add("posX", posX())
                .add("posY", posY())
                .render()
        );
    }
}
