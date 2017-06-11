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
public class softmaxlayernode extends layernode{
    public softmaxlayernode(int N,int M,int type)
    {
        super(N,M,type);
    }
    protected double [] g (double t[])
    {
        double [] aux = new double [t.length];
        double m_ = t[0];
        for(int m=0;m<M;m++)
            m_ = t[m] > m_ ? t[m] : m_;
        double sum = 0.0;
        for(int m=0;m<M;m++)
        {
            aux[m] = Math.exp(t[m]-m_);
            sum += aux[m];
        }
        for(int m=0;m<M;m++)
            aux[m] /= sum;
        return aux;
    }
    protected double [] dg(double t[],double d[])
    {
        double [] a = this.g(t);
        double [] e = new double [d.length];
        for(int n=0;n<M;n++)
            e[n] = 0.0;
        for(int n=0;n<M;n++)
            for(int m=0;m<M;m++)
            {
                e[n] += d[m]*((n==m?a[n]:0.0)-a[n]*a[m]);
            }
        return e;
    }
}
