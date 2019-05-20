package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface Conn2Template extends Collectible {

    @Value.Default
    default String index2() {
        return "";
    }

    String objName2();

    String port2();

    class Builder extends ImmutableConn2Template.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_CONN_2.template()
                .add("index2", index2())
                .add("objName2", objName2())
                .add("port2", port2())
                .render()
        );
    }
}
