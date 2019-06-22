package cecylb.dsl.translator.impl;

import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.impl.elementProcessor.DrawProcessor;
import cecylb.dsl.translator.impl.elementProcessor.InitializeProcessor;

import static cecylb.dsl.translator.Template.TEX_BEGIN;
import static cecylb.dsl.translator.Template.TEX_MAKE_A_TOTHER;

public class ObjectProcessor {

    ObjectProcessor(final TemplateProcessor.Collector collector, final Parser.Context context) {
        InlineProcessor inlineProcessor = new InlineProcessor();
        InitializeProcessor initializeProcessor = new InitializeProcessor();
        DrawProcessor drawProcessor = new DrawProcessor();
        initializeProcessor.generate(collector, context);
        collector.append(TEX_MAKE_A_TOTHER.render());
        collector.append(TEX_BEGIN.render());
        inlineProcessor.generate(collector, context,"tikzpicture");
        drawProcessor.generate(collector, context);
    }

}
