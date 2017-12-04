package de.cit.backend.mgmt.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class RemoteAgent {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl;

    public RemoteAgent(final String url) {
        baseUrl = url.endsWith("/") ? url : url + "/";
    }

    public Info getInfo() throws UnirestException, IOException {
        final HttpResponse<String> response = Unirest.get(baseUrl + "info").asString();
        return mapper.readerFor(Info.class).readValue(response.getBody());
    }

    public Capability [] getCapabilities() throws UnirestException, IOException {
        final HttpResponse<String> response = Unirest.get(baseUrl + "capabilities").asString();
        return mapper.readerFor(Capability[].class).readValue(response.getBody());
    }

    public boolean isAvailable() {
        try {
            if (Unirest.get(baseUrl+"ping").asString().getStatus() == 200) {
                return true;
            }
        } catch (UnirestException e) {
            // nothing to do
        }
        return false;
    }

}
