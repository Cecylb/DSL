package cecylb.dsl.modelv2.elements;

import org.immutables.value.Value;

@Value.Modifiable
public interface Connection {

    @Value.Default
    default Boolean self() {
        return false;
    }

    String objName1();

    String objName2();

    String lineType();

    String port1();

    String port2();

}
