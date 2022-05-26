import java.util.*;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {
    
    //--------NESTED NODE CLASS-----------
    protected static class Node<E> implements Position<E>{
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        /**Constructs a node with the given element and neighbors*/
        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild){
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }
        //accessor methods
        public E getElement() {return element; }
        public Node<E> getParent() { return parent; }
        public Node<E> getLeft() { return left; }
        public Node<E> getRight() { return right; }
        //update methods
        public void setElement(E e) { element = e; }
        public void setParent(Node<E> parentNode) { parent = parentNode; }
        public void setLeft(Node<E> leftChild) { left = leftChild; }
        public void setRight(Node<E> rightChild) { right = rightChild; }
    } //------ END OF NESTED NODE CLASS
    
    /**Factory function to create a new node storing element e*/
    protected Node<E> createNode(E e,  Node<E> parent, Node<E> left, Node<E> right){
        return new Node<E>(e, parent, left, right);
    }
    
    //LinkedBinaryTree instance variables
    protected Node<E> root = null; //root of the tree
    private int size = 0; //number of nodes in the tree
    
    //constructor 
    public LinkedBinaryTree() {} // constructs an empty binary tree
    //nonnpublic utility
    /**Validates the position and returns it as a node*/
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if(!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; //safe cast
        if(node.getParent() == node)
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }
    
    //accessor methods (not already implemented in AbstractBinaryTree)
    /**Returns the number of nodes in the tree*/
    public int size(){
        return size;
    }
    
    /**Returns the Position of the tree (or null if tree is empty)*/
    public Position<E> root(){ 
        return root;
    }
    
    /**Returns the Position of p's parent(or null if p is root)*/
    public Position<E> parent(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getParent();
    }
    
    /**Returns the Position of p's left child (or null if no child exists)*/
    public Position<E> left(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getLeft();
    }
    
    /**Returns the Position of p's right child (or null if no child exists)*/
    public Position<E> right(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getRight();
    }
    
    // update methods supported by this class
    /**Places element e at the root of an empty tree and returns its new position*/
    public Position<E> addRoot(E e) throws IllegalStateException{
        if(!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }
    
    /**Creates a new left child of Position p storing element e; returns its position*/
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if(parent.getLeft() != null)
            throw new IllegalArgumentException("p already has a left child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }
    
    /**Creates a new right child of Position p storing element e; returns its position*/
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if(parent.getRight() != null)
            throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }    
    
    /**Replaces the element at Position p with e and returns the replaced element*/
    public E set(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }
    
    /**Attaches trees t1 and t2 as left and right subtrees of external p*/
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException{
        Node<E> node = validate(p);
        if(isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
        size+= t1.size() + t2.size();
        if(!t1.isEmpty()){
            t1.root.setParent(node); //attach t1 as left subtree of node
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if(!t2.isEmpty()){
            t2.root.setParent(node); //attach t2 as right subtree of node
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }
    
    /**Removes the node as Position p and replaces it with child, if any*/
    public E remove(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        if(numChildren(p) == 2)
            throw new IllegalArgumentException("p has two children");
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if(child != null)
            child.setParent(node.getParent());
        if(node == root)
            root = child; //child becomes root
        else{
            Node<E> parent = node.getParent();
            if(node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }
    
    public Iterable<Position<E>> positions(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Iterator<E> iterator(){
        throw new UnsupportedOperationException("Not supported yet.");        
    }
    
    public void add(E e){
        if(isEmpty()) addRoot(e);
        else {//if((Integer) e < )
           //implement recursive method 
           addElement(e);
        }
    }
    
    public void addElement (E element) {
        Comparable<E> comparableElement = (Comparable<E>)element;
        Node<E> current = root;
        boolean added = false;
        while (!added) {
           if (comparableElement.compareTo(current.element) < 0){
              if (current.left == null) {
                 addLeft(current, element);
                 added = true;
              } else{
                 current = current.left;
              }
           }else{ // else of comparableElement.compareTo(current.element) < 0
              if (current.right == null) {
                 addRight(current, element);
                 added = true;
              } else{
                 current = current.right;
              }
           }
        }//while
   }  // method addElement

    public boolean find(E e){
        boolean found = false;
        if(!isEmpty()){
            if (null != findElement(e)){
            found = true;
            }
        }
        return found;
    }
    
    private E findElement(E e){
        Node<E> node = findRecursive(e, root);
        E element = null;
        if (node != null){
            element = node.element;
        }
        return element;
    }
	
    private Node<E> findRecursive(E targetElement, Node<E> next){
      if (next == null) { 
          return null; 
      }
      
      if (next.element.equals(targetElement)) { 
          return next; 
      }

      Node<E> temp = findRecursive(targetElement, next.left);

      if (temp == null) { 
          temp = findRecursive(targetElement, next.right); 
      }

      return temp;
   }
    
} //-------End of LinkedBinaryTree class--------
    
