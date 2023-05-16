import java.util.*;
import java.io.*;

public class Program {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Dataset ds = new Dataset(args[0], true);
        Dataset ds2 = null;
        List<String> prediction = null;

        ds.discretize();

        DecisionTree dt = new DecisionTree(ds);
        dt.fit(dt.root, ds);

        String str = in.nextLine();

        while(!str.equals("exit")) {
            if (str.equals("print dt")) dt.printDT();
            else if (str.equals("print ds used for training")) ds.printCSV();
            else if (str.equals("print ds used for prediction")) {
                if (ds2 != null) ds2.printCSV();
                else System.out.println(Utility.ANSI_RED + "no dataset for prediction was given yet\n" + Utility.ANSI_RESET);
            }
            else if (str.startsWith("predict")) {
                String path = str.split(" ", 2)[1];
                File file  = new File(path);
                if (file.exists() && path.endsWith(".csv")) {
                    ds2 = new Dataset(path, false);
                    ds2.discretize(ds.get_all_intervals());
                    prediction = dt.predict(ds2);
                    int i = 1;
                    for (String s : prediction) {System.out.println(Utility.ANSI_YELLOW + i + ": " + s + Utility.ANSI_RESET); i++;}
                    System.out.println("");
                }
                else if (file.exists()) System.out.println(Utility.ANSI_RED + "file must be in csv format\n" + Utility.ANSI_RESET);
                else System.out.println(Utility.ANSI_RED + "file does not exist\n" + Utility.ANSI_RESET);
            }
            else if (str.equals("print prediction")) {
                if (ds2 != null) {
                    int i = 0;
                    for (String s : prediction) {System.out.println(Utility.ANSI_YELLOW + i + ": " + s + Utility.ANSI_RESET); i++;}
                    System.out.println("");
                }
                else System.out.println(Utility.ANSI_RED + "no dataset for prediction was given yet\n" + Utility.ANSI_RESET);
            }
            else System.out.println(Utility.ANSI_RED + "invalid input format\n" + Utility.ANSI_RESET);
            if (in.hasNextLine()) str = in.nextLine();
        }

        System.out.println(Utility.ANSI_RED + "goodbye!" + Utility.ANSI_RESET);
        in.close();
    }
}
