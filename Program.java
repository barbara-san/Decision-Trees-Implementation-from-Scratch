import java.util.*;
import java.io.*;

public class Program {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        boolean color = false;
        Dataset ds2 = null;
        List<String> prediction = null;

        // create Dataset object with the given csv file path
        Dataset ds = new Dataset(args[0], true);

        // discretize ds
        ds.discretize();

        // create new Decision Tree object and populate it
        DecisionTree dt = new DecisionTree(ds);
        dt.fit(dt.root);

        String str = in.nextLine();

        // read instructions from the user
        while(!str.equals("exit")) {
            // add colors to the terminal
            if (str.equals("color")) {
                color = !color;
                if (color) System.out.println(Utility.ANSI_RED + "colors activated!\n" + Utility.ANSI_RESET);
                else System.out.println(Utility.ANSI_RED + "colors deactivated!\n" + Utility.ANSI_RESET);
            }

            // prints
            else if (str.equals("print dt")) dt.printDT(color);
            else if (str.equals("print ds used for training")) ds.printCSV(color);
            else if (str.equals("print ds used for prediction")) {
                if (ds2 != null) ds2.printCSV(color);
                else System.out.println(Utility.ANSI_RED + "no dataset for prediction was given yet\n" + Utility.ANSI_RESET);
            }
            else if (str.equals("predict")) System.out.println(Utility.ANSI_RED + "no file given for prediction" + Utility.ANSI_RESET);
            else if (str.equals("print prediction")) {
                if (ds2 != null) {
                    int i = 0;
                    for (String s : prediction) {
                        if (color) System.out.println(Utility.ANSI_YELLOW + i + ": " + s + Utility.ANSI_RESET);
                        else System.out.println(i + ": " + s);
                        i++;
                    }
                    System.out.println("");
                }
                else System.out.println(Utility.ANSI_RED + "no dataset for prediction was given yet\n" + Utility.ANSI_RESET);
            }

            // predict the target for a new given dataset
            else if (str.startsWith("predict")) {
                String path = str.split(" ", 2)[1];
                File file  = new File(path);
                if (file.exists() && path.endsWith(".csv")) {
                    ds2 = new Dataset(path, false);
                    ds2.discretize(ds.get_all_intervals());
                    prediction = dt.predict(ds2);
                    int i = 1;
                    for (String s : prediction) {
                        if (color) System.out.println(Utility.ANSI_YELLOW + i + ": " + s + Utility.ANSI_RESET);
                        else System.out.println(i + ": " + s);
                        i++;
                    }
                    System.out.println("");
                }
                else if (file.exists()) System.out.println(Utility.ANSI_RED + "file must be in csv format\n" + Utility.ANSI_RESET);
                else System.out.println(Utility.ANSI_RED + "file does not exist\n" + Utility.ANSI_RESET);
            }

            // if everything else fails
            else System.out.println(Utility.ANSI_RED + "invalid input format\n" + Utility.ANSI_RESET);
            if (in.hasNextLine()) str = in.nextLine();
        }

        System.out.println("");
        System.out.println(Utility.ANSI_RED + "goodbye!" + Utility.ANSI_RESET);
        in.close();
    }
}
