/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitclassifier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.image.WritableImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.ProgressBar;

import javafx.scene.paint.Color;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import javafx.scene.Node;
import java.io.File;


import multilayerperceptron.feedforwardnet;

/**
 *
 * @author quien
 */
public class digitClassifierController implements Initializable {
    
    private feedforwardnet Net;
    private HuMomentUtils HuMoments;
    private double [][] I;
    private double []   E;
    
    @FXML
    private ImageView digitCanvas;
    
    private Image     valueOrigin;
    private WritableImage currentImage;
    
    @FXML
    private ProgressBar bar0;
    @FXML
    private ProgressBar bar1;
    @FXML
    private ProgressBar bar2;
    @FXML
    private ProgressBar bar3;
    @FXML
    private ProgressBar bar4;
    @FXML
    private ProgressBar bar5;
    @FXML
    private ProgressBar bar6;
    @FXML
    private ProgressBar bar7;
    @FXML
    private ProgressBar bar8;
    @FXML
    private ProgressBar bar9;
    
    @FXML
    protected void handleClassify(ActionEvent event)
    {
        E = HuMoments.evalIm(I);
        double [] Y = Net.eval(E);
        bar0.setProgress(Y[0]);
        bar1.setProgress(Y[1]);
        bar2.setProgress(Y[2]);
        bar3.setProgress(Y[3]);
        bar4.setProgress(Y[4]);
        bar5.setProgress(Y[5]);
        bar6.setProgress(Y[6]);
        bar7.setProgress(Y[7]);
        bar8.setProgress(Y[8]);
        bar9.setProgress(Y[9]);
        System.out.println("CLASSIFY!");
    }
    @FXML
    protected void handleClear(ActionEvent event)
    {
        for(int i=0;i<I.length;i++)
            for(int j=0;j<I[i].length;j++)
                I[i][j] = 0.;
        currentImage = new WritableImage(valueOrigin.getPixelReader(),140,140);
        digitCanvas.setImage(valueOrigin);
        
        bar0.setProgress(0.0);
        bar1.setProgress(0.0);
        bar2.setProgress(0.0);
        bar3.setProgress(0.0);
        bar4.setProgress(0.0);
        bar5.setProgress(0.0);
        bar6.setProgress(0.0);
        bar7.setProgress(0.0);
        bar8.setProgress(0.0);
        bar9.setProgress(0.0);
    }
    @FXML
    protected void handleDraw(MouseEvent event)
    {
        double H = valueOrigin.getHeight();
        double W = valueOrigin.getWidth();
        double X = event.getX();
        double Y = event.getY();
        if(0 <= X && X < W && 0 <= Y && Y < H)
        {
            I[(int)(Y/5.0)][(int)(X/5.0)] = 1.0;
            for(int i=0;i<5;i++)
                for(int j=0;j<5;j++)
                    currentImage.getPixelWriter().setColor(5*(int)(X/5.0)+i,5*(int)(Y/5.0)+j, Color.WHITE);
            digitCanvas.setImage(currentImage);
        }
    }
    @FXML
    protected void handleExit(ActionEvent event)
    {
        System.exit(0);
    }
    @FXML
    protected void handleAbout(ActionEvent event)
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Hecho por Giovanni Marcelo Nuno Zavala");
        a.show();
    }
    @FXML
    protected void handleLoad(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .bin File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Binary Files","*.bin"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
        {
            System.out.println(selectedFile.getAbsolutePath());
            Net = new feedforwardnet(selectedFile.getAbsolutePath());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        valueOrigin = new Image("file:blacksquare.png");
        currentImage = new WritableImage(valueOrigin.getPixelReader(),140,140);
        digitCanvas.setImage(valueOrigin);
        I = new double [28][28];
        E = new double [21];
        HuMoments = new HuMomentUtils();
        Net = new feedforwardnet("net.bin");
    }    
        
}
