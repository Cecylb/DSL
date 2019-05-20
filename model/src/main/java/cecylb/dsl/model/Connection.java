package cecylb.dsl.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Connection {

    String objName1();

    String objName2();

    String lineType();

    String port1();

    String port2();

    double objSizeY1();

    double objSizeY2();

    int port1pos();

    int port2pos();

    class Builder extends ImmutableConnection.Builder {
    }

}
