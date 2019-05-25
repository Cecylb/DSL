package cecylb.dsl.modelv2.builders;


import cecylb.dsl.modelv2.tmp.TTr;
import cecylb.dsl.modelv2.tmp.TexObject;
import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;

public class TTrBuilder extends AbstractBuilder {

    private TTr.Builder builder;
 // builder.addSize(
    public  TTrBuilder() {
        addRule("", leaf -> {
            builder.swy(1d);
        });

        addRule("", leaf -> {
            builder.swx(Double.parseDouble(leaf.token().value()));
        });

    }

    @Override
    public TexObject build(ASTElement tree) {
        builder = new TTr.Builder();
        process(tree);
        return null;
//        return builder.build();
    }
}
