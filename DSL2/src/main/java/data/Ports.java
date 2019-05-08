package data;

import org.immutables.value.Value;

@Value.Immutable
public interface Ports {

    double x();
    double y();
    String name();

    class Builder extends ImmutablePorts.Builder {
    }
}
