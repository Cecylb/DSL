package cecylb.dsl.translator.impl.connectionProcessor;

import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.impl.ConnectionFields;

@FunctionalInterface
public interface ConnectionProcessor {

    void generate(
            final ConnectionFields from,
            final ConnectionFields to,
            final TemplateProcessor.Collector collector
    );

    static ConnectionProcessor byType(
            final Double posX1,
            final Double posY1,
            final Double posX2,
            final Double posY2,
            boolean self
    ) {
        if(posX1 > posX2 && posY1 < posY2) {
            return new Connection1ProcessorImpl();
        } else if (self){
            return new Connection2ProcessorImpl();
        }  else if(posX1.equals(posX2) && posY1.equals(posY2)) {
            return  new Connection3ProcessorImpl();
        } else if(posX1 < posX2) {
            return new Connection3ProcessorImpl();
        } else if(posX1 > posX2 && posY1 > posY2) {
            return new Connection4ProcessorImpl();
        }
        return null;
    }
}
