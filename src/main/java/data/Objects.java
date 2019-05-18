package data;

import elements.Element;
import org.immutables.value.Value;
import java.util.List;

@Value.Immutable
public interface Objects extends Element {

    String objName();
    double sizeX();
    double sizeY();
    double labelX();
    double labelY();
    String labelN();
    List<Ports> inputs();
    List<Ports> outputs();
    int amount();
    int spacing();
    List<Rectangles> rectangles();

    class Builder extends ImmutableObjects.Builder {
    }
}