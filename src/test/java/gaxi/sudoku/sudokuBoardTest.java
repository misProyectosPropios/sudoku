package gaxi.sudoku;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class sudokuBoardTest {
	int[][] matrizCorrecta = {
            {1, 2, 3, 4},
            {3, 4, 1, 2},
            {4, 3, 2, 1},
            {2, 1, 4, 3}
        };
	
	int[][] matrizConRepetidosHorizontalmente = {
            {1, 1, 3, 4},
            {3, 4, 1, 2},
            {4, 3, 2, 1},
            {2, 1, 4, 3}
        };
	
	int[][] matrizConRepetidosVerticalmente = {
            {1, 2, 3, 4},
            {1, 4, 0, 2},
            {4, 3, 2, 1},
            {2, 1, 4, 3}
        };
	
	@Test
	void matrizDebeTenerValores() {
		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class, () -> {
					new SudokuBoard(matrizConRepetidosHorizontalmente);
				});
		assertEquals(SudokuBoard.descriptionErrorShouldNotHaveRepeatedElementsHorizontaly() , exception.getMessage());	
	}
	

	@Test
	void matrizConRepetidosVerticalmenteTiraError() {
		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class, () -> {
					new SudokuBoard(matrizConRepetidosVerticalmente);
				});
		assertEquals(SudokuBoard.descriptionErrorShouldNotHaveRepeatedElementsVerticaly(), exception.getMessage());
		
	}
	
	@Test
	void matrizDebeSerCuadrada() {
		
		
		int[][] matrizNoCuadrada = {
	            {1, 2, 3},
	            {1, 4, 0},
	            {4, 3, 2},
	            {2, 1, 4}
	        };
		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class, () -> {
					new SudokuBoard(matrizNoCuadrada);
				});
		assertEquals(SudokuBoard.descriptionErrorMatrixShouldBeSquare(), exception.getMessage());
	}
	
	@Test
	void matrizDebeSerNoNula() {
		
		
		
		int[][] matrizNoCuadrada = new int[0][0];
		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class, () -> {
					new SudokuBoard(matrizNoCuadrada);
				});
		assertEquals(SudokuBoard.descriptionErrorShouldHaveAtLeastOneElement(), exception.getMessage());
		
	}
	
	@Test
	void matrizDebeTenerLargoUnCuadradoPerfecto() {
		
		int[][] matrizNoCuadrada = new int[5][5];
		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class, () -> {
					new SudokuBoard(matrizNoCuadrada);
				});
		assertEquals(SudokuBoard.descriptionErrorShouldHaveSizeAsPerfectSquare(), exception.getMessage());
		
	}
	
	@Test
	void matrizNoDebeTenerRepetidosEnCadaSubcuadrado() {
		
		int[][] matrizConRepetidosEnCuadrados = {
	            {0, 0, 0, 0},
	            {0, 0, 0, 0},
	            {1, 0, 0, 0},
	            {0, 1, 0, 0}
	        };
		
		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class, () -> {
					new SudokuBoard(matrizConRepetidosEnCuadrados);
				});
		
		String messageErrorGot = exception.getMessage();
		String messageErrorExpected = SudokuBoard.descriptionErrorShouldNotHaveRepeatedElementsInEachSubsquare();
		assertEquals(messageErrorExpected, messageErrorGot);
	}
	
	@Test
	void matrizRecusionModifica() {
		
		int[][] matrizConUnValorPorRecorrerFinal = {
	            {1, 2, 3, 4},
	            {3, 4, 1, 2},
	            {2, 1, 4, 3},
	            {4, 3, 2, 0}
	        };
		
		SudokuBoard board = new SudokuBoard(matrizConUnValorPorRecorrerFinal);

		
		int[][] solutionGot = board.getSolution();
		
		int[][] matrizSolucion = {
	            {1, 2, 3, 4},
	            {3, 4, 1, 2},
	            {2, 1, 4, 3},
	            {4, 3, 2, 1}
	        };
		
		assertArrayEquals(matrizSolucion, solutionGot);
	}
	
	@Test
	void getSolutionHaceBacktrackingHaciaLaDerecha() {
		int[][] matrizConUnVariosValoresAlFinalPorRecorrer = {
	            {4, 3, 2, 1},
	            {2, 1, 3, 4},
	            {3, 4, 1, 2},
	            {0, 0, 0, 0}
	        };
		
		SudokuBoard board = new SudokuBoard(matrizConUnVariosValoresAlFinalPorRecorrer);
		
		
		int[][] solutionGot = board.getSolution();
		
		int[][] matrizSolucion = {
				{4, 3, 2, 1},
	            {2, 1, 3, 4},
	            {3, 4, 1, 2},
	            {1, 2, 4, 3}
	        };
		
		assertArrayEquals(matrizSolucion, solutionGot);
		
	}
	
}
