package cecylb.dsl.translator.impl;

import cecylb.dsl.modelv2.tmp.Connection;
import cecylb.dsl.modelv2.tmp.Port;
import cecylb.dsl.modelv2.tmp.TexObject;
import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.impl.connectionProcessor.ConnectionProcessor;
import cecylb.dsl.translator.impl.elementProcessor.DrawProcessor;
import cecylb.dsl.translator.impl.elementProcessor.InitializeProcessor;
import cecylb.dsl.translator.templates.*;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Map;

import static cecylb.dsl.translator.Template.*;

public class TemplateProcessorImpl implements TemplateProcessor {
    private final DecimalFormat decimalFormat;
    InlineProcessor inlineProcessor = new InlineProcessor();

    public TemplateProcessorImpl() {
        this.decimalFormat = new DecimalFormat("0.0000");
        DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(dfs);
    }

    @Override
    public void process(final Parser.Context context, final OutputStream output) {
        final Collector collector = new Collector(output);
        processProperties(collector, context);
        processObjects(collector, context);
        processConnections(collector, context);
        processLastPart(collector, context);
    }

    private void processProperties(final Collector collector, final Parser.Context context) {
       inlineProcessor.generate(collector, context, "documentclass");
        new PropertyTemplate.Builder()
                .sheetSize(context.getProperties().peek().sheetSize())
                .orientation(context.getProperties().peek().orientation())
                .build()
                .appendBy(collector);
        collector.append(TEX_MAKE_A_LETTER.render());
        inlineProcessor.generate(collector, context, "declareshape");
    }

// Стоит разделить это на отдельные методы?
// Можно ли как-то это упростить?
    private void processObjects(final Collector collector, final Parser.Context context) {
        InitializeProcessor initializeProcessor = new InitializeProcessor();
        DrawProcessor drawProcessor = new DrawProcessor();
        System.out.println("Objects processing . . .\n");
        initializeProcessor.generate(collector, context);
        collector.append(TEX_MAKE_A_TOTHER.render());
        collector.append(TEX_BEGIN.render());
        inlineProcessor.generate(collector, context,"tikzpicture");
        drawProcessor.generate(collector, context);
    }

    private void processConnections(final Collector collector, final Parser.Context context) {
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
            ConnectionProcessor connType = ConnectionProcessor.byType(
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

    private void processLastPart(final Collector collector, final Parser.Context context) {
        Character index = 'a';
        for(TexObject object : context.getTexObject()) {
            for(Port input : object.inputs()) {
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(input.portName())
                        .eorw("east")
                        .space(decimalFormat.format( - object.sizeX() * 2 ))
                        .index("0")
                        .lineType("<-" + input.portLine())
                        .build()
                        .appendBy(collector);
            }
            for(Port output : object.outputs()) {
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(output.portName())
                        .eorw("west")
                        .space(decimalFormat.format( object.sizeX() * 2 ))
                        .index("\\N" + index)
                        .lineType(output.portLine() + "->")
                        .build()
                        .appendBy(collector);
            }
            index++;
        }
        collector.append(TEX_END.render());
    }
}
