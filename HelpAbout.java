package Paint2;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class HelpAbout {
    public static void display(String title, String message){  //Basic Help About option
        Stage window = new Stage();

        window.setTitle(title);                                //Window title
        window.setWidth(400);                                  //Window Width

        Label label = new Label();                             //Creates a new label
        label.setText(message);                                //Puts message parameter is text

        VBox vbox = new VBox(10);                           //Creates VBox
        vbox.getChildren().addAll(label);                      //adds label to vbox

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.show();
    }

    public static void releaseNotes(){                         //Release Notes help Window
        Stage window = new Stage();

        window.setTitle("Release Notes");

        ScrollPane scrollPane = new ScrollPane();              //Adds Scrollbar for the whole window

        //Release Notes Text
        Text text = new Text("==============================================================\n" +
                "==========================Version 1.0.3========================================\n" +
                "========================September 30, 2019======================================\n" +
                "==============================================================\n" +
                "Added Features\n" +
                "\n" +
                "-Keyboard shortcuts\n" +
                "-Text tool\n" +
                "-Added functionality to draw rounded rectangle\n" +
                "-Eraser tool\n" +
                "-Shapes now draw while in progress\n" +
                "-Release notes from help menu\n" +
                "-Tool tutorial from help menu\n" +
                "-Allows user to select and move image\n" +
                "-Create a polygon with sides chosen by user\n" +
                "\n" +
                "Changes\n" +
                "-Class Structure is much cleaner\n" +
                "-zoom in/out no longer affects menu bar and drawing toolbar\n" +
                "\n" +
                "Bugs\n" +
                "-\n" +
                "\n" +
                "Known Issues\n" +
                "-\n" +
                "\n" +
                "Future Plans\n" +
                "-Implement features necessary for paint 5\n" +
                "-Go for Bob Ross mode if I have time\n" +
                "\n" +
                "\n" +
                "==============================================================\n" +
                "==========================Version 1.0.2========================================\n" +
                "========================September 20, 2019======================================\n" +
                "==============================================================\n" +
                "\n" +
                "Added Features\n" +
                "\n" +
                "-Pencil tool\n" +
                "-Added functionality to draw rectangle, elliple, circle, and change stroke/fill color\n" +
                "-smart save\n" +
                "-jpg png and tiff open and save file\n" +
                "types\n" +
                "-Added shapes to toolbar\n" +
                "-Text label describing colors\n" +
                "-Color grabber\n" +
                "-Resize canvas (must input dimensions\n" +
                "manually)\n" +
                "-Undo and redo\n" +
                "-zoom in and out with ctrl + scrollwheel\n" +
                "\n" +
                "Changes\n" +
                "-Split project into many more total files\n" +
                "\n" +
                "Bugs\n" +
                "-zoom in/out affects menu bar and\n" +
                "drawing toolbar\n" +
                "\n" +
                "Known Issues\n" +
                "-Class structure is messy\n" +
                "\n" +
                "Future Plans\n" +
                "-Implement features necessary for paint 4\n" +
                "-Re-restructure classes\n" +
                "\n" +
                "\n" +
                "==============================================================\n" +
                "==========================Version 1.0.1========================================\n" +
                "========================September 13, 2019======================================\n" +
                "==============================================================\n" +
                "\n" +
                " Added Features\n" +
                "\n" +
                "-Draw a line\n" +
                "-Save changes made to image\n" +
                "-Canvas resizes and window recenters to match image added\n" +
                "-Scroll bars\n" +
                "-Line width controller (yes it works)\n" +
                "-Line Color controller (also works)\n" +
                "- help and help about menu options\n" +
                "\n" +
                "\t      Changes\n" +
                "-Save Now works Properly\n" +
                "\n" +
                "\t        Bugs\n" +
                "\n" +
                "Known Issues\n" +
                "-When scrolling, drawing VBox does not stay\n" +
                "stationary\n" +
                "\n" +
                "Future Plans\n" +
                "-Implement features necessary for paint 3\n" +
                "-(Also put code into different classes)\n" +
                "\n" +
                "\n" +
                "==============================================================\n" +
                "==========================Version 1.0.0========================================\n" +
                "========================September 4, 2019======================================\n" +
                "==============================================================\n" +
                "\n" +
                "Added Features\n" +
                "\n" +
                "-Menu Bar\n" +
                "-Close Button\n" +
                "-Opening images\n" +
                "-Saving said images\n" +
                "\n" +
                "Changes\n" +
                "-None\n" +
                "\n" +
                "Bugs\n" +
                "-None (atm)\n" +
                "\n" +
                "Known Issues\n" +
                "-Unclean code\n" +
                "-No comments\n" +
                "\n" +
                "Future Plans\n" +
                "-Implement whatever Rosascus wants next.\n" +
                "\n");
        //end of Release notes text

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(text);                                              //Puts Text inside scrollpane

        scrollPane.setVisible(true);                                              //*******************************
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);              //*                             *
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);              //*    Scrollbar properties     *
        scrollPane.setFitToWidth(true);                                           //*                             *
        scrollPane.setFitToHeight(true);                                          //*******************************

        Scene scene = new Scene(scrollPane, 500, 800);                     //Creates scene with scrollpane inside
        window.setScene(scene);
        window.show();
    }


    public static void Tutorial(){                                    //Tutorial help option
        Stage window = new Stage();

        window.setTitle("How to Use");

        ScrollPane scrollPane = new ScrollPane();                     //Creates scrollpane for the window

        //Tutorial Text
        Text text = new Text("Click on File to Open (Alt + F), Save (Ctrl + S) , or Close (Escape)\n" +
                "\n" +
                "To use the Drawing toolbar click on the button you would\n" +
                "like to use and click on the screen.\n" +
                "\n" +
                "Note: For the polygon tool you have to type in the amount of\n" +
                "sides in the text box below the \"Polygon\" button.\n" +
                "\n" +
                "To drag a piece of an image click \"select\" then drag your\n" +
                "mouse on the screen for what you would like to copy. To move\n" +
                "what you copied click on the \"Move\" tool and drag your mouse.\n" +
                "\n" +
                "To use the Text tool type in the textbox what you would like\n" +
                "to appear and click.\n" +
                "\n" +
                "You can change the Color of the lines/shapes and the insides\n" +
                "of them with the \"Color\" and \"Fill Color\" dropdowns, respectively.\n" +
                "To change the size use the slider below them.\n" +
                "\n" +
                "To change the canvas size use type in the height and Width values\n" +
                "you prefer and then click \"Resize.\"\n" +
                "\n" +
                "To undo or redo click the undo or redo buttons, respectively.");
        //End of Tutorial Text

        scrollPane.setFitToWidth(true);                                                //*******************************
        scrollPane.setContent(text);                                                   //*                             *
        scrollPane.setVisible(true);                                                   //*                             *
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);                   //*     pane inside of it.      *
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);                   //*                             *
        scrollPane.setFitToWidth(true);                                                //*                             *
        scrollPane.setFitToHeight(true);                                               //*******************************

        Scene scene = new Scene(scrollPane, 500, 800);
        window.setScene(scene);
        window.show();
    }
}
