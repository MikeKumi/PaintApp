package Paint2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class PaintUndo {
    public Shape redoPush(Shape removedShape, GraphicsContext graphicsContext){
        if(removedShape.getClass() == Line.class) {

            //creates line
            Line tempLine = (Line) removedShape;

            //sets properties
            tempLine.setFill(graphicsContext.getFill());
            tempLine.setStroke(graphicsContext.getStroke());
            tempLine.setStrokeWidth(graphicsContext.getLineWidth());

            //returns line
            return (new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));

        }
        else if(removedShape.getClass() == Rectangle.class) {

            //creates rectangle
            Rectangle tempRect = (Rectangle) removedShape;

            //sets properties
            tempRect.setFill(graphicsContext.getFill());
            tempRect.setStroke(graphicsContext.getStroke());
            tempRect.setStrokeWidth(graphicsContext.getLineWidth());

            //returns rectangle
            return (new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
        }
        else if(removedShape.getClass() == Circle.class) {

            //creates circle
            Circle tempCirc = (Circle) removedShape;

            //sets properties
            tempCirc.setStrokeWidth(graphicsContext.getLineWidth());
            tempCirc.setFill(graphicsContext.getFill());
            tempCirc.setStroke(graphicsContext.getStroke());

            //returns shape
            return (new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
        }
        else {

            //creates ellipse
            Ellipse tempElps = (Ellipse) removedShape;

            //sets properties
            tempElps.setFill(graphicsContext.getFill());
            tempElps.setStroke(graphicsContext.getStroke());
            tempElps.setStrokeWidth(graphicsContext.getLineWidth());

            //returns ellipse
            return (new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
        }
    }

    public void resetShape(GraphicsContext graphicsContext, Shape shape){
        if(shape.getClass() == Line.class) {

            //creates line
            Line temp = (Line) shape;

            //sets graphics properties
            graphicsContext.setLineWidth(temp.getStrokeWidth());
            graphicsContext.setStroke(temp.getStroke());
            graphicsContext.setFill(temp.getFill());
            graphicsContext.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
        }
        else if(shape.getClass() == Rectangle.class) {

            //creates rectangle
            Rectangle temp = (Rectangle) shape;

            //sets graphics properties
            graphicsContext.setLineWidth(temp.getStrokeWidth());
            graphicsContext.setStroke(temp.getStroke());
            graphicsContext.setFill(temp.getFill());
            graphicsContext.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
            graphicsContext.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
        }
        else if(shape.getClass() == Circle.class) {

            //creates circle
            Circle temp = (Circle) shape;

            //sets graphics properties
            graphicsContext.setLineWidth(temp.getStrokeWidth());
            graphicsContext.setStroke(temp.getStroke());
            graphicsContext.setFill(temp.getFill());
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
        }
        else if(shape.getClass() == Ellipse.class) {

            //creates ellipse
            Ellipse temp = (Ellipse) shape;

            //sets graphics properties
            graphicsContext.setLineWidth(temp.getStrokeWidth());
            graphicsContext.setStroke(temp.getStroke());
            graphicsContext.setFill(temp.getFill());
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
        }
    }
}
