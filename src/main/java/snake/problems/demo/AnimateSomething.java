package snake.problems.demo;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

import static snake.problems.demo.StartSnake.*;

public class AnimateSomething extends AnimationTimer {
    private long lastUpdate = 0;
    public List<Integer> segX = new ArrayList<>();
    public List<Integer> segY = new ArrayList<>();

    @Override
    public void handle(long now){
        int speed = (int) head.getHeight();
        if(now - lastUpdate >= 55_000_000){
            lastUpdate = now;
            updateScore();
            storeCoords();
            switch (Direction) {
                case ("NORTH") -> {
                    if ((head.getLayoutY() - speed) < 0){
                        head.setLayoutY(600);
                    }
                    head.setLayoutY(head.getLayoutY() - speed);
                }
                case ("SOUTH") -> {
                    if ((head.getLayoutY() + speed) > 580){
                        head.setLayoutY(-20);
                    }
                    head.setLayoutY(head.getLayoutY() + speed);
                }
                case ("EAST") -> {
                    if ((head.getLayoutX() + speed) > 580){
                        head.setLayoutX(-20);
                    }
                    head.setLayoutX(head.getLayoutX() + speed);
                }
                case ("WEST") -> {
                    if ((head.getLayoutX() - speed) < 0){
                        head.setLayoutX(600);
                    }
                    head.setLayoutX(head.getLayoutX() - speed);
                }
            }
            segments();
            detect();
        }
    }
    void updateScore(){
        score.setText(Integer.toString(size));
    }
    private void storeCoords(){

        segX.add((int) head.getLayoutX());
        segY.add((int) head.getLayoutY());
        //remove the oldest value if size is smaller than stored values
        if (size < segX.size()){
            segX.remove(0);
            segY.remove(0);
        }
    }

    private void segments(){
        int index = segX.size() - 1;
        for(Rectangle seg : segments){
            seg.setLayoutX(segX.get(index));
            seg.setLayoutY(segY.get(index));
            index--;
        }
    }

    private void detect(){
        if (head.getBoundsInParent().intersects(food.getBoundsInParent())){
            StartSnake ss = new StartSnake();
            ss.foodLocation(food);
            size++;
            ss.Grow();
        }
        for (Rectangle seg : segments){
            String id = seg.getId();
            Shape intersection = Shape.intersect(head, seg);
            if (intersection.getBoundsInLocal().getHeight() != -1 && !id.contains("don't count")){
                timer.stop();
                end.setVisible(true);
                finalScore.setText(Integer.toString(size));
                score.setText("1");
                size = 1;
                head.setLayoutX(0);
                head.setLayoutY(0);
            }
        }
    }
}
