import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import info.gridworld.grid.AbstractGrid;
import java.util.ArrayList;
import java.util.*;


/**
* an implementation of an unbounded grid in which all valid
* locations have non-negative row and column values. 
*/

public class UnboundedGrid2 <E> extends AbstractGrid<E> {
    private Object[][] occupantArray; 

    private int numRows;
    private int numCols;

    /**
     * Constructor for SparseBoundedGrid
     * @param row number of rows
     * @param col number of cols
     */
    public UnboundedGrid2() {
        numRows = 16;
        numCols = 16;
        occupantArray = new Object[numRows][numCols];
    }

    /**
     * Returns the number of rows in this grid.
     * @return the number of rows
     */
    public int getNumRows() {
        return -1;
    }

    /**
     * Returns the number of columns in this grid.
     * @return the number of columns
     */
    public int getNumCols() {
        return -1;
    }

    /**
     * Checks whether a location is valid in this grid. <br />
     * Precondition: <code>loc</code> is not <code>null</code>
     * @param loc the location to check
     * @return <code>true</code> if <code>loc</code> is valid in this grid,
     * <code>false</code> otherwise
     */
    public boolean isValid(Location loc) {

        return loc.getRow() >= 0 && loc.getCol() >= 0;
    }


    /**
    * expand the current array, double numRows and numCols
    */
    private void expand() {
        Object[][] newArray = new Object[numRows * 2][numCols * 2];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                newArray[i][j] = occupantArray[i][j];
            }
        }
        numRows *= 2;
        numCols *= 2;
        occupantArray = newArray;
    }


    /**
     * Puts an object at a given location in this grid. <br />
     * Precondition: (1) <code>loc</code> is valid in this grid (2)
     * <code>obj</code> is not <code>null</code>
     * @param loc the location at which to put the object
     * @param obj the new object to be added
     * @return the object previously at <code>loc</code> (or <code>null</code>
     * if the location was previously unoccupied)
     */
    public E put(Location loc, E obj) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }

        while (loc.getRow() >= numRows || loc.getCol() >= numCols) {
            expand();
        }

        E oldOccupant = (E)occupantArray[loc.getRow()][loc.getCol()];
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }

    /**
     * Removes the object at a given location from this grid. <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc the location of the object that is to be removed
     * @return the object that was removed (or <code>null<code> if the location
     *  is unoccupied)
     */
    public E remove(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (loc.getRow() >= numRows || loc.getRow() < 0 ||
            loc.getCol() >= numCols || loc.getCol() < 0) {
            return null;
        }
        E oldOccupant = (E)occupantArray[loc.getRow()][loc.getCol()];
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return oldOccupant;
    }

    /**
     * Returns the object at a given location in this grid. <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc a location in this grid
     * @return the object at location <code>loc</code> (or <code>null<code> 
     *  if the location is unoccupied)
     */
    public E get(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }

        if (loc.getRow() >= numRows || loc.getRow() < 0 ||
            loc.getCol() >= numCols || loc.getCol() < 0) {
            return null;
        }
        return (E)occupantArray[loc.getRow()][loc.getCol()];
    }


    /**
     * Gets the locations in this grid that contain objects.
     * @return an array list of all occupied locations in this grid
     */
    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> a = new ArrayList<Location>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (occupantArray[i][j] != null) {
                    a.add(new Location(i, j));
                }
            }
        }
        return a;
    }


}