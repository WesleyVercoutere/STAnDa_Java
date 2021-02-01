package com.gilbos.standa.service.dto;

public class CsvFileDTO {

    String url;
    String fileName;

    public CsvFileDTO() {
        super();
    }

    public CsvFileDTO(String url, String fileName) {
        super();
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

}
