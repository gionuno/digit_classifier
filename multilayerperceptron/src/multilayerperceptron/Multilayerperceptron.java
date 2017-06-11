/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilayerperceptron;

import java.io.FileWriter;
/**
 *
 * @author quien
 */
public class Multilayerperceptron {

    /**
     * @param args the command line arguments
     */
    public static trainer T;
    public static data_loader D_train;
    public static data_loader D_test;
    public static void main(String[] args) {
        // TODO code application logic here
        D_train = new data_loader("train-images.idx3-ubyte","train-labels.idx1-ubyte");
        D_test  = new data_loader( "t10k-images.idx3-ubyte","t10k-labels.idx1-ubyte");
        
        if(args.length == 2)
        {
            T = new trainer(args[0],200);
        }
        else
        {
            int sizes_ [] = new int [6];
            sizes_[0] = 21;
            sizes_[1] = 84;
            sizes_[2] = 84;
            sizes_[3] = 84;
            sizes_[4] = 84;
            sizes_[5] = 10;
            int type1_ [] = new int [5];
            type1_[0] = 0;
            type1_[1] = 0;
            type1_[2] = 0;
            type1_[3] = 0;
            type1_[4] = 1;
            int type2_ [] = new int [5];
            type2_[0] = 2;
            type2_[1] = 2;
            type2_[2] = 2;
            type2_[3] = 2;
            type2_[4] = 0;
            T = new trainer(sizes_,type1_,type2_,200);
        }
        T.set_e_and_t(D_train.E,D_train.T);
        
        try
        {
            FileWriter writer = new FileWriter("err.txt");
            for(int t=0;t<1200;t++)
            {   
                double [] err = T.step(10, 5e-3,1e-5);
                double mean_err = 0.0;
                double min_err  = err[0];
                double max_err  = err[0];
                for(int i=0;i<err.length;i++)
                {
                    mean_err += err[i];
                    min_err = min_err > err[i] ? err[i] : min_err;
                    max_err = max_err < err[i] ? err[i] : max_err;
                }    
                mean_err /= err.length;
                double Err = 0.0;
                for(int tt=0;tt<100;tt++)
                {
                    double [] y = T.Net.eval(D_test.E[tt]);
                    for(int k=0;k<y.length;k++)
                        Err -= D_test.T[tt][k]*Math.log(y[k])/100.0;
                }
                System.out.println(t+" "+mean_err+" "+Err);        
                writer.write(min_err+" "+max_err+" "+mean_err+" "+Err+"\n");
            }
            writer.close();
        }
        catch(Exception e){}
        T.Net.save(args[args.length-1]);
    }
}