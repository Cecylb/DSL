package cecylb.dsl.translator.impl;

import cecylb.dsl.model.Connection;
import cecylb.dsl.model.Objects;
import cecylb.dsl.model.Ports;
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

    private void processObjects(final Collector collector, final Parser.Context context) {
        for(TexObject object: context.getTexObject()) {

            //todo: удалить Elements
            Elements elem = new Elements(object);
            collector.append(elem.teXobjN());
            collector.append(elem.teXsize());
            collector.append(TEX_BORDER.render());
            collector.append(elem.teXlabelC());
            collector.append(elem.teXport());
            collector.append(TEX_BACKGROUND_PATH.render());
            collector.append(elem.teXrec());
            collector.append(TEX_CLOSE_P.render());
            collector.append(elem.teXportL());
            collector.append(TEX_END_G.render());
            collector.append(TEX_ADD_FONT.render());
            collector.append(elem.teXfontSize());

        }
        collector.append(TEX_MAKE_A_TOTHER.render());
        collector.append(TEX_BEGIN.render());
        for(Map.Entry<String, String> inline : context.getInline().entrySet()){
            if(inline.getKey().equals("tikzpicture")){
                collector.append(inline.getValue());
            }
        }
        for(TexObject object : context.getTexObject()) {
            Elements elem = new Elements(object);
            collector.append(elem.teXamount());
            new ForEachTemplate.Builder()
                    .k("0")
                    .build()
                    .appendBy(collector);
            collector.append(elem.teXspacing());
        }
        collector.append(TEX_DEF.render());
        new ForEachTemplate.Builder()
                .k("1")
                .build()
                .appendBy(collector);
        collector.append(TEX_BRACKET_L.render());
    }

    private void processConnections(final Collector collector, final Parser.Context context) {
        for (Connection connection : context.getConnections()) {
            new Conn1Template.Builder()
                    .lineType(connection.lineType())
                    .objName1(connection.objName1())
                    .port1(connection.port1())
                    .build()
                    .appendBy(collector);
            if(connection.objName1().equals(connection.objName2())) {
                for(TexObject object : context.getTexObject()) {
                    if(object.labelName().equals(connection.objName1())) {
                        for(Port port : object.outputs()){
                            if(port.portName().equals(connection.port1())) {
                                new ConnCTemplate.Builder()
                                        .objName(connection.objName1())
                                        .port(connection.port1())
                                        .x(String.valueOf(object.spacing() / 4))
                                        .y(String.valueOf(0.0))
                                        .let("p")
                                        .build()
                                        .appendBy(collector);
                            }
                        }
                        for(Port port : object.inputs()){
                            if(port.portName().equals(connection.port2())){
                                new ConnCTemplate.Builder()
                                        .objName(connection.objName2())
                                        .port(connection.port2())
                                        .x(String.valueOf(- object.spacing() / 4))
                                        .y(String.valueOf(0.0))
                                        .let("m")
                                        .build()
                                        .appendBy(collector);

                            }
                        }
                    }
                }
            } else {
                for(TexObject object : context.getTexObject()) {
                    if(object.labelName().equals(connection.objName1())) {
                        for(Port port : object.outputs()){
                            if(port.portName().equals(connection.port1())) {
                                new ConnCTemplate.Builder()
                                        .objName(connection.objName1())
                                        .port(connection.port1())
                                        .x(String.valueOf( object.sizeX() ))
                                        .y(String.valueOf( ( object.sizeY() / object.outputs().size() ))) //* port.position() ))
                                        .let("m")
                                        .build()
                                        .appendBy(collector);

                            }
                        }
                        for(Port port : object.inputs()) {
                            if(port.portName().equals(connection.port2())) {
                                new ConnCTemplate.Builder()
                                        .objName(connection.objName2())
                                        .port(connection.port2())
                                        .x(String.valueOf( - object.sizeX() ))
                                        .y(String.valueOf( ( object.sizeY() / object.outputs().size() ))) // * port.position() ))
                                        .let("m")
                                        .build()
                                        .appendBy(collector);

                            }
                        }
                    }
                }
            }
            new Conn2Template.Builder()
                    .objName2(connection.objName2())
                    .port2(connection.port2())
                    .build()
                    .appendBy(collector);
        }
    }

    private void processLastPart(final Collector collector, final Parser.Context context) {
        for(TexObject object : context.getTexObject()) {
            for(Port ports : object.inputs()) {
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(ports.portName())
                        .eorw("east")
                        .space(decimalFormat.format( - object.sizeX() * 2 ))
                        .index("0")
                        .lineType("<-")
                        .build()
                        .appendBy(collector);
            }
            for(Port output : object.outputs()) {
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(output.portName())
                        .eorw("west")
                        .space(decimalFormat.format( object.sizeX() * 2 ))
                        .index("\\N")
                        .lineType("->")
                        .build()
                        .appendBy(collector);

            }
        }
        collector.append(TEX_END.render());
    }
}
