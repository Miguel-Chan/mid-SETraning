


/**
* Raw linked list node to help implement the linked list
*/
public class SparseGridNode {
    private Object occupant;
    private int col;
    private SparseGridNode next;


    public SparseGridNode(Object ele, int column, SparseGridNode ne) {
        col = column;
        occupant = ele;
        next = ne;
    }

    public int getCol() {
        return col;
    }

    public SparseGridNode getNext() {
        return next;
    }

    public Object getOccupant() {
        return occupant;
    }

    public void setNext(SparseGridNode ne) {
        next = ne;
    }

    public void setOccupant(Object newEle) {
        occupant = newEle;
    }
}