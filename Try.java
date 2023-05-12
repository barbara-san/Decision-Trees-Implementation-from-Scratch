//import java.util.*;

public class Try {
    public static void main(String[] args) {
        //System.out.println("RESTAURANT");
        DataSet ds = new DataSet("datasets/restaurant.csv");
        DecisionTree dt = new DecisionTree(ds);
        ds.printCSV();
        System.out.println(' ');

        ds.format();
        dt.fit(dt.root, ds);
        
        //System.out.println(' ');
        dt.printDT();
        //ds.printAllCollumns();
        //for (int i = 0; i < ds.colnum; i++) System.out.println(ds.getCSV().get(0).get(i+1) + ": " + Entropy.entcalc(ds, i));
        //System.out.println(Entropy.entcalc(ds, 0));
        //System.out.println(' ');
        //System.out.println(ds.size);
        //System.out.println(ds.getPosNum());
        /* List<DataSet> dss = ds.split(ds, 8);
        for (DataSet d : dss) {
            d.printCSV();
            System.out.println(' ');
        } */

        /* System.out.println("WEATHER - before");
        ds = new DataSet("datasets/weather.csv");
        ds.printAllCollumns();
        ds.format();
        System.out.println(' ');

        System.out.println("WEATHER - after");

        //for (int i = 0; i < ds.colnum; i++) System.out.println(ds.getCSV().get(0).get(i+1) + ": " + Entropy.entcalc(ds, i));
        ds.printAllCollumns();
        System.out.println(' ');
        System.out.println(' ');
        System.out.println("IRIS - before");
        ds = new DataSet("datasets/iris.csv");
        ds.printAllCollumns();
        ds.format();
        System.out.println(' ');

        System.out.println("IRIS - after");

        ds.printAllCollumns();
        //for (int i = 0; i < ds.colnum; i++) System.out.println(ds.getCSV().get(0).get(i+1) + ": " + Entropy.entcalc(ds, i));
        System.out.println(' '); */

    }
    
}
