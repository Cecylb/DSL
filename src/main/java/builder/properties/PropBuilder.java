package builder.properties;

import data.Property;

public class PropBuilder {

    String orientation = "a4";
    String sheetSize = "horizontal";

    public PropBuilder(){}

    public Property getProps() {
        Property property = new Property.Builder()
                .orientation(orientation)
                .sheetSize(sheetSize)
                .build();
        return property;
    }

    public void setSheetSize(String sheetSize){
        this.sheetSize = sheetSize;
    }
    public void setOrientation(String orientation){
        this.orientation = orientation;
    }
}
