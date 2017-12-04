package de.cit.backend.mgmt;


import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.cit.backend.mgmt.agent.Capability;
import de.cit.backend.mgmt.agent.RemoteAgent;

import java.io.File;
import java.io.IOException;

public final class AgentManager {

    void init() {
        // Load known agents from database
    }

    void start() {
        // start to ping agents and update capabilities...
    }

    void stop() {
        // stop pinging agents
    }

    public static void main(final String[] args) throws UnirestException, IOException {
        final RemoteAgent agent = new RemoteAgent("http://10.200.2.231:8080");
        if (!agent.isAvailable()) {
            System.out.println("AgentManager is offline!");
            System.exit(0);
        }
        System.out.println("Info:");
        System.out.println(agent.getInfo());
        System.out.println();
        System.out.println("Capabilities:");
        for(Capability capability : agent.getCapabilities()) {
            System.out.println(capability);
        }
    }


}
