package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PropertiesController implements Initializable {

    @FXML
    public ChoiceBox generators;
    @FXML
    public ChoiceBox searchers;
    @FXML
    public ChoiceBox threads;
    @FXML
    public Button apply;
    private IView myViewController;

    ObservableList<String> generatorsList = FXCollections.observableArrayList("EmptyMazeGenerator", "SimpleMazeGenerator", "MyMazeGenerator");
    ObservableList<String> searchersList= FXCollections.observableArrayList("BreadthFirstSearch","BestFirstSearch","DepthFirstSearch");
    ObservableList<Integer> numbersList = FXCollections.observableArrayList(IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList()));


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generators.setItems(generatorsList);
        searchers.setItems(searchersList);
        threads.setItems(numbersList);
    }

    public void init(IView myViewController) {
        this.myViewController = myViewController;
    }

    public void apply(){
        String g = "SimpleMazeGenerator";
        String s = "BreadthFirstSearch";
        int n = 5;

        if(generators.getValue() != null){
            g = generators.getValue().toString();
        }
        if(searchers.getValue() != null){
            s = searchers.getValue().toString();
        }
        if(threads.getValue() != null){
            n = (int)threads.getValue();
        }
        this.myViewController.setProperties(g, s, n);
        Stage stage = (Stage)apply.getScene().getWindow();
        stage.close();
    }
}
