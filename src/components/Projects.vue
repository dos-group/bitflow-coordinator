<template>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-7">
				<h1>{{ title }}</h1>
			</div>
			<div class="col-xs-5 add-button-col">
				<button type="button" class="btn btn-default btn-lg pull-right add-button">Create new project</button>
			</div>
		</div>
		<ul class="list-items list-group">
			<li v-for="item in projects">
				<div class="list-item list-group-item">
					<button type="button" class="btn btn-danger btn-md pull-right action-button">Delete</button>
					<button type="button" class="btn btn-warning btn-md pull-right action-button edit-button">Edit</button>
					<div><h4>{{ item.name }}</h4></div>
					<div>created at: {{ item.createdAt }}</div>
				</div>
			</li>
		</ul>
		<!--debug output {{projects}}-->
	</div>
</template>

<script>
import axios from 'axios';
export default {
	name: 'Projects',
	data() {
		return {
			title: 'Your Projects',
			projects: [],
			errors: []
		}
	},
	async created() {
		try {
			const response = await axios.get(this.$baseUrl + '/projects');
			this.projects = response.data;
		} catch (e) {
			this.errors.push(e);
		}
	}
}
</script>

<style scoped>

</style>