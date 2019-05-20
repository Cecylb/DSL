package builder.objects.elements.parameters.data;

public interface ISize {

     enum Size {

         SMALL(1.0, 2.0),
         MEDIUM(2.0, 4.0),
         LARGE(3.0, 6.0),
         DEFAULT(1.0, 4.0);

         double x;
         double y;

         Size(final double x, final double y) {
             this.x = x;
             this.y = y;
         }

         public final double getSizeX(){
             return x;
         }
         public final double getSizeY(){
             return y;
         }
     }
}
