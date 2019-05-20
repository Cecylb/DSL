package cecylb.dsl.model;

public enum  Sizes {
     SMALL(1.0, 2.0),
     MEDIUM(2.0, 4.0),
     LARGE(3.0, 6.0),
     DEFAULT(1.0, 4.0);

     private final double x;
     private final double y;

     Sizes(final double x, final double y) {
         this.x = x;
         this.y = y;
     }

     public final double getSizeX() {
         return x;
     }

     public final double getSizeY() {
             return y;
     }

}
