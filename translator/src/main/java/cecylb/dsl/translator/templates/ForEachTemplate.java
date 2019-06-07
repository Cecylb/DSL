package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface ForEachTemplate extends Collectible {

    @Value.Default
    default String index1() {
        return "";
    }

    @Value.Default
    default String index2() {
        return "";
    }

    String k();

    class Builder extends ImmutableForEachTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_FOR_EACH.template()
                .add("index1", index1())
                .add("index2", index2())
                .add("K", k())
                .render()
        );
    }
}
