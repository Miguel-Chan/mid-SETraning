package info.gridworld.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;











public class FormLayout
  implements LayoutManager
{
  private int left;
  private int right;
  private int height;
  private static final int GAP = 6;
  
  public FormLayout() {}
  
  public Dimension preferredLayoutSize(Container parent)
  {
    Component[] components = parent.getComponents();
    left = 0;
    right = 0;
    height = 0;
    for (int i = 0; i < components.length; i += 2)
    {
      Component cleft = components[i];
      Component cright = components[(i + 1)];
      
      Dimension dleft = cleft.getPreferredSize();
      Dimension dright = cright.getPreferredSize();
      left = Math.max(left, width);
      right = Math.max(right, width);
      height += Math.max(height, height);
    }
    return new Dimension(left + 6 + right, height);
  }
  
  public Dimension minimumLayoutSize(Container parent)
  {
    return preferredLayoutSize(parent);
  }
  
  public void layoutContainer(Container parent)
  {
    preferredLayoutSize(parent);
    
    Component[] components = parent.getComponents();
    
    Insets insets = parent.getInsets();
    int xcenter = left + left;
    int y = top;
    
    for (int i = 0; i < components.length; i += 2)
    {
      Component cleft = components[i];
      Component cright = components[(i + 1)];
      
      Dimension dleft = cleft.getPreferredSize();
      Dimension dright = cright.getPreferredSize();
      
      int height = Math.max(height, height);
      
      cleft.setBounds(xcenter - width, y + (height - height) / 2, width, height);
      

      cright.setBounds(xcenter + 6, y + (height - height) / 2, width, height);
      
      y += height;
    }
  }
  
  public void addLayoutComponent(String name, Component comp) {}
  
  public void removeLayoutComponent(Component comp) {}
}
