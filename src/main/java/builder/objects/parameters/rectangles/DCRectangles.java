package builder.objects.parameters.rectangle;

import builder.objects.data.DC;
import data.Ports;
import data.Rectangles;

import java.util.ArrayList;
import java.util.List;

public class DCRectangles {

    public List<Rectangles> getRectangles(double sizeX, double sizeY, List<Ports> outputsL) {
        List<Rectangles> rectangles = new ArrayList<>();
        for (DC.Rectangles rec : DC.Rectangles.values()) {
            rectangles.add(new Rectangles.Builder()
                    .neX(rec.getNeX() * sizeX * 2)
                    .neY(rec.getNeY() * sizeY * outputsL.size() / 4)
                    .swX(rec.getSwX() * sizeX * 2)
                    .swY(rec.getSwY() * sizeY * outputsL.size() / 4)
                    .build()
            );
        }
        return rectangles;
    }
}
