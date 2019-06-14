package cecylb.dsl.translator.impl;

import cecylb.dsl.modelv2.tmp.Connection;
import cecylb.dsl.modelv2.tmp.Port;
import cecylb.dsl.modelv2.tmp.TexObject;
import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.*;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Map;

import static cecylb.dsl.translator.Template.*;

public class TemplateProcessorImpl implements TemplateProcessor {
    private final DecimalFormat decimalFormat;

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
        //GATHERING PROPERTIES
        for(Map.Entry<String, String> inline : context.getInline().entrySet()){
            if(inline.getKey().equals("documentclass")){
                collector.append(inline.getValue());
            }
        }
        new PropertyTemplate.Builder()
                .sheetSize(context.getProperties().peek().sheetSize())
                .orientation(context.getProperties().peek().orientation())
                .build()
                .appendBy(collector);
        collector.append(TEX_MAKE_A_LETTER.render());
        //GATHERING OBJECT INFORMATION
        for(Map.Entry<String, String> inline : context.getInline().entrySet()){
            if(inline.getKey().equals("declareshape")){
                collector.append(inline.getValue());
            }
        }
    }

// Стоит разделить это на отдельные методы?
// Можно ли как-то это упростить?
    private void processObjects(final Collector collector, final Parser.Context context) {
        System.out.println("Objects processing . . .\n");
        Character index = 'a';
        for(TexObject object: context.getTexObject()) {
            System.out.println("Object processing . . .\n");
            new ObjNTemplate.Builder()
                    .objName(object.labelName())
                    .build()
                    .appendBy(collector);
            new SizeTemplate.Builder()
                    .x(String.valueOf(object.sizeX()))
                    .y(String.valueOf(object.sizeY()))
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BORDER.render());
            new LabelCTemplate.Builder()
                    .x(String.valueOf(object.labelX()))
                    .y(String.valueOf(object.labelY()))
                    .build()
                    .appendBy(collector);
            for (int i = 0; i < object.inputs().size(); i++) {
                new PortTemplate.Builder()
                        .x(String.valueOf(object.inputs().get(i).portX()))
                        .y(String.valueOf(object.inputs().get(i).portY()))
                        .name(String.valueOf(object.inputs().get(i).portName()))
                        .build()
                        .appendBy(collector);
            }
            for (int i = 0; i < object.outputs().size(); i++) {
                new PortTemplate.Builder()
                        .x(String.valueOf(object.outputs().get(i).portX()))
                        .y(String.valueOf(object.outputs().get(i).portY()))
                        .name(String.valueOf(object.outputs().get(i).portName()))
                        .build()
                        .appendBy(collector);
            }
            collector.append(TEX_BACKGROUND_PATH.render());
            for (int i = 0; i < object.rectangles().size(); i++) {
                new RecTemplate.Builder()
                        .swx(String.valueOf(object.rectangles().get(i).swX()))
                        .swy(String.valueOf(object.rectangles().get(i).swY()))
                        .nex(String.valueOf(object.rectangles().get(i).neX()))
                        .ney(String.valueOf(object.rectangles().get(i).neY()))
                        .build()
                        .appendBy(collector);
            }
            collector.append(TEX_CLOSE_P.render());
            for (int i = 0; i < object.inputs().size(); i++) {
                new PortLTemplate.Builder()
                        .objName(object.labelName())
                        .name(object.inputs().get(i).portName())
                        .label(object.inputs().get(i).portLabel())
                        .lor("left")
                        .build()
                        .appendBy(collector);
            }
            for (int i = 0; i < object.outputs().size(); i++) {
                new PortLTemplate.Builder()
                        .objName(object.labelName())
                        .name(object.outputs().get(i).portName())
                        .label(object.outputs().get(i).portLabel())
                        .lor("right")
                        .build()
                        .appendBy(collector);
            }
            collector.append(TEX_END_G.render());
            collector.append(TEX_ADD_FONT.render());
            new FontSizeTemplate.Builder()
                    .objName(object.labelName())
                    .build()
                    .appendBy(collector);
        }
        collector.append(TEX_MAKE_A_TOTHER.render());
        collector.append(TEX_BEGIN.render());
        for(Map.Entry<String, String> inline : context.getInline().entrySet()){
            if(inline.getKey().equals("tikzpicture")){
                collector.append(inline.getValue());
            }
        }
        index = 'a';
        for(TexObject object : context.getTexObject()) {
            new DefTemplate.Builder()
                    .amount(String.valueOf(object.amount()))
                    .var("N")
                    .index(String.valueOf(index))
                    .build()
                    .appendBy(collector);
            new ForEachTemplate.Builder()
                    .k("0")
                    .index1(String.valueOf(index))
                    .index2(String.valueOf(index))
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BRACKET_L.render());
            new SpacingTemplate.Builder()
                    .labelName(object.labelName())
                    .spacing(String.valueOf(object.spacing()))
                    .index(String.valueOf(index))
                    .posX(object.posX())
                    .posY(object.posY())
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BRACKET_R.render());
            index++;
        }
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
