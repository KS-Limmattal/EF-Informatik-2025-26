public class Methoden {

    public static void hours(int s) {
        int h = s / 3600;
        int m = s % 3600 / 60;
        s = s % 60;
        // Lösung mit formatiertem Print (eleganter)
        System.out.printf("%d:%02d:%02d\n", h, m, s);
        // Lösung mit ternärem Operator wie in der Aufgabenstellung gefordert (komplizierter)
        System.out.println(h + ":" + formatToTwoDecimals(m) + ":" + formatToTwoDecimals(s)); 
    }

    public static String formatToTwoDecimals(int n){
        return (n < 10) ? "0" + n : "" + n;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dy * dy + dx * dx);
    }

    public static void main(String[] args) {
        // Test-Code für Teilaufgabe a)
        System.out.println("Aufgabe a)");
        hours(0);
        hours(59);
        hours(60);
        hours(100);
        hours(3600);
        hours(4000);
        System.out.println();

        // Test-Code für Teilaufgabe b)
        System.out.println("Aufgabe b)");
        System.out.println(distance(0, 0, 0, 0));
        System.out.println(distance(0, 10, 0, 0));
        System.out.println(distance(0, 0, 0, 10));
        System.out.println(distance(10, 10, 0, 0));
        System.out.println(distance(0, 0, 10, 10));
    }
}