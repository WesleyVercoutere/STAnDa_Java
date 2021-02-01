package com.gilbos.standa.service;

import com.gilbos.standa.business.CTSData;
import com.gilbos.standa.business.CsvFile;
import com.gilbos.standa.business.FlowData;
import com.gilbos.standa.service.manager.SettingsManager;
import com.gilbos.standa.service.manager.SmarTwistManager;
import com.gilbos.standa.util.DateUtil;
import com.gilbos.standa.util.Speed;
import com.gilbos.standa.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
@Scope("prototype")
public class ReadCsvFileRunnable implements Runnable {

    private CsvFile file;

    @Autowired
    private SmarTwistManager smarTwistManager;
    @Autowired
    private SettingsManager settingsManager;

    public void setCsvFile(CsvFile file) {
        this.file = file;
    }

    @Override
    public void run() {
        readFile();
    }

    private void readFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(file.getUrl()))) {

            String line;

            while ((line = br.readLine()) != null) {
                readLine(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readLine(String line) {
        String[] data = line.split(";");

        if ((data.length == 3) || (data.length == 9)) {
            addFirstDataToRepos(data);
        }

        if ((data.length == 4) || (data.length == 10)) {

            if (isAverage(data[2])) {
                addAverageToRepos(data);
            } else {
                addDataToRepos(data);
            }
        }

        if (data.length == 60) {
            addCtsData(data);
        }
    }

    private boolean isAverage(String type) {
        return type.contains("AVERAGE");
    }

    private void addFirstDataToRepos(String[] data) {
        String fileName = file.getFileName();
        String smarTwistId = file.getSmarTwistId();
        String recipe = file.getRecipe();
        Speed speed = file.getSpeed();
        Type type = file.getType();
        String sample = "FIRST_FLOW_DATA";
        long timeStamp = DateUtil.epochMilli(data[0]);
        double consumption = Double.parseDouble(data[2]);

        FlowData fd = new FlowData(fileName, smarTwistId, recipe, speed, type, sample, timeStamp, consumption);
        smarTwistManager.addData(fd);
        settingsManager.addTime(timeStamp);
    }

    private void addDataToRepos(String[] data) {
        String fileName = file.getFileName();
        String smarTwistId = file.getSmarTwistId();
        String recipe = file.getRecipe();
        Speed speed = file.getSpeed();
        Type type = file.getType();
        String sample = data[2];
        long timeStamp = DateUtil.epochMilli(data[0]);
        double consumption = Double.parseDouble(data[3]);

        FlowData fd = new FlowData(fileName, smarTwistId, recipe, speed, type, sample, timeStamp, consumption);
        smarTwistManager.addData(fd);
        settingsManager.addTime(timeStamp);
    }

    private void addAverageToRepos(String[] data) {
        String fileName = file.getFileName();
        String smarTwistId = file.getSmarTwistId();
        String recipe = file.getRecipe();
        Speed speed = file.getSpeed();
        Type type = file.getType();
        String sample = data[2];

        String timestamp = data[0].substring(3) + ".000";

        long timeStamp = DateUtil.epochMilli(timestamp);
        double consumption = Double.parseDouble(data[3]);

        if (consumption == -1) {
            consumption = 0;
        }

        FlowData fd = new FlowData(fileName, smarTwistId, recipe, speed, type, sample, timeStamp, consumption);
        smarTwistManager.addAverage(fd);
    }

    private void addCtsData(String[] data) {
        if (data[0].equals("DT")) return;

        String fileName = file.getFileName();
        String smarTwistId = file.getSmarTwistId();
        String recipe = file.getRecipe();
        Speed speed = file.getSpeed();

        Type type = Type.CtsS;

        if (data[1].equals("TwistZ_")){
            type = Type.CtsZ;
        }

        int nbrOfSegments = Integer.parseInt(data[4]);
        double realSpeed = Double.parseDouble(data[2]);

        String timestamp = data[0];
        long timeStamp = DateUtil.epochMilli(timestamp);

        int startIndex = 5;
        int totalTwist = 0;
        int nbrOfSegmentsOk = 0;
        double avgTwistLevel = 0;

        for (int i = startIndex; i < (nbrOfSegments + startIndex); i++) {
            int twistLevel = Integer.parseInt(data[i]);

            if (twistLevel > 0) {
                totalTwist += twistLevel;
                nbrOfSegmentsOk ++;
            }
        }

        if (nbrOfSegmentsOk > 0) {
            avgTwistLevel = (totalTwist / nbrOfSegmentsOk) / realSpeed;
        }

        CTSData cts = new CTSData(fileName, smarTwistId, recipe, speed, type, timeStamp, realSpeed, avgTwistLevel, nbrOfSegments);
        smarTwistManager.addData(cts);
        settingsManager.addTime(timeStamp);
    }

}
