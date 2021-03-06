package cecylb.dsl.modelv2.elements;

import org.immutables.value.Value;

@Value.Immutable
public interface Port {

    double portX();

    double portY();

    String portName();

    String portLabel();

    String portLine();

    class Builder extends ImmutablePort.Builder {
    }

}
