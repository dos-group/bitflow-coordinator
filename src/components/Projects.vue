<template>
  <div class="page-content">
    <div class="row">
      <div class="col-sm-8">
        <h1>{{ title }}</h1>
      </div>
      <div class="col-sm-4">
        <b-btn v-b-modal.add-project-modal
               type="button"
               class="btn btn-success btn-lg float-right add-button"
        >Create new project
        </b-btn>
      </div>
    </div>
    <ul class="list-items list-group">
      <li v-for="item in projects" :key="item.ID">
        <div class="list-item list-group-item">
          <b-btn v-b-modal.delete-project-modal
                 type="button"
                 class="btn btn-danger btn-md float-right action-button"
                 @click="selectedProject = item"
                 v-b-tooltip.hover title="Delete Project">
            <i class="fa fa-trash" />
          </b-btn>
          <b-btn v-b-modal.edit-project-modal
                 type="button"
                 class="btn btn-secondary btn-md float-right action-button"
                 @click="selectedProject = item"
                 v-b-tooltip.hover title="Edit Project Details">
            <i class="fa fa-edit" />
          </b-btn>

          <b-btn v-b-modal.project-users-modal
                 type="button"
                 class="btn btn-secondary btn-md float-right action-button"
                 @click="selectedProject = item"
                 v-b-tooltip.hover title="Manage Users">
            <i class="fa fa-user" />
          </b-btn>

          <div >
            <router-link :to="{path: '/project/' + item.ID + '/pipelines'}" class="list-item-link"
                         v-b-tooltip.hover title="Click to see details" >
              {{ item.Name }}
            </router-link>
          </div>
          <div>created at: {{ formatISODate(item.CreatedAt) }}</div>
        </div>
      </li>
    </ul>

    <!-- modals -->

    <b-modal
        id="add-project-modal"
        ref="createModal"
        title="New Project"
        @ok="createProject"
        @shown="clearName"
    >
      <form @submit.stop.prevent="handleSubmit">
        <b-form-input type="text" placeholder="New Project Name" v-model="name"/>
      </form>
      <span class="error-message">{{ modalErrorMessage }}</span>
    </b-modal>

    <b-modal
        id="edit-project-modal"
        ref="updateModal"
        title="Edit Project"
        @ok="updateProject"
        @shown="clearName"
    >
      <form @submit.stop.prevent="handleSubmit">
        <b-form-input type="text" placeholder="New Project Name" v-model="name"/>
      </form>
      <span class="error-message">{{ modalErrorMessage }}</span>
    </b-modal>
    <b-modal
        id="project-users-modal"
        ref="projectUsersModal"
        title="Project Users"
        @shown="onAddUserModalOpen(selectedProject)"
        ok-title="Close"
        ok-only
    >
      <form @submit.stop.prevent="handleSubmit">
        <ul id="example-1" style="list-style: none;">
          <li v-for="user in users" class="clickable-area">
            <span>
              <b-btn type="button" class="btn btn-success btn-md action-button"
                     @click="addUserToProject(selectedProject, user)"
                     v-if="projectUsersIDs.indexOf(user.ID) === -1"
                     v-b-tooltip.hover title="Add User">
                <i class="fa fa-plus" />
              </b-btn>
              <b-btn type="button" class="btn btn-md action-button"
                     @click="removeUserFromProject(selectedProject, user)"
                     v-else
                     v-b-tooltip.hover title="Remove User">
                <i class="fa fa-minus" />
              </b-btn>
              {{ user.Name }} ({{ user.Email }})
            </span>
          </li>
        </ul>
      </form>
      <span class="error-message">{{ modalErrorMessage }}</span>
    </b-modal>

    <b-modal id="delete-project-modal"
             ref="deleteModal"
             title="Delete Project?"
             @close="clearName"
             @ok="deleteProject(selectedProject)"/>
  </div>
</template>

<script>
import {createCurrentTimeFormatted, formatISODate} from '../utils';

export default {
  name: "Projects",
  data() {
    return {
      title: "Your Projects",
      name: "",
      projects: [],
      selectedProject: {},
      projectUsersIDs: [],
      users: [],
      modalErrorMessage: ""
    };
  },
  async created() {
    try {
      const projectsResp = await this.$backendCli.getProjects();
      this.projects = projectsResp.data;

      const usersResp = await this.$backendCli.getUsers();
      // filter out myself:
      const loggedInUser = this.$backendCli.getLoggedInUser();
      this.users = usersResp.data.filter(function (user) {
        return user.ID !== loggedInUser.ID
      });
    } catch (e) {
      this.$notifyError(e);
    }
  },
  methods: {
    clearName() {
      this.name = "";
      this.modalErrorMessage = "";
    },
    showModalErrorMessage(messageOrError){
      this.modalErrorMessage = messageOrError.message || messageOrError.errorMessage || messageOrError;
    },
    async onAddUserModalOpen(selectedProject) {
      this.clearName();
      this.projectUsersIDs = selectedProject.Users.map(function (user) {
        return user.ID
      });
    },
    formatISODate(date) {
        return formatISODate(date);
    },
    async createProject(evt) {
      evt.preventDefault();
      if (!this.name) {
        this.showModalErrorMessage("Please enter a name");
      } else {
        var loggedInUser = this.$backendCli.getLoggedInUser()
        const project = {
          Name: this.name,
          CreateUser: loggedInUser,
          CreatedAt: createCurrentTimeFormatted()
        };
        try {
          const resp = await this.$backendCli.createProject(project);
          this.projects.push(resp.data);
          this.clearName();
          // add myself as user of the project
          await this.$backendCli.addUserToProject(resp.data.ID, loggedInUser.ID);
          this.$refs.createModal.hide();
        } catch (e) {
          this.showModalErrorMessage(e);
        }
      }
    },
    async updateProject(evt) {
      evt.preventDefault();
      if (!this.name) {
        this.showModalErrorMessage("Please enter a name");
      } else {
        try {
          let updatedProject = this.selectedProject;
          updatedProject.Name = this.name;
          await this.$backendCli.updateProject(updatedProject.ID, updatedProject);
          this.$refs.updateModal.hide();
        } catch (e) {
          this.showModalErrorMessage(e);
        }
      }
    },
    async deleteProject(projectToDelete) {
      try {
        await this.$backendCli.deleteProject(projectToDelete.ID);
        var projectsResp = await this.$backendCli.getProjects();
        this.projects = projectsResp.data;
        this.$refs.deleteModal.hide();
      } catch (e) {
        this.$notifyError(e);
      }
    },
    async addUserToProject(selectedProject, userToAdd) {
      try {
        await this.$backendCli.addUserToProject(selectedProject.ID, userToAdd.ID);
        selectedProject.Users.push(userToAdd);
        this.projectUsersIDs.push(userToAdd.ID)
      } catch (e) {
        this.showModalErrorMessage(e);
      }
    },
    async removeUserFromProject(selectedProject, userToRemove) {
      try {
        await this.$backendCli.removeUserFromProject(selectedProject.ID, userToRemove.ID);
        selectedProject.Users = selectedProject.Users.filter(function (u) {
          return u.ID !== userToRemove.ID;
        });
        this.projectUsersIDs = this.projectUsersIDs.filter(function (u) {
          return u !== userToRemove.ID;
        });
      } catch (e) {
        this.showModalErrorMessage(e);
      }
    }
  }
};
</script>
