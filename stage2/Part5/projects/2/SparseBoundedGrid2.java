import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import info.gridworld.grid.AbstractGrid;
import java.util.ArrayList;
import java.util.*;


/**
* Rewrite SparseBounded with TreeMap,
* methods getOccupiedLocations, remove, put, get in UnboundedGrid
* can be used without change.
*/

public class SparseBoundedGrid2 <E> extends MapBoundedGridBase<E> {

    /**
     * Constructor for SparseBoundedGrid
     * @param row number of rows
     * @param col number of cols
     */
    public SparseBoundedGrid2(int row, int col) {
        super(row, col);
        setOccupantMap(new TreeMap<Location, E>());
    }
}