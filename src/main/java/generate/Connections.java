package generate;

import data.Connection;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class Connections {

    Connection connection;
    STGroup element = new STGroupFile("template.stg");
    public Connections(Connection connection){
        this.connection=connection;
    }

    public String teXconn1(){
        ST teXconn1 = element.getInstanceOf("teXconn1");
        teXconn1.add("linetype", connection.lineType());
        //teXconn1.add("index1", connection.index1());
        teXconn1.add("objName1", connection.objName1());
        teXconn1.add("port1", connection.port1());
        return teXconn1.render();
    }
    public String teXconnC(String objName, String port, double x, double y){
        ST teXconnC = element.getInstanceOf("teXconnC");
        teXconnC.add("objName", objName);
        teXconnC.add("port", port);
        teXconnC.add("x", x);
        teXconnC.add("y", y);
        //teXconnC.add("index", connection.index1());
        return teXconnC.render();
    }
    public String teXconn2(){
        ST teXconn2 = element.getInstanceOf("teXconn2");
        //teXconn2.add("index2", connection.index2());
        teXconn2.add("objName2", connection.objName2());
        teXconn2.add("port2", connection.port2());
        return teXconn2.render();
    }
    public String teXconnIO(){
        ST teXconnIO = element.getInstanceOf("teXconnIO");
        teXconnIO.add("eorw", ""); // Придумать способ получения величины
        teXconnIO.add("port", connection.port1());
        return teXconnIO.render();
    }
}
