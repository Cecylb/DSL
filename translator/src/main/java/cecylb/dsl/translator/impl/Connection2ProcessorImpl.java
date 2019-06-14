package cecylb.dsl.translator.impl;

import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.Conn1Template;
import cecylb.dsl.translator.templates.Conn2Template;
import cecylb.dsl.translator.templates.ConnCTemplate;

import static cecylb.dsl.translator.Template.TEX_BRACKET_R;
import static cecylb.dsl.translator.Template.TEX_LET;

public class Connection2ProcessorImpl implements ConnectionProcessor{

    public void generate(final ConnectionFields from, final ConnectionFields to, final TemplateProcessor.Collector collector) {
        System.out.println(from.portY());
        System.out.println(to.portY());
        new Conn1Template.Builder()
                .objName1(from.objName())
                .port1(from.portName())
                .let("p")
                .build()
                .appendBy(collector);
        new ConnCTemplate.Builder()
                .objName(from.objName())
                .port(from.portName())
                .x(String.valueOf((double)from.spacing()/8))
                .y("0")
                .let("p")
                .build()
                .appendBy(collector);

        new ConnCTemplate.Builder()
                .objName(from.objName())
                .port(from.portName())
                .x(String.valueOf((double)from.spacing()/8))
                .y(String.valueOf(from.sizeY()*8 - (from.portY()/4 - from.sizeY())))
                .let("p")
                .build()
                .appendBy(collector);
        new ConnCTemplate.Builder()
                .objName(to.objName())
                .port(to.portName())
                .x(String.valueOf((double)-to.spacing()/8))
                .y(String.valueOf(to.sizeY()*8 - to.portY()/4))
                .let("p")
                .build()
                .appendBy(collector);

        new ConnCTemplate.Builder()
                .objName(to.objName())
                .port(to.portName())
                .x(String.valueOf(-(double)to.spacing()/8))
                .y("0.0")
                //.index(String.valueOf(index2))
                .let("p")
                .build()
                .appendBy(collector);
        new Conn2Template.Builder()
                .objName2(to.objName())
                .port2(to.portName())
                .let("p")
                .build()
                .appendBy(collector);
        collector.append(TEX_LET.render());
        collector.append(TEX_BRACKET_R.render());
    }

}
