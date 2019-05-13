package elements;

import data.Rectangles;

import javax.swing.text.Element;
import java.util.List;

public interface TTr extends Element {

    List<String> inputs = List.of("S", "T", "C", "R");
    List<String> outputs = List.of("Q", "Qn");
    enum Rectangles {

        FRAME(1.0, 1.0, 1.0, 1.0),
        BORDER(0.5,1.0,0.5,1.0),
        PORT(-0.5, -0.25, 1.0, 1.0);

        double neX;
        double neY;
        double swX;
        double swY;

        Rectangles(double neX, double neY, double swX, double swY){
            this.neX=neX;
            this.neY=neY;
            this.swX=swX;
            this.swY=swY;
        }
    }
}
