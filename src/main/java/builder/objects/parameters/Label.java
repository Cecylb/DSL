package builder.objects.elements.parameters;

import builder.objects.elements.parameters.data.ILabel;

public class Label {

    double labelX = ILabel.Label.DEFAULT.getLabelX();
    double labelY = ILabel.Label.DEFAULT.getLabelY();

    public Label(double labelX, double labelY){
        this.labelX = labelX;
        this.labelY = labelY;
    }
    public Label(String labelKW){
        switch (labelKW){
            case "small":
                labelX = ILabel.Label.SMALL.getLabelX();
                labelY = ILabel.Label.SMALL.getLabelY();
                break;
            case "medium":
                labelX = ILabel.Label.MEDIUM.getLabelX();
                labelY = ILabel.Label.MEDIUM.getLabelY();
                break;
            case "large":
                labelX = ILabel.Label.LARGE.getLabelX();
                labelY = ILabel.Label.LARGE.getLabelY();
                break;
        }
    }
    public Label(){}

    public double getLabelX(){ return labelX; }
    public double getLabelY(){ return labelY; }
}
