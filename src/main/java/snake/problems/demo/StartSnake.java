package snake.problems.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StartSnake extends Application {
    public static Rectangle head;
    public static String Direction = "";
    public static Rectangle food;
    public static int size = 1;
    public static List<Rectangle> segments = new ArrayList<>();
    public static Pane pane = new Pane();
    static int id = 0;
    public static AnimationTimer timer = new AnimateSomething();
    public static Label score = new Label("oi");
    Color body;
    float increment = 0.1f;

    @Override
    public void start(Stage stage){
        score.setFont(new Font("Arial", 20));
        pane.getChildren().addAll(score, Food(), snakeHead());

        pane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> {
                    if (!Direction.contains("SOUTH")){
                        Direction = "NORTH";
                    }
                }
                case DOWN -> {
                    if (!Direction.contains("NORTH")){
                        Direction = "SOUTH";
                    }
                }
                case LEFT -> {
                    if (!Direction.contains("EAST")){
                        Direction = "WEST";
                    }
                }
                case RIGHT -> {
                    if (!Direction.contains("WEST")){
                        Direction = "EAST";
                    }
                }
            }
        });

        timer.start();

        stage.setScene(new Scene(pane, 600, 600));
        stage.setTitle("Snake Game");
        stage.show();
        pane.requestFocus();
    }

    private Node snakeHead(){
        head = new Rectangle(20, 20);
        return head;
    }

    private Node Food(){
        food = new Rectangle(20, 20);
        food.setFill(Color.RED);
        foodLocation(food);
        return food;
    }

    public void Grow(){
        Rectangle segment = new Rectangle(20, 20);
        body = new Color(0.5-increment, 0.5-increment, 0.5-increment, 1.0);
        increment -= 0.01f;
        segment.setFill(body);
        if(id <= 2){
            segment.setId("don't count");
            System.out.println("aag");
            id++;
        }
        else{
            segment.setId(String.valueOf(id));
        }
        id++;
        segments.add(segment);
        pane.getChildren().add(segment);
    }

    public void foodLocation(Node food){
        int foodX = (int) (Math.random() * 580);
        int foodY = (int) (Math.random() * 580);
        food.setLayoutX(foodX);
        food.setLayoutY(foodY);
    }

    public static void main(String[] args) {
        launch();
    }
}