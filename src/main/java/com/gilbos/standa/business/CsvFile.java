package com.gilbos.standa.business;

import com.gilbos.standa.util.Data;
import com.gilbos.standa.util.Speed;
import com.gilbos.standa.util.Type;

public class CsvFile {

    String url;
    String fileName;
    boolean read;

    private String smarTwistId, recipe;
    private Data data;
    private Type type;
    private Speed speed;

    public CsvFile() {
        this.read = false;
    }

    public CsvFile(String url, String fileName) {
        this();
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getSmarTwistId() {
        return smarTwistId;
    }

    public void setSmarTwistId(String smarTwistId) {
        this.smarTwistId = smarTwistId;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
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
        CsvFile other = (CsvFile) obj;
        if (fileName == null) {
            return other.fileName == null;
        } else return fileName.equals(other.fileName);
    }

    @Override
    public String toString() {
        return "CsvFile [smarTwistId=" + smarTwistId + ", recipe=" + recipe + ", data=" + data + ", type=" + type
                + ", speed=" + speed + "]";
    }

}
