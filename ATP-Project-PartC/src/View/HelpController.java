package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    @FXML
    public ImageView player;
    @FXML
    public ImageView target;
    @FXML
    public ImageView wall;
    @FXML
    public ImageView solution;
    @FXML
    public ImageView nampad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String p = "Resources/Images/down.png";
        String t = "Resources/Images/dog.png";
        String w = "Resources/Images/bush.png";
        String s = "Resources/Images/trace.png";
        String n = "Resources/Images/numpad.png";
        player.setImage(new Image(Paths.get(p).toUri().toString()));
        target.setImage(new Image(Paths.get(t).toUri().toString()));
        wall.setImage(new Image(Paths.get(w).toUri().toString()));
        solution.setImage(new Image(Paths.get(s).toUri().toString()));
        nampad.setImage(new Image(Paths.get(n).toUri().toString()));
    }
}
