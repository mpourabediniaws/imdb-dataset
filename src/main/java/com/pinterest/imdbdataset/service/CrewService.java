package com.pinterest.imdbdataset.service;

import com.pinterest.imdbdataset.model.Crew;
import com.pinterest.imdbdataset.util.CellUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class CrewService implements CommandLineRunner {

    private static final String CREW_FILE = "/excel2/title.crew.tsv";

    private List<Crew> crewsWithSameDirectorsAndWriters = new ArrayList<>();

    private final NameService nameService;

    @Override
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource(CREW_FILE);
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(resource.getFile()))) {
            tsvReader.readLine();
            String line;
            while ((line = tsvReader.readLine()) != null) {
                String[] rowItems = line.split("\t");

                if (rowItems.length == 3) {

                    Crew crew = new Crew();
                    crew.setTConst(rowItems[0]);
                    crew.setDirectors(CellUtility.checkIsArrayNull(rowItems[1]));
                    crew.setWriters(CellUtility.checkIsArrayNull(rowItems[2]));
                    if (!crew.getDirectors().isEmpty() && !crew.getWriters().isEmpty() &&
                            crew.getDirectors().containsAll(crew.getWriters())) {
                        crewsWithSameDirectorsAndWriters.add(crew);
                    }
                }
            }
            log.info("Finished crew import");
        }
    }

    public List<String> fetchTitlesHaveSameDirecterAndWriterAndIsAlive() {
        return this.crewsWithSameDirectorsAndWriters.parallelStream().filter(crew ->
                        nameService.getAliveNames().containsAll(crew.getDirectors())).map(Crew::getTConst)
                .collect(Collectors.toList());
    }
}
