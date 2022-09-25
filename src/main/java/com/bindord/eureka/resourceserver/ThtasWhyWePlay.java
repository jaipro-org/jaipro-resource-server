package com.bindord.eureka.resourceserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ThtasWhyWePlay {

    public static void main(String[] args) {
        try {
            var result = new ObjectMapper().writeValueAsString(null);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
