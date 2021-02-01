package com.gilbos.standa.service.manager;

import com.gilbos.standa.business.CsvFile;
import com.gilbos.standa.repository.FileRepository;
import com.gilbos.standa.service.ReadCsvFileRunnable;
import com.gilbos.standa.service.dto.CsvFileDTO;
import com.gilbos.standa.service.mapper.impl.CsvFileMapper;
import com.gilbos.standa.util.Data;
import com.gilbos.standa.util.Speed;
import com.gilbos.standa.util.Type;
import com.gilbos.standa.util.UpdateArgs;
import com.gilbos.standa.util.observer.Observable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class FileManager extends Observable {

    private FileRepository fileRepo;
    private CsvFileMapper mapper;
    private FilterManager filterManager;
    private SettingsManager settingsManager;
    private SmarTwistManager smarTwistManager;
    private ApplicationContext applicationContext;

    private boolean fileNameError;
    private UpdateArgs updateArg;

    public FileManager(FileRepository fileRepository,
                       CsvFileMapper csvFileMapper,
                       FilterManager filterManager,
                       SettingsManager settingsManager,
                       SmarTwistManager smarTwistManager,
                       ApplicationContext applicationContext) {
        this.fileRepo = fileRepository;
        this.mapper = csvFileMapper;
        this.filterManager = filterManager;
        this.settingsManager = settingsManager;
        this.smarTwistManager = smarTwistManager;
        this.applicationContext = applicationContext;
        this.fileNameError = false;
        this.updateArg = UpdateArgs.FILES_NEW;
    }

    public boolean isEmpty() {
        return fileRepo.getAll().isEmpty();
    }

    public void addFiles(List<CsvFileDTO> fileDtos) {
        System.out.println("Start reading files");
        long startTime = System.currentTimeMillis();


        setChanged();
        notifyObservers(UpdateArgs.START_READING_FILES);

        // Check filenames
        Set<CsvFile> okFiles = areFileNamesOk(fileDtos);

        // Add to repository
        fileRepo.add(okFiles);

        // Build filter data
        filterManager.createFilters(okFiles);

        // read the files
        readFiles(okFiles);

        settingsManager.setInitialXAxisSettings();

        setChanged();
        notifyObservers(UpdateArgs.END_READING_FILES);

        if (fileNameError) {
            if (UpdateArgs.FILES_NEW.equals(updateArg)) {
                updateArg = UpdateArgs.FILES_NEW_ERRORS;
            } else {
                updateArg = UpdateArgs.FILES_ADDED_ERRORS;
            }
        }

        setChanged();
        notifyObservers(updateArg);

        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) / 1000 ;

        System.out.println("Stop reading files in : " + totalTime + "sec");
    }

    public void addFiles(String arg, List<CsvFileDTO> fileDtos) {

        if (!arg.equals("Cancel")) {
            updateArg = UpdateArgs.FILES_ADDED;

            if (arg.equals("New")) {
                updateArg = UpdateArgs.FILES_NEW;
                fileRepo.clear();
                filterManager.clear();
                smarTwistManager.clear();
                settingsManager.clear();
            }

            addFiles(fileDtos);
        }
    }

    private Set<CsvFile> areFileNamesOk(List<CsvFileDTO> fileDtos) {
        return fileDtos.stream().filter(this::isFileNameOk).map(this::mapToCsvFile).collect(Collectors.toSet());
    }

    /**
     * example filenames:
     *
     * flow data:
     *  FD_26039.20_1afd847fecb12156a75e3afb35d75ec46a4c66db_Stage2_Tack_Full_2020-01-13-135520.csv
     *
     * cts data:
     *  CTS_GMS1_26134.1_0042f33e3b71e5b31d4403189bcea8fb90fef1c4_Buffer_2020-11-09-22626.csv
     *
     */
    private boolean isFileNameOk(CsvFileDTO dto) {

        if (fileRepo.getAll().stream().map(CsvFile::getFileName).equals(dto.getFileName())) {
            return false;
        }

        int oldTack = 6;
        int oldTwist = 5;
        int newTack = 7;
        int newTwist = 6;
        int rotational = 10;
        int cts = 6;

        String[] fileName = dto.getFileName().split("_");
        String regexNew = "FD|RD|CTS";
        String regexOld = "Twist[SZ]|Stage[12]";

        // New file names
        if ((fileName[0].matches(regexNew)) && ((fileName.length == newTwist) // for Twist signal
                || (fileName.length == newTack) // for Tack signal
                || (fileName.length == rotational)
                || (fileName.length == cts))) // for rotational data
            return true;

        // Old file names - only flow data
        if ((fileName[2].matches(regexOld)) && ((fileName.length == oldTack) // for Twist signal
                || (fileName.length == oldTwist))) // for Tack signal
            return true;

        fileNameError = true;
        return false;
    }

    private CsvFile mapToCsvFile(CsvFileDTO dto) {
        String regexFd = "FD";
        String regexRd = "RD";
        String regexCTS = "CTS";

        String[] fileName = dto.getFileName().split("_");

        if (fileName[0].matches(regexFd))
            return mapFlowFile(dto, fileName);

        if (fileName[0].matches(regexRd))
            return mapRotationalFile(dto, fileName);

        if (fileName[0].matches(regexCTS))
            return mapCtsFile(dto, fileName);

        return mapUnknownFile(dto, fileName);
    }

    private CsvFile mapFlowFile(CsvFileDTO dto, String[] fileName) {
        //FD_26040.3_2f79a351ed106d3e8c493e31d1fb090d3e45e2fa_TwistS_Turtle_2019-02-07-1
        //FD_26040.3_2f79a351ed106d3e8c493e31d1fb090d3e45e2fa_Stage1_Tack_Turtle_2019-02-07-3


        CsvFile file = mapper.mapToObj(dto);

        file.setData(Data.FLOW);
        file.setSmarTwistId(fileName[1]);
        file.setRecipe(fileName[2]);

        if (fileName.length == 6) {
            file.setType(Type.valueOf(fileName[3]));
            file.setSpeed(Speed.valueOf(fileName[4]));
        }

        if (fileName.length == 7) {
            if (fileName[3].matches("Stage1")) {
                file.setType(Type.Tack1);
            } else {
                file.setType(Type.Tack2);
            }

            file.setSpeed(Speed.valueOf(fileName[5]));
        }

        return file;

    }

    private CsvFile mapRotationalFile(CsvFileDTO dto, String[] fileName) {
        // TODO create rot file
        return null;
    }

    private CsvFile mapCtsFile(CsvFileDTO dto, String[] fileName) {
        //CTS_GMS1_26134.1_0042f33e3b71e5b31d4403189bcea8fb90fef1c4_Buffer_2020-11-09-22626

        CsvFile file = mapper.mapToObj(dto);

        file.setData(Data.CTS);
        file.setSmarTwistId(fileName[2]);
        file.setRecipe(fileName[3]);
        file.setSpeed(Speed.valueOf(fileName[4]));

        return file;
    }

    private CsvFile mapUnknownFile(CsvFileDTO dto, String[] fileName) {
        // 26039.2_1bb34f7d97a7afd09603303b6e39f60fd3f7be94_Stage2_Tack_Turtle_2018-12-11-4
        // 26039.2_1bb34f7d97a7afd09603303b6e39f60fd3f7be94_TwistS_Turtle_2018-12-10-1

        CsvFile file = mapper.mapToObj(dto);

        file.setData(Data.FLOW);
        file.setSmarTwistId(fileName[0]);
        file.setRecipe(fileName[1]);

        if (fileName.length == 5) {
            file.setType(Type.valueOf(fileName[2]));
            file.setSpeed(Speed.valueOf(fileName[3]));
        }

        if (fileName.length == 6) {
            if (fileName[2].matches("Stage1")) {
                file.setType(Type.Tack1);
            } else {
                file.setType(Type.Tack2);
            }

            file.setSpeed(Speed.valueOf(fileName[4]));
        }

        return file;
    }

    private void readFiles(Set<CsvFile> okFiles) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        List<Callable<Object>> todo = new ArrayList<>(okFiles.size());

        Iterator<CsvFile> it = okFiles.iterator();

        while (it.hasNext()) {
            todo.add(Executors.callable(getRunnable((CsvFile)it.next())));
        }

        try {
            es.invokeAll(todo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        es.shutdown();
    }

    private Runnable getRunnable(CsvFile file) {
        ReadCsvFileRunnable runner = applicationContext.getBean(ReadCsvFileRunnable.class);
        runner.setCsvFile(file);
        return runner;
    }

}
