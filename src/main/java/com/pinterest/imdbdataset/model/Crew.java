package com.pinterest.imdbdataset.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Crew {

    private String tConst;
    private List<String> directors;
    private List<String> writers;
}
