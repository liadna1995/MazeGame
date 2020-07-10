package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class AboutView implements Initializable {

    @FXML
    public ImageView liad;
    @FXML
    public ImageView shahar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String l = "Resources/Images/liad.jpeg";
        String s = "Resources/Images/shahar.jpeg";
        liad.setImage(new Image(Paths.get(l).toUri().toString()));
        shahar.setImage(new Image(Paths.get(s).toUri().toString()));
    }
}
