package com.gilbos.standa.service.manager;

import com.gilbos.standa.business.CsvFile;
import com.gilbos.standa.business.Filter;
import com.gilbos.standa.business.SelectedFilters;
import com.gilbos.standa.repository.FilterRepository;
import com.gilbos.standa.service.dto.FilterDTO;
import com.gilbos.standa.service.mapper.impl.FilterMapper;
import com.gilbos.standa.util.Data;
import com.gilbos.standa.util.Type;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilterManager extends Observable {

    private FilterRepository filterRepo;
    private SelectedFilters selectedFilters;
    private FilterMapper mapper;

    public FilterManager(FilterRepository filterRepository, SelectedFilters selectedFilters, FilterMapper filterMapper) {
        this.filterRepo = filterRepository;
        this.selectedFilters = selectedFilters;
        this.mapper = filterMapper;
    }

    public void createFilters(Set<CsvFile> files) {
        files.forEach(this::addFilters);
    }

    private void addFilters(CsvFile file) {

        if (file.getData() == Data.FLOW) {
            Filter filter = mapToFilter(file);
            filter.setType(file.getType().toString());
            filterRepo.add(filter);
        } else {
            Filter filterS = mapToFilter(file);
            filterS.setType(Type.CtsS.toString());
            filterRepo.add(filterS);

            Filter filterZ = mapToFilter(file);
            filterZ.setType(Type.CtsZ.toString());
            filterRepo.add(filterZ);
        }

    }

    private Filter mapToFilter(CsvFile file) {
        Filter filter = new Filter();

        filter.setSmarTwistId(file.getSmarTwistId() == null? "Id" : file.getSmarTwistId());
        filter.setRecipe(file.getRecipe());
        filter.setData(file.getData().toString());
        filter.setSpeed(file.getSpeed().toString());

        return filter;
    }

    public FilterDTO getFilters() {
        FilterDTO dto = mapper.mapToDTO(selectedFilters);

        dto.setSmarTwistIds(filterRepo.getAll().stream().filter(this::filterRecipe).filter(this::filterType)
                .filter(this::filterSpeed).map(Filter::getSmarTwistId).distinct().sorted()
                .collect(Collectors.toList()));

        dto.setRecipes(filterRepo.getAll().stream().filter(this::filterId).filter(this::filterType)
                .filter(this::filterSpeed).map(Filter::getRecipe).distinct().sorted().collect(Collectors.toList()));

        dto.setTypes(filterRepo.getAll().stream().filter(this::filterId).filter(this::filterRecipe)
                .filter(this::filterSpeed).map(Filter::getType).distinct().sorted().collect(Collectors.toList()));

        dto.setSpeeds(filterRepo.getAll().stream().filter(this::filterId).filter(this::filterRecipe)
                .filter(this::filterType).map(Filter::getSpeed).distinct().sorted().collect(Collectors.toList()));

        return dto;
    }

    public void updateSelectedFilters(FilterDTO dto) {
        selectedFilters.setSelectedST(dto.getSelectedST());
        selectedFilters.setSelectedRecipe(dto.getSelectedRecipe());
        selectedFilters.setSelectedData(dto.getSelectedData());
        selectedFilters.setSelectedType(dto.getSelectedType());
        selectedFilters.setSelectedSpeed(dto.getSelectedSpeed());

        setAllFiltersSelected();

        setChanged();
        notifyObservers(UpdateArgs.FILTERS_CHANGED);
    }

    private void setAllFiltersSelected() {
        boolean selected = false;

        if (!selectedFilters.getSelectedST().equals("All") && !selectedFilters.getSelectedRecipe().equals("All")
                && !selectedFilters.getSelectedType().equals("All")
                && !selectedFilters.getSelectedSpeed().equals("All")) {
            selected = true;
        }

        selectedFilters.setAllFiltersSelected(selected);
    }

    public void setSelectedFiltersToAll() {
        selectedFilters.setSelectedST("All");
        selectedFilters.setSelectedRecipe("All");
        selectedFilters.setSelectedData("All");
        selectedFilters.setSelectedType("All");
        selectedFilters.setSelectedSpeed("All");

        selectedFilters.setVisibilityST(new HashMap<>());
    }

    private boolean filterId(Filter filter) {
        return selectedFilters.getSelectedST().equals("All")
                || selectedFilters.getSelectedST().equals(filter.getSmarTwistId());
    }

    private boolean filterRecipe(Filter filter) {
        return selectedFilters.getSelectedRecipe().equals("All")
                || selectedFilters.getSelectedRecipe().equals(filter.getRecipe());
    }

    private boolean filterType(Filter filter) {
        return selectedFilters.getSelectedType().equals("All")
                || selectedFilters.getSelectedType().equals(filter.getType());
    }

    private boolean filterSpeed(Filter filter) {
        return selectedFilters.getSelectedSpeed().equals("All")
                || selectedFilters.getSelectedSpeed().equals(filter.getSpeed());
    }

    public void highlightLine(String id) {
        selectedFilters.setHoveredSt(id);

        setChanged();
        notifyObservers(UpdateArgs.HOVER);
    }

    public void resetColors() {
        setChanged();
        notifyObservers(UpdateArgs.UNHOVER);
    }

    public void clear() {
        filterRepo.clear();
    }

    public void toggleLineVisibility(String id, boolean visible) {
        setChanged();

        selectedFilters.getVisibilityST().put(id, visible);

        notifyObservers(UpdateArgs.UPDATE_VISIBILITY);
    }

}
