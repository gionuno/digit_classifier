/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilayerperceptron;

/**
 *
 * @author quien
 */

import java.io.FileInputStream;
import java.io.DataInputStream;

import java.io.FileOutputStream;
import java.io.DataOutputStream;

public class feedforwardnet {
    int [] sizes;
    layernode layers[];
    int [] type1;
    int [] type2;
    
    double h [][];
    
    public feedforwardnet(int [] sizes_,int [] type1_,int [] type2_)
    {
        sizes = sizes_;
        type1 = type1_;
        type2 = type2_;
        layers = new layernode [sizes.length-1];
        
        h = new double [sizes.length] [];
        
        h[0] = new double [sizes[0]];
        for(int l=0;l<layers.length;l++)
        {
            switch(type1_[l])
            {
                case 0:
                    layers[l] = new idlayernode(sizes[l],sizes[l+1],type2_[l]);
                    break;
                case 1:
                    layers[l] = new softmaxlayernode(sizes[l],sizes[l+1],type2_[l]);
                    break;
                case 2:
                    layers[l] = new centeringlayernode(sizes[l],sizes[l+1],type2_[l]);
                    break;
            }
            h[l+1] = new double [sizes[l+1]];
        }
    }
    public feedforwardnet(String filename)
    {
        try
        {
            FileInputStream fin = new FileInputStream(filename);
            DataInputStream din = new DataInputStream(fin);
            sizes = new int [din.readInt()];
            for(int l=0;l<sizes.length;l++)
                sizes[l] = din.readInt();
            type1 = new int [sizes.length-1];
            for(int l=0;l<type1.length;l++)
                type1[l] = din.readInt();
            type2 = new int [sizes.length-1];
            for(int l=0;l<type2.length;l++)
                type2[l] = din.readInt();
         
            layers = new layernode [sizes.length-1];
            h = new double [sizes.length] [];
        
            h[0] = new double [sizes[0]];
            for(int l=0;l<layers.length;l++)
            {
                switch(type1[l])
                {
                case 0:
                    layers[l] = new idlayernode(sizes[l],sizes[l+1],type2[l]);
                    break;
                case 1:
                    layers[l] = new softmaxlayernode(sizes[l],sizes[l+1],type2[l]);
                    break;
                case 2:
                    layers[l] = new centeringlayernode(sizes[l],sizes[l+1],type2[l]);
                    break;
                }
                h[l+1] = new double [sizes[l+1]];
            }
            for(int l=0;l<layers.length;l++)
            {
                for(int n=0;n<layers[l].M;n++)
                {
                    for(int m=0;m<layers[l].N;m++)
                    {
                        layers[l].nodes[n].w[m] = din.readDouble();
                    }
                }
                for(int n=0;n<layers[l].M;n++)
                    layers[l].nodes[n].b = din.readDouble();                
            }
            fin.close();
            din.close();
        }
        catch(Exception e){}
    }
    public double [] eval(double e[])
    {
        for(int i=0;i<e.length;i++)
            h[0][i] = e[i];
        for(int l=0;l<layers.length;l++)
            h[l+1] = layers[l].eval(h[l]);
        return h[h.length-1];
    }
    public void accum_derivs(double t[],double N)
    {
        double [] dEdX   = new double [t.length];
        for(int k=0;k<t.length;k++)
        {
                dEdX[k]   = -t[k]/(h[h.length-1][k]*N);
        }
        for(int l=layers.length-1;l>=0;l--)
        {
            layers[l].accum_derivs(h[l], dEdX);
            dEdX = layers[l].get_dEdX();
        }
    }
    public void step(double eta,double lambda)
    {
        for(int l=0;l<layers.length;l++)
            layers[l].step(eta,lambda);
    }
    public void save(String filename)
    {
        try
        {            
            FileOutputStream fout = new FileOutputStream(filename);
            DataOutputStream dout = new DataOutputStream(fout);
            
            dout.writeInt(sizes.length);
            for(int i=0;i<sizes.length;i++)
                dout.writeInt(sizes[i]);
            for(int i=0;i<type1.length;i++)
                dout.writeInt(type1[i]);
            for(int i=0;i<type2.length;i++)
                dout.writeInt(type2[i]);
            for(int l=0;l<layers.length;l++)
            {
                for(int n=0;n<layers[l].M;n++)
                {
                    for(int m=0;m<layers[l].N;m++)
                    {
                        dout.writeDouble(layers[l].nodes[n].w[m]);
                    }
                }
                for(int n=0;n<layers[l].M;n++)
                    dout.writeDouble(layers[l].nodes[n].b);
            }
            dout.close();
            fout.close();
        }
        catch(Exception e)
        {
        }
    }
}
