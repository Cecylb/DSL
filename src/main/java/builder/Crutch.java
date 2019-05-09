package builder;

public class Crutch {

    double x;
    double y;
    double n;
    String name;

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setSize(String name){
        this.name = name;
    }
    public void setN(double n){
        this.n = n;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getN(){
        return n;
    }
    public String getSize(){
        return name;
    }
}
