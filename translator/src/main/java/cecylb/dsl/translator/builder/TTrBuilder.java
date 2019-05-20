package cecylb.dsl.translator.builder;

import cecylb.dsl.model.IO;
import cecylb.dsl.model.Ports;
import cecylb.dsl.model.Rectangles;
import cecylb.dsl.model.TTr;

import java.util.ArrayList;
import java.util.List;

public class TTrBuilder {

    List<Ports> inputsL = new ArrayList<>();
    List<Ports> outputsL = new ArrayList<>();
    List<Rectangles> rectangles = new ArrayList<>();
    IO io = new IO();

    public TTrBuilder(double sizeX, double sizeY) {
        List<Ports> inputsL = io.getInputs(TTr.INPUTS, sizeX, sizeY);
        List<Ports> outputsL = io.getOutputs(TTr.OUTPUTS, sizeX, sizeY);
        for (TTr.Rectangles rec : TTr.Rectangles.values()) {
            rectangles.add(new Rectangles.Builder()
                    .neX(rec.getNeX() * sizeX * 2)
                    .neY(rec.getNeY() * sizeY * 2)
                    .swX(rec.getSwX() * sizeX * 2)
                    .swY(rec.getSwY() * sizeY * 2)
                    .build()
            );
        }
    }
}
