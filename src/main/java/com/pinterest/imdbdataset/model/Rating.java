package com.pinterest.imdbdataset.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Rating {

    private String tConst;
    private double averageRating;
    private int numVotes;
}
