package Paint2;

import javafx.scene.control.*;

/**
 * Class used to assign tooltips when items are hovered
 *
 * @author Michael Kumicich
 * 10/25/2019
 */
public class PaintTooltip {

    /**
     * Create tooltips for toggle buttons
     * @param lineBtn, rectBtn, arcRectBtn, circleBtn, ellipseBtn, pencilBtn, rubberbtn, grabberBtn, textBtn, selectBtn,
     *        moveBtn, polyBtn, copyBtn
     */
    public void setToggleTip(ToggleButton lineBtn, ToggleButton rectBtn, ToggleButton arcRectBtn, ToggleButton circleBtn,
                           ToggleButton ellipseBtn, ToggleButton pencilBtn, ToggleButton rubberbtn, ToggleButton grabberBtn,
                           ToggleButton textBtn, ToggleButton selectBtn, ToggleButton moveBtn, ToggleButton polyBtn,
                             ToggleButton copyBtn){

        //Create tooltips
        Tooltip tooltipLine = new Tooltip("Draw Line");
        Tooltip tooltipRect = new Tooltip("Draw Rectangle");
        Tooltip tooltipArcRect = new Tooltip("Draw Rounded Rectangle");
        Tooltip tooltipCircle = new Tooltip("Draw Circle");
        Tooltip tooltipEllipse = new Tooltip("Draw Ellipse");
        Tooltip tooltipPencil = new Tooltip("Draw");
        Tooltip tooltipRubber = new Tooltip("Erase");
        Tooltip tooltipGrabber = new Tooltip("Grab Color");
        Tooltip tooltipText = new Tooltip("Paste Text");
        Tooltip tooltipSelect = new Tooltip("Copy and Erase");
        Tooltip tooltipCopy = new Tooltip("Copy");
        Tooltip tooltipMove = new Tooltip("Paste and Move");
        Tooltip tooltipPolygon = new Tooltip("Draw Polygon");

        //Assign tooltips
        lineBtn.setTooltip(tooltipLine);
        rectBtn.setTooltip(tooltipRect);
        arcRectBtn.setTooltip(tooltipArcRect);
        circleBtn.setTooltip(tooltipCircle);
        ellipseBtn.setTooltip(tooltipEllipse);
        pencilBtn.setTooltip(tooltipPencil);
        rubberbtn.setTooltip(tooltipRubber);
        grabberBtn.setTooltip(tooltipGrabber);
        textBtn.setTooltip(tooltipText);
        selectBtn.setTooltip(tooltipSelect);
        moveBtn.setTooltip(tooltipMove);
        polyBtn.setTooltip(tooltipPolygon);
        copyBtn.setTooltip(tooltipCopy);
    }

    /**
     * Create tooltips for text fields
     * @param textWidth, textHeight, textArea, numText, invSetting
     */
    public void setTextFieldTip(TextField textWidth, TextField textHeight, TextField textArea, TextField numText,
                                TextField invSetting){

        //Create Tooltips
        Tooltip tooltipWidth = new Tooltip("Resize Canvas Width");
        Tooltip tooltipHeight = new Tooltip("Resize Canvas Height");
        Tooltip tooltipTextField = new Tooltip("Enter Text");
        Tooltip tooltipPolyNum = new Tooltip("Enter Polygon Sides");
        Tooltip tooltipClrSetting = new Tooltip("Change Color Settings:\n 1: Normal\n 2: Brighten\n 3: Darken\n 4: " +
                "Grayscale\n 5: Invert\n 6: Saturate\n 7: Desaturate");

        //Assign Tooltips
        textWidth.setTooltip(tooltipWidth);
        textHeight.setTooltip(tooltipHeight);
        textArea.setTooltip(tooltipTextField);
        numText.setTooltip(tooltipPolyNum);
        invSetting.setTooltip(tooltipClrSetting);
    }

    /**
     * Create tooltips for buttons
     * @param undoBtn, redoBtn, resize
     */
    public void setBtnTip(Button undoBtn, Button redoBtn, Button resize, Button change){

        //Create Tooltips
        Tooltip tooltipUndo = new Tooltip("Undo");
        Tooltip tooltipRedo = new Tooltip("Redo");
        Tooltip tooltipResize = new Tooltip("Resize canvas");
        Tooltip tooltipChange = new Tooltip("Click to change colors of screen\n based off options-> Color choice");

        //Assign Tooltips
        undoBtn.setTooltip(tooltipUndo);
        redoBtn.setTooltip(tooltipRedo);
        resize.setTooltip(tooltipResize);
        change.setTooltip(tooltipChange);
    }

    /**
     * Create tooltips for item stroke size and color, as well as fill color
     * @param cpLine, cpFill, slider
     */
    public void setGraphicTip(ColorPicker cpLine, ColorPicker cpFill, Slider slider){

        //Create Tooltips
        Tooltip tooltipStroke = new Tooltip("Line Color");
        Tooltip tooltipFill = new Tooltip("Fill Color");
        Tooltip tooltipSlider = new Tooltip("Line Width");

        //Assign Tooltips
        cpLine.setTooltip(tooltipStroke);
        cpFill.setTooltip(tooltipFill);
        slider.setTooltip(tooltipSlider);
    }
}
