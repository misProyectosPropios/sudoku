package gaxi.sudoku;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	int[][] matriz = {
                {1, 2, 3, 4},
                {3, 4, 1, 2},
                {4, 3, 2, 1},
                {2, 1, 4, 3}
            };
    	
        new SudokuBoard(matriz);
    }
}
