package cecylb.dsl.translator.impl;

import cecylb.dsl.modelv2.elements.Connection;
import cecylb.dsl.modelv2.elements.Port;
import cecylb.dsl.modelv2.elements.TexObject;
import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.ConnIOTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static cecylb.dsl.translator.Template.TEX_END;

public class LastPartProcessor {

    LastPartProcessor(final TemplateProcessor.Collector collector, final Parser.Context context) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(dfs);
        Character index = 'a';
        for (TexObject object : context.getTexObject()) {
            renderInputs(collector, object, context, decimalFormat);
            renderOutputs(collector, object, context, decimalFormat, index);
            index++;
        }
        collector.append(TEX_END.render());
    }
    private void renderInputs(
            final TemplateProcessor.Collector collector,
            final TexObject object,
            final Parser.Context context,
            DecimalFormat decimalFormat) {
        for (Port input : object.inputs()) {
            if (checkInput(input.portName(), object.labelName(), context)) {
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(input.portName())
                        .eorw("east")
                        .space(decimalFormat.format(-object.sizeX() * 2))
                        .index("0")
                        .lineType("<-" + input.portLine())
                        .build()
                        .appendBy(collector);
            }
        }
    }

    private void renderOutputs(
            final TemplateProcessor.Collector collector,
            final TexObject object,
            final Parser.Context context,
            DecimalFormat decimalFormat,
            Character index) {
        for (Port output : object.outputs()) {
            if (checkOutput(output.portName(), object.labelName(), context)) {
                new ConnIOTemplate.Builder()
                        .objName(object.labelName())
                        .port(output.portName())
                        .eorw("west")
                        .space(decimalFormat.format(object.sizeX() * 2))
                        .index("\\N" + index)
                        .lineType(output.portLine() + "->")
                        .build()
                        .appendBy(collector);
            }
        }
    }

    private boolean checkOutputObj(final String labelName, final Parser.Context context) {
        for (Connection connection : context.getConnections()) {
            if(labelName.equals(connection.objName1())) return false;
        }
        return true;
    }

    private boolean checkInputObj(final String labelName, final Parser.Context context) {
        for (Connection connection : context.getConnections()) {
            if(labelName.equals(connection.objName2())) return false;
        }
        return true;
    }

    private boolean checkInput(final String portName, final String labelName, final Parser.Context context) {
        for (Connection connection : context.getConnections()) {
            if(labelName.equals(connection.objName2())) {
                if (portName.equals(connection.port2())) return false;
            }
        }
        return true;
    }
        private boolean checkOutput(final String portName, final String labelName, final Parser.Context context) {
            for (Connection connection : context.getConnections()) {
                if(labelName.equals(connection.objName1())) {
                    if (portName.equals(connection.port1())) return false;
                }
            }
            return true;
        }
}