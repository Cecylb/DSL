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
        if(posX1 > posX2 && posY1 < posY2) { // Connection type 1
            System.out.println("1");
            return new Connection1ProcessorImpl();
            //processConnectionType1(from, to, collector);
        } else if (self){ // Connection type 2 (self connection)
            System.out.println("2");
            return new Connection2ProcessorImpl();
            //processConnectionType2(from, to, collector);
        }  else if(posX1.equals(posX2) && posY1.equals(posY2)) { // Connection type 4 (same object)
            System.out.println("3");
            return  new Connection3ProcessorImpl();
            //processConnectionType3(from, to, collector);
        } else if(posX1 < posX2) { // Connection type 3
            System.out.println("3");
            return new Connection3ProcessorImpl();
            //processConnectionType3(from, to, collector);
        } else if(posX1 > posX2 && posY1 > posY2) { // Connection type 4 (different objects)
            System.out.println("4");
            return new Connection4ProcessorImpl();
            //processConnectionType4(from, to, collector);
        }
        return null;
    }
}
