package cecylb.dsl.modelv2;

import cecylb.dsl.modelv2.processor.Path;
import io.github.therealmone.tdf4j.commons.Token;
import io.github.therealmone.tdf4j.parser.model.ast.AST;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void test() {
        AST ast = new AST("object");
        ast.addNode("size");
        ast.moveCursor(AST.Movement.TO_LAST_ADDED_NODE);
        ast.addLeaf(new Token.Builder().tag("X").value("value").build());
        ast.moveCursor(AST.Movement.TO_LAST_ADDED_LEAF);
        //object/size/X

        assertTrue(new Path("object/size/X").check(ast.onCursor().asLeaf()));
        assertFalse(new Path("object/size/Y").check(ast.onCursor().asLeaf()));
    }

}
