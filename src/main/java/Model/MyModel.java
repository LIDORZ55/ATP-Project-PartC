package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import View.Main;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.RunCommunicateWithServers;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import Server.Configurations;



public class MyModel extends Observable implements IModel{
    private int Gencounter=0;
    private Maze maze;
    private Solution solution;
    private int playerRow;
    private int playerCol;
    private int endingRow;
    private int endingCol;
    private boolean end;
    private boolean movementaccour = true;
    private static final Logger LOG = LogManager.getLogger();


    @Override
    public void generateMaze(int rows, int columns) {
        try {
            (new Client(InetAddress.getLocalHost(), View.Main.Port_ServerMazeGenerating, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        Gencounter++;
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, columns};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[((MyDecompressorInputStream)is).getArray_Lenght()];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                        end = false;
                        LOG.info("client number: " + Gencounter + " created a " + rows + "," + columns + " maze");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            })).communicateWithServer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        setChanged();
        notifyObservers("maze generated");
        solution = null;
        playerRow = maze.getStartPosition().getRowIndex();
        playerCol = maze.getStartPosition().getColumnIndex();
        endingRow = maze.getGoalPosition().getRowIndex();
        endingCol = maze.getGoalPosition().getColumnIndex();
        notifyMovement();
    }



    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void solveMaze() {
        try {
            (new Client(InetAddress.getLocalHost(), Main.Port_ServerSearchProblemSolver, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        solution = (Solution)fromServer.readObject();
                        LOG.info("client number: " + Gencounter + " solved a " + maze.getMazeStruct().length + "," + maze.getMazeStruct()[0].length + " maze using the algorithem: " + Configurations.getInstance().getMazeSearchingAlgorithm() + ".");
                    } catch (Exception var10) {
                    }

                }
            })).communicateWithServer();
        } catch (Exception var8) {
        } finally {
        }
        setChanged();
        notifyObservers("found solution");

    }

    @Override
    public void solveMazeWithoutWriting() {
        try {
            (new Client(InetAddress.getLocalHost(), Main.Port_ServerSearchProblemSolver, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        solution = (Solution)fromServer.readObject();
                    } catch (Exception var10) {
                    }

                }
            })).communicateWithServer();
        } catch (Exception var8) {
        } finally {
        }
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {
        if(end == false){
            switch(direction){
                case UP -> {
                    if(playerRow > 0 ){
                        if(maze.getMazeStruct()[playerRow-1][playerCol] == 1){
                        }
                        else{
                            playerRow--;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
                case DOWN -> {
                    if(playerRow < maze.getMazeStruct().length -1){
                        if(maze.getMazeStruct()[playerRow+1][playerCol] == 1){
                        }
                        else{
                            playerRow++;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
                case LEFT -> {
                    if(playerCol > 0){
                        if(maze.getMazeStruct()[playerRow][playerCol-1] == 1){
                        }
                        else{
                            playerCol--;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
                case RIGHT -> {
                    if(playerCol < maze.getMazeStruct()[0].length - 1){
                        if(maze.getMazeStruct()[playerRow][playerCol+1] == 1){
                        }
                        else{
                            playerCol++;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
                case UPRIGHT -> {
                    if(playerCol < maze.getMazeStruct()[0].length - 1 && playerRow > 0) {
                        if(maze.getMazeStruct()[playerRow-1][playerCol+1] == 1){
                        }
                        else{
                            playerCol++;
                            playerRow--;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
                case UPLEFT -> {
                    if(playerCol > 0 &&  playerRow > 0){
                        if(maze.getMazeStruct()[playerRow-1][playerCol-1] == 1){
                        }
                        else{
                            playerCol--;
                            playerRow--;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
                case DOWNRIGHT -> {
                    if(playerRow < maze.getMazeStruct().length -1 && playerCol < maze.getMazeStruct()[0].length - 1){
                        if(maze.getMazeStruct()[playerRow+1][playerCol+1] == 1){
                        }
                        else{
                            playerCol++;
                            playerRow++;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
                case DOWNLEFT -> {
                    if(playerRow < maze.getMazeStruct().length -1 && playerCol > 0){
                        if(maze.getMazeStruct()[playerRow+1][playerCol-1] == 1){
                        }
                        else{
                            playerCol--;
                            playerRow++;
                            if(checkIfEnding()){
                                end = true;
                            }
                        }
                    }
                }
            }
            notifyMovement();
        }
        else{

        }

    }

    public void updatePlayerLocationMouse(MouseEvent mouseEvent,double height, double width){

        double cellHeight = height/maze.getMazeStruct().length;
        double cellWidth = width/maze.getMazeStruct()[0].length;
        int newplayerRow = playerRow+1;
        int newplayerCol = playerCol+1;
        if(end == false && movementaccour){
            if(mouseEvent.getX() - (newplayerCol)*cellWidth > 0 && mouseEvent.getY() - (newplayerRow)*cellHeight < 0 && mouseEvent.getY() - (newplayerRow-1)*cellHeight > 0){
                updatePlayerLocation(MovementDirection.RIGHT);
            }
            else if(mouseEvent.getX() - (newplayerCol-1)*cellWidth < 0  && mouseEvent.getY() - (newplayerRow)*cellHeight < 0 && mouseEvent.getY() - (newplayerRow-1)*cellHeight > 0){
                updatePlayerLocation(MovementDirection.LEFT);
            }
            else if(mouseEvent.getY()-(newplayerRow)*cellHeight > 0 && mouseEvent.getX() - (newplayerCol)*cellWidth < 0 && mouseEvent.getX() - (newplayerCol-1)*cellWidth > 0){
                updatePlayerLocation(MovementDirection.DOWN);
            }
            else if(mouseEvent.getY()-(newplayerRow-1)*cellHeight < 0 && mouseEvent.getX() - (newplayerCol)*cellWidth < 0 && mouseEvent.getX() - (newplayerCol-1)*cellWidth > 0){
                updatePlayerLocation(MovementDirection.UP);
            }
            else if(mouseEvent.getX() - (newplayerCol)*cellWidth > 0 && mouseEvent.getY() - (newplayerRow-1)*cellHeight < 0){ //broken
                updatePlayerLocation(MovementDirection.UPRIGHT);
            }
            else if(mouseEvent.getX() - (newplayerCol)*cellWidth > 0 && mouseEvent.getY() - (newplayerRow)*cellHeight > 0){
                updatePlayerLocation(MovementDirection.DOWNRIGHT);
            }
            else if(mouseEvent.getX() - (newplayerCol-1)*cellWidth < 0  && mouseEvent.getY() - (newplayerRow-1)*cellHeight < 0){ //broken
                updatePlayerLocation(MovementDirection.UPLEFT);
            }
            else if(mouseEvent.getX() - (newplayerCol-1)*cellWidth < 0  && mouseEvent.getY() - (newplayerRow)*cellHeight > 0){
                updatePlayerLocation(MovementDirection.DOWNLEFT);
            }
            notifyMovement();
        }

    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    public int getEndingRow() {
        return endingRow;
    }

    public int getEndingCol() {
        return endingCol;
    }

    public boolean checkIfEnding(){
        if(playerRow == this.getEndingRow() && playerCol == this.getEndingCol()){
            return true;
        }
        return false;
    }

    @Override
    public void setMaze(Maze maze) {
        Gencounter++;
        this.maze = maze;
        LOG.info("client number: " + Gencounter + " loaded a " + this.maze.getMazeStruct().length + "," + this.maze.getMazeStruct()[0].length + " maze");
        end = false;
        setChanged();
        notifyObservers("maze generated");
        solution = null;
        playerRow = maze.getStartPosition().getRowIndex();
        playerCol = maze.getStartPosition().getColumnIndex();
        endingRow = maze.getGoalPosition().getRowIndex();
        endingCol = maze.getGoalPosition().getColumnIndex();
        notifyMovement();
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    private void notifyMovement() {
        setChanged();
        if(end == true){
            notifyObservers("ending");
        }
        else{
            notifyObservers("player moved");
        }
    }

}




