package cecylb.dsl.modelv2.builders;

import cecylb.dsl.modelv2.elements.ModifiableProperty;
import cecylb.dsl.modelv2.elements.Property;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

public class PropertyBuilder extends  AbstractPropertyBuilder{

    private ModifiableProperty builder;
    public PropertyBuilder() {

        addRule("property/SHS", leaf -> {
            builder.setSheetSize(leaf.token().value());
        });

        addRule("property/ORI", leaf -> {
            builder.setOrientation(leaf.token().value());
        });

    }

    public Property build(ASTElement tree) {
        builder = ModifiableProperty.create();
        process(tree);
        return builder;
    }
}
