package Paint2;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

/**
 * PaintMouseReleased handles the drawing event for when the mouse is released. This is to draw the actual shape when the
 * coordinates are decided on.
 * 11/9/2019
 */

public class PaintMouseReleased {

    private PixelReader pixelReader;
    private PixelWriter pixelWriter;
    private WritableImage writableImage;

    /**
     * Finalize line drawing
     * @param line, e, graphicsContext
     * @return line that will be pushed to undo stack
     */
    public Shape LineRel(Line line, MouseEvent e, GraphicsContext graphicsContext){
        line.setEndX(e.getX());                                                     //sets line end X value
        line.setEndY(e.getY());                                                     //sets line end y value
        graphicsContext.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()); //draws line

        //returns line to be pushed
        return (new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
    }

    /**
     * Finalize rectangle drawing
     * @param rectangle, graphicsContext, e
     * @return rectangle that will be pushed to undo stack
     */
    public Shape RectRel(Rectangle rectangle, GraphicsContext graphicsContext, MouseEvent e){
        rectangle.setWidth(Math.abs((e.getX() - rectangle.getX())));                //Width = mouse x - start point
        rectangle.setHeight(Math.abs((e.getY() - rectangle.getY())));               //Width = mouse y - start point

        if(rectangle.getX() > e.getX()) {
            rectangle.setX(e.getX());                                               //resets x
        }
        if(rectangle.getY() > e.getY()) {
            rectangle.setY(e.getY());                                               //resets y
        }

        //draws rectangle
        graphicsContext.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        graphicsContext.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());

        //draws rectangle to be pushed
        return (new Rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight()));
    }

    /**
     * Finalize arc rectangle drawing
     * @param arcRectangle, e, graphicsContext
     * @return arc rectangle that will be pushed to undo stack
     */
    public Shape arcRectRel(Rectangle arcRectangle, MouseEvent e, GraphicsContext graphicsContext){
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

        //returns arcRectangle to be pushed
        return arcRectangle;
    }

    /**
     * Finalize circle drawing
     * @param circle, e, graphicsContext
     * @return circle that will be pushed to undo stack
     */
    public Shape CircRel(Circle circle, MouseEvent e, GraphicsContext graphicsContext){
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

        //returns circle to be pushed
        return (new Circle(circle.getCenterX(), circle.getCenterY(), circle.getRadius()));
    }

    /**
     * Finalize ellipse drawing
     * @param ellipse, e, graphicsContext
     * @return ellipse that will be pushed to undo stack
     */
    public Shape ElpsRel(Ellipse ellipse, MouseEvent e, GraphicsContext graphicsContext){
        ellipse.setRadiusX(Math.abs(e.getX() - ellipse.getCenterX()));              //Radius x = mouse x - center x
        ellipse.setRadiusY(Math.abs(e.getY() - ellipse.getCenterY()));              //Radius y = mouse y - center y
        if(ellipse.getCenterX() > e.getX()) {
            ellipse.setCenterX(e.getX());                                           //Resets Center X
        }
        if(ellipse.getCenterY() > e.getY()) {
            ellipse.setCenterY(e.getY());                                           //Resets Center Y
        }

        //draws ellipse
        graphicsContext.strokeOval(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());
        graphicsContext.fillOval(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());

        //returns ellipse to be pushed
        return (new Ellipse(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY()));
    }

    /**
     * Finalize pencil drawing
     * @param graphicsContext, e
     */
    public void Draw(GraphicsContext graphicsContext, MouseEvent e){
        graphicsContext.lineTo(e.getX(), e.getY());                                 //connects to mouse x and y
        graphicsContext.stroke();                                                   //draws
        graphicsContext.closePath();
    }

    /**
     * Finalize erasure
     * @param graphicsContext, e
     */
    public void Erase(GraphicsContext graphicsContext, MouseEvent e){
        double lineWidth = graphicsContext.getLineWidth();

        //erases from mouseclicked - lineWidth/2
        graphicsContext.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
    }

    /**
     * Copy rectangle for select and move
     * @param selRect, e, canvas, graphicsContext, copySet
     * @return rectangle that will be used for move
     */
    public WritableImage selRel(Rectangle selRect, MouseEvent e, Canvas canvas, GraphicsContext graphicsContext,
                                int copySet, WritableImage selImg){
        selRect.setWidth(Math.abs(e.getX()-selRect.getX()));
        selRect.setHeight(Math.abs(e.getY()-selRect.getY()));

        if(selRect.getX() > e.getX()) {
            selRect.setX(e.getX());                                                 //resets x
        }
        if(selRect.getY() > e.getY()) {
            selRect.setY(e.getY());                                                 //resets y
        }


        writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);

        //draws leftover white rectangle
        graphicsContext.fillRect(selRect.getX(), selRect.getY(), selRect.getWidth(), selRect.getHeight());

        pixelReader = writableImage.getPixelReader();

        //sets selImg
        selImg = new WritableImage(pixelReader, (int)selRect.getX(), (int)selRect.getY(), (int)selRect.getWidth(),
                               (int)selRect.getHeight());
        return selImg;
    }

    /**
     * Copy rectangle without leaving white rectangle behind
     * @param selRect, e, canvas, copySet
     * @return selRect
     */
    public WritableImage CopyRel(Rectangle selRect, MouseEvent e, Canvas canvas, int copySet, WritableImage selImg) {
        selRect.setWidth(Math.abs(e.getX()-selRect.getX()));
        selRect.setHeight(Math.abs(e.getY()-selRect.getY()));

        if(selRect.getX() > e.getX()) {
            selRect.setX(e.getX());                                                 //resets x
        }
        if(selRect.getY() > e.getY()) {
            selRect.setY(e.getY());                                                 //resets y
        }

        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);

        pixelReader = writableImage.getPixelReader();

        //sets selImg
        selImg = new WritableImage(pixelReader, (int)selRect.getX(), (int)selRect.getY(), (int)selRect.getWidth(),
                (int)selRect.getHeight());

        return selImg;
    }

    /**
     * Move copied image from copy or select
     * @param selImg, graphicsContext, e
     */
    public void MoveRel(WritableImage selImg, GraphicsContext graphicsContext,  MouseEvent e) {
        graphicsContext.drawImage(selImg, e.getX(), e.getY());                       //draws movable image
    }

    /**
     * Finalize polygon shape
     * @param e, polyStartX, polyStartY, numSides, graphicsContext
     */
    public void nPolyReleased(MouseEvent e, double polyStartX, double polyStartY, int numSides,
                         GraphicsContext graphicsContext){
        //sets radius
        double radius = ((Math.abs(e.getX() - polyStartX) + Math.abs(e.getY() - polyStartY)) / 2);

        if (polyStartX > e.getX()) {
            polyStartX = e.getX();                                                  //resets start X
        }

        if (polyStartY > e.getY()) {
            polyStartY = e.getY();                                                  //resets start Y
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
