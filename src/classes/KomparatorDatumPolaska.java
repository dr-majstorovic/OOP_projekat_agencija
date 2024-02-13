package classes;

import java.util.Comparator;

public class KomparatorDatumPolaska implements Comparator<Aranzman> {
    @Override
    public int compare(Aranzman o1, Aranzman o2) {
        return o1.getDatumPolaska().compareTo(o2.getDatumPolaska());
    }
}
