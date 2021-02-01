package com.gilbos.standa.repository;

import com.gilbos.standa.business.Filter;

import java.util.List;

public interface FilterRepository extends Repository<Filter> {

    List<String> getSmarTwistIds();

    List<String> getRecipes();

    List<String> getData();

    List<String> getTypes();

    List<String> getSpeeds();

}
