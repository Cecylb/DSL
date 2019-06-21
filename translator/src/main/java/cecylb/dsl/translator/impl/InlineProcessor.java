package cecylb.dsl.translator.impl;

import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;

import java.util.Map;

public class InlineProcessor {
    public void generate(
            final TemplateProcessor.Collector collector,
            final Parser.Context context,
            final String field) {
        for (Map.Entry<String, String> inline : context.getInline().entrySet()) {
            if (field.equals(inline.getKey())) {
                collector.append(inline.getValue().replaceAll("^'", ""));
            }
        }
    }
}
