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
public class trainer {
    public feedforwardnet Net;
    private double e[][];
    private double t[][];
    int batchS;
    int b_idx;
    trainer(int sizes_[],int type1_[], int type2_[],int batchS_)
    {
        b_idx = 0;
        batchS = batchS_;
        Net = new feedforwardnet(sizes_,type1_,type2_);
    }
    trainer(String filename,int batchS_)
    {   
        b_idx = 0;
        batchS = batchS_;
        Net = new feedforwardnet(filename);
    }
    public void set_e_and_t(double e_[][],double t_[][])
    {
        e = e_;
        t = t_;
    }
    public double [] step(int T,double eta,double lambda)
    {
        double [] ERR = new double [T];
        for(int tt=0;tt<T;tt++)
        {
            ERR[tt] = 0.;
            for(int b=b_idx;b<b_idx+batchS;b++)
            {
                int idx = b%e.length;
                double [] y = Net.eval(e[idx]);
                for(int k=0;k<y.length;k++)
                        ERR[tt] += -t[idx][k]*Math.log(y[k]) / batchS;
                Net.accum_derivs(t[idx],batchS);
            }
            Net.step(eta,lambda);
        }
        b_idx += batchS/2;
        return ERR;
    }
}
