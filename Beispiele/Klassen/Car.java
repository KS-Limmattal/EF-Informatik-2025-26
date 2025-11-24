public class Car {
    String make;
    int v = 0;

    public String toString() {
        if (v == 0) {
            return "The " + make + " car is standing still.";
        } else {
            return "The " + make + " car is driving with a velocity of " + v + "km/h.";
        }
    }

    public void accelerate(){
        v = v + 10;
    }

    
}
