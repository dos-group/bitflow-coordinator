import axios from "axios";

function getSession() {
  return JSON.parse(window.sessionStorage.getItem("session"))
}

function storeSession(session) {
  window.sessionStorage.setItem("session", JSON.stringify(session))
}

export function Initialize(baseUrl) {
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
  // TODO: make backend call to verify user credentials or get token (later)
  if (username !== "admin@gmail.com") {
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
  window.sessionStorage.removeItem("session")
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

