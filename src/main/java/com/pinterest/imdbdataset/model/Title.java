package com.pinterest.imdbdataset.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Title {

    private String tConst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private Boolean isAdult;
    private String startYear;
    private String endYear;
    private Double runtimeMinutes;
    private List<String> genres;
}
