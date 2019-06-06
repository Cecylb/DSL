package cecylb.dsl.translator.impl;

import org.immutables.value.Value;

@Value.Immutable
public interface ConnectionFields {

    String objName();

    String portName();

    Double sizeY();

    Double posX();

    Double posY();

    Double portY();

    Integer spacing();

    class Builder extends ImmutableConnectionFields.Builder {
    }
}
