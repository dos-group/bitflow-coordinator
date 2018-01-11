import axios from "axios";

export function Initialize(baseUrl) {
    axios.defaults.baseURL = baseUrl;
    let authHeader = window.sessionStorage.getItem("session");
    if (authHeader) {
        axios.defaults.headers.common["Authentication"] = authHeader;
    }
}

export function isLoggedIn() {
    return !!window.sessionStorage.getItem("session")
}

export function login(username, password) {
    // TODO: basic auth should be replaced with token based authentication

    // TODO: make backend call to verify user credentials or get token (later)

    let authHeader = "Basic " + btoa(username + ":" + password);
    axios.defaults.headers.common["Authentication"] = authHeader;
    window.sessionStorage.setItem("session", authHeader);
}

export function getInfo() {
    return axios.get("/info");

}
// Projects
export function getProjects() {
    return axios.get("/projects");
}
export function createProject(project) {
    return axios.post("/projects", project);
}
export function updateProject(projectId, updatedProject) {
    return axios.put("/project/" + projectId, updatedProject);
}
export function deleteProject(projectId) {
    return axios.delete("/project/" + projectId);
}

// Pipeline APIs
export function getPipelines() {
    return axios.get("/pipelines")
}
export function createPipeline(pipeline) {
    return axios.post("/pipelines", pipeline)
}
export function deletePipeline(pipelineId) {
    return axios.delete("/pipeline/" + pipelineId)
}

