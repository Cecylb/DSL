package cecylb.dsl.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Property {

    String sheetSize();

    String orientation();

    class Builder extends ImmutableProperty.Builder {
    }

}
