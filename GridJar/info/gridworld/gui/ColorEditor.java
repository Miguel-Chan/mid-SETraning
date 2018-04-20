package info.gridworld.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.beans.PropertyEditorSupport;
import javax.swing.Icon;
import javax.swing.JComboBox;





















public class ColorEditor
  extends PropertyEditorSupport
{
  private JComboBox combo;
  
  public ColorEditor()
  {
    combo = new JComboBox(colorIcons);
  }
  
  public Object getValue()
  {
    ColorIcon value = (ColorIcon)combo.getSelectedItem();
    return value.getColor();
  }
  
  public boolean supportsCustomEditor()
  {
    return true;
  }
  
  public Component getCustomEditor()
  {
    combo.setSelectedItem(Integer.valueOf(0));
    return combo;
  }
  
  private static abstract interface ColorIcon extends Icon
  {
    public static final int WIDTH = 120;
    public static final int HEIGHT = 20;
    
    public abstract Color getColor();
  }
  
  private static class SolidColorIcon implements ColorEditor.ColorIcon
  {
    private Color color;
    
    public Color getColor()
    {
      return color;
    }
    
    public SolidColorIcon(Color c)
    {
      color = c;
    }
    
    public int getIconWidth()
    {
      return 120;
    }
    
    public int getIconHeight()
    {
      return 20;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
      Rectangle r = new Rectangle(x, y, 119, 19);
      Graphics2D g2 = (Graphics2D)g;
      Color oldColor = g2.getColor();
      g2.setColor(color);
      g2.fill(r);
      g2.setColor(Color.BLACK);
      g2.draw(r);
      g2.setColor(oldColor);
    }
  }
  
  private static class RandomColorIcon implements ColorEditor.ColorIcon {
    private RandomColorIcon() {}
    
    public Color getColor() {
      return new Color((int)(Math.random() * 256.0D * 256.0D * 256.0D));
    }
    
    public int getIconWidth()
    {
      return 120;
    }
    
    public int getIconHeight()
    {
      return 20;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
      Rectangle r = new Rectangle(x, y, 119, 19);
      Graphics2D g2 = (Graphics2D)g;
      Color oldColor = g2.getColor();
      Rectangle r1 = new Rectangle(x, y, 30, 19);
      for (int i = 0; i < 4; i++)
      {
        g2.setColor(getColor());
        g2.fill(r1);
        r1.translate(30, 0);
      }
      g2.setColor(Color.BLACK);
      g2.draw(r);
      g2.setColor(oldColor);
    }
  }
  


  private static Color[] colorValues = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW };
  







  private static ColorIcon[] colorIcons = new ColorIcon[colorValues.length + 1];
  static { colorIcons[0] = new RandomColorIcon(null);
    for (int i = 0; i < colorValues.length; i++) {
      colorIcons[(i + 1)] = new SolidColorIcon(colorValues[i]);
    }
  }
}
