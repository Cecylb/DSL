package cecylb.dsl.model.builders;

import cecylb.dsl.model.DC;
import cecylb.dsl.model.Objects;
import cecylb.dsl.model.TTr;
import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;

@FunctionalInterface // Интерфейс у которого только один метод, можно использовать как лямбду
public interface Builder extends TTr, DC {

    Objects build(ASTElement tree);

    static Builder byName(final String name) {
        switch (name) {
            case "TTr":
                return new TTrBuilder();
            case "DC":
                return new DCBuilder();
            default:
                return tree -> null;
        }
    }
}

