package cecylb.dsl.model;

public class Label {

    private final double labelX;
    private final double labelY;

    public Label(final double labelX, final double labelY){
        this.labelX = labelX;
        this.labelY = labelY;
    }

    public Label(final String labelKW) {
        switch (labelKW) {
            case "small":
                labelX = Labels.SMALL.getLabelX();
                labelY = Labels.SMALL.getLabelY();
                break;
            case "medium":
                labelX = Labels.MEDIUM.getLabelX();
                labelY = Labels.MEDIUM.getLabelY();
                break;
            case "large":
                labelX = Labels.LARGE.getLabelX();
                labelY = Labels.LARGE.getLabelY();
                break;
            default:
                this.labelX = Labels.DEFAULT.getLabelX();
                this.labelY = Labels.DEFAULT.getLabelY();
        }
    }

    public Label() {
        this.labelX = Labels.DEFAULT.getLabelX();
        this.labelY = Labels.DEFAULT.getLabelY();
    }

    public double getLabelX() {
        return labelX;
    }

    public double getLabelY() {
        return labelY;
    }

}
