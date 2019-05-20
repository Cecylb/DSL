package cecylb.dsl.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Rectangles {

    double swX();

    double swY();

    double neX();

    double neY();

    class Builder extends ImmutableRectangles.Builder {
    }

}
