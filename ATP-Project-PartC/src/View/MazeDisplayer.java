package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {

    private Maze maze;
    private int row_player;
    private int col_player;
    private Solution solution;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameGoal = new SimpleStringProperty();
    StringProperty imageFileNameSol = new SimpleStringProperty();


    public String getImageFileNameSol() {
        return imageFileNameSol.get();
    }

    public StringProperty imageFileNameSolProperty() {
        return imageFileNameSol;
    }

    public void setImageFileNameSol(String imageFileNameSol) {
        this.imageFileNameSol.set(imageFileNameSol);
    }

    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public int getRow_player() {
        return row_player;
    }

    public int getCol_player() {
        return col_player;
    }

    public void set_player_position(int row, int col){
        this.row_player = row;
        this.col_player = col;

        draw();
    }

    public void drawMaze(Maze maze)
    {
        this.maze = maze;
        this.solution = null;
        this.row_player = maze.getStartPosition().getRowIndex();
        this.col_player = maze.getStartPosition().getColumnIndex();
        draw();
    }

    public void drawSolution(Solution solution){
        this.solution = solution;
        draw();
    }

    public void draw()
    {
        if( maze!=null)
        {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int row = maze.getRows();
            int col = maze.getColumns();
            double cellHeight = canvasHeight/row;
            double cellWidth = canvasWidth/col;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0,0,canvasWidth,canvasHeight);
            graphicsContext.setFill(Color.RED);
            double w,h;
            //Draw Maze
            Image wallImage = null;
            try {
                wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no file....");
            }
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    if(maze.getMaze()[i][j] == 1) // Wall
                    {
                        h = i * cellHeight;
                        w = j * cellWidth;
                        if (wallImage == null){
                            graphicsContext.fillRect(w,h,cellWidth,cellHeight);
                        }else{
                            graphicsContext.drawImage(wallImage,w,h,cellWidth,cellHeight);
                        }
                    }

                }
            }

            Image solImage = null;
            try {
                solImage = new Image(new FileInputStream(getImageFileNameSol()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no file....");
            }
            if(solution != null){
                for (AState pos: solution.getSolutionPath()) {
                    h = ((Position)((MazeState)pos).getData()).getRowIndex() * cellHeight;
                    w = ((Position)((MazeState)pos).getData()).getColumnIndex() * cellWidth;
                    if (solImage == null){
                        graphicsContext.fillRect(w,h,cellWidth,cellHeight);
                    }else{
                        graphicsContext.drawImage(solImage,w,h,cellWidth,cellHeight);
                    }

                }
            }

            double h_goal = maze.getGoalPosition().getRowIndex() * cellHeight;
            double w_goal = maze.getGoalPosition().getColumnIndex() * cellWidth;
            Image goalImage = null;
            try {
                goalImage = new Image(new FileInputStream(getImageFileNameGoal()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no Image goal....");
            }
            graphicsContext.drawImage(goalImage,w_goal,h_goal,cellWidth,cellHeight);


            double h_player = getRow_player() * cellHeight;
            double w_player = getCol_player() * cellWidth;
            Image playerImage = null;
            try {
                playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no Image player....");
            }
            graphicsContext.drawImage(playerImage,w_player,h_player,cellWidth,cellHeight);

        }
    }

}
