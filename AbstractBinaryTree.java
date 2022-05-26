import java.util.*;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
    
    /**Returns the Position of p's sibling (or null if no sibling exists)*/
    public Position<E> sibling(Position<E> p){
        Position<E> parent = parent(p);
        if(parent == null) return null; //p must be the root
        if(parent == left(parent)) //p is left child
            return right(parent); //(right child might be null)
        else //p is right child
            return left(parent); //(left child might be null)
    }
    /**Returns the number of children of Position p*/
    public int numChildren(Position<E> p){
        int count = 0; 
        if(left(p) != null)
            count++;
        if(right(p) != null)
            count++;
        return count;
    }
    /**Returns an iterable collection of the Positions representing p's children*/
    public Iterable<Position<E>> children(Position<E> p){
        List<Position<E>> snapshot = new ArrayList<>(2); //max capacity of 2
        if(left(p) != null)
            snapshot.add(left(p));
        if(right(p) != null)
            snapshot.add(right(p));
        return snapshot;
    }
    //------******************-------//
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot){
        if(left(p) != null)
            inorderSubtree(left(p), snapshot);
        snapshot.add(p);
        if(right(p) != null)
            inorderSubtree(right(p), snapshot);
    }
    
    public Iterable<Position<E>> inorder(){
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty())
            inorderSubtree(root(), snapshot);
        return snapshot;
    }
    
    public Iterable<Position<E>> positions(){
        return inorder();
    }
    
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot){
        for(Position<E> c : children(p))
            postorderSubtree(c, snapshot);
        snapshot.add(p);
    }
    
    public Iterable<Position<E>> postorder(){
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty())
            postorderSubtree(root(), snapshot);
        return snapshot;
    }    
    
    public Iterable<Position<E>> preorder(){
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty())
            preorderSubtree(root(), snapshot);
        return snapshot;
    }
    
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot){
        snapshot.add(p);
        for(Position<E> c : children(p))
            preorderSubtree(c, snapshot);
    }
    
}
 