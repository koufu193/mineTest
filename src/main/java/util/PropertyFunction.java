package util;

import fields.node.properties.MinMaxProperty;

public interface PropertyFunction<T extends MinMaxProperty<V>,V>{
    T get(V a,V b);
}
