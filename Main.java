package FX.FXWordLink;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
public class Main extends Application {
	Label letters,length;
	TextField text;
	TableView<String> answersTable;
	ComboBox<Integer> wordLen;
	Button go;
	public static void main (String[] args) {
		launch(args);
	}
	@Override public void start (Stage primaryStage) throws Exception {
		letters = new Label("Enter All Letters Available: ");
		length = new Label("Select Word Length"
				+ ": ");
		text = new TextField() {
		    @Override public void replaceText(int start, int end, String text) {
		        if (!text.matches("[^a-zA-Z]") || text.length() > 10) {
		            super.replaceText(start, end, text);
		        }
		    }
		 
		    @Override public void replaceSelection(String text) {
		        if (!text.matches("[a-z]")) {
		            super.replaceSelection(text);
		        }
		    }
		};
		text.setFont(Font.font(12));
		wordLen = new ComboBox<Integer>();
		go = new Button("Go!");
		answersTable =  new TableView<String>();
		answersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        answersTable.setEditable(false);
        TableColumn<String, String> solutions = new TableColumn<>("Possible Words");
        solutions.prefWidthProperty().bind(answersTable.widthProperty());
        answersTable.getColumns().addAll(solutions);
		GridPane pane = new GridPane();
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setPadding(new Insets(25));
		pane.add(letters, 0, 0);
		pane.add(text, 1, 0);
		pane.add(length, 0, 1);
		pane.add(wordLen, 1, 1);
		pane.add(go, 2, 1);
		pane.add(answersTable, 0, 2);
		GridPane.setColumnSpan(text, 2);
		GridPane.setColumnSpan(answersTable, 3);
		answersTable.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.BACK_SPACE) {
				String selectedItem = answersTable.getSelectionModel().getSelectedItem();
				answersTable.getItems().remove(selectedItem);
		        solutions.setText("Possible Words ("+answersTable.getItems().size()+")");
			}
		});
		text.setOnKeyReleased(x -> {
			wordLen.getItems().clear();
			int wordLength = text.getText().replaceAll("[^a-zA-Z]", "").length();
			for (int i = 2; i <= wordLength; i++)
				wordLen.getItems().add(i);
		});
		
		go.setOnAction(e -> {
			if (wordLen.getValue() != null) {
				try {
					List<String> list = Solver.getAllWords(text.getText(), wordLen.getValue());
			        solutions.setText("Possible Words ("+list.size()+")");
					answersTable.getColumns().clear();
					ObservableList<String> info = FXCollections.observableArrayList(list);
			        answersTable.setItems(info);

					solutions.setCellValueFactory(data -> new SimpleStringProperty(wordFormat(data.getValue())));
			        answersTable.getColumns().addAll(solutions);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		int width = 330,height=380;
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    primaryStage.setX((screenBounds.getWidth() - width) / 2); 
	    primaryStage.setY((screenBounds.getHeight() - height) / 2);
		Scene scene = new Scene(pane,width,height);
		setGlobalEventHandler(pane);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Word Link Solver");
		primaryStage.show();
	}
	private String wordFormat(String value) {
		return value.substring(0, 1).toUpperCase() + "" + value.substring(1);
	}
	private void setGlobalEventHandler(Node root) {
	    root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
	        if (ev.getCode() == KeyCode.ENTER) {
	           go.fire();
	           ev.consume(); 
	        }
	    });
	}
}