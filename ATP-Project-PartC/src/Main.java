import Model.IModel;
import Model.MyModel;
import View.IView;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("The Maze");
        stage.setScene(new Scene(root, 650, 540));
        stage.show();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        IView view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
