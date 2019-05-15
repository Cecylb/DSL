package builder;

import data.Objects;
import data.Ports;
import data.Rectangles;
import elements.DC;
import elements.TTr;
import elements.parameters.*;
import java.util.ArrayList;
import java.util.List;

public class ObjBuilder {

    String name = "name";
    String labelN = "Element";
    double sizeX = ISize.Size.DEFAULT.getSizeX();
    double sizeY = ISize.Size.DEFAULT.getSizeY();
    double labelX = ILabel.Label.DEFAULT.getLabelX();
    double labelY = ILabel.Label.DEFAULT.getLabelY();
    int inputsN = 1; // На случай, например, дешифратора, где количество задается пользователем
    List<Ports> inputsL = new ArrayList<>();
    List<Ports> outputsL = new ArrayList<>();
    List<Rectangles> rectangles = new ArrayList<>();
    String amount = IAmount.amount;
    String spacing = ISpacing.spacing;

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
                inputsL = getInputs(DC.inputs);
                inputsL.addAll(getInputsD(inputsN));
                outputsL = getOutputsD((int)Math.pow(2.0, (double)inputsN));
                System.out.println(outputsL);
                for(DC.Rectangles rec : DC.Rectangles.values()){
                    rectangles.add(new Rectangles.Builder()
                            .neX(rec.getNeX()*sizeX)
                            .neY(rec.getNeY()*sizeY*outputsL.size())
                            .swX(rec.getSwX()*sizeX)
                            .swY(rec.getSwY()*sizeY*outputsL.size())
                            .build()
                    );
                }
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
                inputsL = getInputs(TTr.inputs);
                outputsL = getOutputs(TTr.outputs);
                for(TTr.Rectangles rec : TTr.Rectangles.values()){
                    rectangles.add(new Rectangles.Builder()
                            .neX(rec.getNeX()*sizeX)
                            .neY(rec.getNeY()*sizeY)
                            .swX(rec.getSwX()*sizeX)
                            .swY(rec.getSwY()*sizeY)
                            .build()
                    );
                }
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
                .labelN(labelN)
                .inputs(inputsL)
                .outputs(outputsL)
                .amount(amount)
                .spacing(spacing)
                .rectangles(rectangles)
                .build();
        return element;
    }

    public List<Ports> getInputs(List inputs) {
        for (int i=0; i<inputs.size(); i++) {
            inputsL.add(new Ports.Builder()
                    .name(inputs.get(i).toString())
                    .label(inputs.get(i).toString())
                    .x(-sizeX * 2)
                    .y(((sizeY * 4) / (inputs.size() + 1) * (i + 1)) - sizeY * 2)
                    .position(i+1)
                    .build()
            );
        }
        return inputsL;
    }
    public List<Ports>getOutputs(List outputs) {
        for (int i=0; i<outputs.size(); i++) {
            outputsL.add(new Ports.Builder()
                    .name(outputs.get(i).toString())
                    .label(outputs.get(i).toString())
                    .x(sizeX * 2) // ВОЗМОЖНЫ ПРОБЛЕМЫ С ОТРИСОВКОЙ СОЕДИНЕНИЙ
                    .y(((sizeY * 4) / (outputs.size() + 1) * (i + 1)) - sizeY * 2)
                    .position(i+1)
                    .build()
            );
        }
        return outputsL;
    }

    public List<Ports> getInputsD(int inputsN){
        char alphabet = 'a';
        for(int i=0; i<inputsN; i++){
                inputsL.add(new Ports.Builder()
                        .name(Character.toString(alphabet))
                        .label(Integer.toString(i))
                        .x(-sizeX * 2)
                        .y(((sizeY * 4) / (inputsN + 1) * (i + 1)) - sizeY * 2)
                        .position(i+1)
                        .build()
                );
                alphabet++;
        }
        return inputsL;
    }
    public List<Ports> getOutputsD(int outputsN){
        char alphabet = 'a';
        for(int i=0; i<outputsN; i++){
                outputsL.add(new Ports.Builder()
                        .name(Character.toString(alphabet))
                        .label(Integer.toString(i))
                        .x(-sizeX * 2)
                        .y(((sizeY * 4) / (outputsN + 1) * (i + 1)) - sizeY * 2)
                        .position(i+1)
                        .build()
                );
                alphabet++;
        }
        return outputsL;
    }
  public void setSizeCRD(double sizeX, double sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    public void setSizeKW(String scale){
        switch (scale){
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
    public void setLabelCRD(double labelX, double labelY){
        this.labelX = labelX;
        this.labelY = labelY;
    }
    public void setLabelKW(String scale){
        switch (scale){
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
    public void setLabelN(String labelN){ this.labelN=labelN; }
    public void setInputsN(int inputsN){this.inputsN=inputsN; }
    public void setAmount(String amount){ this.amount = amount; }
    public void setSpacing(String spacing){ this.spacing = spacing; }

}