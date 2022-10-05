package com.pinterest.imdbdataset.resource;

import com.pinterest.imdbdataset.service.CrewService;
import com.pinterest.imdbdataset.service.PrincipleService;
import com.pinterest.imdbdataset.service.TitleService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "titles")
@RequiredArgsConstructor
public class TitleResource {

    private final CrewService crewService;
    private final PrincipleService principleService;
    private final TitleService titleService;
    private final MeterRegistry meterRegistry;
    private Counter apiCounter;

    @PostConstruct
    public void setUp() {
        apiCounter = Counter.builder("api.call")
                .description("The number of api calls happened")
                .register(meterRegistry);
    }

    @GetMapping
    public ResponseEntity<List<String>> getTitlesHaveSameDirecterAndWriterAndIsAlive() {

        apiCounter.increment();
        return ResponseEntity.ok(crewService.fetchTitlesHaveSameDirecterAndWriterAndIsAlive());
    }

    @GetMapping(path = "selected-actors")
    public ResponseEntity<List<String>> getTitlesWithSelectedActors(@RequestParam String firstActor,
                                                                    @RequestParam String secondActor) {

        apiCounter.increment();
        return ResponseEntity.ok(principleService.fetchTitlesHaveSelectedActors(firstActor, secondActor));
    }


    @GetMapping(path = "best-among-genre")
    public ResponseEntity<Map<String, String>> getBestTitles(@RequestParam String genre) {

        apiCounter.increment();
        return ResponseEntity.ok(
                titleService.fetchTitlesWithSelectedGenreAndGroupThemByYearAndSortThemByNumberOfVotesAndRatings(genre));
    }
}
