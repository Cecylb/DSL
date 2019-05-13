package data;

import org.immutables.value.Value;

@Value.Immutable
public interface Ports {

    double x();
    double y();
    String name();
    int position();

    class Builder extends ImmutablePorts.Builder {
    }
}
