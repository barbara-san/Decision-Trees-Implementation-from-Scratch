import java.util.List;

public class MMM {
    public static void main(String[] args) {
        DataSet ds = new DataSet("datasets/iris.csv");
        ds.format();
        System.out.println(' ');
        ds.printAllCollumns();
        //System.out.println(' ');
        //System.out.println(ds.size);
        //System.out.println(ds.getPosNum());
        /* List<DataSet> dss = ds.split(ds, 0);
        for (DataSet d : dss) {
            d.printAllCollumns();
            System.out.println(' ');
        } */
    }
    
}
