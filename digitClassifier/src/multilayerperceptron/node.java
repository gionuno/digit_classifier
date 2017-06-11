/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilayerperceptron;

import java.util.Random;
import java.lang.Math;

/**
 *
 * @author quien
 */

public abstract class node {    
    double [] pw_last;
    double [] dw_last;
    double [] dw;
    
    double    pb_last;
    double    db_last;
    double    db;
    
    
    
    double [] w;
    double    b;

    double dEdy;
    
    double    x;
    double    y;
        
    int N;
    int t;
    protected abstract double f(double t);
    protected abstract double df(double t);
    
    public node(int N)
    {
        this.t = 0;
        this.N = N;
        Random R = new Random();
        
        this.w   = new double [N];
        this.dw  = new double [N];
        
        this.dw_last = new double [N];
        this.pw_last = new double [N];
        for(int n=0;n<N;n++)
            this.w[n] = 2.0*R.nextDouble()-1.0;
        this.b = 2.0*R.nextDouble()-1.0;
        
        for(int n=0;n<N;n++)
            this.dw_last[n] = this.pw_last[n] = this.w[n];
        
        this.db = this.pb_last = this.db_last =  this.b;

        this.x = 0.0;
        this.y = 0.0;
    }
    
    public double eval(double[] h)
    {
        this.y = this.b;
        for(int n=0;n<this.N;n++)
            this.y += w[n]*h[n];
        
        this.x = this.f(this.y);
        return this.x;
    }
    
    
    public void accum_derivs(double [] h,double dEdx)
    {
        double dxdy = this.df(this.y);
        this.dEdy = dEdx * dxdy;
        this.db  += this.dEdy;
        for(int n=0;n<this.N;n++)
        {
            this.dw[n]  += this.dEdy*h[n];
        }
    }
    public void step(double eta,double lambda)
    {
        for(int n=0;n<this.N;n++)
            this.dw[n] += lambda * this.w[n]; 
        
        double den = (this.db-this.db_last)*this.db;
        double num = this.db_last*this.db_last;
        for(int n=0;n<this.N;n++)
        {
            den += (this.dw[n]-this.dw_last[n])*this.dw[n];
            num += this.dw_last[n]*this.dw_last[n];
        }
        double Bk = den/(num+1e-3);
        
        double pw [] = new double [N];
        for(int n=0;n<this.N;n++)
            pw[n] = -this.dw[n]+0.1*Bk*this.pw_last[n];
        double pb = -this.db+0.1*Bk*this.pb_last;
        
        this.b += eta*pb;
        for(int n=0;n<this.N;n++)
        {
            this.w[n] += eta*pw[n];
        }
        
        t++;
        this.pb_last = pb;
        for(int n=0;n<this.N;n++)
        {
            this.pw_last[n] = pw[n];
            this.dw_last[n] = this.dw[n];
            this.dw[n] = 0.0;
        }
        this.db_last = this.db;
        this.db = 0.0;
    }
    public double get_dEdy()
    {
        return dEdy;
    }
    public double get_w(int idx)
    {
        return w[idx];
    }
};