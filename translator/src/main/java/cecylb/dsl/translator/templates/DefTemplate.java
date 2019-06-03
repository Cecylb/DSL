package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface DefTemplate extends Collectible {

    @Value.Default
    default String index() {
        return "";
    }

    String var();

    String amount();

    class Builder extends ImmutableDefTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_DEF.template()
                .add("var", var())
                .add("index", index())
                .add("amount", amount())
                .render()
        );
    }
}
