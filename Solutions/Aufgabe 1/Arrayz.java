import java.util.Arrays;

public class Arrayz {
        static double[] sort(double[] input) {
            input = input.clone(); // sonst wird das ursprüngliche Array sortiert
            // s. https://www.kstbb.de/informatik/oo/07/7_4_Primitive_Variable_vs_Referenzvariable.html
            
            if (input.length <= 1) { // Algorithmus unten gibt in diesem Fall einen Fehler, aber das Array ist dann
                                     // schon sortiert.
                return input;
            }

            boolean sorted = false;
            while (!sorted) {
                sorted = true;
                for (int i = 0; i < input.length - 1; i++) {
                    if (input[i] > input[i + 1]) {
                        sorted = false;
                        double temp = input[i];
                        input[i] = input[i + 1];
                        input[i + 1] = temp;
                    }
                }
            }
            return input;
        }
    
        static boolean isMagicSquare(int[][] square) {
            int i, j, n = square.length;
    
            // --- magicSum = first diagonal sum 
            int magicSum = 0;
            for (i = 0; i < n; i++){
                magicSum += square[i][i];
            }
    
            // check second diagonal
            int secondDiagonalSum = 0;
            for (i = 0; i < n; i++){
                secondDiagonalSum += square[i][n - i - 1];
            }
            if (secondDiagonalSum != magicSum){
                return false;
            }
            
            // --- check rows and columns
            for (i = 0; i < n; i++) {
                int rowSum = 0;
                int columnSum = 0;
                for (j = 0; j < n; j++){
                    rowSum += square[i][j];
                    columnSum += square[j][i];
                }
                if (rowSum != magicSum || columnSum != magicSum){
                    return false;
                }
            }
            return true;
        }
       
        public static void main(String[] args) {
  
            // Test-Code zu Aufgabe e)
            System.out.println("Aufgabe e)");
            double[] values = { 5, 4.3, 4.1, -4.1, 0, -1, 2 };
            System.out.println(Arrays.toString(values));
            System.out.println("sorted: " + Arrays.toString(sort(values)));
            System.out.println(Arrays.toString(values));
            System.out.println();
    
            // Test-Code zu Aufgabe f)
            System.out.println("Aufgabe f)");
            int[][] magicSquare = new int[][] { { 12, 6, 15, 1 },
                    { 13, 3, 10, 8 },
                    { 2, 16, 5, 11 },
                    { 7, 9, 4, 14 }
            };
            System.out.println(isMagicSquare(magicSquare));
            int[][] nonMagicSquare = new int[][] { { 1, 6, 15, 1 },
                    { 13, 3, 10, 8 },
                    { 2, 16, 5, 11 },
                    { 7, 9, 4, 14 }
            };
            System.out.println(isMagicSquare(nonMagicSquare));
            System.out.println();
        }
}
