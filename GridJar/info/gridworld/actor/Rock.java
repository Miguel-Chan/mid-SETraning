package info.gridworld.actor;

import java.awt.Color;
























public class Rock
  extends Actor
{
  private static final Color DEFAULT_COLOR = Color.BLACK;
  



  public Rock()
  {
    setColor(DEFAULT_COLOR);
  }
  




  public Rock(Color rockColor)
  {
    setColor(rockColor);
  }
  
  public void act() {}
}
