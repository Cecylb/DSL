package cecylb.dsl.translator.impl;

import cecylb.dsl.model.Objects;
import cecylb.dsl.modelv2.tmp.TexObject;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

//todo: удалить
public class Elements {

    TexObject data;
    STGroup element = new STGroupFile("template.stg");
    public Elements(TexObject data) {
        this.data = data;
    }

    public String teXobjN() {
        ST teXobjN = element.getInstanceOf("teXobjN");
        teXobjN.add("objName", data.labelName());
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
        for (int i = 0; i < data.inputs().size(); i++) {
            ST teXport = element.getInstanceOf("teXport");
            teXport.add("name", data.inputs().get(i).portName());
            teXport.add("x", data.inputs().get(i).portX());
            teXport.add("y", data.inputs().get(i).portY());
            sb.append(teXport.render());
        }
        for (int i = 0; i < data.outputs().size(); i++) {
            ST teXport = element.getInstanceOf("teXport");
            teXport.add("name", data.outputs().get(i).portName());
            teXport.add("x", data.outputs().get(i).portX());
            teXport.add("y", data.outputs().get(i).portY());
            sb.append(teXport.render());
        }
        return sb.toString();
    }
    public String teXrec(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.rectangles().size(); i++) {
            ST teXrec = element.getInstanceOf("teXrec");
            teXrec.add("swx", data.rectangles().get(i).swX());
            teXrec.add("swy", data.rectangles().get(i).swY());
            teXrec.add("nex", data.rectangles().get(i).neX());
            teXrec.add("ney", data.rectangles().get(i).neY());
            sb.append(teXrec.render());
        }
        return sb.toString();
    }
    public String teXportL() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.inputs().size(); i++) {
            ST teXportL = element.getInstanceOf("teXportL");
            teXportL.add("objName", data.labelName());
            teXportL.add("name", data.inputs().get(i).portName());
            teXportL.add("lor", "left");
            teXportL.add("label", data.inputs().get(i).portLabel());
            sb.append(teXportL.render());
        }
        for (int i = 0; i < data.outputs().size(); i++) {
            ST teXportL = element.getInstanceOf("teXportL");
            teXportL.add("objName", data.labelName());
            teXportL.add("name", data.outputs().get(i).portName());
            teXportL.add("lor", "right");
            teXportL.add("label", data.outputs().get(i).portLabel());
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
        teXspacing.add("objName", data.labelName());
        teXspacing.add("spacing", data.spacing());
        return teXspacing.render();
    }
    public String teXfontSize(){
        ST teXfontSize = element.getInstanceOf("teXfontSize");
        teXfontSize.add("objName", data.labelName());
        return teXfontSize.render();
    }
}
