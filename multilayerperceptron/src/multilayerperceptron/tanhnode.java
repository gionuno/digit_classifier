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
public class tanhnode extends node{
    public tanhnode(int N)
    {
        super(N);
    }
    protected double f(double t)
    {
        return Math.tanh(t);
    }
    protected double df(double t)
    {
        double aux = Math.tanh(t);
        return 1.0-aux*aux;
    }
}
