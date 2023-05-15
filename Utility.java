import java.util.*;

public class Utility {
    
    static Double toDouble(Object o) {
        Double val = null;
        if (o instanceof Number) {
            val = ((Number) o).doubleValue();
        }
        return val;
    }

    static double log2(double n){
        double m;
        if (n==0) m = 0.00000000000000000000000000000000001;
        else m = n;

        double l = (Math.log(m) / Math.log(2));
        return l;
    }

    static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        }
        catch(NumberFormatException e) {  
            return false;  
        }  
    }

    public static double[] createIntervals(List<Object> list_of_values, int number_of_intervals) {

        double[] values = new double[list_of_values.size()];
        double[] intervalBounds = new double[number_of_intervals + 1];

        for (int i = 0; i < list_of_values.size(); i++) {
            values[i] = Utility.toDouble(list_of_values.get(i));
        }

        Arrays.sort(values);

        double range = values[values.length - 1] - values[0];
        double intervalSize = range / number_of_intervals;
        for (int i = 0; i < number_of_intervals + 1; i++) {
            intervalBounds[i] = values[0] + (intervalSize * i);
        }

        double scale = Math.pow(10, 3);
        for (int i = 0; i < intervalBounds.length; i++) {
            intervalBounds[i] = Math.round(intervalBounds[i]*scale)/scale;
        }

        return intervalBounds;
    }

}
