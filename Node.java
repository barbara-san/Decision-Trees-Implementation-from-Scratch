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


    // constructor for new node created with a split condition based on a certain attribute
    Node(Dataset dataset, boolean root, String sCA, String aV, int l) {
        ds = dataset;
        if (!root) {
            splitConditionAttribute = sCA;
            Object test = ds.target().get(0);

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
    Node(Dataset dataset, String sCA, String aV, String most_common, int l) {
        count = 0;
        isFinal = true;
        splitConditionAttribute = sCA;
        attributeValue = aV;
        classification = most_common;
        level = l;
    }

    //adds child node
    void addChild(Node r) {
        children.add(r);
    }

    //getters
    
    List<Node> children() {
        return children;
    }

    Dataset ds() {
        return ds;
    }

    String classification() {
        return classification;
    }

    String splitConditionAttribute() {
        return splitConditionAttribute;
    }

    String attributeValue() {
        return attributeValue;
    }

    int count() {
        return count;
    }

    int level() {
        return level;
    }

    boolean isFinal() {
        return isFinal;
    }
}
