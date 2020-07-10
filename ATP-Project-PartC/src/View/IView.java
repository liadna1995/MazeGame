package View;
import ViewModel.MyViewModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Observer;

public interface IView extends Observer {
    public void setViewModel(MyViewModel viewModel);
    public String get_update_player_position_row();
    public String get_update_player_position_col();
    public void set_update_player_position_row(String update_player_position_row);
    public void set_update_player_position_col(String update_player_position_col);
    public void solveMaze();
    public void showAlert(String message);
    public void keyPressed(KeyEvent keyEvent);
    public void mouseClicked(MouseEvent mouseEvent);
    public void drawMaze();
    public void setProperties(String generator, String searcher, int num);
    public void drawSolution();
    public void loadMaze();
    public void saveToDisk();
    public void newMaze() throws IOException;
    public void setInformation(String row, String col);
    public void playMusic();
    public void zoom();
    public void showProperties() throws IOException;
    public void help() throws IOException;
    public void Exit();
    public void about() throws IOException;

}
