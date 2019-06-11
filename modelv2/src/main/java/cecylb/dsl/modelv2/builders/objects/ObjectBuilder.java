package cecylb.dsl.modelv2.builders.objects;

import cecylb.dsl.modelv2.tmp.OR;
import cecylb.dsl.modelv2.tmp.TexObject;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

@FunctionalInterface // Интерфейс у которого только один метод, можно использовать как лямбду
public interface ObjectBuilder {

    TexObject build(final ASTElement tree);

    static ObjectBuilder byName(final String name) {
        switch (name) {
            case "AND":
                return new ANDBuilder();
            case "EXOR":
                return new EXORBuilder();
            case "NAND":
                return new NANDBuilder();
            case "NOR":
                return new NORBuilder();
            case "NOT":
                return new NOTBuilder();
            case "OR":
                return new ORBuilder();
            case "DTr":
                return new DTrBuilder();
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

