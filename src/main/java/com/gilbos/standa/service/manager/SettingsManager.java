package com.gilbos.standa.service.manager;

import com.gilbos.standa.business.Settings;
import com.gilbos.standa.service.dto.FooterDTO;
import com.gilbos.standa.service.dto.SettingsDTO;
import com.gilbos.standa.service.mapper.impl.SettingsMapper;
import com.gilbos.standa.util.DateUtil;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import org.springframework.stereotype.Service;

@Service
public class SettingsManager extends Observable {

    private Settings settings;
    private SettingsMapper mapper;

    public SettingsManager(Settings settings, SettingsMapper settingsMapper) {
        this.settings = settings;
        this.mapper = settingsMapper;
    }

    public SettingsDTO getSettings() {
        return mapper.mapToDTO(settings);
    }

    public void setYAxisSettings(SettingsDTO dto) {
        settings.setyAxisUpperBound(dto.getyAxisUpperBound());
        settings.setyAxisLowerBound(dto.getyAxisLowerBound());
        settings.setyAxisTickUnit(dto.getyAxisTickUnit());

        setChanged();
        notifyObservers(UpdateArgs.CHANGED_Y_AXIS_VALUES);
    }

    public void setGeneralSettings(SettingsDTO dto) {
        settings.setTimeBetweenSamples((Long.parseLong(dto.getTimeBetweenSamples())) * 1000);
        settings.setTimeToZero((Long.parseLong(dto.getTimeToZero())) * 1000);
        settings.setShowSymbols(dto.isShowSymbols());

        settings.setShowManualAverage(dto.isShowManualAverage());
        settings.setManualAverage(Double.parseDouble(dto.getManualAverage()));
        settings.setManualBoundary(Double.parseDouble(dto.getManualBoundary()));

        settings.setShowAverage(dto.isShowAverage());
        settings.setBoundaryTack(Double.parseDouble(dto.getBoundaryTack()));
        settings.setBoundaryTwist(Double.parseDouble(dto.getBoundaryTwist()));

        settings.setShowColdSamples(dto.isShowColdSamples());
        settings.setShowTeachingSamples(dto.isShowTeachingSamples());
        settings.setShowSpeedChangeSamples(dto.isShowSpeedChangeSamples());
        settings.setShowMonitorSamples(dto.isShowMonitorSamples());

        calculateBoundaries();

        setChanged();
        notifyObservers(UpdateArgs.CHANGED_SETTINGS);
    }

    private void calculateBoundaries() {

        double average = settings.getManualAverage();

        double lower = average * (1 - (settings.getManualBoundary() / 100));
        double upper = average * (1 + (settings.getManualBoundary() / 100));

        settings.setLowerBound(lower);
        settings.setUpperBound(upper);
    }

    public void setInitialXAxisSettings() {
        settings.setxAxisMinLowerBound(settings.getxAxisMinLowerBound());
        settings.setxAxisMaxUpperBound(settings.getxAxisMaxUpperBound());
        settings.setxAxisLowerBound(settings.getxAxisMinLowerBound());
        settings.setxAxisUpperBound(settings.getxAxisMaxUpperBound());

        calculateTickUnit();
    }

    public void updateXAxisSettings(SettingsDTO dto) {
        settings.setxAxisLowerBound(dto.getxAxisLowerBound());
        settings.setxAxisUpperBound(dto.getxAxisUpperBound());

        calculateTickUnit();
    }

    public void calculateTickUnit() {
        double min = settings.getxAxisLowerBound();
        double max = settings.getxAxisUpperBound();
        double tick = (max - min) / 10;

        settings.setxAxisTickUnit(tick);

        setChanged();
        notifyObservers(UpdateArgs.CHANGED_X_AXIS_VALUES);
    }

    public synchronized void addTime(long timeStamp) {

        if (settings.getxAxisMinLowerBound() == 0) {
            settings.setxAxisMinLowerBound(timeStamp);
        } else {
            if (timeStamp < settings.getxAxisMinLowerBound()) {
                settings.setxAxisMinLowerBound(timeStamp);
            }
        }

        if (timeStamp > settings.getxAxisMaxUpperBound()) {
            settings.setxAxisMaxUpperBound(timeStamp);
        }
    }

    public void clear() {
        settings.setxAxisMinLowerBound(0);
        settings.setxAxisMaxUpperBound(0);
    }

    public FooterDTO getFooterDTO() {
        FooterDTO dto = new FooterDTO();
        dto.setTickUnit(DateUtil.fromMilli(settings.getxAxisTickUnit()));

        double totalTime = settings.getxAxisUpperBound() - settings.getxAxisLowerBound();
        dto.setTotalAxisTime(DateUtil.fromMilli(totalTime));

        return dto;
    }

}
