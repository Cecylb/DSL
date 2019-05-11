package builder;

import data.Objects;
import data.Ports;
import data.Rectangles;
import elements.TTr;
import elements.parameters.*;
import java.util.ArrayList;
import java.util.List;

public class ObjBuilder {

    String name = "name";
    double sizeX = ISize.Size.DEFAULT.getSizeX();
    double sizeY = ISize.Size.DEFAULT.getSizeY();
    double labelX = ILabel.Label.DEFAULT.getLabelX();
    double labelY = ILabel.Label.DEFAULT.getLabelY();
    List<Ports> inputsL = new ArrayList<>();
    List<Ports> outputsL = new ArrayList<>();
    List<Rectangles> rectangles = new ArrayList<>();
    String amount = IAmount.amount;
    String spacing = ISpacing.spacing;

    public ObjBuilder(){}

    public Objects getResult(String name) {
        this.name = name;
        switch (name) {
            case "AC":
                break;
            case "ARSTr":
                break;
            case "CTTr":
                break;
            case "DC":
                break;
            case "DTr":
                break;
            case "LDC":
                break;
            case "MX":
                break;
            case "RDC":
                break;
            case "PDC":
                break;
            case "SC":
                break;
            case "ShRSTr":
                break;
            case "SRSTr":
                break;
            case "TTr":
                TTr ttr;
                inputsL = getInputs(TTr.inputs);
                outputsL = getOutputs(TTr.outputs);
                rectangles.add(new Rectangles.Builder()
                        .neX(sizeX)
                        .neY(sizeY*2)
                        .swX(sizeX)
                        .swY(sizeY*2)
                        .build());
                rectangles.add(new Rectangles.Builder()
                        .neX(sizeX*2)
                        .neY(sizeY*2)
                        .swX(sizeX*2)
                        .swY(sizeY*2)
                        .build());
                rectangles.add(new Rectangles.Builder()
                        .neX(-sizeX)
                        .neY(-(sizeY/2)) //СВЯЗАТЬ С ФОРМУЛОЙ КОЛИЧЕСТВА ПОРТОВ
                        .swX(sizeX*2)
                        .swY(sizeY*2)
                        .build());
                break;
            case "JKTr":
                break;
        }
        Objects element = new Objects.Builder()
                .objName(name)
                .sizeX(sizeX)
                .sizeY(sizeY)
                .labelX(labelX)
                .labelY(labelY)
                .labelN(name)
                .inputs(inputsL)
                .outputs(outputsL)
                .amount(amount)
                .spacing(spacing)
                .rectangles(rectangles)
                .build();
        return element;
    }

    public List<Ports> getInputs(List inputs) {
        List<Ports> ports = new ArrayList<>();
        for (int i = inputs.size()-1; i >=0; i--) {
            inputsL.add(new Ports.Builder()
                    .name(inputs.get(i).toString())
                    .x(-sizeX * 2)
                    .y(((sizeY * 4) / (inputs.size() + 1) * (i + 1)) - sizeY * 2)
                    .build()
            );
        }
        return inputsL;
    }
    public List<Ports>getOutputs(List outputs) {
        for (int i = outputs.size() - 1; i >= 0; i--) {
            outputsL.add(new Ports.Builder()
                    .name(outputs.get(i).toString())
                    .x(sizeX * 2) // ВОЗМОЖНЫ ПРОБЛЕМЫ С ОТРИСОВКОЙ СОЕДИНЕНИЙ
                    .y(((sizeY * 4) / (outputs.size() + 1) * (i + 1)) - sizeY * 2)
                    .build()
            );
        }
        return outputsL;
    }

    public void setSizeCRD(double sizeX, double sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    public void setSizeKW(String scale){
        switch (scale){
            case "SMALL":
                sizeX = ISize.Size.SMALL.getSizeX();
                sizeY = ISize.Size.SMALL.getSizeY();
                break;
            case "MEDIUM":
                sizeX = ISize.Size.MEDIUM.getSizeX();
                sizeY = ISize.Size.MEDIUM.getSizeY();
                break;
            case "LARGE":
                sizeX = ISize.Size.LARGE.getSizeX();
                sizeY = ISize.Size.LARGE.getSizeY();
                break;
        }
    }
    public void setLabelCRD(double labelX, double labelY){
        this.labelX = labelX;
        this.labelY = labelY;
    }
    public void setLabelKW(String scale){
        switch (scale){
            case "SMALL":
                labelX = ILabel.Label.SMALL.getLabelX();
                labelY = ILabel.Label.SMALL.getLabelY();
                break;
            case "MEDIUM":
                labelX = ILabel.Label.MEDIUM.getLabelX();
                labelY = ILabel.Label.MEDIUM.getLabelY();
                break;
            case "LARGE":
                labelX = ILabel.Label.LARGE.getLabelX();
                labelY = ILabel.Label.LARGE.getLabelY();
                break;
        }
    }
    public void setAmount(String amount){ this.amount = amount; }
    public void setSpacing(String spacing){ this.spacing = spacing; }

}
