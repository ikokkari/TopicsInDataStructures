public class DLNode<E> {

    private E key;

    private DLNode<E> next, prev;

    /**
     * Default constructor of DLNode. Makes this node to be its own successor and predecessor.
     */
    public DLNode() {
        this.prev = this.next = this;
    }

    /**
     * Constructor to create a node with the given key.
     * @param key The key stored in this node.
     */
    public DLNode(E key) {
        this.key = key;
    }

    /**
     * Constructor to create a node with the given key, predecessor and successor.
     * @param key The key stored in this node.
     * @param prev The predecessor node of this node.
     * @param next The successor node of this node.
     */
    public DLNode(E key, DLNode<E> prev, DLNode<E> next) {
        this.key = key;
        this.next = next;
        this.prev = prev;
    }

    /**
     * Return the key stored in this node.
     * @return The key stored in this node.
     */
    public E getKey() { return this.key; }

    /**
     * Set the key stored in this node.
     * @param key The new value of the key stored in this node.
     */
    public void setKey(E key) { this.key = key; }

    /**
     * Return the reference to the predecessor node of this node.
     * @return The predecessor node of this node.
     */
    public DLNode<E> getPrevious() { return this.prev; }

    /**
     * Return the reference to the successor node of this node.
     * @return The successor node of this node.
     */
    public DLNode<E> getNext() { return this.next; }

    /**
     * Returns a String representation of this node, in the form {key}.
     * @return The String representation of this node.
     */
    @Override public String toString() { return "{" + key + "}"; }

    /**
     * Unlinks this node from its predecessor and successor.
     * @return This node that was unlinked.
     */
    public DLNode<E> unlink() {
        this.prev.next = this.next;
        this.next.prev = this.prev;
        return this;
    }
    /**
     * Links the given node to become the new successor of this node.
     * @param newSuccessor The node to be made the successor of this mode.
     * @return The new node that was linked.
     */
    public DLNode<E> linkSuccessor(DLNode<E> newSuccessor) {
        newSuccessor.prev = this;
        newSuccessor.next = this.next;
        return this.next = this.next.prev = newSuccessor;
    }

    /**
     * Create a new node with the given key and link it to become successor of this node.
     * @param key The key of the new node.
     * @return The newly created successor node.
     */
    public DLNode<E> insertSuccessor(E key) {
        return this.linkSuccessor(new DLNode<E>(key));
    }

    /**
     * Links the given node to become the new predecessor of this node.
     * @param newPredecessor The node to be made the predecessor of this mode.
     * @return The new node that was linked.
     */
    public DLNode<E> linkPredecessor(DLNode<E> newPredecessor) {
        newPredecessor.next = this;
        newPredecessor.prev = this.prev;
        return this.prev = this.prev.next = newPredecessor;
    }

    /**
     * Create a new node with the given key and link it to become predecessor of this node.
     * @param key The key of the new node.
     * @return The newly created predecessor node.
     */
    public DLNode<E> insertPredecessor(E key) {
        return this.linkPredecessor(new DLNode<E>(key));
    }
}