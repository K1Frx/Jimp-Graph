package graph.jimpgraph;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static graph.jimpgraph.HelloController.*;
import static javafx.scene.paint.Color.*;

public class ClickableCircle extends Circle {

    static boolean isGreenChosen = false;
    static boolean isRedChosen = false;

    public ClickableCircle(){
        this.setOnMouseClicked(onMousePressedEventHandler());
    }

    public void setGreenColor(){
        this.setFill(GREEN);
    }
    public void setRedColor(){
        this.setFill(RED);
    }
    public void setBlackColor(){ this.setFill(BLACK); }

    public Paint getColor() { return this.getFill(); }

    private EventHandler<MouseEvent> onMousePressedEventHandler() {
        return event ->{
            if(!isGreenChosen && getColor()==BLACK){
                setGreenColor();
                isGreenChosen = true;
            }
            else if(isGreenChosen && getColor()==GREEN && !isRedChosen){
                setRedColor();
                isGreenChosen = false;
                isRedChosen = true;
            }
            else if(!isRedChosen){
                setRedColor();
                isRedChosen = true;
            }
            else if(isRedChosen && getColor()==RED){
                setBlackColor();
                isRedChosen = false;
            }
            else if(isGreenChosen && isRedChosen && (getColor() == GREEN || getColor() == RED)){
                isGreenChosen = false;
                setBlackColor();
            }
        };
    }
}
