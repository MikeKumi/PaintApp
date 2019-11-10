package Paint2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import java.util.Stack;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.scene.image.*;

import static javafx.scene.paint.Color.WHITE;

/**
 * PaintDraw class is used to create the VBox used for all the drawing tools.
 * It also acts as the class that calls the different mouse options and inner
 * workings of the different drawing methods.
 *
 * @author Michael Kumicich
 * 11/8/2019
 */

public class PaintDraw {

    private boolean drawn;                                              //bool for smart save
    private boolean weirdMode = false;                                  //bool for image preview not working correctly

    private Image image;                                                //Image that is redrawn for undo
    private WritableImage tmpSnap;                                      //Image for image preview to work correctly
    private WritableImage selImg;                                       //Image grabbed by selection tool

    private double polyStartX;                                          //Starting X coordinate for polygon tool
    private double polyStartY;                                          //Starting Y coordinate for polygon tool
    private int numSides;                                               //Number of sides for polygon tool
    private int copySet = 1;


    /**
     * weirdMode setter
     * @param weirdMode
     */
    public void setWeirdMode(boolean weirdMode) {                       //Sets weirdMode in PaintWindow (toggle)
        this.weirdMode = weirdMode;
    }

    /**
     * weirdMode getter
     */
    public Boolean getWeirdMode(){
        return weirdMode;
    }

    /**
     * drawn getter
     */
    public boolean getdrawn(){
        return drawn;
    }

    /**
     * image setter
     * @param img
     */
    public void setImage(Image img){
        image = img;
    }

    /**
     * copySet setter, used to change copy options
     * @param copySetting
     */
    public void setCopy(int copySetting){
        copySet = copySetting;
    }

    /**
      *  Set white canvas background
      * @param graphicsContext, canvas
     */
    private void setBack(GraphicsContext graphicsContext, Canvas canvas){
        Rectangle backGND = new Rectangle();
        backGND.setX(0);
        backGND.setY(0);

        graphicsContext.setStroke(WHITE);
        graphicsContext.setFill(WHITE);

        backGND.setWidth(canvas.getWidth());
        backGND.setHeight(canvas.getHeight());

        graphicsContext.fillRect(backGND.getX(), backGND.getY(), backGND.getWidth(), backGND.getHeight());
        graphicsContext.strokeRect(backGND.getX(), backGND.getY(), backGND.getWidth(), backGND.getHeight());

    }

    /**
     * Redraws for redo, called when redo button is pressed
     * @param redoHistory, undoHistory, graphicsContext, paintUndo
     */
    private void undoClick(Stack<Shape> redoHistory, Stack<Shape> undoHistory, GraphicsContext graphicsContext, PaintUndo paintUndo){
        if(!undoHistory.empty()){
            graphicsContext.clearRect(0, 0, 1080, 790);             //Clear image
            graphicsContext.drawImage(image, 0, 0);                         //Redraw image
            Shape removedShape = undoHistory.lastElement();                        //removed shape = last undohistory
            drawn = true;
            redoHistory.push(paintUndo.redoPush(removedShape, graphicsContext));   //push onto redohistory

            Shape lastRedo = redoHistory.lastElement();                            //lastRedo = last redoHistory

            //Sets redoHistory properties
            lastRedo.setFill(removedShape.getFill());
            lastRedo.setStroke(removedShape.getStroke());
            lastRedo.setStrokeWidth(removedShape.getStrokeWidth());

            undoHistory.pop();

            //shift everything back in place for undoHistory
            for(int i=0; i < undoHistory.size(); i++) {
                Shape shape = undoHistory.elementAt(i);
                paintUndo.resetShape(graphicsContext, shape);
            }
        } else {
            System.out.println("there is no action to undo");
        }
    }

    /**
     * Erases for undo, called when undo button is pressed
     * @param redoHistory, undoHistory, graphicsContext, paintRedo
     */
    private boolean redoClick(Stack<Shape> redoHistory, Stack<Shape> undoHistory, GraphicsContext graphicsContext, PaintRedo paintRedo){
        if(!redoHistory.empty()) {
            Shape shape = redoHistory.lastElement();                                //Shape = last redoHistory

            //set Shape properties
            graphicsContext.setLineWidth(shape.getStrokeWidth());
            graphicsContext.setStroke(shape.getStroke());
            graphicsContext.setFill(shape.getFill());

            redoHistory.pop();
            undoHistory.push(paintRedo.undoPush(shape, graphicsContext));           //push shape onto undoHistory

            Shape lastUndo = undoHistory.lastElement();                             //lastUndo = last UndoHistory

            //set lastUndo properties
            lastUndo.setFill(graphicsContext.getFill());
            lastUndo.setStroke(graphicsContext.getStroke());
            lastUndo.setStrokeWidth(graphicsContext.getLineWidth());
            drawn = true;
        } else {
            System.out.println("there is no action to redo");
        }
        return drawn;
    }

    /**
     * Set properties for tool buttons
     * @param drawBtns, drawArray
     */
    private void btnAttributes(Button[] drawBtns, ToggleButton[] drawArray){
        //set resize button properties
        drawBtns[0].setMinWidth(90);
        drawBtns[0].setCursor(Cursor.HAND);
        drawBtns[0].setTextFill(WHITE);
        drawBtns[0].setStyle("-fx-background-color:#666;");

        //set undo button properties
        drawBtns[1].setMinWidth(90);
        drawBtns[1].setCursor(Cursor.HAND);
        drawBtns[1].setTextFill(WHITE);
        drawBtns[1].setStyle("-fx-background-color:#666;");

        //set redo button properties
        drawBtns[2].setMinWidth(90);
        drawBtns[2].setCursor(Cursor.HAND);
        drawBtns[2].setTextFill(WHITE);
        drawBtns[2].setStyle("-fx-background-color:#666;");

        //set change button properties
        drawBtns[3].setMinWidth(90);
        drawBtns[3].setCursor(Cursor.HAND);
        drawBtns[3].setTextFill(WHITE);
        drawBtns[3].setStyle("-fx-background-color:#666;");

        ToggleGroup tools = new ToggleGroup();

        //Sets all ToggleButtons to have these characteristics
        for (ToggleButton tool : drawArray) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
    }

    /**
     * Changes canvas colors and redraws it
     * @param canvas, graphicsContext
     */
    private boolean ChangeColors(Canvas canvas, GraphicsContext graphicsContext){
        //take snapshot of canvas
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);

        //Read from writable image snapshot
        PixelReader pixelReader = writableImage.getPixelReader();
        WritableImage colorImg = new WritableImage(pixelReader, 0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        //write to colorImg with pixelWriter
        PixelWriter pixelWriter = colorImg.getPixelWriter();
        double tempH, tempW;
        tempH = canvas.getHeight();
        tempW = canvas.getWidth();

        //Read each pixel and then rewrite it based off of copySet
        for(int y = 0; y < tempH; y++){
            for(int x = 0; x < tempW; x++){
                Color color = pixelReader.getColor(x, y);
                switch (copySet){
                    case 2:
                        color = color.brighter();
                        break;
                    case 3:
                        color = color.darker();
                        break;
                    case 4:
                        color = color.grayscale();
                        break;
                    case 5:
                        color = color.invert();
                        break;
                    case 6:
                        color = color.saturate();
                        break;
                    case 7:
                        color = color.desaturate();
                        break;
                    default:
                        break;
                }
                pixelWriter.setColor(x, y, color);
            }
        }
        //redraw canvas with changes
        graphicsContext.drawImage(colorImg, 0, 0);
        return true;
    }

    /**
     * Creates Drawing toolbar Vbox and its tools
     * @param canvas, graphicsContext
     */
    public VBox buttons(Canvas canvas, GraphicsContext graphicsContext) {

        Stack<Shape> undoHistory = new Stack();                         //Undo Stack
        Stack<Shape> redoHistory = new Stack();                         //Redo Stack

        //Drawing ToggleButtons
        ToggleButton lineBtn = new ToggleButton("Line");
        ToggleButton rectBtn = new ToggleButton("Rectangle");
        ToggleButton arcRectBtn = new ToggleButton("Arc Rectangle");
        ToggleButton circleBtn = new ToggleButton("Circle");
        ToggleButton ellipseBtn = new ToggleButton("Ellipse");
        ToggleButton pencilBtn = new ToggleButton("Draw");
        ToggleButton rubberbtn = new ToggleButton("Eraser");
        ToggleButton grabberBtn = new ToggleButton("Grab Color");
        ToggleButton textBtn = new ToggleButton("Text");
        ToggleButton selectBtn = new ToggleButton("Select");
        ToggleButton moveBtn = new ToggleButton("Move");
        ToggleButton polyBtn = new ToggleButton("Polygon");
        ToggleButton copyBtn = new ToggleButton("Copy");

        PaintTooltip paintTooltip = new PaintTooltip();

        //Set tooltips
        paintTooltip.setToggleTip(lineBtn, rectBtn, arcRectBtn, circleBtn, ellipseBtn, pencilBtn, rubberbtn, grabberBtn,
                textBtn, selectBtn, moveBtn, polyBtn, copyBtn);


        //array with size = amount of ToggleButtons
        ToggleButton[] drawArray = {pencilBtn, rubberbtn, lineBtn, rectBtn, circleBtn, ellipseBtn, grabberBtn,
                arcRectBtn, textBtn, selectBtn, moveBtn, polyBtn, copyBtn};


        //Textfields
        TextField textWidth = new TextField();                          //Canvas resize Width
        TextField textHeight = new TextField();                         //Canvas resize Height
        TextField textArea = new TextField();                           //Text... text
        TextField numText = new TextField();                            //Number of polygon sides
        TextField invSetting = new TextField();                         //Change color settings

        //Set tooltips
        paintTooltip.setTextFieldTip(textWidth, textHeight, textArea, numText, invSetting);

        //non-toggle buttons
        Button undoBtn = new Button("Undo");
        Button redoBtn = new Button("Redo");
        Button resize = new Button("Resize");
        Button change = new Button("Change");

        Button[] drawBtns = {resize, undoBtn, redoBtn, change};
        btnAttributes(drawBtns, drawArray);

        //Set Button Tooltips
        paintTooltip.setBtnTip(undoBtn, redoBtn, resize, change);

        //Colors for stroke and fill, and initialization
        ColorPicker cpLine = new ColorPicker(Color.BLACK);
        ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);

        //Slider + properties
        Slider slider = new Slider(1, 50, 3);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        paintTooltip.setGraphicTip(cpLine, cpFill, slider);

        //Labels
        Label lineColor = new Label("Color");
        Label fillColor = new Label("Fill Color");
        Label lineWidth = new Label("1.0");
        Label widthLabel = new Label("Width " + canvas.getWidth());         //Canvas Width
        Label heightLabel = new Label("Height " + canvas.getHeight());      //Canvas Height

        VBox drawVbox = new VBox(2);                                        //Vbox to be returned later


        //Adding all previous elements to Vbox in order
        drawVbox.getChildren().addAll(lineBtn, rectBtn, arcRectBtn, circleBtn, ellipseBtn, pencilBtn, rubberbtn,
                grabberBtn, selectBtn, copyBtn, moveBtn, polyBtn, numText, textBtn, textArea, lineColor, cpLine, fillColor,
                cpFill, lineWidth, slider, widthLabel, textWidth, heightLabel, textHeight, resize, change, undoBtn, redoBtn);

        //Vbox settings
        drawVbox.setPadding(new Insets(5));
        drawVbox.setPrefWidth(50);

        //Shapes
        Line line = new Line();
        Rectangle rectangle = new Rectangle();
        Rectangle arcRectangle = new Rectangle();
        Rectangle selRect = new Rectangle();
        Circle circle = new Circle();
        Ellipse ellipse = new Ellipse();

        //initial stroke size
        graphicsContext.setLineWidth(1);

        //Unit Test declaration
        PaintTests paintTests = new PaintTests();

        setBack(graphicsContext, canvas);

        //When resize is clicked
        resize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String canvasWidth = textWidth.getText();                   //Grabs Canvas Width from textField
                String canvasHeight = textHeight.getText();                 //Grabs Canvas Height from textField
                canvas.setWidth(Double.parseDouble(canvasWidth));           //Resizes canvas Width
                canvas.setHeight(Double.parseDouble(canvasHeight));         //Resizes canvas Height
                graphicsContext.drawImage(image, 0, 0);              //Redraws image
                widthLabel.setText("Width " + canvas.getWidth());           //Adds canvas width to widthLabel
                heightLabel.setText("Height " + canvas.getHeight());        //Adds canvas height to heightLabel

                paintTests.CanvasTest(canvasWidth, canvasHeight, canvas);   //Unit test for canvas size
            }
        });

        //Mouse class declarations
        PaintMousePressed paintMousePressed = new PaintMousePressed();
        PaintMouseDragged paintMouseDragged = new PaintMouseDragged();
        PaintMouseReleased paintMouseReleased = new PaintMouseReleased();

        //When mouse button is pressed
        canvas.setOnMousePressed(e->{

            tmpSnap = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());//Sets tmpSnap = current image
            if(!weirdMode) {                                                //if weirdMode is false
                canvas.snapshot(null, tmpSnap);           //snapshot with tmpsnap
            }                                                               //Necessary for previews to disappear

            if(pencilBtn.isSelected()) drawn = paintMousePressed.DrawPres(graphicsContext, cpLine, e);
            if(lineBtn.isSelected()) drawn = paintMousePressed.LinePres(graphicsContext, cpLine, line, e);
            if(rectBtn.isSelected()) drawn = paintMousePressed.RectPres(graphicsContext, cpLine, cpFill, rectangle, e);
            if(copyBtn.isSelected()) drawn = paintMousePressed.CopyPres(selRect, e);
            if(arcRectBtn.isSelected()) drawn = paintMousePressed.ArcRectPres(graphicsContext, cpLine, cpFill, e, arcRectangle);
            if(rubberbtn.isSelected()) drawn = paintMousePressed.RubPres(graphicsContext, e);
            if(circleBtn.isSelected()) drawn = paintMousePressed.CircPres(graphicsContext, cpLine, cpFill, circle, e);
            if(ellipseBtn.isSelected()) drawn = paintMousePressed.ElpsPres(graphicsContext, cpLine, cpFill, ellipse, e);
            if(grabberBtn.isSelected()) drawn = paintMousePressed.GrabPres(graphicsContext, e, image, cpLine);
            if(selectBtn.isSelected()) drawn = paintMousePressed.SelPres(graphicsContext, selRect, e);
            if(textBtn.isSelected()) drawn = paintMousePressed.TextPres(graphicsContext, slider, cpLine, cpFill, e, textArea);

            if(polyBtn.isSelected()){
                drawn = true;

                graphicsContext.setStroke(cpLine.getValue());               //set fill
                graphicsContext.setFill(cpFill.getValue());                 //set stroke

                numSides = Integer.parseInt(numText.getText());             //gets number of sides
                polyStartX = paintMousePressed.getPolyStartX(e);            //gets starting x value
                polyStartY = paintMousePressed.getPolyStartY(e);            //gets starting y value
            }

            paintTests.StrokeTest((Color) graphicsContext.getStroke(), cpLine.getValue());
            paintTests.FillTest((Color) graphicsContext.getFill(), cpFill.getValue());

        });

        //When mouse button is dragged
        canvas.setOnMouseDragged(e-> {
            if(polyBtn.isSelected()) paintMouseDragged.nPolygon(e, polyStartX, polyStartY, numSides, graphicsContext, tmpSnap);
            if(moveBtn.isSelected()) paintMouseDragged.MoveDrag(graphicsContext, tmpSnap, selImg, e);
            if(ellipseBtn.isSelected()) paintMouseDragged.EllipseDrag(graphicsContext, tmpSnap, ellipse, e);
            if(circleBtn.isSelected()) paintMouseDragged.CircleDrag(graphicsContext, tmpSnap, circle, e);
            if(arcRectBtn.isSelected()) paintMouseDragged.arcRectDrag(graphicsContext, tmpSnap, arcRectangle, e);
            if(rectBtn.isSelected()) paintMouseDragged.RectDrag(graphicsContext, tmpSnap, e, rectangle);
            if(lineBtn.isSelected()) paintMouseDragged.LineDrag(graphicsContext, tmpSnap, line, e);
            if(rubberbtn.isSelected()) paintMouseDragged.RubberDrag(graphicsContext, e);
            if(pencilBtn.isSelected()) paintMouseDragged.PencilDrag(graphicsContext, e);
        });

        //When mouse button is released
        canvas.setOnMouseReleased(e->{
            if(lineBtn.isSelected()) undoHistory.push(paintMouseReleased.LineRel(line, e, graphicsContext));
            if(rectBtn.isSelected()) undoHistory.push(paintMouseReleased.RectRel(rectangle, graphicsContext, e));
            if(arcRectBtn.isSelected()) undoHistory.push(paintMouseReleased.arcRectRel(arcRectangle, e, graphicsContext));
            if(circleBtn.isSelected()) undoHistory.push(paintMouseReleased.CircRel(circle, e, graphicsContext));
            if(ellipseBtn.isSelected()) undoHistory.push(paintMouseReleased.ElpsRel(ellipse, e, graphicsContext));

            if(pencilBtn.isSelected()) paintMouseReleased.Draw(graphicsContext, e);
            if(rectBtn.isSelected()) paintMouseReleased.Erase(graphicsContext, e);
            if(polyBtn.isSelected()) paintMouseReleased.nPolyReleased(e, polyStartX, polyStartY, numSides, graphicsContext);

            if(selectBtn.isSelected()) selImg = paintMouseReleased.selRel(selRect, e, canvas, graphicsContext, copySet, selImg);
            if(copyBtn.isSelected()) selImg = paintMouseReleased.CopyRel(selRect, e, canvas, copySet, selImg);
            if(moveBtn.isSelected()) paintMouseReleased.MoveRel(selImg, graphicsContext, e);

            redoHistory.clear();                                                      //Clears redoHistory
            Shape lastUndo = undoHistory.lastElement();                               //Creates shape of last undoHistory

            //sets lastUndo settings
            lastUndo.setFill(graphicsContext.getFill());
            lastUndo.setStroke(graphicsContext.getStroke());
            lastUndo.setStrokeWidth(graphicsContext.getLineWidth());
        });

        //Changes the stroke of the line
        cpLine.setOnAction(e -> {
            graphicsContext.setStroke(cpLine.getValue());                             //Sets stroke of shapes
        });

        //Changes the fill of the line
        cpFill.setOnAction(e->{
            graphicsContext.setFill(cpFill.getValue());                               //Sets fill of shapes
        });

        //changes the width of the line
        slider.valueProperty().addListener(e -> {
            double sliderWidth = slider.getValue();
            if(textBtn.isSelected()){
                graphicsContext.setLineWidth(1);
                graphicsContext.setFont(Font.font(slider.getValue()));
                lineWidth.setText(String.format("%.1f", sliderWidth));
                return;
            }                                                                         //gets slider width selected
            lineWidth.setText(String.format("%.1f", sliderWidth));                    //Line width label = slider width
            graphicsContext.setLineWidth(sliderWidth);                                //sets line width = slider width
        });

        //undo button
        PaintUndo paintUndo = new PaintUndo();
        undoBtn.setOnAction(e->{
            undoClick(redoHistory, undoHistory, graphicsContext, paintUndo);
        });

        //redo button
        PaintRedo paintRedo = new PaintRedo();
        redoBtn.setOnAction(e->{
            drawn = redoClick(redoHistory, undoHistory, graphicsContext, paintRedo);
        });

        change.setOnAction(e->{
            drawn = ChangeColors(canvas, graphicsContext);
        });

        PaintWindow paintWindow = new PaintWindow();
        paintWindow.setCanvas(canvas);

        //returns drawing toolbar vbox
        return drawVbox;
    }

}
