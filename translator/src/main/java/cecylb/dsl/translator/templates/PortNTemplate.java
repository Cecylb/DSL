package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface PortNTemplate extends Collectible {

    String swx();

    String swy();

    String nex();

    String ney();

    class Builder extends ImmutablePortNTemplate.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_PORT_N.template()
                .add("swx", swx())
                .add("swy", swy())
                .add("nex", nex())
                .add("ney", ney())
                .render()
        );
    }
}
