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
        Character index = 'a';
        for(TexObject object: context.getTexObject()) {
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
                    .index(String.valueOf(index))
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BRACKET_L.render());
            new PutTemplate.Builder()
                    .posX(String.valueOf(object.posX()))
                    .posY(String.valueOf(object.posY()))
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BRACKET_L.render());
            new SpacingTemplate.Builder()
                    .labelName(object.labelName())
                    .spacing(String.valueOf(object.spacing()))
                    .index(String.valueOf(index))
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BRACKET_R.render());
            collector.append(TEX_BRACKET_R.render());
            index++;
        }
    }

    private void processConnections(final Collector collector, final Parser.Context context) {
        ConnectionFields from = new ConnectionFields.Builder().objName("").sizeY(0.0).posX(0.0).posY(0.0).portY(0.0).spacing(0).portName("").build();
        ConnectionFields to = new ConnectionFields.Builder().objName("").sizeY(0.0).posX(0.0).posY(0.0).portY(0.0).spacing(0).portName("").build();
        for (Connection connection : context.getConnections()) {
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
                    .k("1")
                    .index("") // idk how to solve this yet
                    .build()
                    .appendBy(collector);
            collector.append(TEX_BRACKET_L.render());
            new DrawLineTemplate.Builder()
                    .lineType(connection.lineType())
                    .build()
                    .appendBy(collector);
            if(from.posX() > to.posX() && from.posY() < to.posY()) { // Connection type 1
                System.out.println("1");
                processConnectionType1(from, to, collector);
            } else if (connection.self().equals(true)){ // Connection type 2 (self connection)
                System.out.println("2");
                processConnectionType2(from, to, collector);
            } else if(from.posX() < to.posX() && from.posY() < to.posY()) { // Connection type 3
                System.out.println("3");
                processConnectionType3(from, to, collector);
            } else if(from.posX() > to.posX() && from.posY() > to.posY()) { // Connection type 4 (different objects)
                System.out.println("4");
                processConnectionType4(from, to, collector);
            } else if(from.posX().equals(to.posX()) && from.posY().equals(to.posY())) { // Connection type 4 (same object)
                System.out.println("4");
                processConnectionType4(from, to, collector);
            }
        }
        collector.append(TEX_LET.render());
        collector.append(TEX_BRACKET_R.render());
    }

    private ConnectionFields getFrom(final String portName, final TexObject object) {
        double portY = 0.0;
        for(Port port : object.outputs()) {
            if(port.portName().equals(portName)) portY = port.portY();
        }
        return new ConnectionFields.Builder()
                .objName(object.labelName())
                .sizeY(object.sizeY())
                .posX(object.posX())
                .posY(object.posY())
                .portY(portY)
                .portName(portName)
                .spacing(object.spacing())
                .build();
    }
    private ConnectionFields getTo(final String portName, final TexObject object) {
        double portY = 0.0;
        for(Port port : object.inputs()) {
            if(port.portName().equals(portName)) portY = port.portY();
        }
        return new ConnectionFields.Builder()
                .objName(object.labelName())
                .sizeY(object.sizeY())
                .posX(object.posX())
                .posY(object.posY())
                .portY(portY)
                .portName(portName)
                .spacing(object.spacing())
                .build();
    }

    private void processConnectionType1(final ConnectionFields from, final ConnectionFields to, final Collector collector) {
        new Conn1Template.Builder()
                .objName1(from.objName())
                .port1(from.portName())
                .build()
                .appendBy(collector);
        new ConnCTemplate.Builder()
                .objName(from.objName())
                .port(from.portName())
                .x(String.valueOf((double)from.spacing()/4))
                .y("0")
                .let("p")
                .build()
                .appendBy(collector);

        new ConnCTemplate.Builder()
                        .objName(from.objName())
                        .port(from.portName())
                        .x(String.valueOf(from.spacing()/4))
                        .y(String.valueOf(from.sizeY()*2))
                        .let("m")
                        .build()
                        .appendBy(collector);
        new ConnCTemplate.Builder()
                        .objName(to.objName())
                        .port(to.portName())
                        .x(String.valueOf(-to.spacing()/4))
                        .y(String.valueOf(from.sizeY()*2))
                        .let("p")
                        .build()
                        .appendBy(collector);

        new ConnCTemplate.Builder()
                .objName(to.objName())
                .port(to.portName())
                .x(String.valueOf(-(double)to.spacing()/4))
                .y("0")
                //.index(String.valueOf(index2))
                .let("m")
                .build()
                .appendBy(collector);
        new Conn2Template.Builder()
                .objName2(to.objName())
                .port2(to.portName())
                .build()
                .appendBy(collector);
    }

    private void processConnectionType2(final ConnectionFields from, final ConnectionFields to, final Collector collector) {
        System.out.println("HERE ");
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
    }

    private void processConnectionType3(final ConnectionFields from, final ConnectionFields to, final Collector collector) {
        new Conn1Template.Builder()
                .objName1(from.objName())
                .port1(from.portName())
                .build()
                .appendBy(collector);
        new ConnCTemplate.Builder()
                .objName(from.objName())
                .port(from.portName())
                .x(String.valueOf((double)from.spacing()/4))
                .y("0")
                .let("p")
                .build()
                .appendBy(collector);

        new ConnCTemplate.Builder()
                .objName(to.objName())
                .port(to.portName())
                .x(String.valueOf(-(double)to.spacing()/4))
                .y("0")
                //.index(String.valueOf(index2))
                .let("m")
                .build()
                .appendBy(collector);
        new Conn2Template.Builder()
                .objName2(to.objName())
                .port2(to.portName())
                .build()
                .appendBy(collector);
    }


    private void processConnectionType4(final ConnectionFields from, final ConnectionFields to, final Collector collector) {
        new Conn1Template.Builder()
                .objName1(from.objName())
                .port1(from.portName())
                .build()
                .appendBy(collector);
        new ConnCTemplate.Builder()
                .objName(from.objName())
                .port(from.portName())
                .x(String.valueOf((double)from.spacing()/4))
                .y("0")
                .let("p")
                .build()
                .appendBy(collector);

        new ConnCTemplate.Builder()
                .objName(from.objName())
                .port(from.portName())
                .x(String.valueOf(from.spacing()/4))
                .y(String.valueOf(-from.sizeY()*2))
                .let("m")
                .build()
                .appendBy(collector);
        new ConnCTemplate.Builder()
                .objName(to.objName())
                .port(to.portName())
                .x(String.valueOf(-to.spacing()/4))
                .y(String.valueOf(from.sizeY()*2))
                .let("p")
                .build()
                .appendBy(collector);


        new ConnCTemplate.Builder()
                .objName(to.objName())
                .port(to.portName())
                .x(String.valueOf(-(double)to.spacing()/4))
                .y("0")
                //.index(String.valueOf(index2))
                .let("m")
                .build()
                .appendBy(collector);
        new Conn2Template.Builder()
                .objName2(from.objName())
                .port2(to.portName())
                .build()
                .appendBy(collector);
    }

    private void processLastPart(final Collector collector, final Parser.Context context) {
        Character index = 'a';
        for(TexObject object : context.getTexObject()) {
            for(Port ports : object.inputs()) {
                new PutTemplate.Builder()
                        .posX(String.valueOf(object.posX()))
                        .posY(String.valueOf(object.posY()))
                        .build()
                        .appendBy(collector);
                collector.append(TEX_BRACKET_L.render());
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(ports.portName())
                        .eorw("east")
                        .space(decimalFormat.format( - object.sizeX() * 2 ))
                        .index("0")
                        .lineType("<-")
                        .build()
                        .appendBy(collector);
                collector.append(TEX_BRACKET_R.render());
            }
            for(Port output : object.outputs()) {
                new PutTemplate.Builder()
                        .posX(String.valueOf(object.posX()))
                        .posY(String.valueOf(object.posY()))
                        .build()
                        .appendBy(collector);
                collector.append(TEX_BRACKET_L.render());
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(output.portName())
                        .eorw("west")
                        .space(decimalFormat.format( object.sizeX() * 2 ))
                        .index("\\N" + index)
                        .lineType("->")
                        .build()
                        .appendBy(collector);
                collector.append(TEX_BRACKET_R.render());
            }
            index++;
        }
        collector.append(TEX_END.render());
    }
}
