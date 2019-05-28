package cecylb.dsl.modelv2.tmp;

import org.immutables.value.Value;

@Value.Immutable
public interface Port {

    double portX();

    double portY();

    String portName();

    String portLabel();

    class Builder extends ImmutablePort.Builder {
    }

}
