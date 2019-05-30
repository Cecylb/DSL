package cecylb.dsl.modelv2.tmp;

import org.immutables.value.Value;

@Value.Modifiable
public interface Connection {

    String objName1();

    String objName2();

    String lineType();

    String port1();

    String port2();

}
