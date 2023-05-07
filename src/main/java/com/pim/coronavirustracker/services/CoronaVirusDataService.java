package com.pim.coronavirustracker.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronaVirusDataService {


    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct//annotation method that executes on app start
    public void fetchVirusData() throws IOException, InterruptedException {
        //make the request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        //get response
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        //print temporally data in response
        System.out.println(httpResponse.body());
    }

}
