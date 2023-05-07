package com.pim.coronavirustracker.services;

import com.pim.coronavirustracker.models.LocationStats;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {


    //URL with the data
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();


    @PostConstruct//annotation method that executes on app start
    @Scheduled(cron = "* * 1 * * *") //specified execute the first hour of every day (once every day)
    public void fetchVirusData() throws IOException, InterruptedException {

        //creating stats lists to populate allStats list
        List<LocationStats> newStats = new ArrayList<>();

        //make the request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        //get response
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        //create a bodyReader of the response
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        //parsing CSV /auto-detection headers
        Iterable<CSVRecord> records = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(csvBodyReader);

        //looping the records after the parse and populating the model
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size() -1)));
            System.out.println(locationStats);
            newStats.add(locationStats);
        }
        //reference the empty list to the populated list
        this.allStats = newStats;
    }

}
