package com.gilbos.standa.service.mapper.impl;

import com.gilbos.standa.business.SelectedFilters;
import com.gilbos.standa.service.dto.FilterDTO;
import com.gilbos.standa.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "filterMapper")
public class FilterMapper implements Mapper<SelectedFilters, FilterDTO> {

    @Override
    public SelectedFilters mapToObj(FilterDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilterDTO mapToDTO(SelectedFilters filters) {
        if (filters == null)
            return null;

        FilterDTO dto = new FilterDTO();

        dto.setSelectedST(filters.getSelectedST());
        dto.setSelectedRecipe(filters.getSelectedRecipe());
        dto.setSelectedData(filters.getSelectedData());
        dto.setSelectedType(filters.getSelectedType());
        dto.setSelectedSpeed(filters.getSelectedSpeed());
        dto.setHoveredSt(filters.getHoveredSt());
        dto.setAllFiltersSelected(filters.isAllFiltersSelected());

        List<String> st = filters.getVisibilityST().entrySet().stream().filter(entry -> !entry.getValue()).map(Map.Entry::getKey).collect(Collectors.toList());

        dto.setVisisbilitySt(st);

        return dto;
    }

}
