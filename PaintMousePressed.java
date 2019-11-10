package Paint2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * PaintMousePressed handles the drawing event for when the mouse is pressed. This is used for the initial coordinates
 * and colors of the shape being drawn
 *
 * @author Michael Kumicich
 * 11/8/2019
 */

public class PaintMousePressed {
    private double ArcWidth, ArcHeight = 30.0;                                  //Arc rectangle curves

    /**
     * Set size and start point of pencil square
     * @param graphicsContext, cpLine, e
     * @return true
     */
    public Boolean DrawPres(GraphicsContext graphicsContext, ColorPicker cpLine, MouseEvent e){
        graphicsContext.setStroke(cpLine.getValue());                           //Sets stroke color
        graphicsContext.beginPath();                                            //Start of pencil
        graphicsContext.lineTo(e.getX(), e.getY());                             //Draws where mouse is clicked

        return true;
    }

    /**
     * Creates line start point and assigns line colors/stroke width
     * @param graphicsContext, cpLine, cpFill, line, e
     * @return true
     */
    public Boolean LinePres(GraphicsContext graphicsContext, ColorPicker cpLine, Line line, MouseEvent e){
        graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value

        line.setStartX(e.getX());                                               //sets start x coordinate
        line.setStartY(e.getY());                                               //sets start y coordinate

        return true;
    }

    /**
     * Creates Rectangle start point and assigns Rectangle colors/stroke width
     * @param graphicsContext, cpLine, cpFill, rectangle, e
     * @return true
     */
    public Boolean RectPres(GraphicsContext graphicsContext, ColorPicker cpLine, ColorPicker cpFill, Rectangle rectangle,
                            MouseEvent e){
        graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
        graphicsContext.setFill(cpFill.getValue());                             //sets fill value

        rectangle.setX(e.getX());                                               //sets Rectangle start x = mouse
        rectangle.setY(e.getY());                                               //sets Rectangle start y = mouse

        return true;
    }

    /**
     * Creates copy rectangle
     * @param selRect, e
     * @return true
     */
    public Boolean CopyPres(Rectangle selRect, MouseEvent e){
        //Starting x and y values for copy
        selRect.setX(e.getX());
        selRect.setY(e.getY());

        return true;
    }

    /**
     * Creates Arc Rectangle start point and assigns Arc Rectangle colors/stroke width
     * @param graphicsContext, cpLine, cpFill, e, Arc Rectangle
     * @return true
     */
    public Boolean ArcRectPres(GraphicsContext graphicsContext, ColorPicker cpLine, ColorPicker cpFill, MouseEvent e,
                               Rectangle arcRectangle){
        graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
        graphicsContext.setFill(cpFill.getValue());                             //sets fill vale

        arcRectangle.setX(e.getX());                                            //sets Rectangle start x = mouse
        arcRectangle.setY(e.getY());                                            //sets Rectangle start y = mouse

        arcRectangle.setArcWidth(ArcWidth);                                     //sets Arc Width
        arcRectangle.setArcHeight(ArcHeight);                                   //sets Arc Height

        return true;
    }

    /**
     * Set size and start point of eraser square
     * @param graphicsContext, e
     * @return true
     */
    public Boolean RubPres(GraphicsContext graphicsContext, MouseEvent e){
        double lineWidth = graphicsContext.getLineWidth();
        graphicsContext.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);

        return true;
    }

    /**
     * Creates circle start point and assigns circle colors/stroke width
     * @param graphicsContext, cpLine, cpFill, circle, e
     * @return true
     */
    public Boolean CircPres(GraphicsContext graphicsContext, ColorPicker cpLine, ColorPicker cpFill, Circle circle,
                            MouseEvent e){
        graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
        graphicsContext.setFill(cpFill.getValue());                             //sets fill value
        circle.setCenterX(e.getX());                                            //sets circle center x = mouse
        circle.setCenterY(e.getY());                                            //sets circle center y = mouse

        return true;
    }

    /**
     * Creates ellipse start point and assigns ellipse colors/stroke width
     * @param graphicsContext, cpLine, cpFill, ellipse, e
     * @return true
     */
    public Boolean ElpsPres(GraphicsContext graphicsContext, ColorPicker cpLine, ColorPicker cpFill, Ellipse ellipse,
                            MouseEvent e){
        graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
        graphicsContext.setFill(cpFill.getValue());                             //sets fill value
        ellipse.setCenterX(e.getX());                                           //sets ellipse center x = mouse
        ellipse.setCenterY(e.getY());                                           //sets ellipse center y = mouse

        return true;
    }

    /**
     * grabs color on canvas
     * @param graphicsContext, e, image, cpLine
     * @return true
     */
    public Boolean GrabPres(GraphicsContext graphicsContext, MouseEvent e, Image image, ColorPicker cpLine){
        graphicsContext.beginPath();
        double x0 = e.getX();
        double y0 = e.getY();
        graphicsContext.beginPath();
        PixelReader colorDropper = image.getPixelReader();

        Color newColor = colorDropper.getColor((int)x0, (int)y0);               //gets color where mouse is clicked
        cpLine.setValue(newColor);                                              //sets stroke = color grabbed
        cpLine.getCustomColors().add(newColor);                                 //adds this color to custom colors

        return true;
    }

    /**
     * Creates selection rectangle
     * @param graphicsContext, selRect, e
     * @return true
     */
    public Boolean SelPres(GraphicsContext graphicsContext, Rectangle selRect, MouseEvent e){
        //Sets rectangle to leave behind as white
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFill(Color.WHITE);

        //Starting x and y values for select
        selRect.setX(e.getX());
        selRect.setY(e.getY());

        return true;
    }

    /**
     * Draws text from textfield
     * @param graphicsContext, slider, cpLine, cpFill, e, textArea
     * @return true
     */
    public Boolean TextPres(GraphicsContext graphicsContext, Slider slider, ColorPicker cpLine, ColorPicker cpFill,
                            MouseEvent e, TextField textArea){
        //sets text characteristics
        graphicsContext.setLineWidth(1);
        graphicsContext.setFont(Font.font(slider.getValue()));
        graphicsContext.setStroke(cpLine.getValue());
        graphicsContext.setFill(cpFill.getValue());

        //pastes text
        graphicsContext.fillText(textArea.getText(), e.getX(), e.getY());
        graphicsContext.strokeText(textArea.getText(), e.getX(), e.getY());

        return true;
    }

    /**
     * Horizontal start point for polygon
     * @param e
     * @return polyStartX
     */
    public double getPolyStartX(MouseEvent e){
        double polyStartX = e.getX();                                             //sets polyStartX = mouse x coordinate
        return polyStartX;                                                        //returns polyStartX
    }

    /**
     * Vertical start point for polygon
     * @param e
     * @return polyStartY
     */
    public double getPolyStartY(MouseEvent e){
        double polyStartY = e.getY();                                             //sets polyStartY = mouse y coordinate
        return polyStartY;                                                        //returns polyStartY
    }
}
