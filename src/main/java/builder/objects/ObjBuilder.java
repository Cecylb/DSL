package builder.objects;

import data.Objects;
import data.Ports;
import data.Rectangles;
import elements.parameters.*;
import java.util.ArrayList;
import java.util.List;

public class ObjBuilder {

    public String name = "name";
    public String labelN = "Element";
    public double sizeX = ISize.Size.DEFAULT.getSizeX();
    public double sizeY = ISize.Size.DEFAULT.getSizeY();
    public double labelX = ILabel.Label.DEFAULT.getLabelX();
    public double labelY = ILabel.Label.DEFAULT.getLabelY();
    public int inputsN = 1; // На случай, например, дешифратора, где количество задается пользователем
    public List<Ports> inputsL = new ArrayList<>();
    public List<Ports> outputsL = new ArrayList<>();
    public List<Rectangles> rectangles = new ArrayList<>();
    public int amount = IAmount.amount;
    public int spacing = ISpacing.spacing;

    public Objects getObject() {
        Objects object = new Objects.Builder()
                .objName(name)
                .sizeX(sizeX)
                .sizeY(sizeY)
                .labelX(labelX)
                .labelY(labelY)
                .labelN(labelN)
                .inputs(inputsL)
                .outputs(outputsL)
                .amount(amount)
                .spacing(spacing)
                .rectangles(rectangles)
                .build();
        return object;
    }

    public void setShape(String name) {
        this.name = name;
        SetShape setShape = new SetShape();
        setShape.setShape(name);
    }

    public void setSizeCRD(double sizeX, double sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void setSizeKW(String scale) {
        switch (scale) {
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

    public void setLabelCRD(double labelX, double labelY) {
        this.labelX = labelX;
        this.labelY = labelY;
    }

    public void setLabelKW(String scale) {
        switch (scale) {
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

    public void setLabelN(String labelN) { this.labelN = labelN; }
    public void setInputsN(int inputsN) { this.inputsN = inputsN; }
    public void setAmount(int amount) { this.amount = amount - 1; }
    public void setSpacing(int spacing) { this.spacing = spacing; }

    public String getLabelN() { return  labelN; }
    public int getInputsN() { return inputsN; }
    public int getAmount() { return amount; }
    public int getSpacing() { return spacing; }


}