package builder.objects;

import builder.objects.objBuilders.DCBuilder;
import builder.objects.objBuilders.TTRBuilder;
import data.Objects;
import data.Rectangles;
import elements.DC;
import elements.TTr;

public class SetShape {
    public void setShape(String name) {
        switch (name) {
            case "AC":
                break;
            case "ARSTr":
                break;
            case "CTTr":
                break;
            case "DC":
                new DCBuilder().setShape();
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
                new TTRBuilder().setShape();
                break;
            case "JKTr":
                break;
        }
    }
}
