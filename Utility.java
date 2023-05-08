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

    static void shuffle(boolean[] arr, double per) {
        int count = (int)(arr.length * per);
        Random rand = new Random();
        while (count > 0) {
            int random_index = 0 + rand.nextInt((arr.length - 0) + 1);
            if (arr[random_index] == false) {
                arr[random_index] = true;
                count--;
            }
        }
    }

}
