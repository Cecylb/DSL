package cecylb.dsl.translator.impl;

import cecylb.dsl.modelv2.tmp.Connection;
import cecylb.dsl.modelv2.tmp.Port;
import cecylb.dsl.modelv2.tmp.TexObject;
import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.DefTemplate;
import cecylb.dsl.translator.templates.DrawLineTemplate;
import cecylb.dsl.translator.templates.ForEachTemplate;

import static cecylb.dsl.translator.Template.TEX_BRACKET_L;

public class ConnectionProcessor {

    ConnectionProcessor(final TemplateProcessor.Collector collector, final Parser.Context context) {
        System.out.println("Connections processing . . .\n");

        ConnectionFields from = new ConnectionFields.Builder().objName("").sizeY(0.0).posX(0.0).posY(0.0).portY(0.0).spacing(0).portName("").lineType("").build();
        ConnectionFields to = new ConnectionFields.Builder().objName("").sizeY(0.0).posX(0.0).posY(0.0).portY(0.0).spacing(0).portName("").lineType("").build();
        for (Connection connection : context.getConnections()) {

            System.out.println("Connection processing . . .\n");

            for(TexObject object : context.getTexObject()) {
                if (object.labelName().equals(connection.objName1()))
                    from = getFrom(connection.port1(), object);
                if (object.labelName().equals(connection.objName2()))
                    to = getTo(connection.port2(), object);
            }
            new DefTemplate.Builder()
                    .amount("0")
                    .var("p")
                    .build()
                    .appendBy(collector);
            new ForEachTemplate.Builder()
                    .k("0") // This value may vary
                    .index1("") // idk how to solve this yet
                    .index2("a")
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BRACKET_L.render());
            new DrawLineTemplate.Builder()
                    .lineType(from.lineType() + "-" + to.lineType())
                    .build()
                    .appendBy(collector);
            cecylb.dsl.translator.impl.connectionProcessor.ConnectionProcessor connType = cecylb.dsl.translator.impl.connectionProcessor.ConnectionProcessor.byType(
                    from.posX(),
                    from.posY(),
                    to.posX(),
                    to.posY(),
                    connection.self()
            );
            connType.generate(from, to, collector);
        }
    }
    private ConnectionFields getFrom(final String portName, final TexObject object) {
        double portY = 0.0;
        String lineType = "";
        for(Port port : object.outputs()) {
            if(port.portName().equals(portName)) {
                portY = port.portY(); }
            lineType = port.portLine();
        }
        return new ConnectionFields.Builder()
                .objName(object.labelName())
                .sizeY(object.sizeY())
                .posX(object.posX())
                .posY(object.posY())
                .portY(portY)
                .portName(portName)
                .lineType(lineType)
                .spacing(object.spacing())
                .build();
    }

    private ConnectionFields getTo(final String portName, final TexObject object) {
        double portY = 0.0;
        String lineType = "";
        for(Port port : object.inputs()) {
            if(port.portName().equals(portName)) {
                portY = port.portY();
                lineType = port.portLine();
            }
        }
        return new ConnectionFields.Builder()
                .objName(object.labelName())
                .sizeY(object.sizeY())
                .posX(object.posX())
                .posY(object.posY())
                .portY(portY)
                .portName(portName)
                .lineType(lineType)
                .spacing(object.spacing())
                .build();
    }
}
