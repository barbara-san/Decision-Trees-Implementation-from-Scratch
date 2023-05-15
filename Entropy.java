import java.util.*;
import java.util.concurrent.CountDownLatch;

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
        System.out.println(AttributeCol.size());
        for (int j = 0; j < AttributeCol.size(); j++) {
            if (AttributeCol.get(j).equals(attribute)) {
                total++;
                if (list.get(j).equals(target_i)) count++;
            }
        }
        if (total == 0) return 0;
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

    private static double remainder(Dataset ds, int ind) {
        double result = 0;
        for (int i = 0; i < ds.getOptions(ind).size(); i++) {
            double p = probcalc(ds.col(ind), ds.getOptions(ind).get(i));
            double h = Hcalc(ds.col(ind), ds.getOptions(ind).get(i), ds.target(), ds.get_target_options());
            result += p * h;
        }
        return result;
    }

    private static double gain(Dataset ds, int ind) {
        double g = Hcalc(ds.target(), ds.get_target_options()) - remainder(ds,ind);
        //g = Math.round(g*(double)Math.pow(10, 7))/(double)Math.pow(10, 7);
        return g;
    }

    public static double getGain(Dataset ds, int ind) {
        return gain(ds, ind);
    }
}
