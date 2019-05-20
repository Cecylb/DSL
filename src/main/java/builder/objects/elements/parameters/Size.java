package builder.objects.elements.parameters;

import builder.objects.elements.parameters.data.ISize;

public class Size {

    double sizeX = ISize.Size.DEFAULT.getSizeX();
    double sizeY = ISize.Size.DEFAULT.getSizeY();

    public Size(String sizeKW){
        switch (sizeKW){
            case "small":
                sizeX = ISize.Size.SMALL.getSizeX();
                sizeY = ISize.Size.SMALL.getSizeY();
                break;
            case "mediun":
                sizeX = ISize.Size.MEDIUM.getSizeX();
                sizeY = ISize.Size.MEDIUM.getSizeY();
                break;
            case "large":
                sizeX = ISize.Size.LARGE.getSizeX();
                sizeY = ISize.Size.LARGE.getSizeY();
                break;
        }
    }
    public Size(double sizeX, double sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    public Size(){}

    public double getSizeX(){ return sizeX; }
    public double getSizeY(){ return sizeY; }
}
