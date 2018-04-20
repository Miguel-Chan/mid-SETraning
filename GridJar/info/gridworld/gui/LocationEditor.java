package info.gridworld.gui;

import info.gridworld.grid.Location;
import java.awt.Component;
import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;























public class LocationEditor
  extends PropertyEditorSupport
{
  private JFormattedTextField rowField = new JFormattedTextField(NumberFormat.getIntegerInstance());
  
  private JFormattedTextField colField = new JFormattedTextField(NumberFormat.getIntegerInstance());
  
  private JPanel panel = new JPanel();
  
  public LocationEditor()
  {
    rowField.setColumns(5);
    colField.setColumns(5);
    
    panel.add(rowField);
    panel.add(colField);
  }
  
  public Object getValue()
  {
    int row = ((Number)rowField.getValue()).intValue();
    int col = ((Number)colField.getValue()).intValue();
    return new Location(row, col);
  }
  
  public void setValue(Object newValue)
  {
    Location loc = (Location)newValue;
    rowField.setValue(new Integer(loc.getRow()));
    colField.setValue(new Integer(loc.getCol()));
  }
  
  public boolean supportsCustomEditor()
  {
    return true;
  }
  
  public Component getCustomEditor()
  {
    return panel;
  }
}
