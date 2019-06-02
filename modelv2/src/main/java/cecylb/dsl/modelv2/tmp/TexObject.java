package cecylb.dsl.modelv2.tmp;

import java.util.List;

public interface TexObject extends Position, Size, Label {

    String objName();

    Integer amount();

    Integer spacing();

    Integer input();

    Integer output();

    List<Port> inputs();

    List<Port> outputs();

    List<Rectangle> rectangles();

}
