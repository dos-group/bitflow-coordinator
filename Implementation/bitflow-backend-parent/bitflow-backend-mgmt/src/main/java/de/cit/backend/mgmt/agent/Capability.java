package de.cit.backend.mgmt.agent;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Capability {

    @JsonProperty("Name")
    private String name = null;
    @JsonProperty("IsFork")
    private String isFork = null;
    @JsonProperty("Description")
    private String description = null;
    @JsonProperty("RequiredParams")
    private String[] requiredParams = null;
    @JsonProperty("OptionalParams")
    private String[] optionalParams = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsFork() {
        return this.isFork;
    }

    public void setIsFork(String isFork) {
        this.isFork = isFork;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getRequiredParams() {
        return this.requiredParams;
    }

    public void setRequiredParams(String[] requiredParams) {
        this.requiredParams = requiredParams;
    }

    public String[] getOptionalParams() {
        return this.optionalParams;
    }

    public void setOptionalParams(String[] optionalParams) {
        this.optionalParams = optionalParams;
    }

    @Override
    public final String toString() {
        return String.format("%s{name=%s, isFork=%s, description=%s, requiredParams=%s, optionalParams=%s}", getClass().getSimpleName()
                , this.name
                , this.isFork
                , this.description
                , this.requiredParams
                , this.optionalParams
        );
    }
}
