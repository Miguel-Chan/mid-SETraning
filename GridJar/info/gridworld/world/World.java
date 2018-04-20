package info.gridworld.world;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.gui.WorldFrame;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFrame;


























public class World<T>
{
  private Grid<T> gr;
  private Set<String> occupantClassNames;
  private Set<String> gridClassNames;
  private String message;
  private JFrame frame;
  private static Random generator = new Random();
  
  private static final int DEFAULT_ROWS = 10;
  private static final int DEFAULT_COLS = 10;
  
  public World()
  {
    this(new BoundedGrid(10, 10));
    message = null;
  }
  
  public World(Grid<T> g)
  {
    gr = g;
    gridClassNames = new TreeSet();
    occupantClassNames = new TreeSet();
    addGridClass("info.gridworld.grid.BoundedGrid");
    addGridClass("info.gridworld.grid.UnboundedGrid");
  }
  



  public void show()
  {
    if (frame == null)
    {
      frame = new WorldFrame(this);
      frame.setVisible(true);
    }
    else {
      frame.repaint();
    }
  }
  



  public Grid<T> getGrid()
  {
    return gr;
  }
  




  public void setGrid(Grid<T> newGrid)
  {
    gr = newGrid;
    repaint();
  }
  




  public void setMessage(String newMessage)
  {
    message = newMessage;
    repaint();
  }
  




  public String getMessage()
  {
    return message;
  }
  




  public void step()
  {
    repaint();
  }
  








  public boolean locationClicked(Location loc)
  {
    return false;
  }
  










  public boolean keyPressed(String description, Location loc)
  {
    return false;
  }
  




  public Location getRandomEmptyLocation()
  {
    Grid<T> gr = getGrid();
    int rows = gr.getNumRows();
    int cols = gr.getNumCols();
    
    if ((rows > 0) && (cols > 0))
    {

      ArrayList<Location> emptyLocs = new ArrayList();
      for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
        {
          Location loc = new Location(i, j);
          if ((gr.isValid(loc)) && (gr.get(loc) == null))
            emptyLocs.add(loc);
        }
      if (emptyLocs.size() == 0)
        return null;
      int r = generator.nextInt(emptyLocs.size());
      return (Location)emptyLocs.get(r);
    }
    
    for (;;)
    {
      int r;
      
      int r;
      
      if (rows < 0) {
        r = (int)(10.0D * generator.nextGaussian());
      } else
        r = generator.nextInt(rows);
      int c;
      int c; if (cols < 0) {
        c = (int)(10.0D * generator.nextGaussian());
      } else
        c = generator.nextInt(cols);
      Location loc = new Location(r, c);
      if ((gr.isValid(loc)) && (gr.get(loc) == null)) {
        return loc;
      }
    }
  }
  





  public void add(Location loc, T occupant)
  {
    getGrid().put(loc, occupant);
    repaint();
  }
  





  public T remove(Location loc)
  {
    T r = getGrid().remove(loc);
    repaint();
    return r;
  }
  




  public void addGridClass(String className)
  {
    gridClassNames.add(className);
  }
  




  public void addOccupantClass(String className)
  {
    occupantClassNames.add(className);
  }
  





  public Set<String> getGridClasses()
  {
    return gridClassNames;
  }
  





  public Set<String> getOccupantClasses()
  {
    return occupantClassNames;
  }
  
  private void repaint()
  {
    if (frame != null) {
      frame.repaint();
    }
  }
  


  public String toString()
  {
    String s = "";
    Grid<?> gr = getGrid();
    
    int rmin = 0;
    int rmax = gr.getNumRows() - 1;
    int cmin = 0;
    int cmax = gr.getNumCols() - 1;
    if ((rmax < 0) || (cmax < 0))
    {
      for (Location loc : gr.getOccupiedLocations())
      {
        int r = loc.getRow();
        int c = loc.getCol();
        if (r < rmin)
          rmin = r;
        if (r > rmax)
          rmax = r;
        if (c < cmin)
          cmin = c;
        if (c > cmax) {
          cmax = c;
        }
      }
    }
    for (int i = rmin; i <= rmax; i++)
    {
      for (int j = cmin; j < cmax; j++)
      {
        Object obj = gr.get(new Location(i, j));
        if (obj == null) {
          s = s + " ";
        } else
          s = s + obj.toString().substring(0, 1);
      }
      s = s + "\n";
    }
    return s;
  }
}
