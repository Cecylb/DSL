package cecylb.dsl.translator.builder;

import cecylb.dsl.model.Connection;
import cecylb.dsl.model.Objects;
import cecylb.dsl.model.Ports;
import cecylb.dsl.modelv2.tmp.Port;
import cecylb.dsl.modelv2.tmp.TexObject;

import java.util.Queue;

public class ConnBuilder {

    private String objName1;
    private String objName2;
    private String port1;
    private String port2;
    private String lineType;

    public Connection getConnection(final Queue<TexObject> objects) {
        double objSizeY1=0;
        double objSizeY2=0;
        int port1pos=0;
        int port2pos=0;

        for(TexObject object: objects){
            if(object.labelName().equals(objName1)){
                objSizeY1 = object.sizeY();
                for(Port port: object.outputs()){
                    if(port.portName().equals(port1)){
                        //port1pos = port.position();
                    }
                }
            }else if(object.labelName().equals(objName2)){
                objSizeY2 = object.sizeY();
                for(Port port: object.inputs()){
                    if(port.portName().equals(port2)){
                        //port2pos = port.position();
                    }
                }
            }
        }

        return new Connection.Builder()
                .lineType(lineType)
                .objName1(objName1)
                .objSizeY1(objSizeY1)
                .objSizeY2(objSizeY2)
                .objName2(objName2)
                .port1(port1)
                //.port1pos(port1pos)
                .port2(port2)
                //.port2pos(port2pos)
                .build();
    }

    public void setObjName1(final String objName1) {
        this.objName1 = objName1;
    }

    public void setObjName2(final String objName2) {
        this.objName2 = objName2;
    }

    public void setPort1(final String port1) {
        this.port1 = port1;
    }

    public void setPort2(final String port2) {
        this.port2 = port2;
    }

    public void setLineType(final String lineType) {
        this.lineType = lineType;
    }
}
