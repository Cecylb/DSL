package cecylb.dsl.modelv2.tmp;

import org.immutables.value.Value;

@Value.Immutable
public interface Rectangle {

    Double swx();

    Double swy();

    Double nex();

    Double ney();

    class Builder extends ImmutableRectangle.Builder {
    }

}
