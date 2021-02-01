package com.gilbos.standa.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;

public interface Mapper<B, D> {

    B mapToObj(D d);

    D mapToDTO(B b);

    default Set<B> mapToObj(Set<D> ds) {
        return ds.stream().map(this::mapToObj).collect(Collectors.toSet());
    }

    default Set<D> mapToDTO(Set<B> bs) {
        return bs.stream().map(this::mapToDTO).collect(Collectors.toSet());
    }

}
