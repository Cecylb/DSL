package cecylb.dsl.model;

public class Size {

    private final double sizeX;
    private final double sizeY;

    public Size(final String sizeKW) {
        switch (sizeKW){
            case "small":
                sizeX = Sizes.SMALL.getSizeX();
                sizeY = Sizes.SMALL.getSizeY();
                break;
            case "mediun":
                sizeX = Sizes.MEDIUM.getSizeX();
                sizeY = Sizes.MEDIUM.getSizeY();
                break;
            case "large":
                sizeX = Sizes.LARGE.getSizeX();
                sizeY = Sizes.LARGE.getSizeY();
                break;
            default:
               this.sizeX = Sizes.DEFAULT.getSizeX();
               this.sizeY = Sizes.DEFAULT.getSizeY();
        }
    }

    public Size(final double sizeX, final double sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Size() {
        this.sizeX = Sizes.DEFAULT.getSizeX();
        this.sizeY = Sizes.DEFAULT.getSizeY();
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

}
