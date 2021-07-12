package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.MouseEvent;

import java.util.Observer;

public interface IModel {
    void generateMaze(int rows, int columns);
    Maze getMaze();
    void solveMaze();
    void solveMazeWithoutWriting();
    Solution getSolution(); //our solution from the jar
    void updatePlayerLocation(MovementDirection direction); //we will need to verify our player moves to see if they are legit or if they reached the end
    void updatePlayerLocationMouse(MouseEvent mouseEvent,double height, double width);
    int getPlayerRow();
    int getPlayerCol();
    void assignObserver(Observer o); //we do this becuase interface cant inherit another class (observer) so we need to make the operation assignobserver insted of using the build in one
    int getEndingRow();
    int getEndingCol();
    boolean checkIfEnding();
    void setMaze(Maze maze);
}
