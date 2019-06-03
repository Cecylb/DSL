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
        ConnectionFields from = new ConnectionFields.Builder().objName("").sizeY(0.0).posX(0.0).posY(0.0).spacing(0).portName("").build();
        ConnectionFields to = new ConnectionFields.Builder().objName("").sizeY(0.0).posX(0.0).posY(0.0).spacing(0).portName("").build();
        for (Connection connection : context.getConnections()) {
            new DefTemplate.Builder()
                    .amount("0")
                    .var("p")
                    .build()
                    .appendBy(collector);
            new ForEachTemplate.Builder()
                    .k("1")
                    .index("a") // idk how to solve this yet
                    .build()
                    .appendBy(collector);
            //collector.append(TEX_BRACKET_L.render());
            collector.append(TEX_BRACKET_L.render());
            new Conn1Template.Builder()
                    .lineType(connection.lineType())
                    .objName1(connection.objName1())
                    .port1(connection.port1())
                    .build()
                    .appendBy(collector);
            for(TexObject object : context.getTexObject()) {
                if (object.labelName().equals(connection.objName1())) {
                    from = new ConnectionFields.Builder()
                            .objName(object.labelName())
                            .sizeY(object.sizeY())
                            .posX(object.posX())
                            .posY(object.posY())
                            .portName(connection.port1())
                            .spacing(object.spacing())
                            .build();
                } else if (object.labelName().equals(connection.objName2())) {
                    to = new ConnectionFields.Builder()
                            .objName(object.labelName())
                            .sizeY(object.sizeY())
                            .posX(object.posX())
                            .posY(object.posY())
                            .portName(connection.port2())
                            .spacing(object.spacing())
                            .build();
                }
            }
            new ConnCTemplate.Builder()
                    .objName(connection.objName1())
                    .port(connection.port1())
                    .x(String.valueOf(from.spacing()/4))
                    .y("0")
                    .let("m")
                    .build()
                    .appendBy(collector);
            if(from.posX()>to.posX() && from.posY()>to.posY()){
                new ConnCTemplate.Builder()
                        .objName(connection.objName1())
                        .port(connection.port1())
                        .x(String.valueOf(from.spacing()/4))
                        .y(String.valueOf(-from.sizeY()*2))
                        .let("m")
                        .build()
                        .appendBy(collector);
                new ConnCTemplate.Builder()
                        .objName(connection.objName2())
                        .port(connection.port2())
                        .x(String.valueOf(-to.spacing()/4))
                        .y(String.valueOf(from.sizeY()*2))
                        .let("p")
                        .build()
                        .appendBy(collector);
            } else if(from.posX()>to.posX() && from.posY()<to.posY()) {
                new ConnCTemplate.Builder()
                        .objName(connection.objName1())
                        .port(connection.port1())
                        .x(String.valueOf(from.spacing()/4))
                        .y(String.valueOf(from.sizeY()*2))
                        .let("m")
                        .build()
                        .appendBy(collector);
                new ConnCTemplate.Builder()
                        .objName(connection.objName2())
                        .port(connection.port2())
                        .x(String.valueOf(-to.spacing()/4))
                        .y(String.valueOf(from.sizeY()*2))
                        .let("p")
                        .build()
                        .appendBy(collector);
            }
            new ConnCTemplate.Builder()
                    .objName(connection.objName2())
                    .port(connection.port2())
                    .x(String.valueOf(-to.spacing()/4))
                    .y("0")
                    //.index(String.valueOf(index2))
                    .let("p")
                    .build()
                    .appendBy(collector);
            new Conn2Template.Builder()
                    .objName2(connection.objName2())
                    .port2(connection.port2())
                    .build()
                    .appendBy(collector);
        }
        collector.append(TEX_BRACKET_R.render());
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
