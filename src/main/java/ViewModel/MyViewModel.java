package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.event.MouseWheelEvent;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model){
        this.model = model;
        this.model.assignObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void generateMaze(int rows, int cols){ //view will check to see if the rows and cols are valid
        model.generateMaze(rows,cols);
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public void solveMazeWithoutWriting(){
        model.solveMazeWithoutWriting();
    }

    public void setMaze(Maze maze){
        this.model.setMaze(maze);
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public void movePlayer(KeyEvent keyEvent){ //he gets the key because he negotitates the movement model doesnt need to know if a key was pressed
        MovementDirection direction;
        switch(keyEvent.getCode()){
            case NUMPAD8 -> direction = MovementDirection.UP;
            case NUMPAD2 -> direction = MovementDirection.DOWN;
            case NUMPAD4 -> direction = MovementDirection.LEFT;
            case NUMPAD6 -> direction = MovementDirection.RIGHT;
            case NUMPAD7 -> direction = MovementDirection.UPLEFT;
            case NUMPAD9 -> direction = MovementDirection.UPRIGHT;
            case NUMPAD1 -> direction = MovementDirection.DOWNLEFT;
            case NUMPAD3 -> direction = MovementDirection.DOWNRIGHT;
            default -> {
                return;
            }
        }
        model.updatePlayerLocation(direction);
    }

    public int getPlayerRow(){
        return model.getPlayerRow();
    }

    public int getPlayerCol(){
        return model.getPlayerCol();
    }

    public int getEndingRow(){
        return model.getEndingRow();
    }

    public int getEndingCol(){
        return model.getEndingCol();
    }

    public void movePlayerWithMouse(MouseEvent mouseEvent, double height, double width) {
        model.updatePlayerLocationMouse(mouseEvent,height,width);
    }

}
