package generate;

import data.Property;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class Properties {

    STGroup element = new STGroupFile("template.stg");
    public String teXproperty (Property property){
        ST prop = element.getInstanceOf("teXproperty");
        prop.add("sheetSize", property.sheetSize());
        prop.add("orientation", property.orientation());
        return prop.render();
    }
}
