package com.gilbos.standa.repository.impl;

import com.gilbos.standa.business.FlowError;
import com.gilbos.standa.repository.FlowErrorRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository("FlowErrorRepository")
@Scope("prototype")
public class FlowErrorRepoImpl implements FlowErrorRepository {

    private List<FlowError> errorList;

    public FlowErrorRepoImpl() {
        errorList = new ArrayList<>();
    }

    @Override
    public void add(FlowError flowError) {
        errorList.add(flowError);
    }

    @Override
    public void add(Collection<FlowError> t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<FlowError> getAll() {
        return Collections.unmodifiableList(errorList);
    }

    @Override
    public void clear() {
        errorList.clear();
    }

}
