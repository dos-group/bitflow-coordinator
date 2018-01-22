import axios from "axios";

function getSession() {
  return JSON.parse(window.sessionStorage.getItem("session"))
}

function storeSession(session) {
  window.sessionStorage.setItem("session", JSON.stringify(session))
}

export function initialize(baseUrl) {
  axios.defaults.baseURL = baseUrl;
  let session = getSession();
  if (session) {
    axios.defaults.headers.common["Authentication"] = session.authHeader;
  }
}

export function getLoggedInUser() {
  let session = getSession();
  if (session) {
    return session.user
  } else {
    return null;
  }
}

export function isUserLoggedIn() {
  return !!getSession()
}

export function login(username, password) {
  //TODO: make backend call to verify user credentials or get token (later)
  if (username === "invalid@gmail.com") {
    return {error: "invalid password"};
  }
  
  let authHeader = "Basic " + btoa(username + ":" + password);
  axios.defaults.headers.common["Authentication"] = authHeader;
  //TODO: make call to backend to get profile
  let sessionObject = {authHeader: authHeader, user: {name: "student123", id: 123}};
  storeSession(sessionObject);
  return {user: sessionObject.user}
}

export function logout() {
  window.sessionStorage.removeItem("session");
}

export function getInfo() {
  return axios.get("/info");
}

export function getCapabilities() {
  return axios.get("/capabilities");
}

// Users
export function getUsers() {
  return axios.get("/users");
}
export function getUser(userId) {
  return axios.get("/user/" + userId);
}
export function getUsersInProject(projectId) {
  return axios.get("/project/" + projectId + "/users");
}
export function addUserToProject(projectId, userId) {
  return axios.post("/project/" + projectId + "/users/" + userId);
}
export function removeUserFromProject(projectId, userId) {
  return axios.delete("/project/" + projectId + "/users/" + userId);
}

// Projects
export function getProjects() {
  return axios.get("/projects");
}
export function getProject(projectId) {
  return axios.get("/project/" + projectId);
}
export function createProject(project) {
  return axios.post("/projects", project); //TODO: missing in API
}
export function updateProject(projectId, updatedProject) {
  return axios.put("/project/" + projectId, updatedProject); //TODO: missing in API
}
export function deleteProject(projectId) {
  return axios.delete("/project/" + projectId);
}

// Pipelines
export function getPipelines(projectId) {
  return axios.get("/project/" + projectId + "/pipelines");
}
export function getPipeline(projectId, pipelineId) {
  return axios.get("/project/" + projectId + "/pipeline/" + pipelineId);
}
export function createPipeline(projectId, pipeline) {
  return axios.post("/project/" + projectId + "/pipeline", pipeline);
}
export function updatePipeline(projectId, pipeline) {
  return axios.post("/project/" + projectId + "/pipeline/" + pipeline.id, pipeline);
}
export function startPipeline(projectId, pipelineId) {
  return axios.post("/project/" + projectId + "/pipeline/" + pipeline.id + "/start", null);
}
export function deletePipeline(projectId, pipelineId) {
  return axios.delete("/project/" + projectId + "/pipeline/" + pipelineId);
}

