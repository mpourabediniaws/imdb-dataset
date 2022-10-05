package com.pinterest.imdbdataset.service;

import com.pinterest.imdbdataset.model.Rating;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Order(5)
public class RatingService implements CommandLineRunner {

    private static final String RATING_FILE = "/excel/title.ratings.tsv";

    private List<Rating> ratings = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource(RATING_FILE);
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(resource.getFile()))) {
            tsvReader.readLine();
            String line;
            while ((line = tsvReader.readLine()) != null) {
                String[] rowItems = line.split("\t");

                Rating rating = new Rating();
                rating.setTConst(rowItems[0]);
                rating.setAverageRating(Double.valueOf(rowItems[1]));
                rating.setNumVotes(Integer.parseInt(rowItems[2]));

                ratings.add(rating);
            }
        }
    }
}
