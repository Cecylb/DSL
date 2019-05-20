package cecylb.dsl.translator.builder;

import cecylb.dsl.model.DC;
import cecylb.dsl.model.IOD;
import cecylb.dsl.model.Ports;
import cecylb.dsl.model.Rectangles;

import java.util.ArrayList;
import java.util.List;

public class DCBuilder {
    List<Ports> inputsL = new ArrayList<>();
    List<Ports> outputsL = new ArrayList<>();
    List<Rectangles> rectangles = new ArrayList<>();
    IOD io = new IOD();

    public DCBuilder(double sizeX, double sizeY, int inputsN) {
        List<Ports> inputsL = io.getInputsD(DC.INPUTS, inputsN, sizeX, sizeY);
        List<Ports> outputsL = io.getOutputsD((int) Math.pow(2.0, (double) inputsN), sizeX, sizeY);
        for (DC.Rectangles rec : DC.Rectangles.values()) {
            rectangles.add(new Rectangles.Builder()
                    .neX(rec.getNeX() * sizeX * 2)
                    .neY(rec.getNeY() * sizeY * outputsL.size() / 4)
                    .swX(rec.getSwX() * sizeX * 2)
                    .swY(rec.getSwY() * sizeY * outputsL.size() / 4)
                    .build()
            );
        }
    }
}
