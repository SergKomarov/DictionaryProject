package sys101_rest;

import java.util.Comparator;

class Helper implements Comparator<DictionaryList> {

    @Override
    public int compare(DictionaryList o1, DictionaryList o2) {
        return o1.getActualDate().compareTo(o2.getActualDate());
    }
}