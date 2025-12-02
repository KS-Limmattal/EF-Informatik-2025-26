public class FractionTester {
    public static void main(String[] args) {
        Fraction f = new Fraction();
        // Der folgende Code funktioniert nur, wenn die Felder von Fraction nicht privat sind:
        // f.numerator = 12;
        // f.denominator = 0;
        // System.out.println(f.numerator + "/" + f.denominator);

        // toString():
        System.out.println("f = " + f);

        // Encapsulation, Getter und Setter:
        // f.setDenominator(0);
        f.setNumerator(1);
        f.setDenominator(2);
        System.out.println("f = " + f);
        System.out.println();

        // Copy-Konstruktor und equals():
        Fraction g = new Fraction(f);
        System.out.println("Objektgleichheit: " + (f == g));
        System.out.println("Inhaltsgleichheit: " + f.equals(g));
        System.out.println("Gleichheit eines erweiterten Bruchs: " + f.equals(new Fraction(-2, -4)));
        System.out.println();
        
        // Klassen- und Instanzenmethoden:
        g = new Fraction(2, 3);
        Fraction h = Fraction.add(f, g);
        System.out.println("Addition mit Klassenmethode: " + h);
        f.add(g);
        System.out.println("Addition mit Instanzenmethode: " + f);
        System.out.println();

        // Aufgabe 4.1.
        f.setValues(1, 2);
        h = Fraction.subtract(f, g);
        System.out.println("Subtraktion mit Klassenmethode: " + h);
        f.subtract(g);
        System.out.println("Subtraktion mit Instanzenmethode: " + f);
        System.out.println();        

        f.setValues(1, 2);
        h = Fraction.multiply(f, g);
        System.out.println("Multiplikation mit Klassenmethode: " + h);
        f.multiply(g);
        System.out.println("Multiplikation mit Instanzenmethode: " + f);
        System.out.println();

        f.setValues(1, 2);
        h = Fraction.divide(f, g);
        System.out.println("Division mit Klassenmethode: " + h);
        f.divide(g);
        System.out.println("Division mit Instanzenmethode: " + f);
        System.out.println();
        // die folgenden Zeilen erzeugen einen Fehler
        // f.divide(new Fraction(0, 1));
        // Fraction.divide(f, new Fraction(0, 1));
        
        // simplify()
        f.setValues(12, 18);
        f.simplify();
        System.out.println(f);

        // statische Variablen:
        System.out.println("Anzahl Brüche: " + Fraction.getNumberOfFractions());
    }

}
