public class CarTester {
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
