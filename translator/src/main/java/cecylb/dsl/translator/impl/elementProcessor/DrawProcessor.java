package cecylb.dsl.translator.impl.elementProcessor;

import cecylb.dsl.modelv2.tmp.TexObject;
import cecylb.dsl.translator.Parser;
import cecylb.dsl.translator.TemplateProcessor;
import cecylb.dsl.translator.templates.DefTemplate;
import cecylb.dsl.translator.templates.ForEachTemplate;
import cecylb.dsl.translator.templates.SpacingTemplate;

import static cecylb.dsl.translator.Template.TEX_BRACKET_L;
import static cecylb.dsl.translator.Template.TEX_BRACKET_R;

public class DrawProcessor implements ElementProcessor{

    public void generate(final TemplateProcessor.Collector collector, final Parser.Context context) {
        Character index = 'a';
        for (TexObject object : context.getTexObject()) {
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
}
