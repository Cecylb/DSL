package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface ObjNTemplate extends Collectible {

    String objName();

    class Builder extends ImmutableObjNTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_OBJ_N.template()
                .add("objName", objName())
                .render()
        );
    }
}
