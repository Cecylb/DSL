package cecylb.dsl.modelv2.tmp;

import org.immutables.value.Value;

@Value.Immutable
public interface TTr extends TexObject {

    @Override
    default String[] inputs() {
        return new String[] {"S", "T", "C", "R"};
    }

    @Override
    default String[] outputs() {
        return new String[] {"Q", "Qn"};
    }

    class Builder extends ImmutableTTr.Builder {
    }
}
