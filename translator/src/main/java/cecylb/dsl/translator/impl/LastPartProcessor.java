package cecylb.dsl.translator.impl;

import cecylb.dsl.modelv2.tmp.Port;
import cecylb.dsl.modelv2.tmp.TexObject;
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
        decimalFormat.setDecimalFormatSymbols(dfs);        Character index = 'a';
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
