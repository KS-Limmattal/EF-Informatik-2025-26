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

    public static void main(String[] args) {
        Car car1 = new Car();
        car1.make = "Volvo";
        car1.v = 40;

        System.out.println(car1.toString());
        car1.accelerate();
        System.out.println(car1.toString());
        
        Car car2 = new Car();
        car2.make = "Tesla";
        System.out.println(car2.toString());
    }
}
