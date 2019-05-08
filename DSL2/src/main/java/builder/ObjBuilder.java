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
    List<Ports> ports = new ArrayList<>();
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
                ports = getPorts(TTr.inputs, TTr.outputs);
                rectangles.add(new Rectangles.Builder()
                .neX(1.0)
                .neY(1.0)
                .swX(1.0)
                .swY(1.0)
                .build());
                rectangles.add(new Rectangles.Builder()
                        .neX(1.0)
                        .neY(1.0)
                        .swX(0.4)
                        .swY(1.0)
                        .build());
                rectangles.add(new Rectangles.Builder()
                        .neX(-0.4)
                        .neY(1.0)
                        .swX(1.0)
                        .swY(0.7)
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
                .ports(ports)
                .amount(amount)
                .spacing(spacing)
                .rectangles(rectangles)
                .build();
        return element;
    }

    public List<Ports> getPorts(List inputs, List outputs) {
        List<Ports> ports = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            ports.add(new Ports.Builder()
                    .name(inputs.get(i).toString())
                    .x(0)
                    .y(sizeY / inputs.size() * (i + 1))
                    .build()
            );
        }
        for (int i = 0; i < outputs.size(); i++) {
            ports.add(new Ports.Builder()
                    .name(outputs.get(i).toString())
                    .x(sizeX)
                    .y(sizeY / outputs.size() * (i + 1))
                    .build()
            );
        }
        return ports;
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
    public void setAmount(String amount){
        this.amount = amount;
    }
    public void setSpacing(String spacing){
        this.spacing = spacing;
    }

}
