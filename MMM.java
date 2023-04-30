public class MMM {
    public static void main(String[] args) {
        DataSet ds = new DataSet("datasets/restaurant.csv");
        //ds.printAllCollumns();
        //System.out.println(ds.size);
        System.out.println(Entropy.entcalc(ds, 5));
    }
    
}
