package Paint2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PaintMousePressed {
    private double ArcWidth, ArcHeight = 30.0;

    public Boolean MousePressed(GraphicsContext graphicsContext, ToggleButton pencilBtn, ToggleButton lineBtn,
                             ToggleButton rectBtn, ToggleButton circleBtn, ToggleButton ellipseBtn, ToggleButton grabberBtn,
                                ToggleButton rubberbtn, MouseEvent e, ColorPicker cpLine, ColorPicker cpFill, Line line,
                             Rectangle rectangle, Circle circle, Ellipse ellipse, Boolean drawn, Image image, TextField textArea,
                                ToggleButton textBtn, Slider slider, ToggleButton arcRectBtn, Rectangle arcRectangle,
                                ToggleButton selectBtn, Rectangle selRect, PaintTests paintTests){


        //if pencil Btn
        if(pencilBtn.isSelected()) {
            graphicsContext.setStroke(cpLine.getValue());                           //Sets stroke color
            graphicsContext.beginPath();                                            //Start of pencil
            graphicsContext.lineTo(e.getX(), e.getY());                             //Draws where mouse is clicked
            drawn = true;
        }

        //if Line btn
        else if(lineBtn.isSelected()) {
            graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value

            line.setStartX(e.getX());                                               //sets start x coordinate
            line.setStartY(e.getY());                                               //sets start y coordinate
            drawn = true;
        }

        //if rectangle btn
        else if(rectBtn.isSelected()) {
            graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
            graphicsContext.setFill(cpFill.getValue());                             //sets fill value

            rectangle.setX(e.getX());                                               //sets Rectangle start x = mouse
            rectangle.setY(e.getY());                                               //sets Rectangle start y = mouse
            drawn = true;
        }

        //if arc rectangle btn
        else if(arcRectBtn.isSelected()) {
            graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
            graphicsContext.setFill(cpFill.getValue());                             //sets fill vale

            arcRectangle.setX(e.getX());                                            //sets Rectangle start x = mouse
            arcRectangle.setY(e.getY());                                            //sets Rectangle start y = mouse

            arcRectangle.setArcWidth(ArcWidth);                                     //sets Arc Width
            arcRectangle.setArcHeight(ArcHeight);                                   //sets Arc Height
            drawn = true;
        }

        //if rubber btn
        else if(rubberbtn.isSelected()) {
            double lineWidth = graphicsContext.getLineWidth();
            graphicsContext.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            drawn = true;
        }

        //if circle btn
        else if(circleBtn.isSelected()) {
            graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
            graphicsContext.setFill(cpFill.getValue());                             //sets fill value
            circle.setCenterX(e.getX());                                            //sets circle center x = mouse
            circle.setCenterY(e.getY());                                            //sets circle center y = mouse
            drawn = true;
        }

        //if ellipse btn
        else if(ellipseBtn.isSelected()) {
            graphicsContext.setStroke(cpLine.getValue());                           //sets stroke value
            graphicsContext.setFill(cpFill.getValue());                             //sets fill value
            ellipse.setCenterX(e.getX());                                           //sets ellipse center x = mouse
            ellipse.setCenterY(e.getY());                                           //sets ellipse center y = mouse
            drawn = true;
        }

        //if grabber btn
        else if(grabberBtn.isSelected()) {
            graphicsContext.beginPath();
            double x0 = e.getX();
            double y0 = e.getY();
            graphicsContext.beginPath();
            PixelReader colorDropper = image.getPixelReader();

            Color newColor = colorDropper.getColor((int)x0, (int)y0);               //gets color where mouse is clicked
            cpLine.setValue(newColor);                                              //sets stroke = color grabbed
            cpLine.getCustomColors().add(newColor);                                 //adds this color to custom colors

            drawn = false;
        }

        //if select btn
        else if(selectBtn.isSelected()) {
            //Sets rectangle to leave behind as white
            graphicsContext.setStroke(Color.WHITE);
            graphicsContext.setFill(Color.WHITE);

            //Starting x and y values for select
            selRect.setX(e.getX());
            selRect.setY(e.getY());

            drawn = true;
        }

        //if text btn
        else if(textBtn.isSelected()) {

            //sets text characteristics
            graphicsContext.setLineWidth(1);
            graphicsContext.setFont(Font.font(slider.getValue()));
            graphicsContext.setStroke(cpLine.getValue());
            graphicsContext.setFill(cpFill.getValue());

            //pastes text
            graphicsContext.fillText(textArea.getText(), e.getX(), e.getY());
            graphicsContext.strokeText(textArea.getText(), e.getX(), e.getY());

            drawn = true;
        }

        paintTests.StrokeTest((Color) graphicsContext.getStroke(), cpLine.getValue());
        paintTests.FillTest((Color) graphicsContext.getFill(), cpFill.getValue());

        return drawn;
    }

    public double getPolyStartX(MouseEvent e){
        double polyStartX = e.getX();                                             //sets polyStartX = mouse x coordinate
        return polyStartX;                                                        //returns polyStartX
    }

    public double getPolyStartY(MouseEvent e){
        double polyStartY = e.getY();                                             //sets polyStartY = mouse y coordinate
        return polyStartY;                                                        //returns polyStartY
    }


}
