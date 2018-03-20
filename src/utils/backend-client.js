import axios from "axios";

const authHeaderName = "authorization";

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
    axios.defaults.headers.common[authHeaderName] = session.authHeader;
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

export async function login(username, password) {
  let authHeader = "Basic " + btoa(username + ":" + password);
  axios.defaults.headers.common[authHeaderName] = authHeader;
  let response = await axios.post("/login");
  let sessionObject = {authHeader: authHeader, user: response.data};
  storeSession(sessionObject);
  return {user: response.data};
}

export function logout() {
  window.sessionStorage.removeItem("session");
}

export function getInfo() {
  return axios.get("/info");
}

export function getCapabilities(agentId) {
  return axios.get("/capabilities/" + agentId);
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
export async function getProjects() {

  let resp = await axios.get("/projects");
  let seenIds = [];
  const res = resp.data.filter(function(project){
    if (!seenIds.includes(project.ID)){
      seenIds.push(project.ID)
      return true
    }
    return false
  })

  return {data:res};
}

export function getProject(projectId) {
  return axios.get("/project/" + projectId);
}

export function createProject(project) {
  return axios.post("/project", project);
}

export function updateProject(projectId, updatedProject) {
  return axios.post("/project/" + projectId, updatedProject);
}

export function deleteProject(projectId) {
  return axios.delete("/project/" + projectId);
}

// Pipelines
export async function getPipelines(projectId) {
  let resp = await axios.get("/project/" + projectId + "/pipelines");
  let seenIds = [];
  const res = resp.data.filter(function(pipeline){
    if (!seenIds.includes(pipeline.ID)){
      seenIds.push(pipeline.ID)
      return true
    }
    return false
  })
  return {data:res};
}

export function getPipeline(projectId, pipelineId) {
  return axios.get("/project/" + projectId + "/pipeline/" + pipelineId);
}

export function createPipeline(projectId, pipeline) {
  return axios.post("/project/" + projectId + "/pipeline", pipeline);
}

export function updatePipeline(projectId, pipeline) {
  return axios.post("/project/" + projectId + "/pipeline/" + pipeline.ID, pipeline);
}

export function startPipeline(projectId, pipelineId) {
  return axios.post("/project/" + projectId + "/pipeline/" + pipelineId + "/start", {});
}

export function deletePipeline(projectId, pipelineId) {
  return axios.delete("/project/" + projectId + "/pipeline/" + pipelineId);
}

export async function getLastExecutionDateForPipelinesInProject(projectId) {
  let pipelinesResp = await getPipelines(projectId);
  var executionDates = {};

  for (let i = 0; i < pipelinesResp.data.length; i++) {
    const pipId = String(pipelinesResp.data[i].ID);
    const historyResp = await axios.get("/project/" + projectId + "/pipeline/" + pipId + "/history");
    if (historyResp.data[0] == undefined) {
      executionDates[pipId] = "no data";
    } else {
      const firstHistoryEntry = historyResp.data[0].StartedAt;
      executionDates[pipId] = firstHistoryEntry;
    }
  }
  return executionDates;
}
