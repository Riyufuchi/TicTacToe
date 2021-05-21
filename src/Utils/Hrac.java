package Utils;

import java.awt.Color;

public class Hrac 
{
    private String jmeno;
    private Color barva;
    
    public Hrac(String jmeno, Color barva)
    {
        this.jmeno = jmeno;
        this.barva = barva;
    }
    
    public void setBarva(Color barva)
    {
       this.barva = barva;
    }
    
    public String getJmeno()
    {
        return this.jmeno;
    }
    
    public Color getBarva()
    {
       return this.barva;
    }
}
