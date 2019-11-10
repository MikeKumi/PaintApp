/**
 * Paint Application created using Java and JavaFX
 *
 * @author Michael Kumicich
 * 11/9/2019
 */

package Paint2;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class used to pull everything together and create main window + its properties
 * @author Michael Kumicich
 *
 * 10/7/19
 */
public class Main extends Application {
    private Stage window = new Stage();

    /**
     * Window Setter
     * @param window
     */
    void setWindow(Stage window){
        this.window = window;
    }

    /**
     * Create Main Window
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        PaintWindow paintWindow = new PaintWindow();

        window.setWidth(paintWindow.getWindowWidth());                           //*******************************
        window.setHeight(paintWindow.getWindowHeight());                         //*                             *
        window.setScene(paintWindow.Window(window));                             //*    Sets Window Properties   *
        window.setTitle("Paint");                                              //*                             *
        window.show();                                                           //*******************************
    }

    public static void main(String[] args) {
        launch(args);
    }
}
