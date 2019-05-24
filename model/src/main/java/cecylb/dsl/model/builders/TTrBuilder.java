package cecylb.dsl.model.builders;

import cecylb.dsl.model.Objects;
import cecylb.dsl.model.TTr;
import cecylb.dsl.model.processor.Path;
import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;

public class TTrBuilder extends AbstractBuilder {

    private TTr.Builder builder;
 // builder.addSize(
    public  TTrBuilder() {
        addRule("Object/NEW", element -> {
            builder.addSize(element.asLeaf().token().value());
        });
        addRule("", element -> {
            builder.addLabel(element.asLeaf().token().value());
        });
    }

    @Override
    public Objects build(ASTElement tree) {
        builder = new TTr.Builder();
        process(tree);
        return builder.build();
    }
}
