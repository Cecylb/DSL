package cecylb.dsl.translator.impl.elementProcessor;

import cecylb.dsl.modelv2.tmp.TexObject;
import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.*;

import static cecylb.dsl.translator.Template.*;

public class InitializeProcessor implements ElementProcessor {

    public void generate(final TemplateProcessor.Collector collector, final Parser.Context context) {
        Character index = 'a';
        for (
                TexObject object : context.getTexObject()) {
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
    }
}
