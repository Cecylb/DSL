package cecylb.dsl.model.builders;

import cecylb.dsl.model.processor.ASTProcessor;
import cecylb.dsl.model.processor.ASTRule;
import cecylb.dsl.model.processor.Path;
import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;

import java.util.function.Consumer;

public abstract class AbstractBuilder implements Builder {

    private final ASTProcessor processor = new ASTProcessor();

    public void addRule(final ASTRule rule) {
        this.processor.addRule(rule);
    }

    public void addRule(final Path path, final Consumer<ASTElement> action) {
        this.processor.addRule(new ASTRule(path, action));
    }

    public void addRule(final String path, final Consumer<ASTElement> action) {
        this.processor.addRule(new ASTRule(new Path(path), action));
    }

    public void process(final ASTElement element) {
        this.processor.process(element);
    }

    public void addSize(final ASTElement element) {

    }

    public void addSize(final ASTElement x, final ASTElement y) {

    }

    public void addLabel(final ASTElement element) {

    }

    public void addLabel(final ASTElement x, final ASTElement y) {

    }

    public void addAmount(final ASTElement element) {

    }

    public void addSpacing(final ASTElement element) {

    }

}
