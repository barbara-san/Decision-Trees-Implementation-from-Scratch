import java.util.*;

public class Ramification {
    private DataSet ds;
    private String condition;
    int level;
    private boolean isFinal = true;
    private String classif;
    private String value;
    private int count = -1;
    private List<Ramification> sons  = new ArrayList<>();

    Ramification(DataSet dataset, boolean root, String cond, String att, int l) {
        ds = dataset;
        if (!root) {
            condition = cond;
            Object test = ds.getPredCol().get(0);
            for (Object o : ds.getPredCol()) {
                if (!test.equals(o)) {
                    isFinal = false;
                    break;
                }
            }
            if (isFinal) {count = ds.size; classif = test.toString();}
            value = att;
            level = l;
        }
        else {
            isFinal = false;
            level = 0;
        }
    }

    Ramification(DataSet dataset, String cond, String att, String most_common, int l) {
        isFinal = true;
        condition = cond;
        count = 0;
        classif = most_common;
        value = att;
        level = l;
    }

    void addSon(Ramification r) {
        sons.add(r);
    }

    List<Ramification> getSons() {
        return sons;
    }

    String getClassification() {
        return classif;
    }

    String getSplitAttribute() {
        return condition;
    }

    int getCount() {
        return count;
    }

    String getAttributeClass() {
        return value;
    }

    boolean isFinal() {
        return isFinal;
    }
}
