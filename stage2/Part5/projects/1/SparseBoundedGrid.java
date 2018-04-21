import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import info.gridworld.grid.AbstractGrid;
import java.util.ArrayList;


public class SparseBoundedGrid <E> extends AbstractGrid<E> {
    private SparseGridNode[] rows;

    private int numRows;
    private int numCols;


    /**
     * Constructor for SparseBoundedGrid
     * @param row number of rows
     * @param col number of cols
     */
    public SparseBoundedGrid(int row, int col) {
        if (row <= 0) {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (col <= 0) {
            throw new IllegalArgumentException("cols <= 0");
        }
        numRows = row;
        numCols = col;
        rows = new SparseGridNode[row];
    }

    /**
     * Returns the number of rows in this grid.
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in this grid.
     * @return the number of columns
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Checks whether a location is valid in this grid. <br />
     * Precondition: <code>loc</code> is not <code>null</code>
     * @param loc the location to check
     * @return <code>true</code> if <code>loc</code> is valid in this grid,
     * <code>false</code> otherwise
     */
    public boolean isValid(Location loc) {
        return loc.getRow() < numRows && loc.getCol() < numCols 
            && loc.getRow() >= 0 && loc.getCol() >= 0;
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

        E oldElement = remove(loc);

        rows[loc.getRow()] = new SparseGridNode(obj, loc.getCol(), rows[loc.getRow()]);

        return oldElement;
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
        SparseGridNode temp = rows[loc.getRow()];
        SparseGridNode prev = null;

        while (temp != null) {
            if (temp.getCol() == loc.getCol()) {
                if (prev != null) {
                    prev.setNext(temp.getNext());
                }
                else {
                    rows[loc.getRow()] = temp.getNext();
                }
                return (E)temp.getOccupant();
            }
            else {
                prev = temp;
                temp = temp.getNext();
            }
        }

        return null;

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
        SparseGridNode temp = rows[loc.getRow()];

        while (temp != null) {
            if (temp.getCol() == loc.getCol()) {
                return (E)temp.getOccupant();
            }
            else {
                temp = temp.getNext();
            }
        }

        return null;
    }


    /**
     * Gets the locations in this grid that contain objects.
     * @return an array list of all occupied locations in this grid
     */
    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> result = new ArrayList<Location>();
        for (int i = 0; i < getNumRows(); i++) {
            SparseGridNode temp = rows[i];
            while (temp != null) {
                result.add(new Location(i, temp.getCol()));
                temp = temp.getNext();
            }
        }
        return result;
    }


}