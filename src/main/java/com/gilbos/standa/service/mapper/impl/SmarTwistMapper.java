package com.gilbos.standa.service.mapper.impl;

import com.gilbos.standa.business.SmarTwist;
import com.gilbos.standa.service.dto.SmarTwistDTO;
import com.gilbos.standa.service.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service(value = "smarTwistMapper")
public class SmarTwistMapper implements Mapper<SmarTwist, SmarTwistDTO> {

    @Override
    public SmarTwist mapToObj(SmarTwistDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SmarTwistDTO mapToDTO(SmarTwist st) {
        if (st == null)
            return null;

        SmarTwistDTO dto = new SmarTwistDTO();
        dto.setId(st.getId());

        return dto;
    }

}
