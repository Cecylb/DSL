package data;

import org.immutables.value.Value;

@Value.Immutable
public interface Rectangles {

    double swX(); //SouthWest
    double swY();
    double neX(); //NorthEast
    double neY();

    class Builder extends ImmutableRectangles.Builder {
    }
}
