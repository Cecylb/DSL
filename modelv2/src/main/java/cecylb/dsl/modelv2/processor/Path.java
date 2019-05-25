package cecylb.dsl.modelv2.processor;

import io.github.therealmone.tdf4j.parser.model.ast.ASTLeaf;

public class Path {

    private final String[] path;

    public Path(final String path) {
        this.path = path.split("/"); // 0 - object, 1 - NEW
    }

    public boolean check(final ASTLeaf element) { // Нужно пройти с конца на начало, проверяя всех предков
        return true;
    }

}
