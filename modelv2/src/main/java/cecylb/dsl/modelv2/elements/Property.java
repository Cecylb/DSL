package cecylb.dsl.modelv2.elements;

import org.immutables.value.Value;

@Value.Modifiable
public interface Property {

    @Value.Default
    default String sheetSize() { return "a4"; }

    @Value.Default
    default String orientation() { return "landscape"; }

}
