package Paint2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * PaintRedo handles the redo stack methodology for when a shape is to be redrawn
 *
 * @author Michael Kumicich
 * 10/7/2019
 */

public class PaintRedo {

    /**
     * return line to be pushed
     * @param shape, graphicsContext
     * @return shape
     */
    public Shape undoPush(Shape shape, GraphicsContext graphicsContext){

        //if shape == line
        if(shape.getClass() == Line.class) {

            //creates line
            Line tempLine = (Line) shape;

            //draws line
            graphicsContext.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());

            //returns line
            return (new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
        }

        //if shape == rectangle
        else if(shape.getClass() == Rectangle.class) {

            //creates rectangle
            Rectangle tempRect = (Rectangle) shape;

            //draws rectangle
            graphicsContext.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
            graphicsContext.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());

            //returns rectangle
            return (new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
        }

        //if shape == circle
        else if(shape.getClass() == Circle.class) {

            //creates circle
            Circle tempCirc = (Circle) shape;

            //draws circle
            graphicsContext.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
            graphicsContext.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());

            //returns circle
            return (new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
        }

        //if shape == ellipse
        else {

            //creates ellipse
            Ellipse tempElps = (Ellipse) shape;

            //draws ellipse
            graphicsContext.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
            graphicsContext.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());

            //returns ellipse
            return (new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
        }
    }
}
