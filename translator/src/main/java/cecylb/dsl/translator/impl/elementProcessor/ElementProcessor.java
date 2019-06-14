package cecylb.dsl.translator.impl.elementProcessor;

import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;

@FunctionalInterface
public interface ElementProcessor {

    void generate(
            final TemplateProcessor.Collector collector,
            final Parser.Context context
    );

}
