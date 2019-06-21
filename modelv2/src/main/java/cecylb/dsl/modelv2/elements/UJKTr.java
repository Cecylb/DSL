package cecylb.dsl.modelv2.elements;
import org.immutables.value.Value;

import java.util.Map;
import java.util.TreeMap;

@Value.Modifiable
public interface UJKTr extends TexObject {

    String[] inputs = {"S", "J", "C", "K", "R"};

    Map<String, String> outputs = new TreeMap<>(String::compareTo){{
        put("Q", "");
        put("Qn", "o");
    }};

    enum Rectangles {
        FRAME(1.0, 1.0, 1.0, 1.0),
        BORDER(0.5, 1.0, 0.5, 1.0),
        PORT(-0.5, -0.25, 1.0, 0.25);

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
