package gaxi.sudoku;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SudokuGUI extends Application {

	TextField[][] cells = new TextField[9][9];
	SudokuBoard solver = new SudokuBoard(new int[9][9]);
	
    @Override
    public void start(Stage stage) {
    	BorderPane root = new BorderPane();    	
        GridPane gridPane = new GridPane();

        int size = 9;

        // Configure columns
        for (int i = 0; i < size; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / size);
            gridPane.getColumnConstraints().add(col);
        }

        // Configure rows
        for (int i = 0; i < size; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / size);
            gridPane.getRowConstraints().add(row);
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                boolean editable = true; // later: based on puzzle
                TextField cell = createCell(x, y, editable);

                cells[y][x] = cell;

                gridPane.add(cell, x, y);
                GridPane.setHgrow(cell, Priority.ALWAYS);
                GridPane.setVgrow(cell, Priority.ALWAYS);
            }
        }
        root.setCenter(gridPane);
        root.setRight(createRightPanel());
        root.setBottom(createBottomPanel());
        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("Sudoku Grid");
        stage.setScene(scene);
        stage.show();
    }

    
    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox(5); // spacing

        bottomPanel.setStyle("-fx-padding: 10; -fx-background-color: #ddd;");

        for (int i = 1; i <= 9; i++) {
            Button btn = new Button(String.valueOf(i));
            btn.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(btn, Priority.ALWAYS);
            bottomPanel.getChildren().add(btn);
        }

        // Optional: erase button
        Button clear = new Button("X");
        clear.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(clear, Priority.ALWAYS);

        bottomPanel.getChildren().add(clear);

        return bottomPanel;
    }


    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10); // spacing

        rightPanel.setPrefWidth(120); // controls ~20% depending on window size
        rightPanel.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        Button btn1 = new Button("Solve");
        Button btn2 = new Button("Clear");
        Button btn3 = new Button("Hint");

        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);

        rightPanel.getChildren().addAll(btn1, btn2, btn3);
        
        btn1.setOnAction(event -> {
            System.out.println("Button clicked!");
            //Get the matrix
            //Call the solver 
            //Update the GUI with the solution
			int[][] board = getBoard();
			solver = new SudokuBoard(board);
			
			//Debug option
			solver.printBoard();
			
			Optional<int[][]> sol = solver.getSolution();
			if (sol.isPresent()) {
				writeSolutionToBoard(sol);
			} else {
				System.out.println("No solution found.");
			}  
        });
        
        btn2.setOnAction(event -> {
            writeMatrizOriginal();
        });
        
        return rightPanel;
    }


	private void writeMatrizOriginal() {
		for (int y = 0; y < 9; y++) {
		    for (int x = 0; x < 9; x++) {
		        cells[x][y].setText(solver.valorMatrizOriginal(x, y) == 0 ? "" : String.valueOf(solver.valorMatrizOriginal(x, y)));;
		    }
		}
	}


	private void writeSolutionToBoard(Optional<int[][]> sol) {
		int[][] solution = sol.get();
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				cells[y][x].setText(String.valueOf(solution[y][x]));
			}
		}
	}


	private int[][] getBoard() {
		int[][] board = new int[9][9];
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				String text = cells[y][x].getText();
				board[y][x] = text.isEmpty() ? 0 : Integer.parseInt(text);
			}
		}
		return board;
	}
    
    private TextField createCell(int x, int y, boolean editable) {
        TextField tf = new TextField();

        tf.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        tf.setStyle(getStyle(x, y, false));

        tf.focusedProperty().addListener((obs, oldVal, isFocused) -> {
            tf.setStyle(getStyle(x, y, isFocused));
            if (isFocused) tf.deselect();
        });

        tf.setEditable(editable);

        if (!editable) {
            tf.setStyle(getStyle(x, y, false) + "-fx-background-color: lightgray;");
        }

        // Input restriction
        tf.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("[1-9]?") ? change : null;
        }));

        tf.setOnKeyTyped(e -> {
            String key = e.getCharacter();

            if (key.matches("[1-9]")) {
                tf.setText(key);
                e.consume();
            } else if (key.equals("\b")) {
                tf.clear();
                e.consume();
            }
        });

        return tf;
    }
    
    private String getStyle(int x, int y, boolean focused) {
        int top = (y % 3 == 0) ? 3 : 1;
        int left = (x % 3 == 0) ? 3 : 1;
        int bottom = (y == 8) ? 3 : 1;
        int right = (x == 8) ? 3 : 1;

        String bg = focused ? "lightblue" : "white";

        return "-fx-alignment: center;" +
               "-fx-font-size: 18px;" +
               "-fx-border-color: black;" +
               "-fx-border-width: " + top + " " + right + " " + bottom + " " + left + ";" +
               "-fx-background-color: " + bg + ";" +
               "-fx-caret-color: transparent;";
    }
}