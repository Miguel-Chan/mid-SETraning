package info.gridworld.grid;






public class Location
  implements Comparable
{
  private int row;
  




  private int col;
  




  public static final int LEFT = -90;
  




  public static final int RIGHT = 90;
  



  public static final int HALF_LEFT = -45;
  



  public static final int HALF_RIGHT = 45;
  



  public static final int FULL_CIRCLE = 360;
  



  public static final int HALF_CIRCLE = 180;
  



  public static final int AHEAD = 0;
  



  public static final int NORTH = 0;
  



  public static final int NORTHEAST = 45;
  



  public static final int EAST = 90;
  



  public static final int SOUTHEAST = 135;
  



  public static final int SOUTH = 180;
  



  public static final int SOUTHWEST = 225;
  



  public static final int WEST = 270;
  



  public static final int NORTHWEST = 315;
  




  public Location(int r, int c)
  {
    row = r;
    col = c;
  }
  




  public int getRow()
  {
    return row;
  }
  




  public int getCol()
  {
    return col;
  }
  







  public Location getAdjacentLocation(int direction)
  {
    int adjustedDirection = (direction + 22) % 360;
    if (adjustedDirection < 0) {
      adjustedDirection += 360;
    }
    adjustedDirection = adjustedDirection / 45 * 45;
    int dc = 0;
    int dr = 0;
    if (adjustedDirection == 90) {
      dc = 1;
    } else if (adjustedDirection == 135)
    {
      dc = 1;
      dr = 1;
    }
    else if (adjustedDirection == 180) {
      dr = 1;
    } else if (adjustedDirection == 225)
    {
      dc = -1;
      dr = 1;
    }
    else if (adjustedDirection == 270) {
      dc = -1;
    } else if (adjustedDirection == 315)
    {
      dc = -1;
      dr = -1;
    }
    else if (adjustedDirection == 0) {
      dr = -1;
    } else if (adjustedDirection == 45)
    {
      dc = 1;
      dr = -1;
    }
    return new Location(getRow() + dr, getCol() + dc);
  }
  







  public int getDirectionToward(Location target)
  {
    int dx = target.getCol() - getCol();
    int dy = target.getRow() - getRow();
    
    int angle = (int)Math.toDegrees(Math.atan2(-dy, dx));
    


    int compassAngle = 90 - angle;
    
    compassAngle += 22;
    
    if (compassAngle < 0) {
      compassAngle += 360;
    }
    return compassAngle / 45 * 45;
  }
  








  public boolean equals(Object other)
  {
    if (!(other instanceof Location)) {
      return false;
    }
    Location otherLoc = (Location)other;
    return (getRow() == otherLoc.getRow()) && (getCol() == otherLoc.getCol());
  }
  




  public int hashCode()
  {
    return getRow() * 3737 + getCol();
  }
  











  public int compareTo(Object other)
  {
    Location otherLoc = (Location)other;
    if (getRow() < otherLoc.getRow())
      return -1;
    if (getRow() > otherLoc.getRow())
      return 1;
    if (getCol() < otherLoc.getCol())
      return -1;
    if (getCol() > otherLoc.getCol())
      return 1;
    return 0;
  }
  





  public String toString()
  {
    return "(" + getRow() + ", " + getCol() + ")";
  }
}
