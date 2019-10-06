package Paint2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import java.util.Stack;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;

public class PaintDraw {
    private boolean drawn;                                              //bool for smart save
    private boolean weirdMode = false;                                  //bool for image preview not working correctly

    private Image image;                                                //Image that is redrawn for undo
    private WritableImage tmpSnap;                                      //Image for image preview to work correctly
    private WritableImage selImg;                                       //Image grabbed by selection tool

    private double polyStartX;                                          //Starting X coordinate for polygon tool
    private double polyStartY;                                          //Starting Y coordinate for polygon tool
    private int numSides;                                               //Number of sides for polygon tool


    public void setWeirdMode(boolean weirdMode) {                       //Sets weirdMode in PaintWindow (toggle)
        this.weirdMode = weirdMode;
    }

    public Boolean getWeirdMode(){
        return weirdMode;
    }                 //Gets weirdMode

    public boolean getdrawn(){
        return drawn;
    }                         //Gets drawn

    public void setImage(Image img){
        image = img;
    }                    //Sets image

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

        //array with size = amount of ToggleButtons
        ToggleButton[] drawArray = {pencilBtn, rubberbtn, lineBtn, rectBtn, circleBtn, ellipseBtn, grabberBtn,
                arcRectBtn, textBtn, selectBtn, moveBtn, polyBtn};

        ToggleGroup tools = new ToggleGroup();

        //Sets all ToggleButtons to have these characteristics
        for (ToggleButton tool : drawArray) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }


        //Textfields
        TextField textWidth = new TextField();                          //Canvas resize Width
        TextField textHeight = new TextField();                         //Canvas resize Height
        TextField textArea = new TextField();                           //Text... text
        TextField numText = new TextField();                            //Number of polygon sides

        //Other buttons
        Button undoBtn = new Button("Undo");
        Button redoBtn = new Button("Redo");
        Button resize = new Button("Resize");

        //Setting button properties
        resize.setMinWidth(90);
        resize.setCursor(Cursor.HAND);
        resize.setTextFill(Color.WHITE);
        resize.setStyle("-fx-background-color:#666;");

        undoBtn.setMinWidth(90);
        undoBtn.setCursor(Cursor.HAND);
        undoBtn.setTextFill(Color.WHITE);
        undoBtn.setStyle("-fx-background-color:#666;");

        redoBtn.setMinWidth(90);
        redoBtn.setCursor(Cursor.HAND);
        redoBtn.setTextFill(Color.WHITE);
        redoBtn.setStyle("-fx-background-color:#666;");

        //Colors for stroke and fill, and initialization
        ColorPicker cpLine = new ColorPicker(Color.BLACK);
        ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);

        //Slider + properties
        Slider slider = new Slider(1, 50, 3);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        //Labels
        Label lineColor = new Label("Color");
        Label fillColor = new Label("Fill Color");
        Label lineWidth = new Label("1.0");
        Label widthLabel = new Label("Width " + canvas.getWidth());         //Canvas Width
        Label heightLabel = new Label("Height " + canvas.getHeight());      //Canvas Height

        VBox drawVbox = new VBox(2);                                        //Vbox to be returned later


        //Adding all previous elements to Vbox in order
        drawVbox.getChildren().addAll(lineBtn, rectBtn, arcRectBtn, circleBtn, ellipseBtn, pencilBtn, rubberbtn,
                grabberBtn, selectBtn, moveBtn, polyBtn, numText, textBtn, textArea, lineColor, cpLine, fillColor,
                cpFill, lineWidth, slider, widthLabel, textWidth, heightLabel, textHeight, resize, undoBtn, redoBtn);

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


            //If mouse is pressed and any toggle button is selected then drawn will be true
            drawn = paintMousePressed.MousePressed(graphicsContext, pencilBtn, lineBtn, rectBtn, circleBtn, ellipseBtn,
                    grabberBtn, rubberbtn, e, cpLine, cpFill, line, rectangle, circle, ellipse, drawn, image, textArea,
                    textBtn, slider, arcRectBtn, arcRectangle, selectBtn, selRect, paintTests);

            if(polyBtn.isSelected()){
                drawn = true;

                graphicsContext.setStroke(cpLine.getValue());               //set fill
                graphicsContext.setFill(cpFill.getValue());                 //set stroke

                numSides = Integer.parseInt(numText.getText());             //gets number of sides
                polyStartX = paintMousePressed.getPolyStartX(e);            //gets starting x value
                polyStartY = paintMousePressed.getPolyStartY(e);            //gets starting y value
            }
        });

        //When mouse button is dragged
        canvas.setOnMouseDragged(e-> {
            if (polyBtn.isSelected()){                                      //calls nPolygon method
                paintMouseDragged.nPolygon(polyBtn, e, polyStartX, polyStartY, numSides, graphicsContext, tmpSnap);
            }
            else{                                                           //calls MouseDragged method
                paintMouseDragged.MouseDragged(pencilBtn, rubberbtn, graphicsContext, e, lineBtn, rectBtn, circleBtn, ellipseBtn,
                        line, rectangle, circle, ellipse, tmpSnap, arcRectBtn, arcRectangle, moveBtn, selImg);
            }
        });

        //When mouse button is released
        canvas.setOnMouseReleased(e->{


            if(ellipseBtn.isSelected() || circleBtn.isSelected() || rectBtn.isSelected() || lineBtn.isSelected()) {

                //pushes returned shape onto undoHistory
                undoHistory.push(paintMouseReleased.MouseReleased(lineBtn, rectBtn, circleBtn, graphicsContext, e, line,
                        rectangle, circle, ellipse, arcRectBtn, arcRectangle));
            }
            else if(selectBtn.isSelected() || moveBtn.isSelected()){

                //sets selImg = returned WritableImage
                selImg = paintMouseReleased.selectImg(e, graphicsContext, canvas, selectBtn, selRect, moveBtn);
            }
            else if(polyBtn.isSelected()){

                //Draws polygon
                paintMouseReleased.nPolygon(polyBtn, e, polyStartX, polyStartY, numSides, graphicsContext);
            }
            else {

                //erases
                paintMouseReleased.drawErase(pencilBtn, rubberbtn, e, graphicsContext);
            }

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

        //undo Class
        PaintUndo paintUndo = new PaintUndo();

        //undo button
        undoBtn.setOnAction(e->{
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
        });

        //redo button
        PaintRedo paintRedo = new PaintRedo();
        redoBtn.setOnAction(e->{
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
        });

        PaintWindow paintWindow = new PaintWindow();
        paintWindow.setCanvas(canvas);

        return drawVbox;
    }

}