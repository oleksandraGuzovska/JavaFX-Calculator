package compoundInterestCalculator;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TableData {

	NumberFormat curFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

	//Year Field
	private StringProperty yearField;
    public void setYear(String year) {
        yearFieldProperty().set(year);
    }

    public String getYear() {
        return yearFieldProperty().get();
    }

    public StringProperty yearFieldProperty() {
    	if (yearField == null) {
    		yearField = new SimpleStringProperty(this, "year");
    	}
    	return yearField;
    }

    //Opening Balance Field
    private StringProperty openBalField;
    public void setOpenBal(Double openBal) {
    	openBalProperty().set(curFormat.format(openBal));
    }

    public String getOpenBal(String openBal) {
    	return openBalProperty().get();
    }

    public StringProperty openBalProperty() {
    	if (openBalField == null) {
    		openBalField = new SimpleStringProperty(this, "openBal");
    	}
    	return openBalField;
    }


    //Interest
    private StringProperty interestField;
    public void setInterest(Double interest) {
    	interestProperty().set(curFormat.format(interest));
    }

    public String getInterest() {
    	return interestProperty().get();
    }

    public StringProperty interestProperty() {
    	if (interestField == null) {
    		interestField = new SimpleStringProperty(this, "interest");
    	}
    	return interestField;
    }

    //Closing Balance
    private StringProperty closeBalField;
    public void setCloseBal(Double value) {
    	closeBalProperty().set(curFormat.format(value));
    }

    public String getcloseBal() {
    	return interestProperty().get();
    }

    public StringProperty closeBalProperty() {
    	if (closeBalField == null) {
    		closeBalField = new SimpleStringProperty(this, "closeBal");
    	}
    	return closeBalField;
    }

} 