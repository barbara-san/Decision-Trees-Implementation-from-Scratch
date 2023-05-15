//import java.util.*;

public class Try {
    public static void main(String[] args) {
        //System.out.println("RESTAURANT");
        Dataset ds = new Dataset("Datasets/weather.csv", true);
        DecisionTree dt = new DecisionTree(ds);
        ds.printCSV();
        System.out.println(' ');

        ds.discretize();
        ds.printCSV();
        System.out.println(' ');
        dt.fit(dt.root, ds);
        
        dt.printDT();
        System.out.println(' ');

        Dataset ds2 = new Dataset("Datasets/weather_for_pred.csv", false);
        ds2.discretize(ds.get_all_intervals());
        ds2.printCSV();
        System.out.println(' ');
        //dt.predict(ds2);
        System.out.println(dt.predict(ds2));

        /* String line = "Yes,No,Yes,Yes,Full,$,No,No,Thai,10-30";
        String[] values = line.split(",");
        List<Object> list = new ArrayList<Object>(Arrays.asList(values));
        System.out.println(dt.predict(list)); */

        /* Dataset list = new Dataset("Datasets/res-pred.csv");
        List<String> s = dt.predict(list);
        System.out.println(s); */
        //ds.printAllCollumns();
        //for (int i = 0; i < ds.colnum; i++) System.out.println(ds.getCSV().get(0).get(i+1) + ": " + Entropy.entcalc(ds, i));
        //System.out.println(Entropy.entcalc(ds, 0));
        //System.out.println(' ');
        /* List<Dataset> dss = ds.split(ds, 8);
        for (Dataset d : dss) {
            d.printCSV();
            System.out.println(' ');
        } */

        /* System.out.println("WEATHER - before");
        ds = new Dataset("Datasets/weather.csv");
        ds.printAllCollumns();
        ds.format();
        System.out.println(' ');

        System.out.println("WEATHER - after");

        //for (int i = 0; i < ds.colnum; i++) System.out.println(ds.getCSV().get(0).get(i+1) + ": " + Entropy.entcalc(ds, i));
        ds.printAllCollumns();
        System.out.println(' ');
        System.out.println(' ');
        System.out.println("IRIS - before");
        ds = new Dataset("Datasets/iris.csv");
        ds.printAllCollumns();
        ds.format();
        System.out.println(' ');

        System.out.println("IRIS - after");

        ds.printAllCollumns();
        //for (int i = 0; i < ds.colnum; i++) System.out.println(ds.getCSV().get(0).get(i+1) + ": " + Entropy.entcalc(ds, i));
        System.out.println(' '); */

    }
    
}
