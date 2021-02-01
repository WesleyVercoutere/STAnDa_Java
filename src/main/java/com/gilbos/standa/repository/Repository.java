package com.gilbos.standa.repository;

import java.util.Collection;

public interface Repository<T> {

    void add(T t);

    void add(Collection<T> t);

    Collection<T> getAll();

    void clear();

}
