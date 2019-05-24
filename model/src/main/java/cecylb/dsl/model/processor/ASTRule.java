package cecylb.dsl.model.processor;

import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;

import java.util.function.Consumer;

public class ASTRule implements Consumer<ASTElement> {

    private final Path path;
    private final Consumer<ASTElement> action;

    public ASTRule(final Path path, final Consumer<ASTElement> action) {
        this.action = action;
        this.path = path;
    }

    @Override
    public void accept(final ASTElement element) {
        if(path.check(element)) {
            action.accept(element);
        }
    }
}
