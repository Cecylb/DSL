package cecylb.dsl.modelv2.elements;

public enum  Labels {
    DEFAULT(1.5, 1.5);

    private final double x;
    private final double y;

    Labels(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public final double getLabelX(){
        return x;
    }

    public final double getLabelY(){
        return y;
    }
}