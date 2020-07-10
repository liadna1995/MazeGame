package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyViewController implements IView, Initializable {

    private MyViewModel viewModel;
    private Maze maze;
    private Solution solution;


    private String Generator;
    private String Searcher;
    private int Threads;

    @FXML
    public Label rows;
    @FXML
    public Label cols;
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public Label lbl_player_row;
    @FXML
    public Label lbl_player_column;
    @FXML
    private BorderPane window;
    @FXML public CheckBox music;

    @FXML
    public Button solve;

    @FXML
    public Pane pane;
    public ScrollPane scrollPane;

    private AudioClip mediaPlayer = null;
    FileChooser fileChooser = new FileChooser();



    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MyViewModel)
        {
            if(maze == null)//generateMaze
            {
                this.maze = viewModel.getMaze();
                drawMaze();
            }
            else {
                Maze maze = viewModel.getMaze();

                if (maze == this.maze)//Not generateMaze
                {
                    int rowChar = mazeDisplayer.getRow_player();
                    int colChar = mazeDisplayer.getCol_player();
                    int rowFromViewModel = viewModel.getRowChar();
                    int colFromViewModel = viewModel.getColChar();
                    Solution solution = viewModel.getSolution();


                    if(rowFromViewModel == rowChar && colFromViewModel == colChar)//Solve Maze
                    {
                        if(solution != null){
                            //showAlert("Solving Maze ... ");
                            this.solution = solution;
                            drawSolution();
                        }
                    }

                    else//Update location
                    {
                        set_update_player_position_row(rowFromViewModel + "");
                        set_update_player_position_col(colFromViewModel + "");
                        this.mazeDisplayer.set_player_position(rowFromViewModel,colFromViewModel);
                        solve.disableProperty().set(false);

                        if(rowFromViewModel == maze.getGoalPosition().getRowIndex() && colFromViewModel == maze.getGoalPosition().getColumnIndex()){
                            try {
                                Win();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }
                else//GenerateMaze
                {
                    this.maze = maze;
                    drawMaze();
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);

    }


    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public String get_update_player_position_row() {
        return update_player_position_row.get();
    }

    @Override
    public String get_update_player_position_col() {
        return update_player_position_col.get();
    }

    @Override
    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row);
    }

    @Override
    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col);
    }


    @Override
    public void solveMaze() {
        if(this.maze != null){
            viewModel.solveMaze(this.maze);
        }
    }

    @Override
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        String bip = "Resources/Sounds/error.mp3";
        Media hit = new Media(Paths.get(bip).toUri().toString());
        AudioClip media = new AudioClip(hit.getSource());
        if(mediaPlayer != null){mediaPlayer.stop();}
        media.play();
        alert.showAndWait();
        if(mediaPlayer !=null){mediaPlayer.play();}
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case NUMPAD8:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/up.png");
                break;
            case UP:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/up.png");
                break;
            case NUMPAD2:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/down.png");
                break;
            case DOWN:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/down.png");
                break;
            case NUMPAD4:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/left.png");
                break;
            case LEFT:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/left.png");
                break;
            case NUMPAD6:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/right.png");
                break;
            case RIGHT:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/right.png");
                break;
            case NUMPAD9:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/right.png");
                break;
            case NUMPAD3:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/right.png");
                break;
            case NUMPAD1:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/left.png");
                break;
            case NUMPAD7:
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/left.png");
                break;
        }
        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void drawMaze() {
        mazeDisplayer.drawMaze(maze);
    }

    public void drawSolution() {
        mazeDisplayer.drawSolution(solution);
    }

    public void loadMaze(){
        Window stage = window.getScene().getWindow();
        fileChooser.setTitle("Load Maze");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file","*.txt"));

        try{
            mazeDisplayer.setImageFileNamePlayer("./Resources/Images/down.png");
            File file = fileChooser.showOpenDialog(stage);
            viewModel.loadMaze(file);
            rows.setText(String.valueOf(maze.getRows()));
            cols.setText(String.valueOf(maze.getColumns()));
            set_update_player_position_col(String.valueOf(maze.getStartPosition().getColumnIndex()));
            set_update_player_position_row(String.valueOf(maze.getStartPosition().getRowIndex()));

        }
        catch (Exception ex){
            System.out.println("file not valid, try again");
        }

    }

    public void saveToDisk(){
        Window stage = window.getScene().getWindow();
        fileChooser.setTitle("Save Maze");
        fileChooser.setInitialFileName("myMaze");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file","*.txt"));

        try{
            File file = fileChooser.showSaveDialog(stage);
            viewModel.saveMaze(file);
        }
        catch (Exception ex){
            System.out.println("file not valid, try again");
        }


    }
    public void newMaze() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyMazeData.fxml"));
        Parent root = loader.load();
        DataController dataController = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 380, 200));
        stage.setTitle("Insert Data");
        dataController.init(this);
        stage.show();
        mazeDisplayer.setImageFileNamePlayer("./Resources/Images/down.png");

    }

    public void setInformation(String row, String col){
        rows.setText(row);
        cols.setText(col);
        viewModel.generateMaze(Integer.valueOf(row),Integer.valueOf(col));
        set_update_player_position_col(String.valueOf(maze.getStartPosition().getColumnIndex()));
        set_update_player_position_row(String.valueOf(maze.getStartPosition().getRowIndex()));
        solve.disableProperty().set(false);
    }

    public void playMusic() {
        String bip = "Resources/Sounds/music.mp3";
        Media hit = new Media(Paths.get(bip).toUri().toString());
        mediaPlayer = new AudioClip(hit.getSource());
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        if (music.isSelected()) {
            mediaPlayer.play();
        } else {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public void zoom() {
        scrollPane = new ScrollPane(new Group(pane));
        window.setCenter(scrollPane);
        pane.setOnScroll(
                event -> {
                    if(event.isControlDown()){
                        double zoomFactor = 1.05;
                        double deltaY = event.getDeltaY();

                        if (deltaY < 0){
                            zoomFactor = 0.95;
                        }
                        pane.setScaleX(pane.getScaleX() * zoomFactor);
                        pane.setScaleY(pane.getScaleY() * zoomFactor);
                        event.consume();
                    }
                });

        pane.setBackground(new Background(new BackgroundFill(null, null, null)));

    }

    public void showProperties() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyProperties.fxml"));
        Parent root = loader.load();
        PropertiesController propertiesController = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 440, 230));
        stage.setTitle("Insert Properties");
        propertiesController.init(this);
        stage.show();
    }

    public void setProperties(String generator, String searcher, int num){
        this.Generator = generator;
        this.Searcher = searcher;
        this.Threads = num;
        viewModel.setProperties(this.Generator,this.Searcher,this.Threads);
    }

    public void help() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyHelpView.fxml"));
        Parent root = loader.load();
        HelpController helpController = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 650, 480));
        stage.setTitle("Help");
        //propertiesController.init(this);
        stage.show();
    }

    public void Exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wanna stop playing?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }

    }

    private void Win() throws IOException {
        solve.disableProperty().set(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyWin.fxml"));
        Parent root = loader.load();
        WinController winController = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 290, 215));
        stage.setTitle("Congrats!");
        //propertiesController.init(this);
        String bip = "Resources/Sounds/yay.mp3";
        Media hit = new Media(Paths.get(bip).toUri().toString());
        AudioClip media= new AudioClip(hit.getSource());
        if(mediaPlayer != null){mediaPlayer.stop();}
        media.play();
        stage.showAndWait();
        if(mediaPlayer !=null){mediaPlayer.play();}

    }

    public void about() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyAbout.fxml"));
        Parent root = loader.load();
        AboutView aboutView = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 620, 470));
        stage.setTitle("About");
        stage.show();
    }
    public void mouseMoving(MouseEvent mouseEvent){
        if(this.maze!=null) {
            double xPlace = mouseEvent.getX() / (mazeDisplayer.getWidth() / maze.getColumns());
            double yPlace = mouseEvent.getY() / (mazeDisplayer.getHeight() / maze.getRows());
            if (xPlace > this.viewModel.getColChar() + 1) {
                this.viewModel.getModel().updateCharacterLocation(4);
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/right.png");
            }
            if (xPlace < this.viewModel.getColChar()) {
                this.viewModel.getModel().updateCharacterLocation(3);
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/left.png");
            }
            if (yPlace > this.viewModel.getRowChar() + 1) {
                this.viewModel.getModel().updateCharacterLocation(2);
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/down.png");
            }
            if (yPlace < this.viewModel.getRowChar()) {
                this.viewModel.getModel().updateCharacterLocation(1);
                mazeDisplayer.setImageFileNamePlayer("./Resources/Images/up.png");
            }
        }
    }
}
