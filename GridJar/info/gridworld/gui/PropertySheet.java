package info.gridworld.gui;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;





















































































































































































































































































































































































































class PropertySheet
  extends JPanel
{
  private PropertyEditor[] editors;
  private Object[] values;
  
  public PropertySheet(Class[] types, Object[] values)
  {
    this.values = values;
    editors = new PropertyEditor[types.length];
    setLayout(new FormLayout());
    for (int i = 0; i < values.length; i++)
    {
      JLabel label = new JLabel(types[i].getName());
      add(label);
      if (Grid.class.isAssignableFrom(types[i]))
      {
        label.setEnabled(false);
        add(new JPanel());
      }
      else
      {
        editors[i] = getEditor(types[i]);
        if (editors[i] != null)
        {
          editors[i].setValue(values[i]);
          add(getEditorComponent(editors[i]));
        }
        else {
          add(new JLabel("?"));
        }
      }
    }
  }
  








  public PropertyEditor getEditor(Class type)
  {
    PropertyEditor editor = (PropertyEditor)defaultEditors.get(type);
    if (editor != null)
      return editor;
    editor = PropertyEditorManager.findEditor(type);
    return editor;
  }
  






  public Component getEditorComponent(final PropertyEditor editor)
  {
    String[] tags = editor.getTags();
    String text = editor.getAsText();
    if (editor.supportsCustomEditor())
    {
      return editor.getCustomEditor();
    }
    if (tags != null)
    {

      final JComboBox comboBox = new JComboBox(tags);
      comboBox.setSelectedItem(text);
      comboBox.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent event)
        {
          if (event.getStateChange() == 1)
            editor.setAsText((String)comboBox.getSelectedItem());
        }
      });
      return comboBox;
    }
    

    final JTextField textField = new JTextField(text, 10);
    textField.getDocument().addDocumentListener(new DocumentListener()
    {
      public void insertUpdate(DocumentEvent e)
      {
        try
        {
          editor.setAsText(textField.getText());
        }
        catch (IllegalArgumentException exception) {}
      }
      


      public void removeUpdate(DocumentEvent e)
      {
        try
        {
          editor.setAsText(textField.getText());
        }
        catch (IllegalArgumentException exception) {}
      }
      




      public void changedUpdate(DocumentEvent e) {}
    });
    return textField;
  }
  

  public Object[] getValues()
  {
    for (int i = 0; i < editors.length; i++)
      if (editors[i] != null)
        values[i] = editors[i].getValue();
    return values;
  }
  


  public static class StringEditor
    extends PropertyEditorSupport
  {
    public StringEditor() {}
    

    public String getAsText()
    {
      return (String)getValue();
    }
    
    public void setAsText(String s)
    {
      setValue(s);
    }
  }
  


  private static Map<Class, PropertyEditor> defaultEditors = new HashMap();
  static { defaultEditors.put(String.class, new StringEditor());
    defaultEditors.put(Location.class, new LocationEditor());
    defaultEditors.put(Color.class, new ColorEditor());
  }
}
