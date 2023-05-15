import java.util.*;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Dataset ds = new Dataset(args[0], true);
        ds.discretize();

        DecisionTree dt = new DecisionTree(ds);
        dt.fit(dt.root, ds);
        dt.printDT();

        String str;


    


        in.close();
    }
}
