package com.gilbos.standa.repository.impl;

import com.gilbos.standa.business.STCAverageError;
import com.gilbos.standa.repository.STCAverageRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository("STCAverageRepository")
@Scope("prototype")
public class STCAverageRepoImpl implements STCAverageRepository {

    private List<STCAverageError> averages;

    public STCAverageRepoImpl() {
        averages = new ArrayList<>();
    }

    @Override
    public void add(STCAverageError stcAverage) {
        averages.add(stcAverage);
    }

    @Override
    public void add(Collection<STCAverageError> t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<STCAverageError> getAll() {
        return Collections.unmodifiableList(averages);
    }

    @Override
    public void clear() {
        averages.clear();
    }

}
