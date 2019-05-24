package cecylb.dsl.model.processor;

import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;

public class Path {

    private final String[] path;

    public Path(final String path) {
        this.path = path.split("/"); // 0 - object, 1 - NEW
    }

    public boolean check(final ASTElement element) { // Нужно пройти с конца на начало, проверяя всех предков

        

        return true;
    }

}
