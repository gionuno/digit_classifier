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
public class relunode extends node{
    public relunode(int N)
    {
        super(N);
    }
    protected double f(double t)
    {
        return t > -1.0 && t < 1.0 ? t : (t <= -1.0 ? 0.001*(t+1)-1.0 : 0.001*(t-1.0)+1.0);
    }
    protected double df(double t)
    {
        return t > -1.0 && t < 1.0 ? 1.0 : 0.001;
    }
}

