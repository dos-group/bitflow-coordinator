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
			<li v-for="item in projects">
				<div class="list-item list-group-item">
					<b-btn v-b-modal.delete-project-modal 
						type="button" 
						class="btn btn-danger btn-md float-right action-button" 
						@click="selectedId = item.id"
					>Delete</b-btn>
					<b-btn v-b-modal.edit-project-modal 
						type="button" 
						class="btn btn-secondary btn-md float-right action-button"
						@click="selectedId = item.id"
					>Edit</b-btn>
					<div>
						<router-link :to="{path: '/projects/' + item.id + '/pipelines'}" class="list-item-link">
							{{ item.name }}
						</router-link>
					</div>
					<div>created at: {{ item.createdAt }}</div>
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
import axios from "axios";
import moment from "moment";

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
      const response = await axios.get(this.$baseUrl + "/projects");
      this.projects = response.data;
    } catch (e) {
      alert(e);
    }
  },
  methods: {
    clearName() {
      this.name = "";
    },
    async createProject(evt) {
      evt.preventDefault();
      if (!this.name) {
        alert("Please enter a name");
      } else {
        moment().format();
        const date =
          moment().year()
          + "-"
          + Number(moment().month() + 1)
          + "-"
          + moment().date();
        const project = {
          name: this.name,
          creatorId: 0, //TODO: get current user id
          createdAt: date
        };
        try {
          await axios.post(this.$baseUrl + "/projects", project);
          this.projects.push(project);
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
          let updatedProject = this.projects.find(pr => pr.id === id);
          updatedProject.name = this.name;
          await axios.put(this.$baseUrl + "/project/" + id, updatedProject);
        } catch (e) {
          alert(e);
        }
      }
    },
    async deleteProject(id) {
      try {
        await axios.delete(this.$baseUrl + "/project/" + id);
        this.projects = this.projects.filter(pr => pr.id !== id);
        this.$refs.deleteModal.hide();
      } catch (e) {
        alert(e);
      }
    }
  }
};
</script>

<style scoped/>
