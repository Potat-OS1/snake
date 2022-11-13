package snake.problems.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    public static StackPane end = new StackPane();
    public static StackPane intro = new StackPane();
    public static Pane segmentPane = new Pane();
    static int id = 0;
    public static AnimationTimer timer = new AnimateSomething();
    public static Label score = new Label("");
    public static Text finalScore = new Text("pineapple");
    Color body;
    float increment = 0.1f;
    boolean startbool = false;
    double decider;

    @Override
    public void start(Stage stage){
        score.setFont(new Font("Arial", 20));
        pane.getChildren().addAll(score, Food(), snakeHead(), segmentPane, introScreen(), endScreen());

        pane.setOnKeyPressed(e -> {
            if(startbool){
                //sets the direction for animate something to deal with, and it denies going the opposite direction your currently going.
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
            }
        });

        timer.start();

        stage.setScene(new Scene(pane, 600, 600));
        stage.setTitle("Snake Game");
        stage.show();
        pane.requestFocus();
    }

    private Node introScreen(){
        //creates the intro screen layout.
        intro.setMinSize(600, 600);
        Rectangle start = new Rectangle(500, 100);
        Text introText = new Text("Snake Game");
        introText.setFont(new Font("Arial", 80));
        Text subText = new Text("Press Button to Start.");
        subText.setFont(new Font("Arial", 50));

        //putting it together
        Region spacing = new Region();
        Region spacing2 = new Region();
        Region spacing3 = new Region();
        VBox.setVgrow(spacing, Priority.ALWAYS);
        VBox.setVgrow(spacing2, Priority.ALWAYS);
        VBox.setVgrow(spacing3, Priority.ALWAYS);
        VBox vbox = new VBox(spacing3, introText, subText, spacing, start, spacing2);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        intro.getChildren().add(vbox);
        intro.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));

        //start button behavior
        start.setOnMouseClicked(e->{
            intro.setVisible(false);
            startbool = true;
            segments.clear();
            segmentPane.getChildren().clear();
            timer.start();
        });

        return intro;
    }

    private Node endScreen(){
        end.setVisible(false);
        Text endText = new Text("Game Over");
        endText.setFont(new Font("Arial", 100));
        finalScore.setFont(new Font("Arial", 70));

        Region spacing = new Region();
        Region spacing2 = new Region();
        Region spacing3 = new Region();
        VBox.setVgrow(spacing, Priority.ALWAYS);
        VBox.setVgrow(spacing2, Priority.ALWAYS);
        VBox.setVgrow(spacing3, Priority.ALWAYS);

        //button to restart
        Rectangle restart = new Rectangle(500, 100);
        restart.setOnMouseClicked(e->{
            end.setVisible(false);
            intro.setVisible(true);
            Direction = "";
        });

        VBox vbox = new VBox(spacing, endText, finalScore, spacing2, restart, spacing3);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);

        end.getChildren().add(vbox);
        end.setMinSize(600, 600);
        end.setPadding(new Insets(10, 10, 10, 10));
        end.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));

        return end;
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
            id++;
        }
        else{
            segment.setId(String.valueOf(id));
        }
        id++;
        segments.add(segment);
        segmentPane.getChildren().add(segment);
    }

    public void foodLocation(Node food){
        changeFood((Rectangle) food);
        int foodX = (int) (Math.random() * 580);
        int foodY = (int) (Math.random() * 580);
        food.setLayoutX(foodX);
        food.setLayoutY(foodY);
    }

    private void changeFood(Rectangle noms){
        decider = Math.random();
        if (decider < .5){
            double sizeC = (Math.random() * (20 - 10)) + 10;
            noms.setHeight(sizeC);
            noms.setWidth(sizeC);
            noms.setFill(Color.RED);
        }
        if (decider >= .5){
            size++;
            Grow();
            double sizeC = (Math.random() * (30 - 20)) + 20;
            noms.setHeight(sizeC);
            noms.setWidth(sizeC);
            noms.setFill(Color.BLUE);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}