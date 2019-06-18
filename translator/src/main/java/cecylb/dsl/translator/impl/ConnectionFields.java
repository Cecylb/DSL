package cecylb.dsl.translator.impl;

import org.immutables.value.Value;

@Value.Immutable
public interface ConnectionFields {

    String objName();

    String portName();

    Double sizeX();

    Double sizeY();

    Double posX();

    Double posY();

    Double portY();

    String lineType();

    Integer spacing();

    class Builder extends ImmutableConnectionFields.Builder {
    }
}
