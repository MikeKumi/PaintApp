package Paint2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.WritableImage;

/**
 * PaintMouseDragged handles the drawing event for when the mouse is dragged. This includes the show shape as its being made.
 *
 * @author Michael Kumicich
 * 11/9/2019
 */
public class PaintMouseDragged{
    /**
     * Redraw for every mouse movement
     * @param graphicsContext, e
     */
    public void PencilDrag(GraphicsContext graphicsContext, MouseEvent e){
        graphicsContext.lineTo(e.getX(), e.getY());                                 //connects to mouse x and y
        graphicsContext.stroke();                                                   //draws
    }

    /**
     * Erase for every mouse movement
     * @param graphicsContext, e
     */
    public void RubberDrag(GraphicsContext graphicsContext, MouseEvent e){
        double lineWidth = graphicsContext.getLineWidth();

        //erases from mouseclicked - lineWidth/2
        graphicsContext.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
    }

    /**
     * Redraw and delete line for every mouse movement.
     * @param graphicsContext, tmpSnap, line, e
     */
    public void LineDrag(GraphicsContext graphicsContext, WritableImage tmpSnap, Line line, MouseEvent e){
        graphicsContext.drawImage(tmpSnap, 0, 0);                            //redraws image for preview
        line.setEndX(e.getX());                                                     //sets line end X value
        line.setEndY(e.getY());                                                     //sets line end y value
        graphicsContext.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()); //draws line
    }

    /**
     * Redraw and delete rectangle for every mouse movement.
     * @param graphicsContext, tmpSnap, e, rectangle
     */
    public void RectDrag(GraphicsContext graphicsContext, WritableImage tmpSnap, MouseEvent e, Rectangle rectangle){
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

    /**
     * Redraw and delete arc rectangle for every mouse movement.
     * @param graphicsContext, tmpSnap, e, arc rectangle
     */
    public void arcRectDrag(GraphicsContext graphicsContext, WritableImage tmpSnap, Rectangle arcRectangle, MouseEvent e){
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

    /**
     * Redraw and delete circle for every mouse movement.
     * @param graphicsContext, tmpSnap, circle, e
     */
    public void CircleDrag(GraphicsContext graphicsContext, WritableImage tmpSnap, Circle circle, MouseEvent e){
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

    /**
     * Redraw and delete ellipse for every mouse movement.
     * @param graphicsContext, tmpSnap, ellipse, e
     */
    public void EllipseDrag(GraphicsContext graphicsContext, WritableImage tmpSnap, Ellipse ellipse, MouseEvent e){
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

    /**
     * Redraw and delete copied image for every mouse movement.
     * @param graphicsContext, tmpSnap, selImg, e
     */
    public void MoveDrag(GraphicsContext graphicsContext, WritableImage tmpSnap, WritableImage selImg, MouseEvent e){
        graphicsContext.drawImage(tmpSnap, 0, 0);
        graphicsContext.drawImage(selImg, e.getX(), e.getY());
    }

    /**
     * Redraw and delete polygon of n sides for every mouse movement.
     * @param e, polyStartX, polyStartY, numSides, graphicsContext
     */
    public void nPolygon(MouseEvent e, double polyStartX, double polyStartY, int numSides, GraphicsContext graphicsContext,
                  WritableImage tmpSnap){

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
