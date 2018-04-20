package info.gridworld.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;






































public class PseudoInfiniteViewport
  extends JViewport
{
  private JScrollPane scrollParent;
  private Point panPoint = new Point(0, 0);
  




  public PseudoInfiniteViewport(JScrollPane parent)
  {
    scrollParent = parent;
    setBackground(Color.lightGray);
  }
  





  public void setViewPosition(Point pt)
  {
    boolean isAdjusting = (scrollParent.getVerticalScrollBar().getValueIsAdjusting()) || (scrollParent.getHorizontalScrollBar().getValueIsAdjusting());
    

    boolean changed = true;
    
    if (viewIsUnbounded())
    {
      int hDelta = x - panPoint.x;
      int vDelta = y - panPoint.y;
      if ((hDelta != 0) && (vDelta == 0)) {
        getPannableView().panBy(hDelta, vDelta);
      } else if ((vDelta != 0) && (hDelta == 0)) {
        getPannableView().panBy(hDelta, vDelta);
      } else
        changed = false;
      panPoint = pt;
      if ((!panPoint.equals(getPanCenterPoint())) && (!isAdjusting))
      {
        panPoint = getPanCenterPoint();
        fireStateChanged();
      }
      
    }
    else
    {
      changed = !getViewPosition().equals(pt);
      super.setViewPosition(pt);
    }
    if ((changed) || (isAdjusting)) {
      getPannableView().showPanTip();
    }
  }
  



  public Point getViewPosition()
  {
    return viewIsUnbounded() ? getPanCenterPoint() : super.getViewPosition();
  }
  





  public Dimension getViewSize()
  {
    return viewIsUnbounded() ? getView().getPreferredSize() : super.getViewSize();
  }
  



  private Pannable getPannableView()
  {
    return (Pannable)getView();
  }
  
  private boolean viewIsUnbounded()
  {
    Pannable p = getPannableView();
    return (p != null) && (p.isPannableUnbounded());
  }
  
  private Point getPanCenterPoint()
  {
    Dimension size = getViewSize();
    return new Point(width / 2, height / 2);
  }
  
  public static abstract interface Pannable
  {
    public abstract void panBy(int paramInt1, int paramInt2);
    
    public abstract boolean isPannableUnbounded();
    
    public abstract void showPanTip();
  }
}
