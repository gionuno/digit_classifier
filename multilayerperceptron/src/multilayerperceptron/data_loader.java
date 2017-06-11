/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilayerperceptron;

import java.io.FileInputStream;
import java.io.DataInputStream;


/**
 *
 * @author quien
 */
public class data_loader {
    public double E[][];
    public double T[][];
    
    public data_loader(String filenameIm,String filenameLa)
    {
        try
        {
            FileInputStream finputIm = new FileInputStream(filenameIm);
            FileInputStream finputLa = new FileInputStream(filenameLa);
            
            DataInputStream inputIm = new DataInputStream(finputIm); 
            DataInputStream inputLa = new DataInputStream(finputLa);
            try
            {
                int magicnum = inputIm.readInt(); inputLa.readInt();
                int N = inputIm.readInt(); inputLa.readInt();
                
                int R = inputIm.readInt();
                int C = inputIm.readInt();
                
                //N = 6000;
                
                System.out.println(N);
                System.out.println(R);
                System.out.println(C);
                double [][] I = new double [R][C];
                E = new double [N][21];
                T = new double [N][10];
                for(int n=0;n<N;n++)
                {
                    for(int d=0;d<10;d++)
                        T[n][d] = 0.0;
                    T[n][inputLa.readByte()] = 1.0;
                    for(int r=0;r<R;r++)
                    for(int c=0;c<C;c++)
                        I[r][c] = (inputIm.readByte()==0?0.0:1.0);                   
                    double [] aux = getHuMoments(I);
                    for(int k=0;k<aux.length;k++)
                        E[n][k] = aux[k];
                    aux = getHuMoments(getAbsDx(I));
                    for(int k=0;k<aux.length;k++)
                        E[n][7+k] = aux[k];
                    aux = getHuMoments(getAbsDy(I));
                    for(int k=0;k<aux.length;k++)
                        E[n][14+k] = aux[k];
                    
                }
            }
            catch (Exception e) {
                System.out.println("[EOF]");
            }
            finputLa.close();
            finputIm.close();
            
            inputLa.close();
            inputIm.close();
        }
        catch (Exception e) {
            System.out.println("No file found!");
        }
        
    }
    public double [][] getAbsDx(double [][] a)
    {
        double [][] dx = new double [a.length-1][a.length];
        for(int i=0;i<dx.length;i++)
            for(int j=0;j<dx[i].length;j++)
                dx[i][j] = Math.abs(a[i][j]-a[i+1][j]);
        return dx;
    }
    public double [][] getAbsDy(double [][] a)
    {
        double [][] dy = new double [a.length][a.length-1];
        for(int i=0;i<dy.length;i++)
            for(int j=0;j<dy[i].length;j++)
                dy[i][j] = Math.abs(a[i][j]-a[i][j+1]);
        return dy;
    }
    public double [] getHuMoments(double [][] a)
    {
        double M00 = 0.0;
        double M01 = 0.0;
        double M10 = 0.0;
        double M11 = 0.0;
        double M12 = 0.0;
        double M21 = 0.0;
        double M20 = 0.0;
        double M02 = 0.0;
        double M22 = 0.0;
        double M30 = 0.0;
        double M03 = 0.0;
        
        double x_bar = 0.0;
        double y_bar = 0.0;
        for(int i=0;i<a.length;i++)
            for(int j=0;j<a[i].length;j++)
            {
                double aux = a[i][j];
                M00 += aux;
                M01 += j*aux;
                M02 += j*j*aux;
                M03 += j*j*j*aux;
                
                M10 += i*aux;
                M20 += i*i*aux;
                M30 += i*i*i*aux;
                
                M11 += i*j*aux;
                
                M12 += i*j*j*aux;
                M21 += i*i*j*aux;
                M22 += i*i*j*j*aux;
            }
        x_bar = M10/M00;
        y_bar = M01/M00;
        
        double m00 = M00;
        
        
        double m11 = M11-x_bar*M01;
        
        double m02 = M02-y_bar*M01;
        double m20 = M20-x_bar*M10;
        
        double m12 = M12-2.0*y_bar*M11-x_bar*M02+2.0*y_bar*y_bar*M10;
        double m21 = M21-2.0*x_bar*M11-y_bar*M20+2.0*x_bar*x_bar*M01;
        
        double m22 = M22+M00*x_bar*x_bar*y_bar*y_bar
                     -2.0*x_bar*x_bar*y_bar*M01
                     -2.0*x_bar*y_bar*y_bar*M10
                     +x_bar*x_bar*M02+y_bar*y_bar*M20+4.0*x_bar*y_bar*M11
                     -2.0*x_bar*M12-2.0*y_bar*M21;
        double m03 = M03-3.0*y_bar*M02+2.0*y_bar*y_bar*M01;
        double m30 = M30-3.0*x_bar*M20+2.0*x_bar*x_bar*M10;
        
        
        double r [] = new double [7];
        double n11  = m11 / Math.pow(m00,2.0);
        double n02  = m02 / Math.pow(m00,2.0);
        double n20  = m20 / Math.pow(m00,2.0);
        double n12  = m12 / Math.pow(m00,2.5);
        double n21  = m21 / Math.pow(m00,2.5);
        double n03  = m03 / Math.pow(m00,2.5);
        double n30  = m30 / Math.pow(m00,2.5);
        double n22  = m22 / Math.pow(m00,3.0);
        
        r[0]  = n20 + n02;
        r[1]  = Math.pow(n20-n02,2.0)+4.0*n11*n11;
        r[2]  = Math.pow(n30-3.0*n12,2.0)+Math.pow(3.0*n21-n03,2.0);
        r[3] = Math.pow(n30+n12,2.0)+Math.pow(n03+n21,2.0);
        r[4] = (n30-3.0*n12)*(n30+n12)*(Math.pow(n30+n12,2)-3.0*Math.pow(n21+n03,2))+(3.0*n21-n03)*(n21+n03)*(3.0*Math.pow(n30+n12,2)-Math.pow(n21+n03,2.0));
        r[5] = (n20-n02)*(Math.pow(n30+n12,2)-Math.pow(n21+n03,2))+4.0*n11*(n30+n12)*(n21+n03);
        r[6] = (3.0*n21-n03)*(n30+n12)*(Math.pow(n30+n12,2)-3.0*Math.pow(n21+n03,2))-(n30-3.0*n12)*(n21+n03)*(3.0*Math.pow(n30+n12,2)-Math.pow(n21+n03,2));
        return r;
    }
}
