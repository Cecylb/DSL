package generate;

import data.Objects;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class Elements {

    Objects data;
    STGroup element = new STGroupFile("template.stg");
    public Elements(Objects data) {
        this.data = data;
    }

    public String teXobjN() {
        ST teXobjN = element.getInstanceOf("teXobjN");
        teXobjN.add("objName", data.objName());
        return teXobjN.render();
    }
    public String teXsize() {
        ST teXsize = element.getInstanceOf("teXsize");
        teXsize.add("x", data.sizeX());
        teXsize.add("y", data.sizeY());
        return teXsize.render();
    }
    public String teXlabelC(){
        ST teXlabelC = element.getInstanceOf("teXlabelC");
        teXlabelC.add("x", data.labelX());
        teXlabelC.add("y", data.labelY());
        return teXlabelC.render();
    }

    public String teXport(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.ports().size(); i++) {
            ST teXport = element.getInstanceOf("teXport");
            teXport.add("name", data.ports().get(i).name());
            teXport.add("x", data.ports().get(i).x());
            teXport.add("y", data.ports().get(i).y());
            sb.append(teXport.render());
        }
        return sb.toString();
    }
    public String teXportN(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.rectangles().size(); i++) {
            ST teXportN = element.getInstanceOf("teXportN");
            teXportN.add("swx", data.rectangles().get(i).swX());
            teXportN.add("swy", data.rectangles().get(i).swY());
            teXportN.add("nex", data.rectangles().get(i).neX());
            teXportN.add("ney", data.rectangles().get(i).neY());
            sb.append(teXportN.render());
        }
        return sb.toString();
    }
    public String teXportL() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.ports().size(); i++) {
            ST teXportL = element.getInstanceOf("teXportL");
            teXportL.add("objName", data.objName());
            teXportL.add("name", data.ports().get(i).name());
            sb.append(teXportL.render());
        }
        return sb.toString();
    }
    public String teXamount(){
        ST teXamount = element.getInstanceOf("teXdef");
        teXamount.add("amount", data.amount());
        teXamount.add("var", "N");
        return teXamount.render();
    }
    public String teXspacing(){
        ST teXspacing = element.getInstanceOf("teXspacing");
        teXspacing.add("objName", data.objName());
        teXspacing.add("spacing", data.spacing());
        return teXspacing.render();
    }
    public String teXforEach(){
        ST teXforEach = element.getInstanceOf("teXforEach");
        return teXforEach.render();
    }
}