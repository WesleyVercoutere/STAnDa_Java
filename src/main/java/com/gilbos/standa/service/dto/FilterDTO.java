package com.gilbos.standa.service.dto;

import java.util.ArrayList;
import java.util.List;

public class FilterDTO {

    private List<String> smarTwistIds;
    private List<String> recipes;
    private List<String> data;
    private List<String> types;
    private List<String> speeds;

    private String selectedST;
    private String selectedRecipe;
    private String selectedData;
    private String selectedType;
    private String selectedSpeed;

    private String hoveredSt;
    private boolean allFiltersSelected;

    private List<String> visisbilitySt;

    public FilterDTO() {
        smarTwistIds = new ArrayList<>();
        recipes = new ArrayList<>();
        data = new ArrayList<>();
        types = new ArrayList<>();
        speeds = new ArrayList<>();

        selectedST = "";
        selectedRecipe = "";
        selectedData = "";
        selectedType = "";
        selectedSpeed = "";

    }

    public List<String> getSmarTwistIds() {
        return smarTwistIds;
    }

    public void setSmarTwistIds(List<String> smarTwistIds) {
        this.smarTwistIds = smarTwistIds;
    }

    public List<String> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getSpeeds() {
        return speeds;
    }

    public void setSpeeds(List<String> speeds) {
        this.speeds = speeds;
    }

    public String getSelectedST() {
        return selectedST;
    }

    public void setSelectedST(String selectedST) {
        this.selectedST = selectedST;
    }

    public String getSelectedRecipe() {
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

    public List<String> getVisisbilitySt() {
        return visisbilitySt;
    }

    public void setVisisbilitySt(List<String> visisbilitySt) {
        this.visisbilitySt = visisbilitySt;
    }

}
