import java.util.*;

public class DecisionTree {

    Node root; // first node, root


    // constructor based on a dataset
    DecisionTree(Dataset ds){
        root = new Node(ds, true, null, null, 0, null);
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
            List<Dataset> split = ds.split(aindex);

            // add children
            int i = 0;
            for (Dataset ds2 : split) {
                Node r2;
                // if the child node has examples
                if (ds2.numberLines() > 0 && ds2.numberCols() >= 0) r2 = new Node(ds2, false, ds.attribute(aindex), ds.getOptions(aindex).get(i).toString(), r.level()+1, r);
                
                // if the child node does not have examples
                else {
                    String most_common = ds.plurarityValue();
                    r2 = new Node(ds2, ds.attribute(aindex), ds.getOptions(aindex).get(i).toString(), most_common, r.level() + 1, r);
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
        List<String> for_prediction = new ArrayList<>(line);
        Node r = root;
        while (!r.isFinal()) {
            for (Node r2 : r.children()) {
                int i = r.ds().getAttributes().indexOf(r2.splitConditionAttribute());
                if (r2.attributeValue().equals(for_prediction.get(i+1))) {
                    if (r.isFinal()) return r.classification();
                    r = r2;
                    for_prediction.remove(i+1);
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
    public void printDT(boolean color) {
        Map<String, Integer> attributes = new HashMap<String, Integer>();

        for (String att : root.ds().getAttributes()) {
            attributes.put(att, 0);
        }

        Stack<Node> s = new Stack<Node>();
        s.add(root);
        boolean isRoot = true;
        while (!s.isEmpty()) {
            Node r = s.pop();
            String space = "";
            
            for (Node son : r.children()) {
                if (son != null) s.add(son);
            }
            if (isRoot) {isRoot = false; continue;}

            space = new String(new char[r.level()-1]).replace("\0", "\t");
            if (attributes.get(r.splitConditionAttribute()) == 0) {
                System.out.println("");
                if (!color) System.out.println(space + "<" + r.splitConditionAttribute() + ">");
                else System.out.println(Utility.ANSI_PURPLE_BACKGROUND + space + "<" + r.splitConditionAttribute() + ">" + Utility.ANSI_RESET);
                System.out.println("");
                attributes.put(r.splitConditionAttribute(), r.parent().children().size());
            }
            if (!color) System.out.print(space + "  " + r.attributeValue());
            else System.out.print(Utility.ANSI_PURPLE + space + "  " + r.attributeValue() + Utility.ANSI_RESET);

            if (r.isFinal()) {
                if (!color) System.out.println(": " + r.classification() + " (" + r.count() + ")");
                else System.out.println(Utility.ANSI_PURPLE + ": " + r.classification() + " (" + r.count() + ")" + Utility.ANSI_RESET);
            }
            System.out.println("");
            attributes.put(r.splitConditionAttribute(), attributes.get(r.splitConditionAttribute()) - 1);
        }
    }


    // prints the Decision Tree with colour purple
    public void printDT_color() {
        Map<String, Integer> attributes = new HashMap<String, Integer>();

        for (String att : root.ds().getAttributes()) {
            attributes.put(att, 0);
        }

        Stack<Node> s = new Stack<Node>();
        s.add(root);
        boolean isRoot = true;
        while (!s.isEmpty()) {
            Node r = s.pop();
            String space = "";
            
            for (Node son : r.children()) {
                if (son != null) s.add(son);
            }
            if (isRoot) {isRoot = false; continue;}

            space = new String(new char[r.level()-1]).replace("\0", "\t");
            if (attributes.get(r.splitConditionAttribute()) == 0) {
                System.out.println("");
                System.out.println(Utility.ANSI_PURPLE_BACKGROUND + space + "<" + r.splitConditionAttribute() + ">" + Utility.ANSI_RESET);
                System.out.println("");
                attributes.put(r.splitConditionAttribute(), r.parent().children().size());
            }
            System.out.print(Utility.ANSI_PURPLE + space + "  " + r.attributeValue() + Utility.ANSI_RESET);

            if (r.isFinal()) System.out.println(Utility.ANSI_PURPLE + ": " + r.classification() + " (" + r.count() + ")" + Utility.ANSI_RESET);
            System.out.println("");
            attributes.put(r.splitConditionAttribute(), attributes.get(r.splitConditionAttribute()) - 1);
        }
    }
}
