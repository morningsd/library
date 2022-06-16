package edu.demian.util.tag;

import java.util.Collection;

public final class ContainsFunctionTag {
    public static boolean contains(final Collection collection, final Object o) {
        if (collection == null || o == null) {
            return false;
        }
        return collection.contains(o);
    }
}
