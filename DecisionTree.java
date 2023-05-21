import java.util.*;

public class DecisionTree {

    Node root; // first node, root

    // constructor based on a dataset
    DecisionTree(Dataset ds){
        root = new Node(ds, true, null, null, 0, null);
    }

    // function used to fill our decision tree, given a node (that will get children during the run of the function) and the respective dataset
    public Node fit(Node n) {
        if (n.isFinal()) return n;
        else {
            // get best gain value and the index of its attribute
            double avalue = -1; int aindex = 0;
            for (int i = 0; i < n.ds().numberCols(); i++) {
                double ent = Entropy.gain(n.ds(), i);
                if (avalue < ent){
                    avalue = ent;
                    aindex = i;
                }
            }

            //split the dataset
            List<Dataset> split = n.ds().split(aindex);

            // add children
            int i = 0;
            for (Dataset ds2 : split) {
                Node r2;
                // if the child node has examples
                if (ds2.numberLines() > 0 && ds2.numberCols() >= 0) r2 = new Node(ds2, false, n.ds().attribute(aindex), n.ds().getOptions(aindex).get(i).toString(), n.level()+1, n);
                
                // if the child node does not have examples
                else {
                    String most_common = n.ds().plurarityValue();
                    r2 = new Node(ds2, n.ds().attribute(aindex), n.ds().getOptions(aindex).get(i).toString(), most_common, n.level() + 1, n);
                }
                Node subtree = fit(r2);
                n.addChild(subtree);
                i++;
            }
        return n;
        }
    }

    // function used to predict the class of an example using the generated decision tree
    private String predict(List<String> line) {
        List<String> for_prediction = new ArrayList<>(line);
        Node n = root;
        while (!n.isFinal()) {
            for (Node r2 : n.children()) {
                int i = n.ds().getAttributes().indexOf(r2.splitConditionAttribute());
                if (r2.attributeValue().equals(for_prediction.get(i+1))) {
                    if (n.isFinal()) return n.classification();
                    n = r2;
                    for_prediction.remove(i+1);
                    break;
                }
            }
        }
        return n.classification();
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
    public void printDT(boolean color) {
        Map<String, Integer> attributes = new HashMap<String, Integer>();

        for (String att : root.ds().getAttributes()) {
            attributes.put(att, 0);
        }

        Stack<Node> s = new Stack<Node>();
        s.add(root);
        boolean isRoot = true;
        while (!s.isEmpty()) {
            Node n = s.pop();
            String space = "";
            
            for (Node son : n.children()) {
                if (son != null) s.add(son);
            }
            if (isRoot) {isRoot = false; continue;}

            space = new String(new char[n.level()-1]).replace("\0", "\t");
            if (attributes.get(n.splitConditionAttribute()) == 0) {
                System.out.println("");
                if (!color) System.out.println(space + "<" + n.splitConditionAttribute() + ">");
                else System.out.println(Utility.ANSI_PURPLE_BACKGROUND + space + "<" + n.splitConditionAttribute() + ">" + Utility.ANSI_RESET);
                System.out.println("");
                attributes.put(n.splitConditionAttribute(), n.parent().children().size());
            }
            if (!color) System.out.print(space + "  " + n.attributeValue());
            else System.out.print(Utility.ANSI_PURPLE + space + "  " + n.attributeValue() + Utility.ANSI_RESET);

            if (n.isFinal()) {
                if (!color) System.out.println(": " + n.classification() + " (" + n.count() + ")");
                else System.out.println(Utility.ANSI_PURPLE + ": " + n.classification() + " (" + n.count() + ")" + Utility.ANSI_RESET);
            }
            System.out.println("");
            attributes.put(n.splitConditionAttribute(), attributes.get(n.splitConditionAttribute()) - 1);
        }
    }

}
