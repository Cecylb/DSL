package cecylb.dsl.modelv2.processor;

import io.github.therealmone.tdf4j.model.Token;
import io.github.therealmone.tdf4j.model.ast.ASTElement;
import io.github.therealmone.tdf4j.model.ast.ASTLeaf;

public class Path {

    private final String[] path;

    public Path(final String path) {
        this.path = path.split("/"); // 0 - object, 1 - NEW
    }

    public boolean check(final ASTLeaf element) { // Нужно пройти с конца на начало, проверяя всех предков
        if(!checkToken(element.token())) {
            return false;
        }
        return checkParents(element);
    }

    private boolean checkToken(final Token token) {
        return path[path.length - 1].equals(token.tag().value());
    }

    private boolean checkParents(final ASTLeaf leaf) {
        ASTElement current = leaf.parent();
        for (int i = path.length - 2; i > 0; i--) {
            if(current.isNode() && !current.asNode().tag().equals(path[i])) {
                return false;
            } else if(current.isRoot() && !current.asRoot().tag().equals(path[i])) {
                return false;
            }
            current = current.isNode()
                    ? current.asNode().parent()
                    : current;
        }
        return true;
    }

}
