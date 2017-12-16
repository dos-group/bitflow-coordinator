<template>
	<div class="page-content">
		<div class="row">
			<div class="col-sm-8">
				<h1>{{ title }}</h1>
			</div>
			<div class="col-sm-4">
				<b-btn v-b-modal.project-modal type="button" class="btn btn-success btn-lg float-right add-button">
					Create new project
				</b-btn>
			</div>
		</div>
		<ul class="list-items list-group">
			<li v-for="item in projects">
				<div class="list-item list-group-item">
					<button type="button" class="btn btn-danger btn-md float-right action-button">Delete</button>
					<button disabled type="button" class="btn btn-warning btn-md float-right action-button edit-button">Edit</button>
					<div><h4>{{ item.name }}</h4></div>
					<div>created at: {{ item.createdAt }}</div>
				</div>
			</li>
		</ul>

		<b-modal id="project-modal" ref="modal" title="New Project" @ok="handleOk" @shown="clearName">
    	<form @submit.stop.prevent="handleSubmit">
        <b-form-input type="text" placeholder="New Project Name" v-model="name"></b-form-input>
      </form>
  	</b-modal>
	</div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

export default {
	name: 'Projects',
	data() {
		return {
			title: 'Your Projects',
			name: '',
			projects: [],
			errors: []
		}
	},
	async created() {
		try {
			const response = await axios.get(this.$baseUrl + '/projects');
			this.projects = response.data;
		} catch (e) {
			alert(e);
		}
	},
	methods: {
		clearName() {
			this.name = '';
		},
		handleOk(evt) {
			evt.preventDefault();
			if (!this.name) {
				alert('Please enter a name');
			} else {
				this.handleSubmit();
			}
		},
		async handleSubmit() {
			moment().format();
			const date = moment().year() + '-' + Number(moment().month()+1) + '-' + moment().date();
			const project = {
				"name": this.name,
  			"creatorId": 0, //TODO: get current user id
  			"createdAt": date
			}
			try {
				await axios.post(this.$baseUrl + '/projects', project);
				this.projects.push(project);
      	this.clearName();
      	this.$refs.modal.hide();
			} catch (e) {
				alert(e);
			}
		}
	}
}
</script>

<style scoped/>
