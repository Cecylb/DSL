package data;

import org.immutables.value.Value;

@Value.Immutable
public interface Connection {

    String objName1();
    String objName2();
    String lineType();
    String port1();
    String port2();
    // String index1();
    // String index2();

    class Builder extends ImmutableConnection.Builder {
    }
}
