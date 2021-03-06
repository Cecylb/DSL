package cecylb.dsl.modelv2.elements;

import org.immutables.value.Value;

@Value.Immutable
public interface Rectangle {

    Double swX();

    Double swY();

    Double neX();

    Double neY();

    class Builder extends ImmutableRectangle.Builder {
    }

}
