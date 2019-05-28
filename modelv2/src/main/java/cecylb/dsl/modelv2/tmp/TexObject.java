package cecylb.dsl.modelv2.tmp;

import org.immutables.value.Value;

import java.util.List;

public interface TexObject extends Size, Label{

    Integer amount();

    Integer spacing();

    Integer input();

    Integer output();

    List<Port> inputs();

    List<Port> outputs();

    List<Rectangle> rectangles();

}
