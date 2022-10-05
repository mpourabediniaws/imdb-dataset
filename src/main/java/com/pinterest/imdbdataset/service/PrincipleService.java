package com.pinterest.imdbdataset.service;

import com.pinterest.imdbdataset.model.Principle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Slf4j
@Order(3)
public class PrincipleService implements CommandLineRunner {

    private static final String PRINCIPLE_FILE = "/excel/title.principles.tsv";

    private List<Principle> actorPrinciples = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource(PRINCIPLE_FILE);
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(resource.getFile()))) {
            tsvReader.readLine();
            String line;
            while ((line = tsvReader.readLine()) != null) {
                String[] rowItems = line.split("\t");

                if (rowItems.length == 6 && rowItems[3].equals("actor")) {

                    Principle principle = new Principle();
                    principle.setTConst(rowItems[0]);
                    principle.setOrdering(Integer.parseInt(rowItems[1]));
                    principle.setNConst(CellUtility.checkIsYearNull(rowItems[2]));
                    principle.setCategory(CellUtility.checkIsYearNull(rowItems[3]));
                    principle.setJob(rowItems[4]);
                    principle.setCharacters(rowItems[5]);

                    actorPrinciples.add(principle);
                }
            }
        }
        log.info("Finished principle import");
    }

    public List<String> fetchTitlesHaveSelectedActors(String firstActor, String secondActor) {

        List<String> titlesForFirstActor = this.actorPrinciples.parallelStream().filter(principle
                -> principle.getNConst().equals(firstActor)).map(Principle::getTConst).collect(Collectors.toList());
        List<String> titlesForSecondActor = this.actorPrinciples.parallelStream().filter(principle
                -> principle.getNConst().equals(secondActor)).map(Principle::getTConst).collect(Collectors.toList());

        titlesForFirstActor.retainAll(titlesForSecondActor);
        return titlesForFirstActor;
    }
}
