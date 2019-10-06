package Paint2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import java.util.Stack;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;

public class PaintTests {
    public void CanvasTest(String canvasWidth, String canvasHeight, Canvas canvas){
        if(Double.parseDouble(canvasWidth) == canvas.getWidth()){
            System.out.println("Canvas width has successfully resized to " + canvasWidth + "\n");
        }
        else {
            System.out.println("Canvas width has not been resized");
        }

        if(Double.parseDouble(canvasHeight) == canvas.getHeight()){
            System.out.println("Canvas height has successfully resized to " + canvasHeight + "\n");
        }
        else {
            System.out.println("Canvas Height has not been resized");
        }
    }

    public void StrokeTest(Color inStroke, Color cpLine){
        if(inStroke == cpLine){
            System.out.println("The Shape stroke matches the color picker stroke");
        }
        else{
            System.out.println("Shape stroke does not match color picker stroke");
        }
    }

    public void FillTest(Color inFill, Color cpFill){
        if(inFill == cpFill){
            System.out.println("The Shape fill matches the color picker fill");
        }
        else{
            System.out.println("Shape fill does not match color picker fill\n");
        }
    }
}
