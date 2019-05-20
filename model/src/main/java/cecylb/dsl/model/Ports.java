package cecylb.dsl.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Ports {

    double x();

    double y();

    String name();

    String label();

    int position();

    class Builder extends ImmutablePorts.Builder {
    }

}
