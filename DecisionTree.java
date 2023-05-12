import java.util.*;

public class DecisionTree {

    Ramification root;

    DecisionTree(DataSet ds){
        root = new Ramification(ds, true, "", "", 0);
    }

    Ramification fit(Ramification r, DataSet ds) {
        if (r.isFinal()) return r;
        else {
            double avalue = -1;
            int aindex = 0;
            for (int i = 0; i < ds.colnum; i++) {
                double ent = Entropy.getGain(ds, i);
                if (avalue < ent){
                    avalue = ent;
                    aindex = i;
                }
            }
            List<DataSet> split = ds.split(ds, aindex);
            int i = 0;
            for (DataSet ds2 : split) {
                Ramification r2;
                if (ds2.size > 0 && ds2.colnum > 0) r2 = new Ramification(ds2, false, ds.getAttribute(aindex), ds.getOptions(aindex).get(i).toString(), r.level+1);
                else {
                    String most_common = ds.getMostCommonClass(ds);
                    r2 = new Ramification(ds2, ds.getAttribute(aindex), ds.getOptions(aindex).get(i).toString(), most_common, r.level + 1);
                    //System.out.println("here");
                }
                Ramification subtree = fit(r2, ds2);
                r.addSon(subtree);
                i++;
            }
            
            return r;
        }

    }

    void printDT() {
        Stack<Ramification> s = new Stack<Ramification>();
        s.add(root);
        boolean isroot = true;
        while (!s.isEmpty()) {
            //for (Ramification r : root.getSons()) {
                Ramification r = s.pop();
                for (Ramification son : r.getSons()) {
                    if (son != null) s.add(son);
                }
                if (isroot) {isroot = false; continue;}
                //System.out.println(r.level);
                //String space = String.join("", Collections.nCopies(r.level-1, "\t"));
                //if (r == null) {continue;}
                String space = new String(new char[r.level-1]).replace("\0", "\t");
                System.out.println(space + r.getSplitAttribute() + " = " + r.getAttributeClass());
                //System.out.println(r.getSplitAttribute() + " = " + r.getAttributeClass());
                //System.out.println("is final? " + r.isFinal());
                if (r.isFinal()) System.out.println(space + "class = " + r.getClassification() + "; count = " + r.getCount());
                System.out.println(' ');
            
            //}
        }
    }

}
