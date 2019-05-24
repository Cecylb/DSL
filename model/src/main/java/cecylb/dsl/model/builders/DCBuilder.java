package cecylb.dsl.model.builders;

import cecylb.dsl.model.DC;
import cecylb.dsl.model.Objects;
import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;

public class DCBuilder  extends AbstractBuilder {
    private DC.Builder builder;
    // builder.addSize(
    public  DCBuilder() {
        addRule("Object/NEW", element -> {
           builder.addObj(element.asLeaf().token().value());
        });
    }

    @Override
    public Objects build(ASTElement tree) {
        builder = new DC.Builder();
        process(tree);
        return builder.build();
    }
}
