import java.util.*;

public class DecisionTree {

    Node root; // first node, root

    // constructor based on a dataset
    DecisionTree(Dataset ds){
        root = new Node(ds, true, null, null, 0);
    }

    // function used to fill our decision tree, given a node (that will get children during the run of the function) and the respective dataset
    public Node fit(Node r, Dataset ds) {
        if (r.isFinal()) return r;
        else {
            // get best gain value and the index of its attribute
            double avalue = -1; int aindex = 0;
            for (int i = 0; i < ds.numberCols(); i++) {
                double ent = Entropy.getGain(ds, i);
                if (avalue < ent){
                    avalue = ent;
                    aindex = i;
                }
            }

            //split the dataset
            List<Dataset> split = ds.split(ds, aindex);

            // add children
            int i = 0;
            for (Dataset ds2 : split) {
                Node r2;
                // if the child node has examples
                if (ds2.numberLines() > 0 && ds2.numberCols() >= 0) r2 = new Node(ds2, false, ds.attribute(aindex), ds.getOptions(aindex).get(i).toString(), r.level()+1);
                
                // if the child node does not have examples
                else {
                    String most_common = ds.plurarityValue(ds);
                    r2 = new Node(ds2, ds.attribute(aindex), ds.getOptions(aindex).get(i).toString(), most_common, r.level() + 1);
                }
                Node subtree = fit(r2, ds2);
                r.addChild(subtree);
                i++;
            }
        return r;
        }
    }

    // function used to predict the class of an example
    private String predict(List<String> line) {
        Node r = root;
        while (!r.isFinal()) {
            for (Node r2 : r.children()) {
                int i = r.ds().getAttributes().indexOf(r2.splitConditionAttribute());
                if (r2.attributeValue().equals(line.get(i+1))) {
                    if (r.isFinal()) return r.classification();
                    r = r2;
                    line.remove(i+1);
                    break;
                }
            }
        }
        return r.classification();
    }

    // function used to predict the class of the examples in a new dataset, iterating through all lines
    public List<String> predict(Dataset new_lines) {
        List<String> prediction = new ArrayList<>();
        for (int i = 1; i < new_lines.numberLines() + 1; i++) {
            prediction.add(predict(new_lines.csv().get(i)));
        }
        return prediction;
    }

    // prints the Decision Tree
    public void printDT() {
        Stack<Node> s = new Stack<Node>();
        s.add(root);
        boolean isRoot = true;
        int count = 0;
        while (!s.isEmpty()) {
            Node r = s.pop();

            for (Node son : r.children()) {if (son != null) s.add(son);}
            if (isRoot) {isRoot = false; continue;}

            String space = new String(new char[r.level()-1]).replace("\0", "\t");
            System.out.println(space + r.splitConditionAttribute() + ": " + r.attributeValue());

            if (r.isFinal()) System.out.println(space + "class = " + r.classification() + "; count = " + r.count());
            if (r.isFinal()) count += r.count();

            System.out.println(' ');
        }
        System.out.println(count);
    }
}
