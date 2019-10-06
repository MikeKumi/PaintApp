package Paint2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.WritableImage;


public class PaintMouseDragged {
    public void MouseDragged(ToggleButton pencilBtn, ToggleButton rubberbtn, GraphicsContext graphicsContext, MouseEvent e,
                             ToggleButton lineBtn, ToggleButton rectBtn, ToggleButton circleBtn, ToggleButton ellipseBtn,
                             Line line, Rectangle rectangle, Circle circle, Ellipse ellipse, WritableImage tmpSnap,
                             ToggleButton arcRectBtn, Rectangle arcRectangle, ToggleButton moveBtn, WritableImage selImg) {

        //If pencilBtn
        if (pencilBtn.isSelected()) {
            graphicsContext.lineTo(e.getX(), e.getY());                                 //connects to mouse x and y
            graphicsContext.stroke();                                                   //draws
        }

        //If rubber Btn
        else if (rubberbtn.isSelected()) {
            double lineWidth = graphicsContext.getLineWidth();

            //erases from mouseclicked - lineWidth/2
            graphicsContext.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
        }

        //If lineBtn
        else if(lineBtn.isSelected()) {
            graphicsContext.drawImage(tmpSnap, 0, 0);                            //redraws image for preview
            line.setEndX(e.getX());                                                     //sets line end X value
            line.setEndY(e.getY());                                                     //sets line end y value
            graphicsContext.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()); //draws line
        }

        //If rectangle Btn
        else if(rectBtn.isSelected()) {
            graphicsContext.drawImage(tmpSnap, 0, 0);                            //redraws image for preview
            rectangle.setWidth(Math.abs((e.getX() - rectangle.getX())));                //Width = mouse x - start point
            rectangle.setHeight(Math.abs((e.getY() - rectangle.getY())));               //Width = mouse y - start point

            if(rectangle.getX() > e.getX()) {
                rectangle.setX(e.getX());                                               //resets X
            }
            if(rectangle.getY() > e.getY()) {
                rectangle.setY(e.getY());                                               //resets Y

            }
            //draw rectangle
            graphicsContext.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            graphicsContext.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }

        //If arc rectangle Btn
        else if(arcRectBtn.isSelected()) {
            graphicsContext.drawImage(tmpSnap, 0, 0);                            //redraws image for preview
            arcRectangle.setWidth(Math.abs((e.getX() - arcRectangle.getX())));          //Width = mouse x - start point
            arcRectangle.setHeight(Math.abs((e.getY() - arcRectangle.getY())));         //Width = mouse y - start point
            arcRectangle.setArcWidth(30.0f);
            arcRectangle.setArcHeight(30.0f);
            if(arcRectangle.getX() > e.getX()) {
                arcRectangle.setX(e.getX());                                            //resets x
            }
            if(arcRectangle.getY() > e.getY()) {
                arcRectangle.setY(e.getY());                                            //resets y
            }

            //draws arc rectangle
            graphicsContext.fillRoundRect(arcRectangle.getX(), arcRectangle.getY(), arcRectangle.getWidth(),
                    arcRectangle.getHeight(), arcRectangle.getArcWidth(), arcRectangle.getArcHeight());

            graphicsContext.strokeRoundRect(arcRectangle.getX(), arcRectangle.getY(), arcRectangle.getWidth(),
                    arcRectangle.getHeight(), arcRectangle.getArcWidth(), arcRectangle.getArcHeight());
        }

        //If circle Btn
        else if(circleBtn.isSelected()) {
            graphicsContext.drawImage(tmpSnap, 0, 0);                           //redraws image for preview

            //sets radius
            circle.setRadius((Math.abs(e.getX() - circle.getCenterX()) + Math.abs(e.getY() - circle.getCenterY())) / 2);

            if (circle.getCenterX() > e.getX()) {
                circle.setCenterX(e.getX());                                            //resets center x
            }
            if (circle.getCenterY() > e.getY()) {
                circle.setCenterY(e.getY());                                            //resets center y
            }

            //draws circle
            graphicsContext.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
            graphicsContext.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
        }

        //if ellipse btn
        else if(ellipseBtn.isSelected()){
            graphicsContext.drawImage(tmpSnap, 0, 0);                           //redraws image for preview
            ellipse.setRadiusX(Math.abs(e.getX() - ellipse.getCenterX()));             //Radius x = mouse x - center x
            ellipse.setRadiusY(Math.abs(e.getY() - ellipse.getCenterY()));             //Radius y = mouse y - center y

            if(ellipse.getCenterX() > e.getX()) {
                ellipse.setCenterX(e.getX());                                          //Resets Center X
            }
            if(ellipse.getCenterY() > e.getY()) {
                ellipse.setCenterY(e.getY());                                          //Resets Center Y
            }

            //draws ellipse
            graphicsContext.strokeOval(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());
            graphicsContext.fillOval(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());
        }

        //if move btn
        else if(moveBtn.isSelected()){
            graphicsContext.drawImage(tmpSnap, 0, 0);                           //redraws image for preview
            graphicsContext.drawImage(selImg, e.getX(), e.getY());                     //draws image that can be moved
        }

    }


    public void nPolygon(ToggleButton polyBtn, MouseEvent e, double polyStartX, double polyStartY, int numSides,
                         GraphicsContext graphicsContext, WritableImage tmpSnap){

        //if polygon btn
        if(polyBtn.isSelected()) {                                                     //redraws image for preview
            graphicsContext.drawImage(tmpSnap, 0, 0);

            //sets radies
            double radius = ((Math.abs(e.getX() - polyStartX) + Math.abs(e.getY() - polyStartY)) / 2);

            if (polyStartX > e.getX()) {
                polyStartX = e.getX();                                                 //resets start X
            }

            if (polyStartY > e.getY()) {
                polyStartY = e.getY();                                                 //resets start Y
            }

            //coordinate arrays with size of numSizes
            double[] xSides = new double[numSides];
            double[] ySides = new double[numSides];

            //fills arrays
            for(int i = 0;i<numSides;i++){
                xSides[i] = radius * Math.cos(2*i*Math.PI/numSides) + polyStartX;
                ySides[i] = radius * Math.sin(2*i*Math.PI/numSides) + polyStartY;
            }

            //draws polygon
            graphicsContext.strokePolygon(xSides, ySides, numSides);
            graphicsContext.fillPolygon(xSides, ySides, numSides);
        }
    }
}
