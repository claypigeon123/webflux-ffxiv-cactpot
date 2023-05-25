package com.cp.minigames.minicactpot.domain.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParamsBuilder {
    private final MultiValueMap<String, String> queryParams;

    private QueryParamsBuilder() {
        queryParams = new LinkedMultiValueMap<>();
    }

    public static QueryParamsBuilder init() {
        return new QueryParamsBuilder();
    }

    public QueryParamsBuilder query(String queryParam, String value) {
        queryParams.add(queryParam, value);
        return this;
    }

    public QueryParamsBuilder query(String queryParam, List<String> values) {
        queryParams.addAll(queryParam, values);
        return this;
    }

    public MultiValueMap<String, String> buildMultiValueMap() {
        return queryParams;
    }

    public Map<String, String> buildSingleValueMap() {
        return queryParams.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> String.join(",", entry.getValue()),
                (s1, s2) -> String.join(",", s1, s2)
            ));
    }

}
