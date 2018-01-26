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

export function login(username, password) {
  return new Promise(function (resolve, reject) {
      let authHeader = "Basic " + btoa(username + ":" + password);
      axios.defaults.headers.common[authHeaderName] = authHeader;
      axios.post("/login").then(function (response) {
          let sessionObject = {authHeader: authHeader, user: response.data};
          storeSession(sessionObject);
          resolve({user: response.data})
        }, function (error) {
          reject(error.response);
        }
      )
    }
  );
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

// Users APIs
export function getUsers() {
  return axios.get("/users")
}

