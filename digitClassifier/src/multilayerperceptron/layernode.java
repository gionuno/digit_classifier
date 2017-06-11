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

public abstract class layernode{
       int N;
       int M;
       
       double [] dEdY;
       
       double [] dEdX;

       double [] y;
       double [] x;
       
       public node nodes[];
       public layernode(int N,int M,int type)
       {
           
           this.nodes = new node [M];
           this.N    = N;
           this.M    = M;

           this.y = new double [M];

           for(int m=0;m<M;m++)
           {
               switch(type)
               {
                   case 0:
                      this.nodes[m] = new idnode(N);
                      break;
                   case 1:
                      this.nodes[m] = new tanhnode(N);
                      break;
                   case 2:
                      this.nodes[m] = new relunode(N);
                      break;
               }
           }
           this.dEdX   = new double [N];
           
           this.dEdY   = new double [M];
       }
       
       protected abstract double [] g(double [] t);
       protected abstract double [] dg(double [] t,double [] d);
       
       public double[] eval(double [] h)
       {
           for(int m=0;m<this.M;m++)
               this.y[m] = nodes[m].eval(h);
           this.x = this.g(y);
           return x;
       }
       public void accum_derivs(double [] h,double [] dEdX_)
       {
           double [] dEdz = this.dg(this.y,dEdX_);
           for(int m=0;m<this.M;m++)
           {
               nodes[m].accum_derivs(h, dEdz[m]);
               dEdY[m] = nodes[m].get_dEdy();
           }
           for(int n=0;n<N;n++) dEdX[n] = 0.0;
           
           for(int n=0;n<N;n++)
               for(int m=0;m<M;m++)
               {
                   dEdX[n] += dEdY[m]*nodes[m].get_w(n);
               }
       }
       public double [] get_dEdX()
       {
           return dEdX;
       }
       public void step(double eta,double lambda)
       {
            for(int m=0;m<this.M;m++)
                nodes[m].step(eta,lambda);
       }
}
