package cecylb.dsl.modelv2.processor;

import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;
import io.github.therealmone.tdf4j.parser.model.ast.ASTLeaf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ASTProcessor {

    private final List<ASTRule> rules = new ArrayList<>();

    public void addRule(final ASTRule rule) {
        rules.add(rule);
    }

    public void addRule(final Path path, final Consumer<ASTLeaf> action) {
        rules.add(new ASTRule(path, action));
    }

    public void process(final ASTElement element) {
        if(element.isRoot()) {
            for(final ASTElement child : element.asRoot().children()) {
                process(child);
            }
        } else if(element.isNode()) {
            for(final ASTElement child : element.asNode().children()) {
                process(child);
            }
        } else {
            applyRules(element.asLeaf());
        }
    }

    public void applyRules(final ASTLeaf leaf) {
        for(final ASTRule rule : rules) {
            rule.accept(leaf);
        }
    }

}
