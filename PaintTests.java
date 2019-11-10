package Paint2;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * PaintTests is used for the unit tests.
 *
 * @author Michael Kumicich
 * 10/7/2019
 */

public class PaintTests {
    /**
     * Test if canvas is the right size
     * @param canvasWidth, canvasHeight, canvas
     */
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

    /**
     * Test to check if stroke color matches the picked color
     * @param inStroke, cpLine
     */
    public void StrokeTest(Color inStroke, Color cpLine){
        if(inStroke == cpLine){
            System.out.println("The Shape stroke matches the color picker stroke");
        }
        else{
            System.out.println("Shape stroke does not match color picker stroke");
        }
    }

    /**
     * Test to check if fill color matches the picked color
     * @param inFill, cpLine
     */
    public void FillTest(Color inFill, Color cpFill){
        if(inFill == cpFill){
            System.out.println("The Shape fill matches the color picker fill");
        }
        else{
            System.out.println("Shape fill does not match color picker fill\n");
        }
    }
}
