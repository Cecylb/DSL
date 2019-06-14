package cecylb.dsl.translator.impl;
import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import java.io.OutputStream;

public class TemplateProcessorImpl implements TemplateProcessor {

    @Override
    public void process(final Parser.Context context, final OutputStream output) {
        final Collector collector = new Collector(output);
        new PropertiesProcessor(collector, context);
        new ObjectProcessor(collector, context);
        new ConnectionProcessor(collector, context);
        new LastPartProcessor(collector, context);
    }
}
