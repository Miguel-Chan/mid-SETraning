package info.gridworld.gui;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;






































public class MenuMaker<T>
{
  private T occupant;
  private Grid currentGrid;
  private Location currentLocation;
  private WorldFrame<T> parent;
  private DisplayMap displayMap;
  private ResourceBundle resources;
  
  public MenuMaker(WorldFrame<T> parent, ResourceBundle resources, DisplayMap displayMap)
  {
    this.parent = parent;
    this.resources = resources;
    this.displayMap = displayMap;
  }
  






  public JPopupMenu makeMethodMenu(T occupant, Location loc)
  {
    this.occupant = occupant;
    currentLocation = loc;
    JPopupMenu menu = new JPopupMenu();
    Method[] methods = getMethods();
    Class oldDcl = null;
    for (int i = 0; i < methods.length; i++)
    {
      Class dcl = methods[i].getDeclaringClass();
      if (dcl != Object.class)
      {
        if ((i > 0) && (dcl != oldDcl))
          menu.addSeparator();
        menu.add(new MethodItem(methods[i]));
      }
      oldDcl = dcl;
    }
    return menu;
  }
  








  public JPopupMenu makeConstructorMenu(Collection<Class> classes, Location loc)
  {
    currentLocation = loc;
    JPopupMenu menu = new JPopupMenu();
    boolean first = true;
    Iterator<Class> iter = classes.iterator();
    while (iter.hasNext())
    {
      if (first) {
        first = false;
      } else
        menu.addSeparator();
      Class cl = (Class)iter.next();
      Constructor[] cons = (Constructor[])cl.getConstructors();
      for (int i = 0; i < cons.length; i++)
      {
        menu.add(new OccupantConstructorItem(cons[i]));
      }
    }
    return menu;
  }
  






  public void addConstructors(JMenu menu, Collection<Class> classes)
  {
    boolean first = true;
    Iterator<Class> iter = classes.iterator();
    while (iter.hasNext())
    {
      if (first) {
        first = false;
      } else
        menu.addSeparator();
      Class cl = (Class)iter.next();
      Constructor[] cons = cl.getConstructors();
      for (int i = 0; i < cons.length; i++)
      {
        menu.add(new GridConstructorItem(cons[i]));
      }
    }
  }
  
  private Method[] getMethods()
  {
    Class cl = occupant.getClass();
    Method[] methods = cl.getMethods();
    
    Arrays.sort(methods, new Comparator()
    {
      public int compare(Method m1, Method m2)
      {
        int d1 = depth(m1.getDeclaringClass());
        int d2 = depth(m2.getDeclaringClass());
        if (d1 != d2)
          return d2 - d1;
        int d = m1.getName().compareTo(m2.getName());
        if (d != 0)
          return d;
        d1 = m1.getParameterTypes().length;
        d2 = m2.getParameterTypes().length;
        return d1 - d2;
      }
      
      private int depth(Class cl)
      {
        if (cl == null) {
          return 0;
        }
        return 1 + depth(cl.getSuperclass());
      }
    });
    return methods;
  }
  

  private class MCItem
    extends JMenuItem
  {
    private MCItem() {}
    
    public String getDisplayString(Class retType, String name, Class[] paramTypes)
    {
      StringBuffer b = new StringBuffer();
      b.append("<html>");
      if (retType != null)
        appendTypeName(b, retType.getName());
      b.append(" <font color='blue'>");
      appendTypeName(b, name);
      b.append("</font>( ");
      for (int i = 0; i < paramTypes.length; i++)
      {
        if (i > 0)
          b.append(", ");
        appendTypeName(b, paramTypes[i].getName());
      }
      b.append(" )</html>");
      return b.toString();
    }
    
    public void appendTypeName(StringBuffer b, String name)
    {
      int i = name.lastIndexOf('.');
      if (i >= 0)
      {
        String prefix = name.substring(0, i + 1);
        if (!prefix.equals("java.lang"))
        {
          b.append("<font color='gray'>");
          b.append(prefix);
          b.append("</font>");
        }
        b.append(name.substring(i + 1));
      }
      else {
        b.append(name);
      }
    }
    
    public Object makeDefaultValue(Class type) {
      if (type == Integer.TYPE)
        return new Integer(0);
      if (type == Boolean.TYPE)
        return Boolean.FALSE;
      if (type == Double.TYPE)
        return new Double(0.0D);
      if (type == String.class)
        return "";
      if (type == Color.class)
        return Color.BLACK;
      if (type == Location.class)
        return currentLocation;
      if (Grid.class.isAssignableFrom(type)) {
        return currentGrid;
      }
      
      try
      {
        return type.newInstance();
      }
      catch (Exception ex) {}
      
      return null;
    }
  }
  
  private abstract class ConstructorItem extends MenuMaker.MCItem
  {
    private Constructor c;
    
    public ConstructorItem(Constructor c) {
      super(null);
      setText(getDisplayString(null, c.getDeclaringClass().getName(), c.getParameterTypes()));
      
      this.c = c;
    }
    
    public Object invokeConstructor()
    {
      Class[] types = c.getParameterTypes();
      Object[] values = new Object[types.length];
      
      for (int i = 0; i < types.length; i++)
      {
        values[i] = makeDefaultValue(types[i]);
      }
      
      if (types.length > 0)
      {
        PropertySheet sheet = new PropertySheet(types, values);
        JOptionPane.showMessageDialog(this, sheet, resources.getString("dialog.method.params"), 3);
        

        values = sheet.getValues();
      }
      
      try
      {
        return c.newInstance(values);
      }
      catch (InvocationTargetException ex)
      {
        WorldFrame tmp97_94 = parent;tmp97_94.getClass();new WorldFrame.GUIExceptionHandler(tmp97_94).handle(ex.getCause());
        return null;
      }
      catch (Exception ex)
      {
        WorldFrame tmp126_123 = parent;tmp126_123.getClass();new WorldFrame.GUIExceptionHandler(tmp126_123).handle(ex); }
      return null;
    }
  }
  


  private class OccupantConstructorItem
    extends MenuMaker.ConstructorItem
    implements ActionListener
  {
    public OccupantConstructorItem(Constructor c)
    {
      super(c);
      addActionListener(this);
      setIcon(displayMap.getIcon(c.getDeclaringClass(), 16, 16));
    }
    

    public void actionPerformed(ActionEvent event)
    {
      T result = invokeConstructor();
      parent.getWorld().add(currentLocation, result);
      parent.repaint();
    }
  }
  
  private class GridConstructorItem
    extends MenuMaker.ConstructorItem implements ActionListener
  {
    public GridConstructorItem(Constructor c)
    {
      super(c);
      addActionListener(this);
      setIcon(displayMap.getIcon(c.getDeclaringClass(), 16, 16));
    }
    

    public void actionPerformed(ActionEvent event)
    {
      Grid<T> newGrid = (Grid)invokeConstructor();
      parent.setGrid(newGrid);
    }
  }
  
  private class MethodItem extends MenuMaker.MCItem implements ActionListener {
    private Method m;
    
    public MethodItem(Method m) { super(null);
      setText(getDisplayString(m.getReturnType(), m.getName(), m.getParameterTypes()));
      
      this.m = m;
      addActionListener(this);
      setIcon(displayMap.getIcon(m.getDeclaringClass(), 16, 16));
    }
    
    public void actionPerformed(ActionEvent event)
    {
      Class[] types = m.getParameterTypes();
      Object[] values = new Object[types.length];
      
      for (int i = 0; i < types.length; i++)
      {
        values[i] = makeDefaultValue(types[i]);
      }
      
      if (types.length > 0)
      {
        PropertySheet sheet = new PropertySheet(types, values);
        JOptionPane.showMessageDialog(this, sheet, resources.getString("dialog.method.params"), 3);
        

        values = sheet.getValues();
      }
      
      try
      {
        Object result = m.invoke(occupant, values);
        parent.repaint();
        if (m.getReturnType() != Void.TYPE)
        {
          String resultString = result.toString();
          
          int MAX_LENGTH = 50;
          int MAX_HEIGHT = 10;
          Object resultObject; Object resultObject; if (resultString.length() < 50) {
            resultObject = resultString;
          }
          else {
            int rows = Math.min(10, 1 + resultString.length() / 50);
            
            JTextArea pane = new JTextArea(rows, 50);
            pane.setText(resultString);
            pane.setLineWrap(true);
            resultObject = new JScrollPane(pane);
          }
          JOptionPane.showMessageDialog(parent, resultObject, resources.getString("dialog.method.return"), 1);
        }
        

      }
      catch (InvocationTargetException ex)
      {
        WorldFrame tmp250_247 = parent;tmp250_247.getClass();new WorldFrame.GUIExceptionHandler(tmp250_247).handle(ex.getCause());
      }
      catch (Exception ex)
      {
        WorldFrame tmp282_279 = parent;tmp282_279.getClass();new WorldFrame.GUIExceptionHandler(tmp282_279).handle(ex);
      }
    }
  }
}
