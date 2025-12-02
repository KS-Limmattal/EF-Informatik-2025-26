public class Fraction {
    private static int numberOfFractions;
    private int numerator, denominator;

    /*
     * Konstruktoren
     */
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.setDenominator(denominator);
        numberOfFractions++;
        // this.simplify();
    }
    
    public Fraction(Fraction f) {
        this(f.numerator, f.denominator);
    }

    public Fraction() {
        this(0, 1);
    }

    /*
    * Getter und Setter
    */

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero");
        } else {
            this.denominator = denominator;
        }
    }

    // wichtig für dynamische Version von multiply() und divide()
    public void setValues(int numerator, int denominator){
        this.setNumerator(numerator);
        this.setDenominator(denominator);
    }

    // Aufgabe 4.2.
    public static int getNumberOfFractions() {
        return numberOfFractions;
    }

    public static void setNumberOfFractions(int numberOfFractions) {
        Fraction.numberOfFractions = numberOfFractions;
    }

    /*
     * Operationen in-place (dynamisch)
     * --------------------------------
     * (Etwas zur Design-Entscheidung: Wenn alle Operationen sowohl statisch als auch dynamisch definiert sind, 
     * gibt es grundsätzlich drei Möglichkeiten zur Implementation:
     * - die statischen und dynamischen Methoden werden voneinander unabhängig implementiert (wie hier).
     *   Vorteil: Übersichtlichkeit
     *   Nachteil: (geringe) Code-Wiederholungen, d.h. es muss auch mehr getestet werden
     * - die dynamischen Methoden greifen auf die statischen zurück
     *   Vorteil: weniger Code-Wiederholungen, kurzer Code
     *   Nachteil: der (wahrscheinlich für die meisten Anwendungen vernachlässigbare) Vorteil des InPlace-Rechnens
     *   (geringerer Speicherbedarf) wird zunichte gemacht)
     * - die statischen Methoden greifen auf die dynamischen zu
     *   Vorteil: ebenfalls weniger Code-Wiederholungen, optimaler Speicherverbrauch
     *   Nachteil: die statischen Methoden werden umständlich, wenn jeweils zuerst mit dem Copy-Konstruktor 
     *   ein neues Objekt erzeugt, darauf die dynamische Methode aufgerufen werden muss und danach das Resultat ausgegeben wird)
     */
    public static int gcd(int x, int y){ // berechnet das kgV zweier Zahlen mit dem Euklidischen Algorithmus
        if (x % y == 0) {
            return y;
        } else {
            return gcd(y, x % y);
        }
    }
    
    public void simplify(){
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    } // Falls Brüche immer gekürzt gespeichert werden sollen, muss an drei Stellen Code entkommentiert werden.
    
    public void add(Fraction f) {
        this.setNumerator(this.numerator * f.denominator + this.denominator * f.numerator);
        this.setDenominator(this.denominator * f.denominator);
        // this.simplify();
    }

    public void negative(){
        this.setNumerator(-this.numerator);
    }

    public void subtract(Fraction f){
        this.add(negative(f));
    }

    public void multiply(Fraction f) {
        this.setValues(this.numerator * f.numerator, this.denominator * f.denominator);
        // this.simplify();
    }

    // kann ArithmeticException verursachen
    public void invert(){
        this.setValues(this.denominator, this.numerator);
    }

    public void divide(Fraction f){
        this.multiply(invert(f));
    }


    /*
     * Operationen statisch
     */
    public static Fraction add(Fraction f, Fraction g) {
        return new Fraction(f.numerator * g.denominator + f.denominator * g.numerator, f.denominator * g.denominator);
    }

    public static Fraction negative(Fraction f){
        return new Fraction(-f.numerator, f.denominator);
    }

    public static Fraction subtract(Fraction f, Fraction g){
        return add(negative(f), g);
    }

    public static Fraction multiply(Fraction f, Fraction g) {
        return new Fraction(f.numerator * g.numerator, f.denominator * g.denominator);
    }

    // kann ArithmeticException verursachen
    public static Fraction invert(Fraction f){
        return new Fraction(f.denominator, f.numerator);
    }

    public static Fraction divide(Fraction f, Fraction g){
        return multiply(f, invert(g));
    }

    /*
     * equals() und toString())
    */
    
    public boolean equals(Fraction f) {
        return this.numerator * f.denominator == this.denominator * f.numerator;
    }

    @Override
    public String toString() {
        return "[" + numerator + "/" + denominator + "]";
    }
}