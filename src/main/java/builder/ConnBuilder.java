package builder;

import data.Connection;
import io.github.therealmone.tdf4j.parser.model.ast.ASTNode;

public class ConnBuilder {

    String objName1;
    String objName2;
    String port1;
    String port2;
    String lineType;

    public ConnBuilder(){}

    public Connection getConnection(){
        Connection connections = new Connection.Builder()
                .lineType(lineType)
                .objName1(objName1)
                .objName2(objName2)
                .port1(port1)
                .port2(port2)
                .build();
        return connections;
    }

    public void setObjName1(String objName1){
        this.objName1 = objName1;
    }
    public void setObjName2(String objName2){
        this.objName2 = objName2;
    }
    public void setPort1(String port1){ this.port1 = port1; }
    public void setPort2(String port2){
        this.port2 = port2;
    }
    public void setLineType(String lineType){
        this.lineType = lineType;
    }
}
