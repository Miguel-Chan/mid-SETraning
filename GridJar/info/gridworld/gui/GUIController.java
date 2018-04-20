package info.gridworld.gui;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;























public class GUIController<T>
{
  public static final int INDEFINITE = 0;
  public static final int FIXED_STEPS = 1;
  public static final int PROMPT_STEPS = 2;
  private static final int MIN_DELAY_MSECS = 10;
  private static final int MAX_DELAY_MSECS = 1000;
  private static final int INITIAL_DELAY = 505;
  private Timer timer;
  private JButton stepButton;
  private JButton runButton;
  private JButton stopButton;
  private JComponent controlPanel;
  private GridPanel display;
  private WorldFrame<T> parentFrame;
  private int numStepsToRun;
  private int numStepsSoFar;
  private ResourceBundle resources;
  private DisplayMap displayMap;
  private boolean running;
  private Set<Class> occupantClasses;
  
  public GUIController(WorldFrame<T> parent, GridPanel disp, DisplayMap displayMap, ResourceBundle res)
  {
    resources = res;
    display = disp;
    parentFrame = parent;
    this.displayMap = displayMap;
    makeControls();
    
    occupantClasses = new TreeSet(new Comparator()
    {
      public int compare(Class a, Class b)
      {
        return a.getName().compareTo(b.getName());
      }
      
    });
    World<T> world = parentFrame.getWorld();
    Grid<T> gr = world.getGrid();
    for (Location loc : gr.getOccupiedLocations())
      addOccupant(gr.get(loc));
    for (String name : world.getOccupantClasses()) {
      try
      {
        occupantClasses.add(Class.forName(name));
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    timer = new Timer(505, new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        step();
      }
      
    });
    display.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent evt)
      {
        Grid<T> gr = parentFrame.getWorld().getGrid();
        Location loc = display.locationForPoint(evt.getPoint());
        if ((loc != null) && (gr.isValid(loc)) && (!isRunning()))
        {
          display.setCurrentLocation(loc);
          GUIController.this.locationClicked();
        }
      }
    });
    stop();
  }
  



  public void step()
  {
    parentFrame.getWorld().step();
    parentFrame.repaint();
    if (++numStepsSoFar == numStepsToRun)
      stop();
    Grid<T> gr = parentFrame.getWorld().getGrid();
    
    for (Location loc : gr.getOccupiedLocations()) {
      addOccupant(gr.get(loc));
    }
  }
  
  private void addOccupant(T occupant) {
    Class cl = occupant.getClass();
    do
    {
      if ((cl.getModifiers() & 0x400) == 0)
        occupantClasses.add(cl);
      cl = cl.getSuperclass();
    }
    while (cl != Object.class);
  }
  






  public void run()
  {
    display.setToolTipsEnabled(false);
    parentFrame.setRunMenuItemsEnabled(false);
    stopButton.setEnabled(true);
    stepButton.setEnabled(false);
    runButton.setEnabled(false);
    numStepsSoFar = 0;
    timer.start();
    running = true;
  }
  



  public void stop()
  {
    display.setToolTipsEnabled(true);
    parentFrame.setRunMenuItemsEnabled(true);
    timer.stop();
    stopButton.setEnabled(false);
    runButton.setEnabled(true);
    stepButton.setEnabled(true);
    running = false;
  }
  
  public boolean isRunning()
  {
    return running;
  }
  




  private void makeControls()
  {
    controlPanel = new JPanel();
    stepButton = new JButton(resources.getString("button.gui.step"));
    runButton = new JButton(resources.getString("button.gui.run"));
    stopButton = new JButton(resources.getString("button.gui.stop"));
    
    controlPanel.setLayout(new BoxLayout(controlPanel, 0));
    controlPanel.setBorder(BorderFactory.createEtchedBorder());
    
    Dimension spacer = new Dimension(5, stepButton.getPreferredSize().height + 10);
    
    controlPanel.add(Box.createRigidArea(spacer));
    
    controlPanel.add(stepButton);
    controlPanel.add(Box.createRigidArea(spacer));
    controlPanel.add(runButton);
    controlPanel.add(Box.createRigidArea(spacer));
    controlPanel.add(stopButton);
    runButton.setEnabled(false);
    stepButton.setEnabled(false);
    stopButton.setEnabled(false);
    
    controlPanel.add(Box.createRigidArea(spacer));
    controlPanel.add(new JLabel(resources.getString("slider.gui.slow")));
    JSlider speedSlider = new JSlider(10, 1000, 505);
    
    speedSlider.setInverted(true);
    speedSlider.setPreferredSize(new Dimension(100, getPreferredSizeheight));
    
    speedSlider.setMaximumSize(speedSlider.getPreferredSize());
    


    InputMap map = speedSlider.getInputMap();
    while (map != null)
    {
      map.remove(KeyStroke.getKeyStroke("control PAGE_UP"));
      map.remove(KeyStroke.getKeyStroke("control PAGE_DOWN"));
      map = map.getParent();
    }
    
    controlPanel.add(speedSlider);
    controlPanel.add(new JLabel(resources.getString("slider.gui.fast")));
    controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    
    stepButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        step();
      }
    });
    runButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        run();
      }
    });
    stopButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        stop();
      }
    });
    speedSlider.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent evt)
      {
        timer.setDelay(((JSlider)evt.getSource()).getValue());
      }
    });
  }
  




  public JComponent controlPanel()
  {
    return controlPanel;
  }
  



  private void locationClicked()
  {
    World<T> world = parentFrame.getWorld();
    Location loc = display.getCurrentLocation();
    if ((loc != null) && (!world.locationClicked(loc)))
      editLocation();
    parentFrame.repaint();
  }
  




  public void editLocation()
  {
    World<T> world = parentFrame.getWorld();
    
    Location loc = display.getCurrentLocation();
    if (loc != null)
    {
      T occupant = world.getGrid().get(loc);
      if (occupant == null)
      {
        MenuMaker<T> maker = new MenuMaker(parentFrame, resources, displayMap);
        
        JPopupMenu popup = maker.makeConstructorMenu(occupantClasses, loc);
        
        Point p = display.pointForLocation(loc);
        popup.show(display, x, y);
      }
      else
      {
        MenuMaker<T> maker = new MenuMaker(parentFrame, resources, displayMap);
        
        JPopupMenu popup = maker.makeMethodMenu(occupant, loc);
        Point p = display.pointForLocation(loc);
        popup.show(display, x, y);
      }
    }
    parentFrame.repaint();
  }
  




  public void deleteLocation()
  {
    World<T> world = parentFrame.getWorld();
    Location loc = display.getCurrentLocation();
    if (loc != null)
    {
      world.remove(loc);
      parentFrame.repaint();
    }
  }
}
