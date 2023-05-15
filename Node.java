import java.util.*;

public class Node {
    private int level;
    private int count = -1;
    private boolean isFinal = true;

    private Dataset ds;
    private String classification;
    private String splitConditionAttribute;
    private String attributeValue;

    private List<Node> children  = new ArrayList<>();


    // constructor for new node created with a split condition based on a certain attribute
    Node(Dataset dataset, boolean root, String sC, String aV, int l) {
        ds = dataset;
        if (!root) {
            splitConditionAttribute = sC;
            Object test = ds.target().get(0);
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
    Node(Dataset dataset, String sC, String aV, String most_common, int l) {
        count = 0;
        isFinal = true;
        splitConditionAttribute = sC;
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

    int count() {
        return count;
    }

    int level() {
        return level;
    }

    String attributeValue() {
        return attributeValue;
    }

    boolean isFinal() {
        return isFinal;
    }
}
