package ca.calculatorFX;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;


public class MainGUI extends Application{

	private TableView<TableData> table = new TableView<>();
	private ObservableList<TableData> teamMembers;
	
	private TextField principalField;
	private TextField interestField;
	private TextField yearsField;
	private ComboBox <String> frequencyField;
	
	private BigDecimal principalAmt;
	private BigDecimal interestAmt;
	private BigDecimal yearsAmt;
	
	private int principalHelpVar;
	private int interestHelpVar;
	private int yearsHelpVar;
	
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private BarChart <String,Number> bc;
	
	private VBox vboxLabels;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Compound Interest Calculator");
		stage.setWidth(1100);
		stage.setWidth(1100);
		
		final Text mainText = new Text();
		mainText.setText("Compound Interest Calculator");
		mainText.setFont(Font.font("verdana", FontWeight.BOLD, 20));
		mainText.setX(30);
		mainText.setY(20);
		
		//Labels and Input Fields
		addLabels();
		addFields();
		
		principalField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	principalHelpVar = 0;
		    	if (isNum(newValue)) {
		    		principalAmt = new BigDecimal(newValue);
		    		principalHelpVar = 1;
		    		calculateAmts();
		    	}
		    }
		});
		
		
		//Recalculate based on new value
		interestField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	interestHelpVar = 0;
		    	if (isNum(newValue)) {
		    		interestAmt = new BigDecimal(newValue);
		    		interestHelpVar = 1;
		    		calculateAmts();
		    	}
		    }
		});
		
		//Recalculate based on new value
		yearsField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	yearsHelpVar = 0;
		    	if (isInt(newValue)) {
		    		yearsAmt = new BigDecimal(newValue);
		    		yearsHelpVar = 1;
		    		calculateAmts();
		    	}
		    }
		});
		
		//Recalculate based on new value
		frequencyField.valueProperty().addListener(new ChangeListener<String>() {
		        @Override public void changed(ObservableValue<? extends String> observable,
		        			String oldValue, String newValue) {
		        	calculateAmts();
		        }    
		    });		
		
		final VBox vboxInput = new VBox(20);
		vboxInput.setLayoutX(250);
		vboxInput.setLayoutY(45);
		
		vboxInput.getChildren().addAll(principalField, interestField, frequencyField, yearsField);
		
		//Calculation Table and Bar Chart
		createTable();
		createBarChart();
	
		((Group) scene.getRoot()).getChildren().addAll(mainText, vboxLabels, vboxInput, table, bc);
		
		stage.setScene(scene);
		stage.show();
	}
	
	 /**
     * Validates values entered in Principal and Interest Fields
     * @param String representation of the entered value
     * @return True if entry is int or float, false otherwise
     */
	private static boolean isNum(String str) {
	    return str.matches("\\d+(\\.\\d+)?");
	}
	
	/**
     * Validates values entered in Years Field
     * @param String representation of the entered value
     * @return True if entry is int, false otherwise
     */
	private static boolean isInt(String str) {
	    return str.matches("\\d+");
	}
	
	/**
     * Creates a BarChart
     */
	private void createBarChart() {
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Balance Growth over Years");
		xAxis.setLabel("Year");
		yAxis.setLabel("Opening Balance");
		bc.setLayoutX(40);
		bc.setLayoutY(350);
		bc.setPrefWidth(1000);
		bc.setLegendVisible(false);
	}
	
	/**
     * Creates a table that will hold all the calculated values
     */
	private void createTable() {

	    table = new TableView<>();
	    teamMembers = FXCollections.observableArrayList();
	    table.setItems(teamMembers);

	    table.setPrefWidth(500);
	    table.setPrefHeight(300);
	    table.setLayoutX(550);
	    table.setLayoutY(45);
	        
	    TableColumn<TableData, Integer> yearCol = new TableColumn<>("Year");
	    yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
	    yearCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

	    TableColumn<TableData, Double> openBalCol = new TableColumn<>("Opening Balance");
	    openBalCol.setCellValueFactory(new PropertyValueFactory<>("openBal"));
	    openBalCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3));

	    TableColumn<TableData, Double> interestCol = new TableColumn<>("Interest");
	    interestCol.setCellValueFactory(new PropertyValueFactory<>("interest"));
	    interestCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
	        
	    TableColumn<TableData, Double> closeBalCol = new TableColumn<>("Closing Balance");
	    closeBalCol.setCellValueFactory(new PropertyValueFactory<>("closeBal"));
	    closeBalCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3));

	    table.getColumns().setAll(yearCol, openBalCol, interestCol, closeBalCol);
	 }
	 
	
	/**
     * Checks currently selected value in the ComboBox
     * @param none
     * @return int number assigned to selected value
     */
	 private int comboBoxValue() {
		 
		 if (frequencyField.getValue().equals("Monthly")) {
			 return 12;
		 } else if (frequencyField.getValue().equals("Quarterly")) {
			 return 4;
		 } else if (frequencyField.getValue().equals("Semi-annually")) {
			 return 2;
		 }
		 return 1;
	 }
	 
	 
	 /**
	 * Adds Input Fields to be shown on the main stage
	 */
	 private void addFields() {
		principalField = new TextField();
		principalField.setPrefWidth(270);
		
		interestField = new TextField();
		interestField.setPrefWidth(270);
		
		yearsField = new TextField();
		yearsField.setPrefWidth(270);
		
		frequencyField = new ComboBox<>();
		frequencyField.getItems().addAll(
			    "Monthly",
			    "Quarterly",
			    "Semi-annually",
			    "Annually"
			);
		frequencyField.setValue("Annually");		
	 }
	 
	 
	 /**
	 * Adds labels to be shown on the main scene
	 */
	 public void addLabels() {
		 
		vboxLabels = new VBox(30);
		 	
		final Label principalLabel = new Label();
		principalLabel.setText("Principal ($):");
			
		final Label interestLabel = new Label();
		interestLabel.setText("Annual interest (%):");
			
		final Label frequencyLabel = new Label();
		frequencyLabel.setText("Compounding Frequency:");
			
		final Label yearsLabel = new Label();
		yearsLabel.setText("Number of Years:");		

		vboxLabels.setLayoutX(30);
		vboxLabels.setLayoutY(45);
			
		vboxLabels.getChildren().addAll(principalLabel, interestLabel, frequencyLabel, yearsLabel);
	 }
	 
	 
	 /**
	 * Calculates interest and balances for each year entered by the user
	 */
	 private void calculateAmts() {
		 
		 //check that all fields contain valid entries
		 if (principalHelpVar == 1 && interestHelpVar == 1 && yearsHelpVar == 1) {
			 BigDecimal openBal = principalAmt;
			 BigDecimal closeBal;
			 BigDecimal interest;
			 BigDecimal interestHelp = interestAmt.divide(BigDecimal.valueOf(100));
			 interestHelp = interestHelp.divide(new BigDecimal(comboBoxValue()), 20, RoundingMode.HALF_DOWN);
			 interestHelp = interestHelp.add(new BigDecimal("1"));		 
			 interestHelp = interestHelp.pow(comboBoxValue());			
			 
			 teamMembers.clear();
			 bc.getData().clear();
			
			 Series<String, Number> data = new XYChart.Series<>();
			 
			 for (int i = 1; i < yearsAmt.intValue()+1; i ++) {
				 
				interest = openBal.multiply(interestHelp).subtract(openBal);
				interest = interest.setScale(2, BigDecimal.ROUND_HALF_UP);
				closeBal = openBal.add(interest);
			
				TableData entry = new TableData();
				entry.setOpenBal(openBal.doubleValue());
				entry.setInterest(interest.doubleValue());
				entry.setCloseBal(closeBal.doubleValue());
				entry.setYear(Integer.toString(i));
				teamMembers.add(entry);
				
				data.getData().add(new Data<String, Number>(Integer.toString(i), openBal));
				
				openBal = closeBal;
				
			 }
			 
			 
			 //work-around: BarChart: auto-range of CategoryAxis not working on dynamically setting data
			 //from https://bugs.openjdk.java.net/browse/JDK-8198830
			 String[] helperArray = new String[yearsAmt.intValue()];
			 for (int i = 1; i < yearsAmt.intValue()+1; i ++) {
				 helperArray[i-1] = Integer.toString(i);
			 }

			 xAxis.invalidateRange(FXCollections.observableArrayList(helperArray)); 
			 xAxis.setCategories(FXCollections.observableArrayList(helperArray));
			 /////////////////////////////////////////////////////////////////////////////////
			 
			 bc.getData().add(data);
		}

	 }
}