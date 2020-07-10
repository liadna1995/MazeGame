package View;

import javafx.fxml.FXML;


import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DataController implements Initializable {

    @FXML
    public TextField textField_mazeRows;
    @FXML
    public TextField textField_mazeColumns;
    @FXML
    public Button generate;

    private IView myViewController;


    public void sendData() throws IOException {
        boolean checkNum = false;
        try
        {
            Integer.parseInt(textField_mazeRows.getText());
            Integer.parseInt(textField_mazeColumns.getText());
        } catch (NumberFormatException e) {
            checkNum = true;
        }

        if(checkNum)
        {
            myViewController.showAlert("you entered invalid values!");
            return;
        }
        int row = Integer.parseInt(textField_mazeRows.getText());;
        int col = Integer.parseInt(textField_mazeColumns.getText());
        if(row < 2 || row > 500 || col < 2 || col > 500){
            myViewController.showAlert("you entered invalid values!");
        }
        else{
            myViewController.setInformation(textField_mazeRows.getText(),textField_mazeColumns.getText());
            Stage stage = (Stage)generate.getScene().getWindow();
            stage.close();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(IView myViewController) {
        this.myViewController = myViewController;
    }
}
