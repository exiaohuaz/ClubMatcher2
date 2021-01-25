package com.example.clubmaker;

import java.util.Arrays;
import java.util.List;

public class TFIDF {

    public double tf(List<String> document, String term) {
        double result = 0;
        for (String word : document) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / document.size();
    }

    public double idf(List<List<String>> documents, String term) {
        double n = 0;
        for (List<String> doc : documents) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(documents.size() / n);
    }

    public double calc_TFIDF(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }

}