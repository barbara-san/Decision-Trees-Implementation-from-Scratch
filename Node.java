import java.util.*;

public class Node {
    private int level; // how many decisions have been made to get to this node
    private int count = -1; // number of cases that fit this node, if it's final
    private boolean isFinal = true; // whether a node is final or not

    private Dataset ds; // dataset
    private String classification; // classification
    private String splitConditionAttribute; // attribute by which the split that created this node was made
    private String attributeValue; // the value of the attribute that created this node in specific

    private List<Node> children  = new ArrayList<>(); // list of child nodes, empty if the current one is final
    private Node parent;

    // constructor for new node created with a split condition based on a certain attribute
    Node(Dataset dataset, boolean root, String sCA, String aV, int l, Node p) {
        ds = dataset;
        if (!root) {
            splitConditionAttribute = sCA;
            Object test = ds.target().get(0);
            parent = p;
            //check if any class in the dataset is different
            for (Object o : ds.target()) {
                if (!test.equals(o)) {
                    isFinal = false;
                    break;
                }
            }

            if (isFinal) {count = ds.numberLines(); classification = test.toString();}
            
            attributeValue = aV;
            level = l;
        }
        else {
            isFinal = false;
            level = 0;
        }
    }

    // constructor for new node created by Plurarity Value
    Node(Dataset dataset, String sCA, String aV, String most_common, int l, Node p) {
        count = 0;
        isFinal = true;
        splitConditionAttribute = sCA;
        attributeValue = aV;
        classification = most_common;
        level = l;
        parent = p;
    }

    //adds child node
    void addChild(Node r) {
        children.add(r);
    }

    //getters
    int level() {return level;}
    int count() {return count;}
    boolean isFinal() {return isFinal;}
    
    Dataset ds() {return ds;}
    
    String classification() {return classification;}
    String splitConditionAttribute() {return splitConditionAttribute;}
    String attributeValue() {return attributeValue;}
    
    List<Node> children() {return children;}
    Node parent() {return parent;}
}
