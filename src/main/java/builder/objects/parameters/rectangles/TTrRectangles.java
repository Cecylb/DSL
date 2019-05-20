package builder.objects.parameters.rectangle;

import builder.objects.data.TTr;
import builder.objects.parameters.IO;
import data.Ports;
import data.Rectangles;

import java.util.ArrayList;
import java.util.List;

public class TTrRectangles {

    public List<Rectangles> getRectangles(double sizeX, double sizeY) {
        List<Rectangles> rectangles = new ArrayList<>();
        for (TTr.Rectangles rec : TTr.Rectangles.values()) {
            rectangles.add(new Rectangles.Builder()
                    .neX(rec.getNeX() * sizeX * 2)
                    .neY(rec.getNeY() * sizeY * 2)
                    .swX(rec.getSwX() * sizeX * 2)
                    .swY(rec.getSwY() * sizeY * 2)
                    .build()
            );
        }
        return  rectangles;
    }
}
