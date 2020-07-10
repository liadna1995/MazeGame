package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private Maze maze;
    private Solution solution;
    private int rowChar;
    private int colChar;

    private String Generator;
    private String Searcher;
    private int Threads;


    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
        this.maze = null;
        this.solution = null;

    }

    public Maze getMaze() {
        return maze;
    }

    public Solution getSolution() {
        return solution;
    }

    public int getRowChar() {
        return rowChar;
    }

    public int getColChar() {
        return colChar;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof IModel)
        {
            if(maze == null)//generateMaze
            {
                this.maze = model.getMaze();

            }
            else {
                Maze maze = model.getMaze();

                if (maze == this.maze)//Not generateMaze
                {
                    int rowChar = model.getRowChar();
                    int colChar = model.getColChar();
                    if(model.getSolution() != null){//solving maze
                        this.solution = model.getSolution();
                    }
                    if(this.colChar != colChar || this.rowChar != rowChar)//Update location
                    {
                        this.rowChar = rowChar;
                        this.colChar = colChar;
                    }


                }
                else//GenerateMaze
                {
                    this.maze = maze;
                    this.solution = null;
                }
            }

            setChanged();
            notifyObservers();
        }
    }

    public void generateMaze(int row,int col)
    {
        this.model.generateRandomMaze(row,col);

    }

    public void moveCharacter(KeyEvent keyEvent)
    {
        int direction = -1;

        switch (keyEvent.getCode()){
            case NUMPAD8:
                direction = 1;
                break;
            case UP:
                direction = 1;
                break;
            case NUMPAD2:
                direction = 2;
                break;
            case DOWN:
                direction = 2;
                break;
            case NUMPAD4:
                direction = 3;
                break;
            case LEFT:
                direction = 3;
                break;
            case NUMPAD6:
                direction = 4;
                break;
            case RIGHT:
                direction = 4;
                break;
            case NUMPAD9:
                direction = 5;
                break;
            case NUMPAD3:
                direction = 6;
                break;
            case NUMPAD1:
                direction = 7;
                break;
            case NUMPAD7:
                direction = 8;
                break;
        }
        if (maze != null) {
            model.updateCharacterLocation(direction);
        }
    }

    public void solveMaze(Maze maze)
    {
        model.solveMaze(maze);
    }

    public void loadMaze(File file) throws IOException, ClassNotFoundException {
        model.loadMaze(file);
    }

    public void saveMaze(File file) throws IOException {
        model.saveMaze(file);
    }

    public void setProperties(String generator, String searcher, int num){
        this.Generator = generator;
        this.Searcher = searcher;
        this.Threads = num;
        model.setProperties(this.Generator,this.Searcher,this.Threads);
    }

    public IModel getModel() {
        return model;
    }
}
