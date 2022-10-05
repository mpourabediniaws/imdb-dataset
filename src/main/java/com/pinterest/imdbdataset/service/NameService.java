package com.pinterest.imdbdataset.service;

import com.pinterest.imdbdataset.model.Name;
import com.pinterest.imdbdataset.util.CellUtility;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Component
@Getter
@Slf4j
@Order(1)
public class NameService implements CommandLineRunner {

    private static final String NAME_BASIC_FILE = "/excel/name.basics.tsv";

    private Set<String> aliveNames = new TreeSet<>();

    @Override
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource(NAME_BASIC_FILE);
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(resource.getFile()))) {
            tsvReader.readLine();
            String line;
            while ((line = tsvReader.readLine()) != null) {
                String[] rowItems = line.split("\t");

                Name name = new Name();
                name.setNConst(rowItems[0]);
//                name.setPrimaryName(rowItems[1]);
//                name.setBirthYear(CellUtility.checkIsYearNull(rowItems[2]));
                name.setDeathYear(CellUtility.checkIsYearNull(rowItems[3]));
//                name.setPrimaryProfession(Arrays.asList(rowItems[4].split(",")));
//                name.setKnownForTitles(Arrays.asList(rowItems[5].split(",")));
                if (name.getDeathYear() == null) {
                    aliveNames.add(name.getNConst());
                }
            }
        }
        log.info("Finished name import");
    }
}
