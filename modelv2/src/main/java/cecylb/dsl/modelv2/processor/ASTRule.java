package cecylb.dsl.modelv2.processor;

import io.github.therealmone.tdf4j.parser.model.ast.ASTLeaf;

import java.util.function.Consumer;

public class ASTRule implements Consumer<ASTLeaf> {

    private final Path path;
    private final Consumer<ASTLeaf> action;

    public ASTRule(final Path path, final Consumer<ASTLeaf> action) {
        this.action = action;
        this.path = path;
    }

    @Override
    public void accept(final ASTLeaf element) {
        if(path.check(element)) {
            action.accept(element);
        }
    }
}
