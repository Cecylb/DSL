package cecylb.dsl.modelv2.tmp;

import org.immutables.value.Value;

import java.util.Map;

@Value.Modifiable
public interface OR extends TexObject {

    String objName = "≥1";

    Map<String, String> inputs = Map.of(
            "A", "",
            "B", ""
    );

    Map<String, String> outputs = Map.of(
            "Q", ""
    );


    enum Rectangles {
        FRAME(1.0, 1.0, 1.0, 1.0);
        //BORDER(0.5, 1.0, 0.5, 1.0);

        private final double neX;
        private final double neY;
        private final double swX;
        private final double swY;

        Rectangles(
                final double neX,
                final double neY,
                final double swX,
                final double swY) {
            this.neX = neX;
            this.neY = neY;
            this.swX = swX;
            this.swY = swY;
        }

        public double getNeX() {
            return neX;
        }

        public double getNeY() {
            return neY;
        }

        public double getSwX() {
            return swX;
        }

        public double getSwY() {
            return swY;
        }
    }
}