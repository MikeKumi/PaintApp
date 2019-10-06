package Paint2;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.Canvas;

public class PaintMouseReleased {
    private WritableImage selImg;
    public Shape MouseReleased(ToggleButton lineBtn, ToggleButton rectBtn, ToggleButton circleBtn,
                               GraphicsContext graphicsContext, MouseEvent e, Line line, Rectangle rectangle,
                               Circle circle, Ellipse ellipse, ToggleButton arcRectBtn, Rectangle arcRectangle){

        if(lineBtn.isSelected()) {
            line.setEndX(e.getX());                                                     //sets line end X value
            line.setEndY(e.getY());                                                     //sets line end y value
            graphicsContext.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()); //draws line

            //returns line to be pushed
            return (new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
        }
        // rectangle btn
        else if(rectBtn.isSelected()) {
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

        //if arc rectangle btn
        else if(arcRectBtn.isSelected()) {
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
            Rectangle arcRect2 = new Rectangle();
            arcRect2 = arcRectangle;

            //returns arcRectangle to be pushed
            return arcRect2;
        }

        //if circle btn
        else if(circleBtn.isSelected()) {
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

            //returns circle to be pushed
            return (new Circle(circle.getCenterX(), circle.getCenterY(), circle.getRadius()));
        }

        //if ellipse btn
        else{
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
    }

    public void drawErase(ToggleButton pencilBtn, ToggleButton rubberbtn, MouseEvent e, GraphicsContext graphicsContext){

        //if pencil btn
        if(pencilBtn.isSelected()) {
            graphicsContext.lineTo(e.getX(), e.getY());                                 //connects to mouse x and y
            graphicsContext.stroke();                                                   //draws
            graphicsContext.closePath();                                                //closes
        }

        //If rubber btn
        else if(rubberbtn.isSelected()) {
            double lineWidth = graphicsContext.getLineWidth();

            //erases from mouseclicked - lineWidth/2
            graphicsContext.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
        }
    }


    public WritableImage selectImg(MouseEvent e, GraphicsContext graphicsContext,
                          Canvas canvas, ToggleButton selectBtn, Rectangle selRect, ToggleButton moveBtn){

        //if select btn
        if(selectBtn.isSelected()){
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

            //draws leftover white rectangle
            graphicsContext.fillRect(selRect.getX(), selRect.getY(), selRect.getWidth(), selRect.getHeight());

            PixelReader pixelReader = writableImage.getPixelReader();

            //sets selImg
            selImg = new WritableImage(pixelReader, (int)selRect.getX(), (int)selRect.getY(), (int)selRect.getWidth(),
                    (int)selRect.getHeight());
        }

        //if move btn
        else if(moveBtn.isSelected()){
            graphicsContext.drawImage(selImg, e.getX(), e.getY());                       //draws movable image
        }

        //returns movable image
        return selImg;
    }


    public void nPolygon(ToggleButton polyBtn, MouseEvent e, double polyStartX, double polyStartY, int numSides,
                         GraphicsContext graphicsContext){
        //if polygon btn
        if(polyBtn.isSelected()) {
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
}
