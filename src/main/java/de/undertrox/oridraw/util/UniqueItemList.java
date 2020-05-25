package de.undertrox.oridraw.util;

import java.util.ArrayList;

/**
 * An ArrayList that will not allow duplicated entries
 * @param <T>: Type that will be stored
 */
public class UniqueItemList<T> extends ArrayList<T> {

    /**
     * Behaves like ArrayList#add, but will return false if
     * t is already in the List.
     * @param t: Item to add
     * @return true: element was added, false: element was not added
     */
    @Override
    public boolean add(T t) {
        if (!contains(t)) {
            return super.add(t);
        }
        return false;
    }

    /**
     * Behaves like ArrayList#add, but will return the added Element / the
     * Element that prevented the addition
     * @param t: Item to add
     * @return the Item that was Added if it wasn't in the List, otherwise the Element in the list
     * that equals t
     */
    public T push(T t) {
        if (!contains(t)) {
            super.add(t);
            return t;
        }
        return get(indexOf(t));
    }
}
