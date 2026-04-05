package gaxi.sudoku;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SudokuGUI extends Application {

	TextField[][] cells = new TextField[9][9];
	
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
			int[][] board = new int[9][9];
			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					String text = cells[y][x].getText();
					board[y][x] = text.isEmpty() ? 0 : Integer.parseInt(text);
				}
			}
			SudokuBoard solver = new SudokuBoard(board);
			Optional<int[][]> sol = solver.getSolution();
			if (sol.isPresent()) {
			
				int[][] solution = sol.get();
				for (int y = 0; y < 9; y++) {
					for (int x = 0; x < 9; x++) {
						cells[y][x].setText(String.valueOf(solution[y][x]));
					}
				}
			} else {
				System.out.println("No solution found.");
			}
            
        });
        
        btn2.setOnAction(event -> {
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    cells[x][y].setText("");
                }
            }
        });
        
        return rightPanel;
    }
    

	private TextField createCell(int x, int y, boolean editable) {
        TextField tf = new TextField();

        // Fill cell
        tf.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Center text
        tf.setStyle(
            "-fx-alignment: center;" +
            "-fx-font-size: 18px;" +
            "-fx-border-color: black;" +
            "-fx-border-width: 1px;" + 
            "-fx-caret-color: transparent;"
        );
        

        tf.focusedProperty().addListener((obs, oldVal, isFocused) -> {
            if (isFocused) {
     
                tf.setStyle(
                    "-fx-alignment: center;" +
                    "-fx-font-size: 18px;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 1px;" +
                    "-fx-background-color: lightblue;" +
                    "-fx-display-caret: false;;"
                );
                tf.deselect(); // remove selection
            } else {
                tf.setStyle(
                    "-fx-alignment: center;" +
                    "-fx-font-size: 18px;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 1px;" +
                    "-fx-background-color: white;" +
                    "-fx-caret-color: transparent;"
                );
            }
        });
        
        // Editable or fixed (clue)
        tf.setEditable(editable);

        if (!editable) {
            tf.setStyle(
                "-fx-alignment: center;" +
                "-fx-font-size: 18px;" +
                "-fx-background-color: lightgray;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1px;"
            );
        }

        // Input restriction: only 1–9 and max length 1
        tf.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.matches("[1-9]?")) {
                return change;
            }

            return null; // blocks the change completely
        }));
        
        tf.setOnKeyTyped(e -> {
            String key = e.getCharacter();
            
            // Handle digits 1–9
            if (key.matches("[1-9]")) {
                tf.setText(key);
                e.consume(); // <-- critical: prevents duplicate insertion
            }
            // Handle backspace
            else if (key.equals("\b")) {
                tf.clear();
                e.consume();
            }
        });


        // Optional: debug
        tf.setOnMouseClicked(e -> {
            System.out.println("Clicked cell: (" + x + "," + y + ")");
        });

        return tf;
    }
    
}