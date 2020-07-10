package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class WinController implements Initializable {

    @FXML
    public ImageView goodjob;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String n = "Resources/Images/you-win-animation.gif";
        goodjob.setImage(new Image(Paths.get(n).toUri().toString()));
    }
}
