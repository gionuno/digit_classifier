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
public class idlayernode extends layernode{
    public idlayernode(int N,int M,int type)
    {
        super(N,M,type);
    }
    protected double [] g(double [] t)
    {
        return t.clone();
    }
    protected double [] dg(double [] t,double [] d)
    {
        return d.clone();
    }
}
