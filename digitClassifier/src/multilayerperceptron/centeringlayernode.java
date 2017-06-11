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
public class centeringlayernode extends layernode{
    public centeringlayernode(int N,int M,int type)
    {
        super(N,M,type);
    }
    protected double [] g(double [] t)
    {
        double [] t_ = t.clone();
        double m_t = 0.0;
        for(int n=0;n<N;n++)
            m_t += t[n];
        m_t /= N;
        double n_t = 1e-2;
        for(int n=0;n<N;n++)
            t_[n] -= m_t;
        for(int n=0;n<N;n++)
            n_t += 0.5*t_[n]*t_[n];
        for(int n=0;n<N;n++)
            t_[n] /= n_t;
        return t_;
    }
    protected double [] dg(double [] t,double [] d)
    {
        double [] e = new double [N];
        
        double [] t_ = t.clone();
        double m_t = 0.0;
        for(int n=0;n<N;n++)
            m_t += t[n];
        m_t /= N;
        double n_t = 1e-2;
        for(int n=0;n<N;n++)
            t_[n] -= m_t;
        for(int n=0;n<N;n++)
            n_t += 0.5*t_[n]*t_[n];
        for(int n=0;n<N;n++)
            t_[n] /= n_t;
        
        for(int n=0;n<N;n++)
            e[n] = 0.0;
        for(int n=0;n<N;n++)
            for(int m=0;m<N;m++)
            {
                e[n] += d[m] * ((n==m?(N-1.0)/(n_t*N):-1.0/(n_t*N))-t_[n]*t_[m]);
            }
        return e;
    }
}
