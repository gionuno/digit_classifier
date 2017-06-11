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
public class idnode extends node{
    public idnode(int N)
    {
        super(N);
    }
    protected double f(double t)
    {
        return t;
    }
    protected double df(double t)
    {
        return 1.0;
    }
}
