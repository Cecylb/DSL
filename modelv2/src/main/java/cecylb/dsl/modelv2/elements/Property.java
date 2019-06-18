package cecylb.dsl.modelv2.elements;

import org.immutables.value.Value;

@Value.Modifiable
public interface Property {

    String sheetSize();

    String orientation();

}
