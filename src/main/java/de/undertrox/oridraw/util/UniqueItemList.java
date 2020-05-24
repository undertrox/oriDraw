package de.undertrox.oridraw.util;

import java.util.ArrayList;

public class UniqueItemList<T> extends ArrayList<T> {
    @Override
    public boolean add(T t) {
        if (!contains(t)) {
            return super.add(t);
        }
        return false;
    }

    public T push(T t) {
        if (!contains(t)) {
            super.add(t);
            return t;
        }
        return get(indexOf(t));
    }
}
