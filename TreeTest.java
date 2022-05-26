import java.util.List;
import java.util.Arrays;

public class TreeTest {
    public static void main(String[] args){
        
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        List<Integer> numbersList = Arrays.asList(5, 4, 20, 15, 3, 1, 50, 7, 9);
        for(Integer number: numbersList){
            tree.add(number);
        }
        searchFound(tree, 7);
        searchFound(tree, 100);
    }
    
    private static void searchFound(LinkedBinaryTree<Integer> tree, Integer integer){ 
        System.out.println("Found " + integer + " in the tree ? : " + tree.find(integer));
    }
}
