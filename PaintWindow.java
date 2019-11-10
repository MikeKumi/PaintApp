package Paint2;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;

/**
 * PaintWindow orients the different panes and scene elements.
 * It also adds the keyboard shortcuts and scroll functionality
 *
 * @author Michael Kumicich
 * 10/7/2019
 */

public class PaintWindow {
    //Create panes
    private BorderPane borderPane = new BorderPane();
    private StackPane stackPane = new StackPane();

    //create window size
    private int WindowWidth = 1500;
    private int WindowHeight = 950;

    private boolean weirdMode = false;

    //create canvas and graphicsContext
    private Canvas canvas = new Canvas(WindowWidth, WindowHeight);
    private GraphicsContext graphicsContext;

    //create paintDraw class
    public PaintDraw paintDraw = new PaintDraw();

    /**
     * canvas setter
     * @param canvas
     */
    void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * graphicsContext setter
     * @param graphicsContext
     */
    void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * WindowWidth getter
     * @return
     */
    int getWindowWidth(){
        return WindowWidth;
    }

    /**
     * WindowHeight getter
     * @return
     */
    int getWindowHeight(){
        return WindowHeight;
    }

    /**
     * Creates panes and orients them with respective vboxes for creating scene
     * @param primaryStage
     * @return
     */
    public Scene Window(Stage primaryStage) {
        ScrollPane scrollPane = new ScrollPane();                                       //creates scrollpane

        scrollPane.setContent(stackPane);                                               //puts stackPane in scrollPane

        //scrollPane properties
        scrollPane.setVisible(true);
        scrollPane.setPrefSize(600, 200);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        //creates paintDraw and MenuBar classes
        PaintDraw paintDraw = new PaintDraw();
        PaintMenuBar paintMenuBar = new PaintMenuBar(paintDraw);

        //sets saved = to menuBarSaved
        boolean saved = paintMenuBar.getSaved();

        //when scrolled
        canvas.setOnScroll(evt -> {
            if (evt.isControlDown()) {                                              //if control is pressed
                Scroll(evt, scrollPane);
            }
        });


        stackPane.getChildren().addAll(canvas);                                              //puts canvas in stackPane

        //sets canvas size
        stackPane.maxHeight(canvas.getHeight());
        stackPane.maxWidth(canvas.getWidth());

        graphicsContext = canvas.getGraphicsContext2D();

        borderPane.setLeft(paintDraw.buttons(canvas, graphicsContext));                           //*******************************
        borderPane.setTop(paintMenuBar.MenuBar(primaryStage, canvas, graphicsContext, paintDraw));//*Orients elements inside pane *
        borderPane.setCenter(scrollPane);                                                         //*******************************

        //creates scene with borderPane inside
        Scene scene = new Scene(borderPane, WindowWidth, WindowHeight);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                KeyPress(ke, paintMenuBar, primaryStage, saved);
            }
        });


        Main main = new Main();                                                          //creates main
        main.setWindow(primaryStage);                                                    //puts window in main

        return scene;
    }

    /**
     * Method for scrolling
     * @param evt, scrollPane
     */
    private void Scroll(ScrollEvent evt, ScrollPane scrollPane){
        evt.consume();
        //controls amount to be zoomed
        final double zoomFactor = evt.getDeltaY() > 0 ? 1.2 : 1 / 1.2;

        Bounds groupBounds = stackPane.getLayoutBounds();
        final Bounds viewPortBounds = scrollPane.getViewportBounds();
        //finds pixel range
        double valX = scrollPane.getHvalue() * (groupBounds.getWidth() - viewPortBounds.getWidth());
        double valY = scrollPane.getVvalue() * (groupBounds.getHeight() - viewPortBounds.getHeight());

        Point2D posInZoomTarget = stackPane.parentToLocal(canvas.parentToLocal(new Point2D(evt.getX(), evt.getY())));

        Point2D adjustment = stackPane.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        stackPane.setScaleX(zoomFactor * stackPane.getScaleX());
        stackPane.setScaleY(zoomFactor * stackPane.getScaleY());

        scrollPane.layout();

        //controls how much scrolling moves
        groupBounds = canvas.getLayoutBounds();
        scrollPane.setHvalue((valX + adjustment.getX() / (groupBounds.getWidth() - viewPortBounds.getWidth())));
        scrollPane.setVvalue((valY + adjustment.getY() / (groupBounds.getHeight() - viewPortBounds.getHeight())));
    }

    /**
     * Method for different key presses
     * @param ke, paintMenuBar, primaryStage, saved
     */
    private void KeyPress(KeyEvent ke, PaintMenuBar paintMenuBar, Stage primaryStage, Boolean saved){
        if (ke.getCode() == KeyCode.ESCAPE) {                                   //closes when escape is pressed
            paintMenuBar.SmartSave(primaryStage, canvas, paintDraw);
        }
        else if (ke.getCode() == KeyCode.S && ke.isControlDown()) {             //Saves when Ctrl+S is pressed
            if(saved){
                paintMenuBar.Save(canvas);
            } else {
                paintMenuBar.SaveAs(primaryStage, canvas);
            }
        }
        else if (ke.getCode() == KeyCode.F && ke.isAltDown()){                  //Opens when Alt+F is pressed
            paintMenuBar.OpenImage(primaryStage, canvas, graphicsContext, paintMenuBar.MenuBar(primaryStage, canvas, graphicsContext, paintDraw));
        }
        else if (ke.getCode() == KeyCode.W){                                    //Toggles weird Mode
            if (paintDraw.getWeirdMode()) {
                paintDraw.setWeirdMode(false);
            } else {
                paintDraw.setWeirdMode(true);
            }
        }
    }
}
