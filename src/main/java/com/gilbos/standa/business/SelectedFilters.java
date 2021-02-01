package com.gilbos.standa.business;

import java.util.HashMap;
import java.util.Map;

public class SelectedFilters {

    private String selectedST;
    private String selectedRecipe;
    private String selectedData;
    private String selectedType;
    private String selectedSpeed;
    private String hoveredSt;
    private boolean allFiltersSelected;
    private Map<String, Boolean> visibilityST;

    public SelectedFilters() {
        this.selectedST = "All";
        this.selectedRecipe = "All";
        this.selectedData = "All";
        this.selectedType = "All";
        this.selectedSpeed = "All";
        this.hoveredSt = "All";

        this.allFiltersSelected = false;
        this.visibilityST = new HashMap<>();
    }

    public String getSelectedST() {
        return selectedST;
    }

    public void setSelectedST(String selectedST) {
        this.selectedST = selectedST;
    }

    public String getSelectedRecipe() {
        if (selectedRecipe == null) return "All";

        return selectedRecipe;
    }

    public void setSelectedRecipe(String selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    public String getSelectedData() {
        return selectedData;
    }

    public void setSelectedData(String selectedData) {
        this.selectedData = selectedData;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public String getSelectedSpeed() {
        return selectedSpeed;
    }

    public void setSelectedSpeed(String selectedSpeed) {
        this.selectedSpeed = selectedSpeed;
    }

    public String getHoveredSt() {
        return hoveredSt;
    }

    public void setHoveredSt(String hoveredSt) {
        this.hoveredSt = hoveredSt;
    }

    public boolean isAllFiltersSelected() {
        return allFiltersSelected;
    }

    public void setAllFiltersSelected(boolean allFiltersSelected) {
        this.allFiltersSelected = allFiltersSelected;
    }

    public Map<String, Boolean> getVisibilityST() {
        return visibilityST;
    }

    public void setVisibilityST(Map<String, Boolean> visibilityST) {
        this.visibilityST = visibilityST;
    }

}
