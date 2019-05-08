package elements.parameters;

public interface ILabel extends Parameter{

    enum Label {

        SMALL(1.0, 2.0),
        MEDIUM(2.0, 4.0),
        LARGE(3.0, 6.0),
        DEFAULT(1.0, 4.0);

        double x;
        double y;

        Label(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        public final double getLabelX(){
            return x;
        }
        public final double getLabelY(){
            return y;
        }
    }

    String name();

}
