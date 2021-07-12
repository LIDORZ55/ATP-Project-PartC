package View;


import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    private double playerRow = 0;
    private double playerCol = 0;
    private int endingRow = 0;
    private int endingCol = 0;



    private Solution solution;
    private Maze maze;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameEnding = new SimpleStringProperty();



    public double getPlayerRow() {
        return playerRow;
    }

    public double getPlayerCol() {
        return playerCol;
    }

    public int getEndingRow() {
        return endingRow;
    }

    public int getEndingCol() {
        return endingCol;
    }

    public void setPlayerPosition(double row,double col) {
        this.playerCol = col;
        this.playerRow = row;
        draw();
    }

    public void setEndingPosition(int row,int col){
        this.endingCol = col;
        this.endingRow = row;
        draw();
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public String getImageFileNameEnding() {
        return imageFileNameEnding.get();
    }

    public void setImageFileNameEnding(String imageFileNameEnding) {
        this.imageFileNameEnding.set(imageFileNameEnding);
    }

    public void drawMaze(Maze maze) {
        this.maze = maze;
        this.solution = null;
        this.endingRow = this.maze.getGoalPosition().getRowIndex();
        this.endingCol = this.maze.getGoalPosition().getColumnIndex();
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.GetRow();
            int cols = maze.GetColumn();

            double cellHeight = canvasHeight/rows;
            double cellWidth = canvasWidth/cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas
            graphicsContext.clearRect(0,0,canvasWidth,canvasHeight);

            drawMazeWalls(graphicsContext,rows,cols,cellHeight,cellWidth);
            if(solution != null){
                drawSolution(graphicsContext,cellHeight,cellWidth);
            }

            drawMazePlayer(graphicsContext,cellHeight,cellWidth);
            drawMazeEnding(graphicsContext,cellHeight,cellWidth);

        }
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        //we will want a green rectangle to move to the solution based on the steps

        ArrayList<AState> mazeSolutionSteps = solution.getSolutionPath();
        Image playerImage = null;
        graphicsContext.setFill(Color.GREEN);

        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
            int[] xAndY = mazeSolutionStepIntoInt(mazeSolutionSteps.get(i).toString());
            double x = xAndY[1] * cellWidth;
            double y = xAndY[0] * cellHeight;
            if(playerImage == null){
                graphicsContext.fillRect(x,y,cellWidth,cellHeight);
            }
            else{
                graphicsContext.drawImage(playerImage,x,y,cellWidth,cellHeight);
            }
        }

    }

    private int[] mazeSolutionStepIntoInt(String mazeStep){
        int[] twonumbers = new int[2];
        int i = 1;
        String num = "";
        while(i<mazeStep.length()){
            if(mazeStep.charAt(i) == ','){
                twonumbers[0] = Integer.parseInt(num);
                num = "";
                i++;
            }
            else if(mazeStep.charAt(i) == '}'){
                twonumbers[1] = Integer.parseInt(num);
                i++;
            }
            else{
                char c = mazeStep.charAt(i);
                num += c;
                i++;
            }
        }
        return twonumbers;
    }

    private void drawMazePlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image");
        }

        graphicsContext.setFill(Color.GREEN);

        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow()*cellHeight;
        if(playerImage == null){
            graphicsContext.fillRect(x,y,cellWidth,cellHeight);
        }
        else{
            graphicsContext.drawImage(playerImage,x,y,cellWidth,cellHeight);
        }
    }

    private void drawMazeEnding(GraphicsContext graphicsContext, double cellHeight, double cellWidth){
        Image endingImage = null;
        try {
            endingImage = new Image(new FileInputStream(getImageFileNameEnding()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image");
        }

        graphicsContext.setFill(Color.GREEN);

        double x = getEndingCol() * cellWidth;
        double y = getEndingRow() * cellHeight;
        if(endingImage == null){
            graphicsContext.fillRect(x,y,cellWidth,cellHeight);
        }
        else{
            graphicsContext.drawImage(endingImage,x,y,cellWidth,cellHeight);
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, int rows, int cols, double cellHeight, double cellWidth) {
        Image wallImage = null;
        try {
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image");
        }

        graphicsContext.setFill(Color.RED);

        double x = 0;
        double y = 0;
        int[][] mazeStruct = maze.getMazeStruct();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(mazeStruct[i][j] == 1){
                    //it is a wall
                    x = j*cellWidth;
                    y = i*cellHeight;
                    if(wallImage == null){
                        graphicsContext.fillRect(x,y,cellWidth,cellHeight);
                    }
                    else{
                        graphicsContext.drawImage(wallImage,x,y,cellWidth,cellHeight);
                    }

                }
            }
        }
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }

    public void movePlayer(double x, double y) {
        setPlayerPosition(x,y);
        draw();
    }
}
