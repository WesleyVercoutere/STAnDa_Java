package com.gilbos.standa.repository.impl;

import com.gilbos.standa.business.SmarTwist;
import com.gilbos.standa.repository.SmarTwistRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository("SmarTwistRepository")
public class SmarTwistRepoImpl implements SmarTwistRepository {

    private Set<SmarTwist> smarTwists;

    public SmarTwistRepoImpl() {
        super();
        this.smarTwists = new HashSet<>();
    }

    @Override
    public void add(SmarTwist t) {
        smarTwists.add(t);
    }

    @Override
    public void add(Collection<SmarTwist> t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<SmarTwist> getAll() {
        return Collections.unmodifiableSet(smarTwists);
    }

    @Override
    public void clear() {
        smarTwists.clear();
    }

}
