package com.example.clubmaker;

import java.util.Comparator;

public class ClubComparator implements Comparator<Club> {
    @Override
    public int compare(Club o1, Club o2) {
        return (int)(o2.score-o1.score);
    }
}
