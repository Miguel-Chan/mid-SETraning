package info.gridworld.actor;

import java.awt.Color;






















public class Flower
  extends Actor
{
  private static final Color DEFAULT_COLOR = Color.PINK;
  


  private static final double DARKENING_FACTOR = 0.05D;
  


  public Flower()
  {
    setColor(DEFAULT_COLOR);
  }
  




  public Flower(Color initialColor)
  {
    setColor(initialColor);
  }
  



  public void act()
  {
    Color c = getColor();
    int red = (int)(c.getRed() * 0.95D);
    int green = (int)(c.getGreen() * 0.95D);
    int blue = (int)(c.getBlue() * 0.95D);
    
    setColor(new Color(red, green, blue));
  }
}
