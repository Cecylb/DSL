package cecylb.dsl.translator.impl;

import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.PropertyTemplate;

import static cecylb.dsl.translator.Template.TEX_MAKE_A_LETTER;

public class PropertiesProcessor {

    PropertiesProcessor(final TemplateProcessor.Collector collector, final Parser.Context context) {
        InlineProcessor inlineProcessor = new InlineProcessor();

        inlineProcessor.generate(collector, context, "documentclass");
        new PropertyTemplate.Builder()
                .sheetSize(context.getProperties().peek().sheetSize())
                .orientation(context.getProperties().peek().orientation())
                .build()
                .appendBy(collector);
        collector.append(TEX_MAKE_A_LETTER.render());
        inlineProcessor.generate(collector, context, "declareshape");
    }

}
