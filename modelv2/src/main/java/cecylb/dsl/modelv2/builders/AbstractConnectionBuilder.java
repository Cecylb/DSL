package cecylb.dsl.modelv2.builders;

import cecylb.dsl.modelv2.processor.ASTProcessor;
import cecylb.dsl.modelv2.processor.ASTRule;
import cecylb.dsl.modelv2.processor.Path;
import io.github.therealmone.tdf4j.model.ast.ASTElement;
import io.github.therealmone.tdf4j.model.ast.ASTLeaf;

import java.util.function.Consumer;

public abstract class AbstractConnectionBuilder {
    private final ASTProcessor processor = new ASTProcessor();

    void addRule(final ASTRule rule) {
        this.processor.addRule(rule);
    }

    void addRule(final Path path, final Consumer<ASTLeaf> action) {
        this.processor.addRule(new ASTRule(path, action));
    }

    void addRule(final String path, final Consumer<ASTLeaf> action) {
        this.processor.addRule(new ASTRule(new Path(path), action));
    }

    void process(final ASTElement element) { this.processor.process(element); }
}