import java.util.*;

public class Node {
    private int level;
    private Dataset ds;
    private String splitCondition;
    private String classification;
    private String attributeValue;
    private int count = -1;
    private boolean isFinal = true;
    private List<Node> children  = new ArrayList<>();


    // constructor for new node created with a split condition based on a certain attribute
    Node(Dataset dataset, boolean root, String sC, String aV, int l) {
        ds = dataset;
        if (!root) {
            splitCondition = sC;
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
        isFinal = true;
        splitCondition = sC;
        count = 0;
        classification = most_common;
        attributeValue = aV;
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

    String getSplitConditionAttribute() {
        return splitCondition;
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
