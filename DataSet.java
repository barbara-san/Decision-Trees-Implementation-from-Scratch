import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dataset {

    private List<List<String>> csv = new ArrayList<>(); //csv as given
    private List<List<Object>> list_of_all_cols = new ArrayList<>(); //list of all the columns
    private List<Object> target = new ArrayList<>(); //pred column
    private List<String> attributes = new ArrayList<>(); //list of all attributes
    private List<List<Object>> options_per_attribute = new ArrayList<List<Object>>(); //map with the different options_per_attribute by atribute
    private List<Boolean> isNumerical = new ArrayList<>(); //list that says for each col if its numeric or not
    private int numberLines;
    private int numberCols;

    // constructor for brand new dataset
    Dataset(String fileName, boolean hasTarget){
        File file = new File(fileName);

        boolean first_line = true;
        int cur_line = 0;
        numberLines = 0;

        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String line = sc.next();
                String[] values = line.split(",");
                List<String> list = new ArrayList<String>(Arrays.asList(values));
                csv.add(list);
                numberLines++;
                if (first_line) {
                    numberCols = csv.get(0).size();
                    for (int i = 0; i < numberCols; i++) {
                        List<Object> option = new ArrayList<>();
                        List<Object> attribute = new ArrayList<>();
                        list_of_all_cols.add(attribute);
                        options_per_attribute.add(option);
                    }
                    first_line = false;
                }
                else {
                    int cur_row = 0;
                    for (List<Object> cur_atr : options_per_attribute) {
                        String value = csv.get(cur_line).get(cur_row);
                        if (Utility.isNumeric(value)) {
                            double valueAsDouble = Double.parseDouble(value);
                            list_of_all_cols.get(cur_row).add(valueAsDouble);
                        }
                        else list_of_all_cols.get(cur_row).add(value);

                        if (!cur_atr.contains(value)) 
                            cur_atr.add(value);
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

        for (String value: csv.get(1)) {
            if (Utility.isNumeric(value)) isNumerical.add(true);
            else isNumerical.add(false);
        }

        if (hasTarget) target = list_of_all_cols.get(numberCols-1);
        list_of_all_cols.remove(numberCols-1);
        list_of_all_cols.remove(0);

        isNumerical.remove(numberCols-1);
        isNumerical.remove(0);

        attributes = new ArrayList<>(csv.get(0));
        attributes.remove(0);
        options_per_attribute.remove(0);

        numberLines--;
        numberCols-= 2;
    }

    // constructor for dataset split based on a certain attribute (given its index) and its value
    Dataset(Dataset ds, int i, Object attributeValue){
        target = new ArrayList<>(ds.target());
        isNumerical = new ArrayList<>(ds.getNum());
        attributes = new ArrayList<>(ds.getAttributes());

        for (List<String> sublist : ds.csv()) {
            csv.add(new ArrayList<>(sublist));
        }
        for (List<Object> sublist : ds.get_all_Cols()) {
            list_of_all_cols.add(new ArrayList<>(sublist));
        }
        for (List<Object> sublist : ds.get_all_options_per_attribute()) {
            options_per_attribute.add(new ArrayList<>(sublist));
        }
        

        for (int j = 1; j < csv.size(); j++) {
            if (!csv.get(j).get(i+1).equals(attributeValue)) {
                csv.remove(j);
                target.remove(j-1);
                for (int k = 0; k < list_of_all_cols.size(); k++) {
                    list_of_all_cols.get(k).remove(j-1);
                }
                j--;
            }
        }

        for (int m = 1; m < csv.size(); m++) {
            csv.get(m).remove(i+1);
        }

        csv.get(0).remove(i+1);
        list_of_all_cols.remove(i);
        options_per_attribute.remove(i);
        isNumerical.remove(i);
        attributes.remove(i);
        numberLines = csv.size()-1;
        numberCols = list_of_all_cols.size();
    }

    // getters
    public List<List<String>> csv() {return csv;}
    public List<List<Object>> get_all_Cols() {return list_of_all_cols;}
    public List<Object> col(int i) {return list_of_all_cols.get(i);}
    public List<Object> target() {return target;}
    
    
    public List<String> getAttributes() {return attributes;}
    public String attribute(int i) {return attributes.get(i);}
    
    public List<List<Object>> get_all_options_per_attribute() {return options_per_attribute;}
    public List<Object> getOptions(int i) {return options_per_attribute.get(i);}
    public List<Object> get_target_options() {
        List<Object> opt = new ArrayList<>();
        for (int i = 0; i < numberLines; i++) {
            if (!opt.contains(target.get(i))) opt.add(target.get(i));
        }
        return opt;
    }
    
    
    public List<Boolean> getNum() {return isNumerical;}
    
    public int numberLines() {return numberLines;}
    public int numberCols() {return numberCols;}
    

    // useful functions

    // returns the most common class in a dataset
    public String plurarityValue(Dataset ds) {
        HashMap<Object, Integer> count = new HashMap<Object, Integer>();
        for (Object value : target) {
            if (!count.containsKey(value)) count.put(value, 1);
            else count.put(value, count.get(value)+1);
        }
        int max = 0;
        String most_common_value = "";
        for (Map.Entry<Object, Integer> value : count.entrySet()) {
            if (value.getValue() > max) {most_common_value = value.getKey().toString(); max = value.getValue();}
        }
        return most_common_value;
    }

    // returns a list of new datasets which are the result of splitting the original dataset based on a certain attribute, given its index
    public List<Dataset> split(Dataset ds, int i) {
        List<Dataset> new_datasets = new ArrayList<>();
        for (int j = 0; j < options_per_attribute.get(i).size(); j++) {
            Dataset ds2 = new Dataset(ds, i, options_per_attribute.get(i).get(j));
            new_datasets.add(ds2);
        }
        return new_datasets;
    }

    // discretizes the dataset
    public void discretize() {
        for (int j = 0; j < numberCols; j++) {
            if (isNumerical.get(j)) {
                int number_of_intervals = (int)Math.round(1 + Utility.log2(numberLines));
                double[] list_of_interval_values = Utility.createIntervals(col(j), number_of_intervals);
                
                List<Object> formatted_col = new ArrayList<>();
                List<Object> formatted_col_options = new ArrayList<>();
                for (int k = 0; k < list_of_all_cols.get(j).size(); k++) {
                    List<String> line = new ArrayList<>(csv.get(k+1));
                    for (int i = 0; i < number_of_intervals; i++) {
                        if (Utility.toDouble(list_of_all_cols.get(j).get(k)) >= list_of_interval_values[i] && Utility.toDouble(list_of_all_cols.get(j).get(k)) < list_of_interval_values[i+1]) {
                            Object o = "["+ list_of_interval_values[i] + ',' + list_of_interval_values[i+1] + "[";
                            formatted_col.add(o);
                            line.set(j+1, o.toString());
                            if (!formatted_col_options.contains(o)) {
                                formatted_col_options.add(o);
                            }
                            break;
                        }
                        else if (Utility.toDouble(list_of_all_cols.get(j).get(k)) < list_of_interval_values[0]) {
                            Object o = "<" + list_of_interval_values[0];
                            formatted_col.add(o);
                            line.set(j+1, o.toString());
                            break;
                        }
                        else if (Utility.toDouble(list_of_all_cols.get(j).get(k)) >= list_of_interval_values[number_of_intervals]) {
                            Object o = ">=" + list_of_interval_values[number_of_intervals];
                            formatted_col.add(o);
                            line.set(j+1, o.toString());
                            break;
                        }
                    }
                    csv.set(k+1, line);
                }

                formatted_col_options.add("<" + list_of_interval_values[0]);
                formatted_col_options.add(">=" + list_of_interval_values[number_of_intervals]);

                options_per_attribute.set(j, formatted_col_options);
                list_of_all_cols.set(j, formatted_col);
            }
        }
    }

    // print functions
    public void printCSV(){
        System.out.println("PRINT CSV");
        for(List<String> line: csv) {
            for (String value: line) {
                System.out.print(value + ' ');
            }
            System.out.println("");
        }
    }

    public void print_options_per_attribute(){
        System.out.println("PRINT ALL OPTIONS");
        for (List<Object> aa : options_per_attribute){
            for (int i = 0; i < aa.size(); i++) {
                System.out.print(aa.get(i).toString() + ' ');
            }
            System.out.println("");
        }
    }

    public void printAllCollumns(){
        for (List<Object> line: list_of_all_cols){
            for (Object value: line) {
                System.out.print(value + " ");
            }
            System.out.println("");
        }
    }

}
