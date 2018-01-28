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
				>Create new project</b-btn>
			</div>
		</div>
		<ul class="list-items list-group">
			<li v-for="item in projects" :key="item.ID">
				<div class="list-item list-group-item">
					<b-btn v-b-modal.delete-project-modal
						type="button"
						class="btn btn-danger btn-md float-right action-button"
						@click="selectedId = item.ID"
					><icon name="trash" class="inline"/></b-btn>
					<b-btn v-b-modal.edit-project-modal
						type="button"
						class="btn btn-secondary btn-md float-right action-button"
						@click="selectedId = item.ID"
					><icon name="edit" class="inline"/></b-btn>
					<div>
						<router-link :to="{path: '/project/' + item.ID + '/pipelines'}" class="list-item-link">
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
  	</b-modal>

		<b-modal
			id="edit-project-modal"
			ref="updateModal"
			title="Edit Project"
			@ok="updateProject(selectedId)"
			@shown="clearName"
		>
    	<form @submit.stop.prevent="handleSubmit">
        <b-form-input type="text" placeholder="New Project Name" v-model="name"/>
      </form>
  	</b-modal>

		<b-modal id="delete-project-modal" ref="deleteModal" title="Delete Project?" @ok="deleteProject(selectedId)"/>
	</div>
</template>

<script>
import { createCurrentTimeFormatted, formatISODate } from '../utils';

export default {
  name: "Projects",
  data() {
    return {
      title: "Your Projects",
      name: "",
      selectedId: null,
      projects: []
    };
  },
  async created() {
    try {
      const response = await this.$backendCli.getProjects();
      //TODO: filter by CreateUser.ID
      this.projects = response.data;
    } catch (e) {
      alert(e);
    }
  },
  methods: {
    clearName() {
      this.name = "";
    },
    formatISODate(date) {
      return formatISODate(date);
    },
    async createProject(evt) {
      evt.preventDefault();
      if (!this.name) {
        alert("Please enter a name");
      } else {
        const project = {
          Name: this.name,
          CreateUser: 0, //TODO: get current user id
          CreatedAt: createCurrentTimeFormatted()
        };
        try {
          const resp = await this.$backendCli.createProject(); //TODO: 405 (method not allowed)
          this.projects.push(resp.data);
          this.clearName();
          this.$refs.createModal.hide();
        } catch (e) {
          alert(e);
        }
      }
    },
    async updateProject(id) {
      if (!this.name) {
        alert("Please enter a name");
      } else {
        try {
          let updatedProject = this.projects.find(pr => pr.ID === id);
          updatedProject.name = this.name;
          await this.$backendCli.updateProject(id, updatedProject); //TODO: 405 (method not allowed)
        } catch (e) {
          alert(e);
        }
      }
    },
    async deleteProject(id) {
      try {
        await this.$backendCli.deleteProject(id); //TODO: 400
        this.projects = this.projects.filter(pr => pr.ID !== id);
        this.$refs.deleteModal.hide();
      } catch (e) {
        alert(e);
      }
    }
  }
};
</script>

<style scoped/>
