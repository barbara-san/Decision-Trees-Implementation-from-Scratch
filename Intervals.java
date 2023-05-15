import java.util.*;

public class Intervals {
    
    public static double[] getIntervals(List<Object> list, int num) {

        double[] data = new double[list.size()];
        double[] intervalBounds = new double[num + 1];
        int[] freq = new int[num];

        for (int i = 0; i < list.size(); i++) {
            data[i] = Utility.toDouble(list.get(i));
        }

        Arrays.sort(data);

        double range = data[data.length - 1] - data[0];
        double intervalSize = range / num;
        for (int i = 0; i <= num; i++) {
            intervalBounds[i] = data[0] + (intervalSize * i);
        }

        for (double value : data) {
            int intervalIndex = findIndex(value, data, intervalBounds, freq, num);
            freq[intervalIndex]++;
        }
        //intervalBounds[num] += 0.01;

        double scale = Math.pow(10, 3);
        for (int i = 0; i < intervalBounds.length; i++) {
            intervalBounds[i] = Math.round(intervalBounds[i]*scale)/scale;
        }

        return intervalBounds;
    }
    
    private static int findIndex(double value, double[] data, double[] intervalBounds, int[] freq, int num) {
        int index = Arrays.binarySearch(intervalBounds, value);
        if (index < 0) {
            index = -index - 2;
        }
        if (index < 0) {
            index = 0;
        }
        if (index >= num) {
            index = num - 1;
        }
        return index;
    }

}
