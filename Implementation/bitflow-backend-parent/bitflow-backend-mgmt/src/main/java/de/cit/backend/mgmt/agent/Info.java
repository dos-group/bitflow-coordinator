package de.cit.backend.mgmt.agent;


import com.fasterxml.jackson.annotation.*;

import java.util.Map;

public class Info {

    @JsonProperty("Hostname")
    private String hostname = null;
    @JsonProperty("Tags")
    private Map<String, String> tags = null;
    @JsonProperty("NumCores")
    private int numCores = 0;
    @JsonProperty("TotalMem")
    private long totalMem = 0L;
    @JsonProperty("UsedCpuCores")
    private double[] usedCpuCores = null;
    @JsonProperty("UsedCpu")
    private double usedCpu = 0.0;
    @JsonProperty("UsedMem")
    private double usedMem = 0.0;
    @JsonProperty("NumProcs")
    private int numProcs = 0;
    @JsonProperty("Goroutines")
    private int goroutines = 0;

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public int getNumCores() {
        return this.numCores;
    }

    public void setNumCores(int numCores) {
        this.numCores = numCores;
    }

    public long getTotalMem() {
        return this.totalMem;
    }

    public void setTotalMem(long totalMem) {
        this.totalMem = totalMem;
    }

    public double[] getUsedCpuCores() {
        return this.usedCpuCores;
    }

    public void setUsedCpuCores(double[] usedCpuCores) {
        this.usedCpuCores = usedCpuCores;
    }

    public double getUsedCpu() {
        return this.usedCpu;
    }

    public void setUsedCpu(double usedCpu) {
        this.usedCpu = usedCpu;
    }

    public double getUsedMem() {
        return this.usedMem;
    }

    public void setUsedMem(double usedMem) {
        this.usedMem = usedMem;
    }

    public int getNumProcs() {
        return this.numProcs;
    }

    public void setNumProcs(int numProcs) {
        this.numProcs = numProcs;
    }

    public int getGoroutines() {
        return this.goroutines;
    }

    public void setGoroutines(int goroutines) {
        this.goroutines = goroutines;
    }

    @Override
    public final String toString() {
        return String.format("%s{hostname=%s, tags=%s, numCores=%s, totalMem=%s, usedCpuCores=%s, usedCpu=%s, usedMem=%s, numProcs=%s, goRoutines=%s}", getClass().getSimpleName()
                , this.hostname
                , this.tags
                , this.numCores
                , this.totalMem
                , this.usedCpuCores
                , this.usedCpu
                , this.usedMem
                , this.numProcs
                , this.goroutines
        );
    }
}
