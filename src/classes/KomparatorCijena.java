package classes;

import java.util.Comparator;

public class KomparatorCijena implements Comparator<Aranzman> {
    @Override
    public int compare(Aranzman o1, Aranzman o2) {
        return Double.compare(o1.getCijena(), o2.getCijena());
    }
}
