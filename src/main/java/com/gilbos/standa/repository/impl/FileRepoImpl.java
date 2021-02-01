package com.gilbos.standa.repository.impl;

import com.gilbos.standa.business.CsvFile;
import com.gilbos.standa.repository.FileRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository("FileRepository")
public class FileRepoImpl implements FileRepository {

    private Set<CsvFile> files;

    public FileRepoImpl() {
        files = new HashSet<>();
    }

    @Override
    public void add(CsvFile t) {
        files.add(t);
    }

    @Override
    public void add(Collection<CsvFile> t) {
        files.addAll(t);
    }

    @Override
    public Set<CsvFile> getAll() {
        return Collections.unmodifiableSet(files);
    }

    @Override
    public void clear() {
        files.clear();
    }

}
