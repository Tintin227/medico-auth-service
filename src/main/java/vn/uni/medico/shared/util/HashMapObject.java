package vn.uni.medico.shared.util;

import java.util.HashMap;

public class HashMapObject extends HashMap<Object, Object> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void merge(HashMapObject map) {
        if (map == null || map.isEmpty())
            return;
        putAll(map);
    }
}
