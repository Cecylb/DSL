package cecylb.dsl.modelv2;

import cecylb.dsl.modelv2.processor.Path;
import io.github.therealmone.tdf4j.model.Token;
import io.github.therealmone.tdf4j.model.ast.AST;
import io.github.therealmone.tdf4j.model.ast.ASTCursor;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void test() {
        AST ast = AST.create("object");
        ast.addNode("size");
        ast.moveCursor(ASTCursor.Movement.TO_LAST_ADDED_NODE);
        ast.addLeaf(new Token.Builder().tag("X").value("value").build());
        ast.moveCursor(ASTCursor.Movement.TO_LAST_ADDED_LEAF);
        //object/size/X

        assertTrue(new Path("object/size/X").check(ast.onCursor().asLeaf()));
        assertFalse(new Path("object/size/Y").check(ast.onCursor().asLeaf()));
    }

}
