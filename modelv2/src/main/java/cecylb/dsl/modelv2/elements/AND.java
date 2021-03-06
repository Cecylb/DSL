package cecylb.dsl.modelv2.elements;

import org.immutables.value.Value;

import java.util.Map;
import java.util.TreeMap;

@Value.Modifiable
    public interface AND extends TexObject {

        String objName = "AND";

    Map<String, String> inputs = new TreeMap<>(String::compareTo){{
        put("B", " ");
        put("A", " ");
    }};

    Map<String, String> outputs = Map.of(
            "Q", " "
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

    @Value.Default
    default Double posX() { return 0.0; }
    @Value.Default
    default Double posY() { return 15.0; }
    @Value.Default
    default Double sizeX() { return 0.5; }
    @Value.Default
    default Double sizeY() { return 0.5; }
    @Value.Default
    default Double labelX() { return 0.5; }
    @Value.Default
    default Double labelY() { return 0.5; }
    @Value.Default
    default String labelName() { return "AND"; }
    @Value.Default
    default Integer input() { return 1; }
    @Value.Default
    default Integer amount() { return 1; }
    @Value.Default
    default Integer spacing() { return 1; }
    }
