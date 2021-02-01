package com.gilbos.standa.business;

public class Filter {

    private String smarTwistId;
    private String customer;
    private String division;
    private String recipe;
    private String data;
    private String type;
    private String speed;

    public Filter() {

    }

    public String getSmarTwistId() {
        return smarTwistId;
    }

    public void setSmarTwistId(String smarTwistId) {
        this.smarTwistId = smarTwistId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((division == null) ? 0 : division.hashCode());
        result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
        result = prime * result + ((smarTwistId == null) ? 0 : smarTwistId.hashCode());
        result = prime * result + ((speed == null) ? 0 : speed.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Filter other = (Filter) obj;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (division == null) {
            if (other.division != null)
                return false;
        } else if (!division.equals(other.division))
            return false;
        if (recipe == null) {
            if (other.recipe != null)
                return false;
        } else if (!recipe.equals(other.recipe))
            return false;
        if (smarTwistId == null) {
            if (other.smarTwistId != null)
                return false;
        } else if (!smarTwistId.equals(other.smarTwistId))
            return false;
        if (speed == null) {
            if (other.speed != null)
                return false;
        } else if (!speed.equals(other.speed))
            return false;
        if (type == null) {
            return other.type == null;
        } else return type.equals(other.type);
    }

}
