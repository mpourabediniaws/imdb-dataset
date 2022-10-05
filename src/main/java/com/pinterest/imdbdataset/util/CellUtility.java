package com.pinterest.imdbdataset.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CellUtility {

    public static Double checkIsDoubleNull(String field) {
        if (field.equals("\\N")) {
            return null;
        } else {
            return Double.valueOf(field);
        }
    }

    public static Boolean checkIsBooleanNull(String field) {
        if (field.equals("\\N")) {
            return null;
        } else {
            return Boolean.parseBoolean(field);
        }
    }

    public static String checkIsYearNull(String field) {
        if (field.equals("\\N")) {
            return null;
        } else {
            return field;
        }
    }

    public static List<String> checkIsArrayNull(String field) {
        if (field.equals("\\N")) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(field.split(","));
        }
    }
}
