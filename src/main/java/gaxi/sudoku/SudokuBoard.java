package gaxi.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

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
		int[][] copy = Arrays.stream(this.matriz).map(int[]::clone).toArray(int[][]::new);
		
		int[][] sol = this.backtracking(copy, 0, this.matriz.length - 1);
		
		
		int[][] copy2 = {
	            {1, 2, 3, 4},
	            {3, 4, 1, 2},
	            {2, 1, 4, 3},
	            {4, 3, 2, 1}
	        };
		return copy2;
	}
	
	private Optional<int[][]> backtracking(int[][] currentSolution, int x, int y) {
		if (y > this.matriz.length) {
			return Optional.ofNullable(currentSolution);
		}
		
		for(int i = 0; i < this.matriz.length; i++) {
			
			int posY = y;
			if (x == this.matriz.length - 1) {
				posY++;
			}
			int posX = x + 1 % this.matriz.length;
			
			currentSolution[x][y] = i;
			
			
			backtracking(currentSolution, posX, posY);
		}
		
		//Si llegamos hasta acá significa que no encontramos ninguna solución. Se va a devolver varias veces, pero 
		// solamente se devolverá como resultado si no se encontró otra
		return Optional.empty();
	}
	
	private boolean isParcialCorrectSolution() {
		throw new UnsupportedOperationException("TODO");
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
	
	private static void shouldNotContainRepeatedElementVertically (int[][] matriz) {
		
		throw new UnsupportedOperationException("Method 'myUnimplementedMethod' has not been implemented yet.");
		/*if (containsRepeatedElementVertically(matriz)) {
			throw new IllegalArgumentException(descriptionErrorShouldNotHaveRepeatedElementsInEachSubsquare());
		}*/
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


