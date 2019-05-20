package builder.objects.elements;

import builder.objects.elements.parameters.IO;
import data.Ports;
import data.Rectangles;

import java.util.ArrayList;
import java.util.List;

public class TTrBuilder {

    List<Ports> inputsL = new ArrayList<>();
    List<Ports> outputsL = new ArrayList<>();
    List<Rectangles> rectangles = new ArrayList<>();
    IO io = new IO();

    public TTrBuilder(double sizeX, double sizeY) {
        List<Ports> inputsL = io.getInputs(builder.objects.elements.data.TTr.inputs, sizeX, sizeY);
        List<Ports> outputsL = io.getOutputs(builder.objects.elements.data.TTr.outputs, sizeX, sizeY);
        for (
                builder.objects.elements.data.TTr.Rectangles rec : builder.objects.elements.data.TTr.Rectangles.values()) {
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
