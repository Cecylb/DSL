package cecylb.dsl.model.processor;

import io.github.therealmone.tdf4j.parser.model.ast.ASTElement;
import io.github.therealmone.tdf4j.parser.model.ast.ASTNode;
import io.github.therealmone.tdf4j.parser.model.ast.ASTRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ASTProcessor {

    private final List<ASTRule> rules = new ArrayList<>();

    public void addRule(final ASTRule rule) {
        rules.add(rule);
    }

    public void addRule(final Path path, final Consumer<ASTElement> action) {
        rules.add(new ASTRule(path, action));
    }

    public void process(final ASTElement element) {
        if(element.isRoot()) {
            final ASTRoot root = element.asRoot();
            applyRules(root);
            for(final ASTElement child: root.children()) {
                process(child);
            }
        } else if(element.isNode()) {
            final ASTNode node = element.asNode();
            applyRules(node);
            for(final ASTElement child: node.children()) {
                process(child);
            }
        } else {
            applyRules(element.asLeaf());
        }
    }

    public void applyRules(final ASTElement element) {
        for(final ASTRule rule : rules) {
            rule.accept(element);
        }
    }

}
