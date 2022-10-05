package com.pinterest.imdbdataset.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Name {

    private String nConst;
    private String primaryName;
    private String birthYear;
    private String deathYear;
    private List<String> primaryProfession;
    private List<String> knownForTitles;
}
