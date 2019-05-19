package builder.connections;

import data.Connection;
import data.Objects;
import data.Ports;
import io.github.therealmone.tdf4j.parser.model.ast.ASTNode;

import java.util.Queue;

public class ConnBuilder {

    String objName1;
    String objName2;
    String port1;
    String port2;
    String lineType;

    public Connection getConnection(Queue<Objects> objects) {

        double objSizeY1=0;
        double objSizeY2=0;
        int port1pos=0;
        int port2pos=0;

        for(Objects object: objects){
            if(object.labelN().equals(objName1)){
                objSizeY1 = object.sizeY();
                for(Ports port: object.outputs()){
                    if(port.name().equals(port1)){
                        port1pos = port.position();
                    }
                }
            }else if(object.labelN().equals(objName2)){
                objSizeY2 = object.sizeY();
                for(Ports port: object.inputs()){
                    if(port.name().equals(port2)){
                        port2pos = port.position();
                    }
                }
            }
        }

        Connection connections = new Connection.Builder()
                .lineType(lineType)
                .objName1(objName1)
                .objSizeY1(objSizeY1)
                .objSizeY2(objSizeY2)
                .objName2(objName2)
                .port1(port1)
                .port1pos(port1pos)
                .port2(port2)
                .port2pos(port2pos)
                .build();
        return connections;
    }

    public void setObjName1(String objName1){ this.objName1 = objName1; }
    public void setObjName2(String objName2){ this.objName2 = objName2; }
    public void setPort1(String port1){ this.port1 = port1; }
    public void setPort2(String port2){ this.port2 = port2; }
    public void setLineType(String lineType){ this.lineType = lineType; }
}
