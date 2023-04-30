public class Entropy {
    private static double log2(double n){
        double l = (Math.log(n) / Math.log(2));
        //System.out.println(n);
        return l;
    }

    private static double[] countPos(DataSet ds, int ind, String option) {
        double count[] = new double[3];
        //System.out.println(option);
        for (int i = 0; i < ds.size; i++) {
            if (ds.getCol(ind).get(i).equals(option)) {
                count[2]++;
                if (ds.getPredCol().get(i).equals(ds.getPredOptions().get(0))) count[0]++;
                else count[1]++;
            }
        }
        return count;
    }
    
    private static double Bcalc(double q) {
        //if (q == 1) return 0;
        double bvalue = -(q * log2(q) + ((1-q) * log2(1-q)));
        //System.out.println(((1-q) * log2(1-q)));
        //System.out.println("log(" + q + ") = " + log2(q));

        return bvalue;
    }

    private static double remaindercalc(DataSet ds, int ind) {
        double count = 0;

        for (int index = 0; index < ds.getOptions(ind).size(); index++) {
            double pos[] = countPos(ds,ind,ds.getOptions(ind).get(index));
            double fraction = (pos[2])/(ds.size);
            double b = Bcalc(pos[0]/(pos[2]));
            count += fraction*b;
            //System.out.print("b value = " + b + " ");
            //System.out.println("q value = " + pos[0]/(pos[2]));
        }
        return count;
    }

    private static double gain(DataSet ds, int ind) {
        double s = ds.size;
        double prob = ds.getPosNum()/s;
        double g = Bcalc(prob)-remaindercalc(ds,ind);
        //System.out.println(remaindercalc(ds,ind));
        System.out.println(prob);
        double scale = Math.pow(10, 5);
        g = Math.round(g*scale)/scale;
        return g;
    }

    public static double entcalc(DataSet ds, int ind) {
        return gain(ds, ind);
    }
}
