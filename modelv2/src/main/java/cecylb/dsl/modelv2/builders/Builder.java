package cecylb.dsl.modelv2.builders;

import cecylb.dsl.modelv2.tmp.TexObject;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

@FunctionalInterface // Интерфейс у которого только один метод, можно использовать как лямбду
public interface Builder {

    TexObject build(final ASTElement tree);

    static Builder byName(final String name) {
        switch (name) {
            case "TTr":
                return new TTrBuilder();
            case "DC":
                return new DCBuilder();
            case "MX":
                return new MXBuilder();
            case "UDVTr":
                return new UDVTrBuilder();
            case "UJKTr":
                return new UJKTrBuilder();
            default:
                return tree -> null;
        }
    }
}

