package Model;

import Server.Configurations;
import algorithms.mazeGenerators.*;
import algorithms.search.*;


import java.io.*;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {
    private Maze maze;
    private Solution solution;
    private int rowChar;
    private int colChar;


    public MyModel() {
        maze = null;
        solution = null;
        rowChar =0;
        colChar =0;
        Configurations.setMazeAlgorithm("MyMazeGenerator");
        Configurations.setSearchingAlgorithm("BreadthFirstSearch");
        Configurations.setThreadPoolNUM(5);
    }

    @Override
    public void setProperties(String generator, String searcher, int num){
        Configurations.setMazeAlgorithm(generator);
        Configurations.setSearchingAlgorithm(searcher);
        Configurations.setThreadPoolNUM(num);
    }

    @Override
    public void generateRandomMaze(int row, int col) {

        AMazeGenerator mazeGenerator;
        String configData = Configurations.getMazeAlgorithm();
        if(configData.equals("MyMazeGenerator")){
            mazeGenerator = new MyMazeGenerator();
        }
        else if(configData.equals("EmptyMazeGenerator") ){
            mazeGenerator =  new EmptyMazeGenerator();
        }
        else if(configData.equals("SimpleMazeGenerator")){
            mazeGenerator = new SimpleMazeGenerator();
        }
        else {
            mazeGenerator = new SimpleMazeGenerator();
        }
        Maze m = mazeGenerator.generate(row, col);

        maze = m;
        solution = null;
        rowChar = maze.getStartPosition().getRowIndex();
        colChar = maze.getStartPosition().getColumnIndex();

        setChanged();
        notifyObservers();
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void updateCharacterLocation(int direction) {
        /*
            direction = 1 -> Up
            direction = 2 -> Down
            direction = 3 -> Left
            direction = 4 -> Right
            direction = 5 -> Up Right
            direction = 6 -> Down Right
            direction = 7 -> Down Left
            direction = 8 -> Up Left

         */

        switch(direction)
        {
            case 1: //Up
                if(rowChar!=0){
                    if( maze.getMaze()[rowChar-1][colChar] == 0){
                        rowChar--;
                    }
                }
                break;

            case 2: //Down
                if(rowChar!=maze.getRows()-1 ){
                    if(maze.getMaze()[rowChar+1][colChar] == 0){
                        rowChar++;
                    }
                }
                break;
            case 3: //Left
                if(colChar!=0){
                    if(maze.getMaze()[rowChar][colChar-1] == 0){
                        colChar--;
                    }
                }
                break;
            case 4: //Right
                if(colChar!=maze.getColumns()-1)
                    if(maze.getMaze()[rowChar][colChar+1] == 0){
                        colChar++;
                    }
                break;
            case 5: //Up Right
                if(rowChar!=0 && colChar!=maze.getColumns()-1) {
                    if( maze.getMaze()[rowChar-1][colChar+1] == 0){
                        rowChar--;
                        colChar++;
                    }
                }
                break;

            case 6: //Down Right
                if(rowChar!=maze.getRows()-1 && colChar!=maze.getColumns()-1){
                    if(maze.getMaze()[rowChar+1][colChar+1] == 0){
                        rowChar++;
                        colChar++;
                    }
                }
                break;
            case 7: //Down Left
                if(rowChar!=maze.getRows()-1 && colChar!=0){
                    if(maze.getMaze()[rowChar+1][colChar-1] == 0){
                        rowChar++;
                        colChar--;
                    }
                }
                break;
            case 8: //Up Left
                if(rowChar!=0 && colChar!=0 ){
                    if(maze.getMaze()[rowChar-1][colChar-1] == 0){
                        rowChar--;
                        colChar--;
                    }
                }
                break;
        }

        setChanged();
        notifyObservers();

    }

    @Override
    public int getRowChar() {
        return rowChar;
    }

    @Override
    public int getColChar() {
        return colChar;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void solveMaze(Maze maze) {
        Position p = new Position(rowChar,colChar);
        maze.setStartPosition(p);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        ASearchingAlgorithm searcher;
        String configData = Configurations.getSearchingAlgorithm();
        if( configData.equals("BestFirstSearch") ){
            searcher = new BestFirstSearch();
        }
        else if(configData.equals("BreadthFirstSearch")){
            searcher = new BreadthFirstSearch();
        }
        else if(configData.equals("DepthFirstSearch")){
            searcher = new DepthFirstSearch();
        }
        else {
            searcher = new BreadthFirstSearch();
        }
        solution = searcher.solve(searchableMaze);

        setChanged();
        notifyObservers();

    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public void loadMaze(File file) throws IOException, ClassNotFoundException {
        FileInputStream toLoad = new FileInputStream(file);
        ObjectInputStream fromFile = new ObjectInputStream(toLoad);
        this.maze = new Maze((byte[])fromFile.readObject());
        this.colChar = maze.getStartPosition().getColumnIndex();
        this.rowChar = maze.getStartPosition().getRowIndex();
        this.solution = null;

        setChanged();
        notifyObservers();
    }

    @Override
    public void saveMaze(File file) throws IOException {
        FileOutputStream toSave = new FileOutputStream(file);
        ObjectOutputStream toFile = new ObjectOutputStream(toSave);
        toFile.writeObject(maze.toByteArray());
    }

}
