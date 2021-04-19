package com.rsystems.noriel.userrecomandations;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRecommendationsDAOService {

    private static List<Integer> recommendations = new ArrayList<Integer>();

    public List<Integer> getRecommendationsByUser() {
        for (int i = 0; i < 12; i++) {
            recommendations.add(i);
        }
        return recommendations;
    }

}
