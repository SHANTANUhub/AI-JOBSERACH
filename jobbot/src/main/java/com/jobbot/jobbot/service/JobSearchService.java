
package com.jobbot.jobbot.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class JobSearchService {

    private static final String API_KEY = "7dad89ab0bmsh18d646e25444f37p1ca2d7jsn2b3ddda352cd";

    public String searchJobs(String query) {
    try {
        String url = "https://jsearch.p.rapidapi.com/search?query=" + query.replace(" ", "%20") + "&page=1&num_pages=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", "jsearch.p.rapidapi.com")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println("API RESPONSE:\n" + body); // debug

        if (!body.contains("\"job_title\"")) {
            return "No jobs found for your query.";
        }

        String[] jobs = body.split("\"job_title\"");
        StringBuilder result = new StringBuilder("Here are some job results:\n");

        for (int i = 1; i < Math.min(4, jobs.length); i++) {
            String title = jobs[i].split(":")[1].split(",")[0].replace("\"", "").trim();
            String company = "Unknown";
            String city = "Location not specified";

            if (jobs[i].contains("\"employer_name\"")) {
                company = jobs[i].split("\"employer_name\"")[1].split(":")[1].split(",")[0].replace("\"", "").trim();
            }

if (jobs[i].contains("\"job_city\"")) {
    city = jobs[i].split("\"job_city\"")[1].split(":")[1].split(",")[0].replace("\"", "").trim();
} else if (jobs[i].contains("\"job_state\"")) {
    city = jobs[i].split("\"job_state\"")[1].split(":")[1].split(",")[0].replace("\"", "").trim();
} else if (jobs[i].contains("\"job_country\"")) {
    city = jobs[i].split("\"job_country\"")[1].split(":")[1].split(",")[0].replace("\"", "").trim();
} else if (jobs[i].contains("\"job_is_remote\":true")) {
    city = "Remote";
}


            result.append("- ").append(title).append(" at ").append(company).append(" (").append(city).append(")\n");
        }

        return result.toString();

    } catch (Exception e) {
        e.printStackTrace();
        return "Something went wrong while fetching jobs.";
    }
}

    
}
