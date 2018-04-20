package info.gridworld.gui;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;




























public class WorldFrame<T>
  extends JFrame
{
  private GUIController<T> control;
  private GridPanel display;
  private JTextArea messageArea;
  private ArrayList<JMenuItem> menuItemsDisabledDuringRun;
  private World<T> world;
  private ResourceBundle resources;
  private DisplayMap displayMap;
  private Set<Class> gridClasses;
  private JMenu newGridMenu;
  private static int count = 0;
  




  public WorldFrame(World<T> world)
  {
    this.world = world;
    count += 1;
    resources = ResourceBundle.getBundle(getClass().getName() + "Resources");
    

    try
    {
      System.setProperty("sun.awt.exception.handler", GUIExceptionHandler.class.getName());
    }
    catch (SecurityException ex) {}
    




    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent event)
      {
        WorldFrame.access$010();
        if (WorldFrame.count == 0) {
          System.exit(0);
        }
      }
    });
    displayMap = new DisplayMap();
    String title = System.getProperty("info.gridworld.gui.frametitle");
    if (title == null) title = resources.getString("frame.title");
    setTitle(title);
    setLocation(25, 15);
    
    URL appIconUrl = getClass().getResource("GridWorld.gif");
    ImageIcon appIcon = new ImageIcon(appIconUrl);
    setIconImage(appIcon.getImage());
    
    makeMenus();
    
    JPanel content = new JPanel();
    content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    content.setLayout(new BorderLayout());
    setContentPane(content);
    
    display = new GridPanel(displayMap, resources);
    
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
    {

      public boolean dispatchKeyEvent(KeyEvent event)
      {
        if (getFocusOwner() == null) return false;
        String text = KeyStroke.getKeyStrokeForEvent(event).toString();
        String PRESSED = "pressed ";
        int n = text.indexOf("pressed ");
        if (n < 0) { return false;
        }
        if ((event.getKeyChar() == 65535) && (!event.isActionKey()))
          return false;
        text = text.substring(0, n) + text.substring(n + "pressed ".length());
        boolean consumed = getWorld().keyPressed(text, display.getCurrentLocation());
        if (consumed) repaint();
        return consumed;
      }
      
    });
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewport(new PseudoInfiniteViewport(scrollPane));
    scrollPane.setViewportView(display);
    content.add(scrollPane, "Center");
    
    gridClasses = new TreeSet(new Comparator()
    {
      public int compare(Class a, Class b)
      {
        return a.getName().compareTo(b.getName());
      }
    });
    for (String name : world.getGridClasses()) {
      try
      {
        gridClasses.add(Class.forName(name));
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    Grid<T> gr = world.getGrid();
    gridClasses.add(gr.getClass());
    
    makeNewGridMenu();
    
    control = new GUIController(this, display, displayMap, resources);
    content.add(control.controlPanel(), "South");
    
    messageArea = new JTextArea(2, 35);
    messageArea.setEditable(false);
    messageArea.setFocusable(false);
    messageArea.setBackground(new Color(16448210));
    content.add(new JScrollPane(messageArea), "North");
    
    pack();
    repaint();
    display.setGrid(gr);
  }
  
  public void repaint()
  {
    String message = getWorld().getMessage();
    if (message == null)
      message = resources.getString("message.default");
    messageArea.setText(message);
    messageArea.repaint();
    display.repaint();
    super.repaint();
  }
  




  public World<T> getWorld()
  {
    return world;
  }
  





  public void setGrid(Grid<T> newGrid)
  {
    Grid<T> oldGrid = world.getGrid();
    Map<Location, T> occupants = new HashMap();
    for (Location loc : oldGrid.getOccupiedLocations()) {
      occupants.put(loc, world.remove(loc));
    }
    world.setGrid(newGrid);
    for (Location loc : occupants.keySet())
    {
      if (newGrid.isValid(loc)) {
        world.add(loc, occupants.get(loc));
      }
    }
    display.setGrid(newGrid);
    repaint();
  }
  



  public void showError(Throwable t, String resource)
  {
    String text;
    


    try
    {
      text = resources.getString(resource + ".text");
    }
    catch (MissingResourceException e)
    {
      text = resources.getString("error.text");
    }
    
    String title;
    try
    {
      title = resources.getString(resource + ".title");
    }
    catch (MissingResourceException e)
    {
      title = resources.getString("error.title");
    }
    
    String reason = resources.getString("error.reason");
    String message = text + "\n" + MessageFormat.format(reason, new Object[] { t });
    


    JOptionPane.showMessageDialog(this, message, title, 0);
  }
  



  private JMenu makeMenu(String resource)
  {
    JMenu menu = new JMenu();
    configureAbstractButton(menu, resource);
    return menu;
  }
  
  private JMenuItem makeMenuItem(String resource, ActionListener listener)
  {
    JMenuItem item = new JMenuItem();
    configureMenuItem(item, resource, listener);
    return item;
  }
  

  private void configureMenuItem(JMenuItem item, String resource, ActionListener listener)
  {
    configureAbstractButton(item, resource);
    item.addActionListener(listener);
    try
    {
      String accel = resources.getString(resource + ".accel");
      String metaPrefix = "@";
      if (accel.startsWith(metaPrefix))
      {
        int menuMask = getToolkit().getMenuShortcutKeyMask();
        KeyStroke key = KeyStroke.getKeyStroke(KeyStroke.getKeyStroke(accel.substring(metaPrefix.length())).getKeyCode(), menuMask);
        

        item.setAccelerator(key);
      }
      else
      {
        item.setAccelerator(KeyStroke.getKeyStroke(accel));
      }
    }
    catch (MissingResourceException ex) {}
  }
  



  private void configureAbstractButton(AbstractButton button, String resource)
  {
    String title = resources.getString(resource);
    int i = title.indexOf('&');
    int mnemonic = 0;
    if (i >= 0)
    {
      mnemonic = title.charAt(i + 1);
      title = title.substring(0, i) + title.substring(i + 1);
      button.setText(title);
      button.setMnemonic(Character.toUpperCase(mnemonic));
      button.setDisplayedMnemonicIndex(i);
    }
    else {
      button.setText(title);
    }
  }
  
  private void makeMenus() {
    JMenuBar mbar = new JMenuBar();
    

    menuItemsDisabledDuringRun = new ArrayList();
    JMenu menu;
    mbar.add(menu = makeMenu("menu.file"));
    
    newGridMenu = makeMenu("menu.file.new");
    menu.add(newGridMenu);
    menuItemsDisabledDuringRun.add(newGridMenu);
    
    menu.add(makeMenuItem("menu.file.quit", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
      
    }));
    mbar.add(menu = makeMenu("menu.view"));
    
    menu.add(makeMenuItem("menu.view.up", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        display.moveLocation(-1, 0);
      }
    }));
    menu.add(makeMenuItem("menu.view.down", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        display.moveLocation(1, 0);
      }
    }));
    menu.add(makeMenuItem("menu.view.left", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        display.moveLocation(0, -1);
      }
    }));
    menu.add(makeMenuItem("menu.view.right", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        display.moveLocation(0, 1);
      }
    }));
    
    JMenuItem viewEditMenu;
    menu.add(viewEditMenu = makeMenuItem("menu.view.edit", new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        control.editLocation();
      }
    }));
    menuItemsDisabledDuringRun.add(viewEditMenu);
    
    JMenuItem viewDeleteMenu;
    menu.add(viewDeleteMenu = makeMenuItem("menu.view.delete", new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        control.deleteLocation();
      }
    }));
    menuItemsDisabledDuringRun.add(viewDeleteMenu);
    
    menu.add(makeMenuItem("menu.view.zoomin", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        display.zoomIn();
      }
      
    }));
    menu.add(makeMenuItem("menu.view.zoomout", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        display.zoomOut();
      }
      
    }));
    mbar.add(menu = makeMenu("menu.help"));
    menu.add(makeMenuItem("menu.help.about", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        WorldFrame.this.showAboutPanel();
      }
    }));
    menu.add(makeMenuItem("menu.help.help", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        WorldFrame.this.showHelp();
      }
    }));
    menu.add(makeMenuItem("menu.help.license", new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        WorldFrame.this.showLicense();
      }
      
    }));
    setRunMenuItemsEnabled(true);
    setJMenuBar(mbar);
  }
  
  private void makeNewGridMenu()
  {
    newGridMenu.removeAll();
    MenuMaker<T> maker = new MenuMaker(this, resources, displayMap);
    maker.addConstructors(newGridMenu, gridClasses);
  }
  





  public void setRunMenuItemsEnabled(boolean enable)
  {
    for (JMenuItem item : menuItemsDisabledDuringRun) {
      item.setEnabled(enable);
    }
  }
  


  private void showAboutPanel()
  {
    String html = MessageFormat.format(resources.getString("dialog.about.text"), new Object[] { resources.getString("version.id") });
    

    String[] props = { "java.version", "java.vendor", "java.home", "os.name", "os.arch", "os.version", "user.name", "user.home", "user.dir" };
    html = html + "<table border='1'>";
    for (String prop : props)
    {
      try
      {
        String value = System.getProperty(prop);
        html = html + "<tr><td>" + prop + "</td><td>" + value + "</td></tr>";
      }
      catch (SecurityException ex) {}
    }
    


    html = html + "</table>";
    html = "<html>" + html + "</html>";
    JOptionPane.showMessageDialog(this, new JLabel(html), resources.getString("dialog.about.title"), 1);
  }
  






  private void showHelp()
  {
    JDialog dialog = new JDialog(this, resources.getString("dialog.help.title"));
    
    final JEditorPane helpText = new JEditorPane();
    try
    {
      URL url = getClass().getResource("GridWorldHelp.html");
      
      helpText.setPage(url);
    }
    catch (Exception e)
    {
      helpText.setText(resources.getString("dialog.help.error"));
    }
    helpText.setEditable(false);
    helpText.addHyperlinkListener(new HyperlinkListener()
    {
      public void hyperlinkUpdate(HyperlinkEvent ev)
      {
        if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
          try
          {
            helpText.setPage(ev.getURL());

          }
          catch (Exception ex) {}
        }
      }
    });
    JScrollPane sp = new JScrollPane(helpText);
    sp.setPreferredSize(new Dimension(650, 500));
    dialog.getContentPane().add(sp);
    dialog.setLocation(getX() + getWidth() - 200, getY() + 50);
    dialog.pack();
    dialog.setVisible(true);
  }
  



  private void showLicense()
  {
    JDialog dialog = new JDialog(this, resources.getString("dialog.license.title"));
    
    JEditorPane text = new JEditorPane();
    try
    {
      URL url = getClass().getResource("GNULicense.txt");
      
      text.setPage(url);
    }
    catch (Exception e)
    {
      text.setText(resources.getString("dialog.license.error"));
    }
    text.setEditable(false);
    JScrollPane sp = new JScrollPane(text);
    sp.setPreferredSize(new Dimension(650, 500));
    dialog.getContentPane().add(sp);
    dialog.setLocation(getX() + getWidth() - 200, getY() + 50);
    dialog.pack();
    dialog.setVisible(true);
  }
  


  public class GUIExceptionHandler
  {
    public GUIExceptionHandler() {}
    

    public void handle(Throwable e)
    {
      e.printStackTrace();
      
      JTextArea area = new JTextArea(10, 40);
      StringWriter writer = new StringWriter();
      e.printStackTrace(new PrintWriter(writer));
      area.setText(writer.toString());
      area.setCaretPosition(0);
      String copyOption = resources.getString("dialog.error.copy");
      JOptionPane pane = new JOptionPane(new JScrollPane(area), 0, 0, null, new String[] { copyOption, resources.getString("cancel") });
      


      pane.createDialog(WorldFrame.this, e.toString()).setVisible(true);
      if (copyOption.equals(pane.getValue()))
      {
        area.setSelectionStart(0);
        area.setSelectionEnd(area.getText().length());
        area.copy();
      }
    }
  }
}
