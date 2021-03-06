package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.Template;
import cecylb.dsl.translator.TemplateProcessor;
import org.immutables.value.Value;

@Value.Immutable
public interface Conn1Template extends Collectible {

    @Value.Default
    default String index1() {
        return "";
    }

    @Value.Default
    default String let() {
        return "";
    }

    String objName1();

    String port1();

    class Builder extends ImmutableConn1Template.Builder {
    }

    @Override
    default void appendBy(final TemplateProcessor.Collector collector) {
        collector.append(Template.TEX_CONN_1.template()
                .add("index1", index1())
                .add("let", let())
                .add("objName1", objName1())
                .add("port1", port1())
                .render()
        );
    }
}
