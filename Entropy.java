import java.util.*;

public class Entropy {

    private static double probcalc(List<Object> target, Object target_option) {
        double count = 0;
        for (int i = 0; i < target.size(); i++) {
            if (target.get(i).equals(target_option)) {
                count++;
            }
        }
        count /= target.size();
        return count;
    }

    private static double probcalc(List<Object> attributeCol, Object attributeOption, List<Object> target, Object target_option) {
        double count = 0;
        double total = 0;
        for (int j = 0; j < attributeCol.size(); j++) {
            if (attributeCol.get(j).equals(attributeOption)) {
                total++;
                if (target.get(j).equals(target_option)) count++;
            }
        }
        if (total == 0) return 0;
        count /= total;
        return count;
    }

    private static double Hcalc(List<Object> target, List<Object> target_options) {
        double result = 0;
        for (int i = 0; i < target_options.size(); i++) {
            double p = probcalc(target, target_options.get(i));
            if (p == 0) continue;
            else result -= (p * Utility.log2(p));
        }
        return result;
    }

    private static double Hcalc(List<Object> attributeCol, Object attributeOption, List<Object> target, List<Object> target_options) {
        double result = 0;
        for (int i = 0; i < target_options.size(); i++) {
            double p = probcalc(attributeCol, attributeOption, target, target_options.get(i));
            if (p == 0) continue;
            else result -= (p * Utility.log2(p));
        }
        return result;
    }

    private static double remainder(Dataset ds, int index) {
        double result = 0;
        for (int i = 0; i < ds.getOptions(index).size(); i++) {
            double p = probcalc(ds.col(index), ds.getOptions(index).get(i));
            double h = Hcalc(ds.col(index), ds.getOptions(index).get(i), ds.target(), ds.get_target_options());
            result += p * h;
        }
        return result;
    }

    public static double gain(Dataset ds, int index) {
        double g = Hcalc(ds.target(), ds.get_target_options()) - remainder(ds,index);
        return g;
    }
}
