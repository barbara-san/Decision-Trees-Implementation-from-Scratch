import java.util.*;

public class DecisionTree {

    Node root;

    DecisionTree(Dataset ds){
        root = new Node(ds, true, null, null, 0);
    }

    public Node fit(Node r, Dataset ds) {
        if (r.isFinal()) return r;
        else {
            double avalue = -1;
            int aindex = 0;
            for (int i = 0; i < ds.numberCols(); i++) {
                double ent = Entropy.getGain(ds, i);
                if (avalue < ent){
                    avalue = ent;
                    aindex = i;
                }
            }
            List<Dataset> split = ds.split(ds, aindex);
            int i = 0;
            for (Dataset ds2 : split) {
                Node r2;
                if (ds2.numberLines() > 0 && ds2.numberCols() > 0) r2 = new Node(ds2, false, ds.attribute(aindex), ds.getOptions(aindex).get(i).toString(), r.level()+1);
                else {
                    String most_common = ds.plurarityValue(ds);
                    r2 = new Node(ds2, ds.attribute(aindex), ds.getOptions(aindex).get(i).toString(), most_common, r.level() + 1);
                    //System.out.println("here");
                }
                Node subtree = fit(r2, ds2);
                r.addChild(subtree);
                i++;
            }
            
            return r;
        }

    }

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

    public List<String> predict(Dataset new_lines) {
        List<String> prediction = new ArrayList<>();
        for (int i = 1; i < new_lines.numberLines() + 1; i++) {
            prediction.add(predict(new_lines.csv().get(i)));
        }
        return prediction;
    }

    public void printDT() {
        Stack<Node> s = new Stack<Node>();
        s.add(root);
        boolean isroot = true;
        while (!s.isEmpty()) {
            Node r = s.pop();
            for (Node son : r.children()) {
                if (son != null) s.add(son);
                }
            if (isroot) {isroot = false; continue;}
            String space = new String(new char[r.level()-1]).replace("\0", "\t");
            System.out.println(space + r.splitConditionAttribute() + ": " + r.attributeValue());
            if (r.isFinal()) System.out.println(space + "class = " + r.classification() + "; count = " + r.count());
            System.out.println(' ');
            
        }
    }
}
