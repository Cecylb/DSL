package builder.objects.elements;

import builder.objects.elements.parameters.IOD;
import data.Ports;
import data.Rectangles;

import java.util.ArrayList;
import java.util.List;

public class DCBuilder {

    List<Ports> inputsL = new ArrayList<>();
    List<Ports> outputsL = new ArrayList<>();
    List<Rectangles> rectangles = new ArrayList<>();
    IOD io = new IOD();

    public DCBuilder(double sizeX, double sizeY, int inputsN) {
        List<Ports> inputsL = io.getInputsD(builder.objects.elements.data.DC.inputs, inputsN, sizeX, sizeY);
        List<Ports> outputsL = io.getOutputsD((int) Math.pow(2.0, (double) inputsN), sizeX, sizeY);
        for (
                builder.objects.elements.data.DC.Rectangles rec : builder.objects.elements.data.DC.Rectangles.values()) {
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
