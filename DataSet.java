import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class DataSet {

    public boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        }
        catch(NumberFormatException e) {  
            return false;  
        }  
    }

    private List<String> atributes = new ArrayList<>();
    private List<List<String>> csv = new ArrayList<>();
    private List<List<Object>> all_cols = new ArrayList<>();
    private LinkedHashMap<String, List<String>> options = new LinkedHashMap<String, List<String>>();
    private List<Boolean> isNum = new ArrayList<>();
    public int size;

    DataSet(String fileName){
        File file = new File(fileName);

        boolean first_line = true;
        int cur_line = 0;
        size = 0;

        try {
            Scanner sc = new Scanner(file);

            while(sc.hasNext()){
                String line = sc.next();
                String[] values = line.split(",");
                csv.add(Arrays.asList(values));
                size++;
                if (first_line) {
                    atributes = Arrays.asList(values);
                    for (String value: csv.get(0)) {
                        List<String> option = new ArrayList<>();
                        List<Object> attribute = new ArrayList<>();
                        //attribute.add(value);
                        all_cols.add(attribute);
                        options.put(value, option);
                    }
                    first_line = false;
                }
                else {
                    int cur_row = 0;
                    for (Map.Entry<String, List<String>> cur_atr : options.entrySet()) {
                        String value = csv.get(cur_line).get(cur_row);
                        if (isNumeric(value)) {
                            double value2 = Double.parseDouble(value);
                            all_cols.get(cur_row).add(value2);
                        }
                        else all_cols.get(cur_row).add(value);

                        if (!cur_atr.getValue().contains(value)) 
                            cur_atr.getValue().add(value);
                        
                        cur_row++;
                    }
                }

                cur_line++;
            }
            
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        size--;

        for (String value: csv.get(1)) {
            if (isNumeric(value)) isNum.add(true);
            else isNum.add(false);
        }
    }

    public List<List<String>> getCSV() {
        return csv;
    }
    public List<List<Object>> getallCols() {
        return all_cols;
    }
    public List<Object> getCol(int i) {
        return all_cols.get(i);
    }
    public List<Object> getPredCol() {
        return all_cols.get(size-1);
    }
    public List<String> getPredOptions() {
        return options.get(atributes.get(size-1));
    }
    public List<String> getOptions(int i) {
        return options.get(atributes.get(i));
    }
    public List<Boolean> getNum() {
        return isNum;
    }
    public int getPosNum() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (getPredCol().get(i).equals(getPredOptions().get(0))) {
                count++;
            }
        }
        return count;
    }

    public void printCSV(){
        System.out.println("PRINT CSV");
        for(List<String> line: csv) {
            for (String value: line) {
                System.out.print(value + ' ');
            }
            System.out.println("");
        }
    }

    public void printOptions(){
        System.out.println("PRINT OPTIONS");
        for (Map.Entry<String, List<String>> aa : options.entrySet()){
            System.out.print(aa.getKey() + ": ");
            for (int i = 0; i < aa.getValue().size(); i++) {
                System.out.print(aa.getValue().get(i) + ' ');
            }
            System.out.println("");
        }
    }

    public void printAllCollumns(){
        for (List<Object> line: all_cols){
            for (Object value: line) {
                System.out.print(value + " ");
            }
            System.out.println("");
        }
    }

    public void printNum(){
        System.out.println("PRINT NUM OR NOT");
        for (Boolean value: isNum) {
            System.out.print(value + " ");
        }
        System.out.println("");

    }
}
