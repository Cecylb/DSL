package generate;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class TeXfields {

    STGroup element = new STGroupFile("template.stg");

    public String teXmakeAletter(){
        ST teXmakeAletter = element.getInstanceOf("teXmakeAletter");
        return teXmakeAletter.render();
    }
    public String teXborder() {
        ST teXborder = element.getInstanceOf("teXborder");
        return teXborder.render();
    }
    public String teXbackgroundpath(){
        ST teXbackgroundpath = element.getInstanceOf("teXbackgroundpath");
        return teXbackgroundpath.render();
    }
    public String teXcloseP(){
        ST teXcloseP = element.getInstanceOf("teXcloseP");
        return teXcloseP.render();
    }
    public String teXendG(){
        ST teXendG = element.getInstanceOf("teXendG");
        return teXendG.render();
    }
    public String teXaddFont(){
        ST teXaddFont = element.getInstanceOf("teXaddFont");
        return teXaddFont.render();
    }
    public String teXmakeAtother(){
        ST teXmakeAtother = element.getInstanceOf("teXmakeAtother");
        return teXmakeAtother.render();
    }
    public String teXbegin(){
        ST teXbegin = element.getInstanceOf("teXbegin");
        return teXbegin.render();
    }
    public String teXdefC(){
        ST teXdefC = element.getInstanceOf("teXdef"); // ДОБАВИТЬ index
        teXdefC.add("var", "p");
        teXdefC.add("amount", "0");
        return teXdefC.render();
    }
    public String teXforEachC(){
        ST teXforEachC = element.getInstanceOf("teXforEachC");
        teXforEachC.add("index", "a");
        return teXforEachC.render();
    }
    public String teXlet(){
        ST teXlet = element.getInstanceOf("teXlet"); // ДОДЕЛАТЬ
        teXlet.add("var", "m");
        return teXlet.render();
    }
    public String teXend(){
        ST teXend = element.getInstanceOf("teXend");
        return teXend.render();
    }
    public String teXbrL(){
        ST teXbrL = element.getInstanceOf("teXbracketL");
        return teXbrL.render();
    }
    public String teXbrR(){
        ST teXbrR = element.getInstanceOf("teXbracketR");
        return teXbrR.render();
    }
    public String teXforEach(String k){
        ST teXforEach = element.getInstanceOf("teXforEach");
        teXforEach.add("K", k);
        return teXforEach.render();
    }
}
