package com.gilbos.standa.repository.impl;

import com.gilbos.standa.business.Filter;
import com.gilbos.standa.repository.FilterRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository("FilterRepository")
public class FilterRepoImpl implements FilterRepository {

    private Set<Filter> filters;

    public FilterRepoImpl() {
        super();
        this.filters = new HashSet<>();
    }

    @Override
    public void add(Filter t) {
        filters.add(t);
    }

    @Override
    public void add(Collection<Filter> t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Filter> getAll() {
        return Collections.unmodifiableSet(filters);
    }

    @Override
    public void clear() {
        filters.clear();
    }

    @Override
    public List<String> getSmarTwistIds() {
        return filters.stream().map(Filter::getSmarTwistId).distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public List<String> getRecipes() {
        return filters.stream().map(Filter::getRecipe).distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public List<String> getData() {
        return filters.stream().map(Filter::getData).distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public List<String> getTypes() {
        return filters.stream().map(Filter::getType).distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public List<String> getSpeeds() {
        return filters.stream().map(Filter::getSpeed).distinct().sorted().collect(Collectors.toList());
    }

}
