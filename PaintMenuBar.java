package Paint2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javax.imageio.ImageIO;
import javafx.scene.canvas.Canvas;
import java.awt.image.RenderedImage;
import java.io.InputStream;
import java.util.List;

import javafx.scene.image.WritableImage;

public class PaintMenuBar {
    private File file;

    private boolean saved = false;
    private boolean changed = false;
    private boolean allImage = false;
    private boolean pngImage = false;
    private boolean jpgImage = false;
    private boolean gifImage = false;

    public Image image;

    public boolean getSaved(){
        return saved;
    }

    PaintDraw paintDraw = new PaintDraw();                                           //creates paintDraw class

    PaintMenuBar(PaintDraw paintDraw){
        this.paintDraw = paintDraw;
    }                //sets paintDraw

    PaintWindow paintWindow = new PaintWindow();                                     //instantiates PaintWindow class
    public VBox MenuBar(Stage primaryStage, Canvas canvas, GraphicsContext graphicsContext, PaintDraw paintDraw) {

        VBox menuBox = new VBox();                                                   //creates menu Vbox
        MenuBar menuBar = new MenuBar();                                             //creates menuBar

        Menu menuFile = new Menu("File");                                         //*********************************
        Menu menuOptions = new Menu("Options");                                   //*   Creates menu bar options    *
        Menu menuHelp = new Menu("Help");                                         //*********************************

        MenuItem menuItem_Save = new MenuItem("Save");                            //*********************************
        MenuItem menuItem_SaveAs = new MenuItem("Save As");                       //*                               *
        MenuItem menuItem_Open = new MenuItem("_Open Image");                     //* Creates dropdown options for  *
        MenuItem menuItem_Close = new MenuItem("Close");                          //* the different menuBar options *
        MenuItem menuItem_getSize = new MenuItem("Print total Size");             //*                               *
        MenuItem menuItem_HelpAbout = new MenuItem("About");                      //*                               *
        MenuItem menuItem_ReleaseNotes = new MenuItem("Release Notes");           //*                               *
        MenuItem menuItem_Tutorial = new MenuItem("Tutorial");                    //*********************************

        menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);                                //adds menus

        menuFile.getItems().addAll(menuItem_Open, menuItem_Save, menuItem_SaveAs, menuItem_Close); //*******************
        menuOptions.getItems().add(menuItem_getSize);                                              //Adding menu options
        menuHelp.getItems().addAll(menuItem_HelpAbout, menuItem_ReleaseNotes, menuItem_Tutorial);  //*******************

        menuBox.getChildren().addAll(menuBar);

        //When Open is clicked
        menuItem_Open.setOnAction((e) -> {
            OpenImage(primaryStage, canvas, graphicsContext, menuBox);               //calls openImage method
        });

        //When saveAs is clicked
        menuItem_SaveAs.setOnAction((e) -> {
            SaveAs(primaryStage, canvas);                                            //calls SaveAs method
        });

        //When Save is clicked
        menuItem_Save.setOnAction((e) -> {
            if(saved){                                                               //If already saved
                Save(canvas);                                                        //Calls save method
            } else {                                                                 //else
                SaveAs(primaryStage, canvas);                                        //Calls SaveAs method
            }
        });

        //When getSize is clicked
        menuItem_getSize.setOnAction((e) -> {
            System.out.println("Width = " + canvas.getWidth() + "\n" + "Height = " + canvas.getHeight()); //print canvas size
        });

        //When close is clicked
        menuItem_Close.setOnAction((e) -> {
            SmartSave(primaryStage, canvas, paintDraw);                              //Calls SmartSave method
        });

        //When trying to close
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                SmartSave(primaryStage, canvas, paintDraw);                          //Calls SmartSave method
            }
        });

        //Creates new helpAbout class
        HelpAbout helpAbout = new HelpAbout();

        //When help about is clicked, calls helpAbout.display
        menuItem_HelpAbout.setOnAction(e -> helpAbout.display("About", "Version 1.0.4 \n " +
                "Powered by Michael Kumicich's suffering\n"
                + "Click on File->open to actually do things."));

        //When Release Notes is clicked
        menuItem_ReleaseNotes.setOnAction((e) -> {
            helpAbout.releaseNotes();                                               //Calls release notes method in
        });                                                                         //help about

        //When Tutorial is clicked
        menuItem_Tutorial.setOnAction((e) -> {
            helpAbout.Tutorial();                                                   //Calls Tutorial method in help about
        });

        paintWindow.setCanvas(canvas);
        paintWindow.setGraphicsContext(graphicsContext);

        Main main = new Main();                                                     //instantiates main class
        main.setWindow(primaryStage);

        return menuBox;
    }

    public void OpenImage(Stage primaryStage, Canvas canvas, GraphicsContext graphicsContext, VBox menuBox) {

        FileChooser openFile = new FileChooser();                                   //openFile window
        openFile.setTitle("Open File");
        openFile.getExtensionFilters().addAll(
                //File extensions
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif"));
        File file = openFile.showOpenDialog(primaryStage);
        if (file != null) {                                                         //If there is a file
            try {

                InputStream io = new FileInputStream(file);
                image = new Image(io);                                              //grabs image of file
                double imageHeight = image.getHeight();
                double imageWidth = image.getWidth();

                paintDraw.setImage(image);

                //Resizes canvas to match image size
                canvas.setWidth(imageWidth);
                canvas.setHeight(imageHeight);

                //draws the image onto the canvas
                graphicsContext.drawImage(image, 0, 0);
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

                //Re-centers window
                primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);

                changed = true;
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

    public void SaveAs(Stage primaryStage, Canvas canvas){
        FileChooser saveFile = new FileChooser();                                   //saveFile Window
        saveFile.setTitle("Save As");
        saveFile.getExtensionFilters().addAll(
                //File extensions
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif"));
        file = saveFile.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                //Saves writableImage with canvas size
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);


                saved = true;
                changed = false;
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

    public void Save(Canvas canvas){
        if (file != null) {                                                    //saves again if file is real
            try {
                //Saves without having to go to file window
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);

                changed = false;
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

    public void SmartSave(Stage primaryStage, Canvas canvas, PaintDraw paintDraw){
        primaryStage.close();                                             //Closes primaryStage
        if(changed||paintDraw.getdrawn()) {                               //If drawn or changes is true
            Stage window = new Stage();                                   //creates new Stage
            window.initModality(Modality.WINDOW_MODAL);                   //Stage cannot be closed until used

            window.setTitle("Smart Save");
            window.setWidth(300);

            Button saveYes = new Button("Yes");                        //Creates yes save button
            Button saveNo = new Button("No");                          //Creates no save button

            //if user clicks yes
            saveYes.setOnAction(e -> {
                if (saved) {                                              //If save as has already happened
                    Save(canvas);                                         //Call save method
                } else {                                                  //If save as has not happened
                    SaveAs(primaryStage, canvas);                         //Call saveAs method
                }
            });

            //if user clicks no
            saveNo.setOnAction(e -> {
                //Closes window
                primaryStage.close();
                window.close();
            });

            Label saveLabel = new Label();                                //Creates a new label
            saveLabel.setText("Save?");                                   //Puts message parameter is text

            GridPane gridPane = new GridPane();                           //Creates gridPane

            //GridPane properties
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(5);
            gridPane.setVgap(5);
            gridPane.add(saveLabel, 1, 0, 1, 2);
            gridPane.add(saveYes, 0, 3, 1, 1);
            gridPane.add(saveNo, 2, 3, 1, 1);

            Scene scene = new Scene(gridPane);
            window.setScene(scene);
            window.show();

            }
        }
    }
