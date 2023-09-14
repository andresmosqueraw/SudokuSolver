/*
 Sudoku
 Reglas:
 1. Cada fila debe contener los números del 1 al 9 sin repetirse.
 2. Cada columna debe contener los números del 1 al 9 sin repetirse.
 3. Cada subcuadrícula de 3x3 debe contener los números del 1 al 9 sin repetirse.

 Utilizamos: Backtracking, matrices, recursividad

 */
public class Main {

    // Tamaño del tablero 9*9
    private static final int GRID_SIZE = 9;

    public static void main(String[] args) {

        // Hacemos el tablero
        // Los ceros los representaremos como los espacios vacios
        int[][] board = {
                {7, 0, 2, 0, 5, 0, 6, 0, 0},
                {0, 0, 0, 0, 0, 3, 0, 0, 0},
                {1, 0, 0, 0, 0, 9, 5, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 9, 0},
                {0, 4, 3, 0, 0, 0, 7, 5, 0},
                {0, 9, 0, 0, 0, 0, 0, 0, 8},
                {0, 0, 9, 7, 0, 0, 0, 0, 5},
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 7, 0, 4, 0, 2, 0, 3}
        };

        // Imprimimos el tablero antes de resolverlo
        printBoard(board);
        System.out.println();

        if (solveBoard(board)){
            System.out.println("Sudoku resuelto");
        } else {
            System.out.println("Es imposible resolver el sudoku");
        }

        // Imprimimos el tablero despues de resolverlo
        printBoard(board);
    }

    private static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % 3 == 0 && row != 0){
                System.out.println("---------------------");
            }
            for (int column = 0; column < GRID_SIZE; column++) {
                if (column % 3 == 0 && column != 0){
                    System.out.print("| ");
                }
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }

    // Método para validar si el número existe en una fila
    // Parametro 1: Tablero
    // Parametro 2: Número a validar
    // Parametro 3: Fila a validar
    private static boolean isNumberInRow(int[][] board, int number, int row){
        // GRID_SIZE = 9
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number){
                return true;
            }
        }
        return false;
        // Explicacion: Si el número existe en la fila, retorna true, si no, retorna false
    }


    // Método para validar si el número existe en una columna
    // Parametro 1: Tablero
    // Parametro 2: Número a validar
    // Parametro 3: Columna a validar
    private static boolean isNumberInColumn(int[][] board, int number, int column){
        // GRID_SIZE = 9
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == number){
                return true;
            }
        }
        return false;
        // Explicacion: Si el número existe en la columna, retorna true, si no, retorna false
    }

    // Método para validar si el número existe en una subcuadrícula de 3*3
    // Parametro 1: Tablero
    // Parametro 2: Número a validar
    // Parametro 3: Fila a validar
    // Parametro 4: Columna a validar
    // (En este tomamos tanto la fila como la columna para saber en que subcuadrícula de 3*3 estamos)
    private static boolean isNumberInBox(int[][] board, int number, int row, int column){
        // GRID_SIZE = 9
        // Para saber en que fila de cuadriculas estamos es el boxrow
        // Para saber en que columna de cuadriculas estamos es el boxcolumn
        // Eso nos dara la esquina superior izquierda de la subcuadricula de 3*3 que estamos buscando
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        // Recorremos la subcuadrícula de 3*3
        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                // Si el número existe en la subcuadrícula, retorna true, si no, retorna false
                if (board[i][j] == number){
                    return true;
                }
            }
        }
        return false;
    }


    // Método para validar si el número es válido
    // Parametro 1: Tablero
    // Parametro 2: Número a validar
    // Parametro 3: Fila a validar
    // Parametro 4: Columna a validar
    private static boolean isValidPlacement(int[][] board, int number, int row, int column){
        // Si el número no existe en la fila, columna o subcuadrícula, retorna true, si no, retorna false
        // Lo que hacemos es llamar los tres métodos anteriores y si ninguno retorna true, retorna true
        // Si alguno retorna true, retorna false
        return !isNumberInRow(board, number, row) &&
                !isNumberInColumn(board, number, column) &&
                !isNumberInBox(board, number, row, column);
        // Aqui sabremos si el número es válido o no ya que no esta en ninguna fila, columna o subcuadrícula
    }

    // Algoritmo para resolver el sudoku (La carne del programa)
    // Los metodos en los que hacemos recursividad y backtracking

    // Como el algoritmo trabaja:
    // 1. Busca un espacio vacio
    // 2. Valida si el número es válido teniendo en cuenta las reglas del sudoku
    // 3. Si no es válido, intenta con otro número
    // 4. Si es válido, lo pone en el espacio vacio y ahora utilizariamos recursividad
    //   para volver al paso 1 y buscar otro espacio vacio

    // ¿Que pasa si vamos a un espacio vacio y no hay ningun número válido?
    // Ahi utilizariamos el backtracking
    // Volvemos al espacio anterior que ya habiamos rellenado y probamos con otro número.
    // Haria este procesos hasta que complete el tablero con números válidos


    // Método para resolver el sudoku
    private static boolean solveBoard(int[][] board) {
        // Nested for loop para recorrer el tablero
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {

                // Si el espacio esta vacio (El espacio vacio lo representamos con un 0)
                if (board[row][column] == 0) {

                    // Probamos con los números del 1 al 9 para rellenar el espacio vacio
                    // Con otro nested for loop
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {

                        // Si el número es válido
                        if (isValidPlacement(board, numberToTry, row, column)) {

                            // Lo ponemos en el espacio vacio
                            board[row][column] = numberToTry;

                            // Llamamos recursivamente al método para buscar otro espacio vacio
                            // E intentar rellenar el tablero
                            // Si el método retorna true, significa que ya lleno el tablero
                            if (solveBoard(board)) {
                                return true;
                            } else {
                                // Entonces volvemos al espacio anterior y probamos con otro número
                                // Esto es el backtracking
                                // Si no hay ningún número válido, ponemos el espacio vacio como 0
                                // Y volvemos al espacio anterior
                                // Y probamos con otro número
                                // Esto se repite hasta que complete el tablero
                                // Si no puede completar el tablero, retorna false
                                // Y el tablero no se pudo completar
                                // Y el sudoku no tiene solución
                                board[row][column] = 0;
                            }
                        }
                    }
                    // Si no hay ningún número válido, retorna false diciendo que no pudo llenar el tablero
                    return false;
                }
            }
        }
        return true;
    }

}