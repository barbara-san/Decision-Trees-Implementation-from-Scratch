import java.util.*;

public class DecisionTree {

    Node root;

    DecisionTree(Dataset ds){
        root = new Node(ds, true, "", "", 0);
    }

    Node fit(Node r, Dataset ds) {
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
                    String most_common = ds.getMostCommonClass(ds);
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

    String predict(List<Object> line) {
        Node r = root;
        while (!r.isFinal()) {
            for (Node r2 : r.children()) {
                int i = r.ds().getAttributes().indexOf(r2.getSplitConditionAttribute());
                if (r2.attributeValue().equals(line.get(i).toString())) {
                    if (r.isFinal()) return r.classification();
                    r = r2;
                    line.remove(i);
                    break;
                }
            }
        }
        return r.classification();
    }

/*     List<String> predict(Dataset new_lines) {
        List<String> prediction = new ArrayList<>();
        for (int i = 0; i < new_lines.numberLines(); i++) {
            prediction.add(predict(new_lines.getCSV().get(i)));
        }
        return prediction;
    } */

    void printDT() {
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
            System.out.println(space + r.getSplitConditionAttribute() + " = " + r.attributeValue());
            if (r.isFinal()) System.out.println(space + "class = " + r.classification() + "; count = " + r.count());
            System.out.println(' ');
            
        }
    }
}
