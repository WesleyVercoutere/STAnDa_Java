package com.gilbos.standa.service.mapper.impl;

import com.gilbos.standa.business.CsvFile;
import com.gilbos.standa.service.dto.CsvFileDTO;
import com.gilbos.standa.service.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service(value = "csvFileMapper")
public class CsvFileMapper implements Mapper<CsvFile, CsvFileDTO> {

    @Override
    public CsvFile mapToObj(CsvFileDTO dto) {
        if (dto == null)
            return null;

        CsvFile csv = new CsvFile(dto.getUrl(), dto.getFileName());
        return csv;
    }

    @Override
    public CsvFileDTO mapToDTO(CsvFile b) {
        throw new UnsupportedOperationException();
    }


}
