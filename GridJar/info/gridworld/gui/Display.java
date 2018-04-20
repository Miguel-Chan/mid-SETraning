package info.gridworld.gui;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract interface Display
{
  public abstract void draw(Object paramObject, Component paramComponent, Graphics2D paramGraphics2D, Rectangle paramRectangle);
}
