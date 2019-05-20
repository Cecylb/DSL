package cecylb.dsl.translator.templates;

import cecylb.dsl.translator.TemplateProcessor;

public interface Collectible {

    void appendBy(final TemplateProcessor.Collector collector);

}
