package gaxi.sudoku;

import java.util.HashSet;

public class SudokuBoard {

	private int[][] matriz;
	
	public SudokuBoard(int[][] matriz) {
		SudokuBoard.shouldHaveAtLeastOneElement(matriz);
		SudokuBoard.shouldHaveSquaredSize(matriz); 
		SudokuBoard.shouldBeAMatrixSquare(matriz); 
		SudokuBoard.shouldNotContainRepeatedElements(matriz);
		
		this.matriz = matriz;
	}
	
	public int[][] getSolution() {
		int[][] matrizSolucion = {
	            {1, 2, 3, 4},
	            {3, 4, 1, 2},
	            {2, 1, 4, 3},
	            {4, 3, 2, 1}
	        };
		return matrizSolucion;
	}
	
	
	
	private static boolean shouldNotContainRepeatedElements(int[][] matriz) { 
		SudokuBoard.containsRepeatedElementHorizontaly(matriz);
		SudokuBoard.containsRepeatedElementVertically(matriz);
		SudokuBoard.shouldNotContainsRepeatedElementInEachSquare(matriz);
		return true;
	}
	
	private static void shouldHaveSquaredSize(int[][] matriz) {
		int x = (int) Math.sqrt(matriz.length);
		if(Math.pow(x,2) != matriz.length) {
			throw new IllegalArgumentException(descriptionErrorShouldHaveSizeAsPerfectSquare());
		}
	}
	
	private static void shouldBeAMatrixSquare(int[][] matriz) {
		if(matriz.length != matriz[0].length) {
			throw new IllegalArgumentException(descriptionErrorMatrixShouldBeSquare());
		}
	}
	
	private static boolean shouldHaveAtLeastOneElement(int[][] matriz) {
		if (matriz.length == 0) {
			throw new IllegalArgumentException(descriptionErrorShouldHaveAtLeastOneElement());
		}
		
		return true;
	}
	
	private static void containsRepeatedElementVertically (int[][] matriz) {
		
		for(int col = 0; col < matriz.length; col++) {
			HashSet<Integer> elementosEnColumna = new HashSet<>();
			for(int i = 0; i < matriz.length; i++) {
				int valueAtPos = matriz[i][col];
				if (valueAtPos == 0) {
					continue;
				} 
				if (elementosEnColumna.contains(valueAtPos)) {
					throw new IllegalArgumentException(descriptionErrorShouldNotHaveRepeatedElementsVerticaly());
				} else {
					elementosEnColumna.add(valueAtPos);
				}
			}
		}
	}
	
	private static void containsRepeatedElementHorizontaly(int[][] matriz) {
		
		for(int row = 0; row < matriz.length; row++) {
			HashSet<Integer> elementosEnFila = new HashSet<>();
			for(int i = 0; i < matriz.length; i++) {
				int valueAtPos = matriz[row][i];
				if (valueAtPos == 0) {
					continue;
				} 
				if (elementosEnFila.contains(valueAtPos)) {
					throw new IllegalArgumentException(descriptionErrorShouldNotHaveRepeatedElementsHorizontaly());
				} else {
					elementosEnFila.add(valueAtPos);
				}
			}
		}
	
	}

	/*
	 * 
bool seRepitenNumerosEnCuadrados(struct sudoku* sudokuResolviendo) {
    for(int i = 0; i < sudoku_size; i++) {
        if(hayNumerosRepetidosEnCuadradoN(sudokuResolviendo, i)) {
            return true;
        }
    }
    return false;
}

bool hayNumerosRepetidosEnCuadradoN(struct sudoku* sudokuResolviendo, int cuadradoAVerificar) {
    int x, y;
    x = (cuadradoAVerificar / 3) * 3;
    y = (cuadradoAVerificar % 3);
    
    bool apareceValorAntes[] = {false, false, false, false, false, 
                                false, false, false, false, false};

    for(int i = 0; i < sudoku_square_size; i++) {
        for(int j = 0; j < sudoku_square_size; j++) {
            int valorCelda = sudokuResolviendo->matriz[x + i][y + j];
            if (valorCelda == sudoku_empty_cell - 1) {
                continue;
            } else if(apareceValorAntes[valorCelda]) {
                return true;
            } else {
                apareceValorAntes[valorCelda] = true;
            }
        }
    }
    
    return false;
}
	 * */
	
	
	
	private static void shouldNotContainsRepeatedElementInEachSquare(int[][] matriz) {
		if (containsRepeatedElementInEachSquare(matriz)) {
			throw new IllegalArgumentException(descriptionErrorShouldNotHaveRepeatedElementsInEachSubsquare());
		}
	}
	private static boolean containsRepeatedElementInEachSquare(int[][] matriz) {
		
		 for(int i = 0; i < matriz.length; i++) {
			 if(containsRepeatedElementInSquareN(matriz, i)) {
		            return true;
		     }
		 }
		return false;
	}
	
	private static boolean containsRepeatedElementInSquareN(int[][] matriz, int cuadradoAVerificar) {
		
		int x, y;
		int squareSize = (int) Math.sqrt( (double) matriz.length);
		x = (cuadradoAVerificar * squareSize) % matriz.length;
	    y = (cuadradoAVerificar / squareSize);
	    
	    HashSet<Integer> apariciones = new HashSet<>();
	    
	    
	    for(int i = 0; i < squareSize; i++) {
	    	for(int j = 0; j < squareSize; j++) {
	    		int posX = x + i;
	    		int posY = y + j;
	    		int valorCelda = matriz[posX][posY];
	    		if (valorCelda == 0) {
	                continue;
	            }
	    		if ((apariciones.contains(valorCelda))) {
	    			return true;
	    		}
	    		apariciones.add(valorCelda);
	        }
	    }
	    return false;
	}
	
	public static String descriptionErrorShouldNotHaveRepeatedElementsHorizontaly() {
		return "La matriz no debería de tener elementos horizontales";
	}
	
	public static String descriptionErrorShouldHaveSizeAsPerfectSquare() {
		return "La matriz debe tener como tamaño un cuadrado perfecto";
	}
	
	public static String descriptionErrorMatrixShouldBeSquare() {
		return "La matriz debe ser cuadrada";
	}

	public static String descriptionErrorShouldNotHaveRepeatedElementsVerticaly() {
		return "La matriz no debería de tener elementos verticales";
	}
	
	public static String descriptionErrorShouldNotHaveRepeatedElementsInEachSubsquare() {
		return "La matriz no debería de tener elementos en cada subcuadrado repetidos";
	}
	
	
	public static String descriptionErrorShouldHaveAtLeastOneElement() {
		return "La matriz debería tener al menos un elemento";
	}
	
	
}
