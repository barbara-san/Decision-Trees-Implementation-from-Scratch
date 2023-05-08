import java.util.*;

public class Entropy {

    private static double probcalc(List<Object> list, Object target_i) {
        double count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target_i)) {
                count++;
            }
        }
        count /= list.size();
        return count;
    }

    private static double probcalc(List<Object> AttributeCol, Object attribute, List<Object> list, Object target_i) {
        double count = 0;
        double total = 0;
        for (int j = 0; j < AttributeCol.size(); j++) {
            if (AttributeCol.get(j).equals(attribute)) {
                total++;
                if (list.get(j).equals(target_i)) count++;
            }
        }
        count /= total;
        return count;
    }

    private static double Hcalc(List<Object> target, List<Object> options) {
        double result = 0;
        for (int i = 0; i < options.size(); i++) {
            double p = probcalc(target, options.get(i));
            if (p == 0) continue;
            else result -= (p * Utility.log2(p));
        }
        return result;
    }

    private static double Hcalc(List<Object> AttributeCol, Object attribute, List<Object> target, List<Object> options) {
        double result = 0;
        for (int i = 0; i < options.size(); i++) {
            double p = probcalc(AttributeCol, attribute, target, options.get(i));
            if (p == 0) continue;
            else result -= (p * Utility.log2(p));
        }
        return result;
    }

    private static double remainder(DataSet ds, int ind) {
        double result = 0;
        for (int i = 0; i < ds.getOptions(ind).size(); i++) {
            double p = probcalc(ds.getCol(ind), ds.getOptions(ind).get(i));
            double h = Hcalc(ds.getCol(ind), ds.getOptions(ind).get(i), ds.getPredCol(), ds.getPredOptions());
            result += p * h;
        }
        return result;
    }

    private static double gain(DataSet ds, int ind) {
        double g = Hcalc(ds.getPredCol(), ds.getPredOptions())-remainder(ds,ind);
        g = Math.round(g*(double)Math.pow(10, 5))/(double)Math.pow(10, 5);
        return g;
    }

    public static double entcalc(DataSet ds, int ind) {
        return gain(ds, ind);
    }
}
