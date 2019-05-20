package cecylb.dsl.translator.builder;

import cecylb.dsl.model.Property;

public class PropBuilder {

    private String orientation = "a4";
    private String sheetSize = "horizontal";

    public PropBuilder() {

    }

    public Property getProps() {
        return new Property.Builder()
                .orientation(orientation)
                .sheetSize(sheetSize)
                .build();
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setSheetSize(String sheetSize) {
        this.sheetSize = sheetSize;
    }
}
