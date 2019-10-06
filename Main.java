 /*************************************************************************************
 * Name: Michael Kumicich                                                             *
 * Assignment: Paint 4                                                                *
 * Date: 9/30/2019                                                                    *
 * Honor Code I have neither given or received, nor have I tolerated others' use of   *
 * unauthorized aid.  ~Michael Kumicich                                               *
 * Much of this code is derived from                                                  *
 * http://java-buddy.blogspot.com/2014/12/javafx-filechooser-open-and-save-image.html *
 *************************************************************************************/

 package Paint2;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage window = new Stage();
    void setWindow(Stage window){
        this.window = window;
    }                       //Setter for the Window that will be
                                                                                 //used in the rest of the classes

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        PaintWindow paintWindow = new PaintWindow();

        window.setWidth(paintWindow.getWindowWidth());                           //*******************************
        window.setHeight(paintWindow.getWindowHeight());                         //*                             *
        window.setScene(paintWindow.Window(window));                             //*    Sets Window Properties   *
        window.setTitle("Pain(t)");                                              //*                             *
        window.show();                                                           //*******************************
    }

    public static void main(String[] args) {
        launch(args);
    }
}