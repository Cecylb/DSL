package cecylb.dsl.translator.impl;

import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.Conn1Template;
import cecylb.dsl.translator.templates.Conn2Template;
import cecylb.dsl.translator.templates.ConnCTemplate;

import static cecylb.dsl.translator.Template.TEX_BRACKET_R;
import static cecylb.dsl.translator.Template.TEX_LET;

public class Connection3ProcessorImpl implements ConnectionProcessor{

    public void generate(final ConnectionFields from, final ConnectionFields to, final TemplateProcessor.Collector collector) {
        new Conn1Template.Builder()
                .objName1(from.objName())
                .port1(from.portName())
                .let("p")
                .build()
                .appendBy(collector);
        new ConnCTemplate.Builder()
                .objName(from.objName())
                .port(from.portName())
                .x(String.valueOf((to.posX()-from.posX())/2.5))
                .y("0")
                .let("p")
                .build()
                .appendBy(collector);

        new ConnCTemplate.Builder()
                .objName(to.objName())
                .port(to.portName())
                .x(String.valueOf(-(to.posX()-from.posX())/2.5))
                .y("0")
                //.index(String.valueOf(index2))
                .let("m")
                .build()
                .appendBy(collector);
        new Conn2Template.Builder()
                .objName2(to.objName())
                .port2(to.portName())
                .let("m")
                .build()
                .appendBy(collector);
        collector.append(TEX_LET.render());
        collector.append(TEX_BRACKET_R.render());
    }

}
