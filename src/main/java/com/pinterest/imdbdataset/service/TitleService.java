package com.pinterest.imdbdataset.service;

import com.pinterest.imdbdataset.model.Rating;
import com.pinterest.imdbdataset.model.Title;
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
import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
@Order(4)
public class TitleService implements CommandLineRunner {

    private static final String TITLE_BASIC_FILE = "/excel/title.basics.tsv";

    private Map<String, List<Title>> titles = new HashMap<>();

    private final RatingService ratingService;

    @Override
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource(TITLE_BASIC_FILE);
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(resource.getFile()))) {
            tsvReader.readLine();
            String line;
            while ((line = tsvReader.readLine()) != null) {
                String[] rowItems = line.split("\t");

                Title title = new Title();
                title.setTConst(rowItems[0]);
                title.setTitleType(rowItems[1]);
                title.setPrimaryTitle(rowItems[2]);
                title.setOriginalTitle(rowItems[3]);
                title.setIsAdult(CellUtility.checkIsBooleanNull(rowItems[4]));
                title.setStartYear(CellUtility.checkIsYearNull(rowItems[5]));
                title.setEndYear(CellUtility.checkIsYearNull(rowItems[6]));
                title.setRuntimeMinutes(CellUtility.checkIsDoubleNull(rowItems[7]));
                title.setGenres(Arrays.asList(rowItems[8].split(",")));

                if (title.getStartYear() != null) {
                    List<Title> titlesForSelectedYear = titles.getOrDefault(title.getStartYear(), new ArrayList<>());
                    titlesForSelectedYear.add(title);
                    titles.put(title.getStartYear(), titlesForSelectedYear);
                }
            }
        }
        log.info("Finished title import");
    }

    public Map<String, String> fetchTitlesWithSelectedGenreAndGroupThemByYearAndSortThemByNumberOfVotesAndRatings(String genre) {
        Map<String, String> bestTitleOfEachYear = new HashMap<>();
        for (Map.Entry<String, List<Title>> entry : titles.entrySet()) {
            Set<String> titleIdsWithSelectedGenre = entry.getValue().parallelStream().filter(title -> title.getGenres().contains(genre))
                    .map(Title::getTConst).collect(Collectors.toCollection(() -> new TreeSet<>()));
            Optional<Rating> maxRating = ratingService.getRatings().parallelStream().filter(rating ->
                            titleIdsWithSelectedGenre.contains(rating.getTConst()))
                    .max(Comparator.comparing(Rating::getNumVotes).thenComparing((Rating::getAverageRating)));
            if (maxRating.isPresent()) {
                bestTitleOfEachYear.put(entry.getKey(), maxRating.get().getTConst());
            }
        }
        return bestTitleOfEachYear;
    }
}
