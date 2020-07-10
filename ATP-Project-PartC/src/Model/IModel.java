package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.File;
import java.io.IOException;
import java.util.Observer;

public interface IModel {
    public void generateRandomMaze(int row, int col);
    public Maze getMaze();
    public void updateCharacterLocation(int direction);
    public int getRowChar();
    public int getColChar();
    public void assignObserver(Observer o);
    public void solveMaze(Maze maze);
    public Solution getSolution();
    public void loadMaze(File file) throws IOException, ClassNotFoundException;
    public void saveMaze(File file) throws IOException;
    public void setProperties(String generator, String searcher, int num);
}
