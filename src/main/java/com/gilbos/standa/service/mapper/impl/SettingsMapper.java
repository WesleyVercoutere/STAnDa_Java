package com.gilbos.standa.service.mapper.impl;

import com.gilbos.standa.business.Settings;
import com.gilbos.standa.service.dto.SettingsDTO;
import com.gilbos.standa.service.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service(value = "settingsMapper")
public class SettingsMapper implements Mapper<Settings, SettingsDTO> {

    @Override
    public Settings mapToObj(SettingsDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SettingsDTO mapToDTO(Settings settings) {

        if (settings == null)
            return null;

        SettingsDTO dto = new SettingsDTO();
        dto.setyAxisLowerBound(settings.getyAxisLowerBound());
        dto.setyAxisUpperBound(settings.getyAxisUpperBound());
        dto.setyAxisTickUnit(settings.getyAxisTickUnit());
        dto.setxAxisLowerBound(settings.getxAxisLowerBound());
        dto.setxAxisMinLowerBound(settings.getxAxisMinLowerBound());
        dto.setxAxisUpperBound(settings.getxAxisUpperBound());
        dto.setxAxisMaxUpperBound(settings.getxAxisMaxUpperBound());
        dto.setxAxisTickUnit(settings.getxAxisTickUnit());

        dto.setTimeBetweenSamples(Long.toString(settings.getTimeBetweenSamples() / 1000));
        dto.setTimeToZero(Long.toString(settings.getTimeToZero() / 1000));
        dto.setShowSymbols(settings.isShowSymbols());

        dto.setShowManualAverage(settings.isShowManualAverage());
        dto.setManualAverage(Double.toString(settings.getManualAverage()));
        dto.setManualBoundary(Double.toString(settings.getManualBoundary()));

        dto.setShowAverage(settings.isShowAverage());
        dto.setBoundaryTack(Double.toString(settings.getBoundaryTack()));
        dto.setBoundaryTwist(Double.toString(settings.getBoundaryTwist()));

        dto.setLowerBound(settings.getLowerBound());
        dto.setUpperBound(settings.getUpperBound());

        dto.setShowColdSamples(settings.isShowColdSamples());
        dto.setShowTeachingSamples(settings.isShowTeachingSamples());
        dto.setShowSpeedChangeSamples(settings.isShowSpeedChangeSamples());
        dto.setShowMonitorSamples(settings.isShowMonitorSamples());

        return dto;
    }

}
