<template>
  <div class="page-content">
    <div class="row">
      <div class="col-sm-8">
        <h1>{{ title }}</h1>
      </div>
      <div class="col-sm-4">
        <router-link to="/editor">
          <button type="button" class="btn btn-success btn-lg float-right add-button">Create new pipeline</button>
        </router-link>
      </div>
    </div>
    <ul class="list-items list-group">
      <li v-for="item in pipelines">
        <div class="list-item list-group-item">
          <button type="button" class="btn btn-danger btn-md float-right action-button">Delete</button>
          <button type="button" class="btn btn-warning btn-md float-right action-button edit-button">Edit</button>
          <div><h4>{{ item.name }}</h4></div>
          <div>last changed: {{ item.lastChanged }}</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Pipelines',
  data () {
    return {
      title: 'Existing Pipelines',
      pipelines : [],
      errors: []
    }
  },
  async created() {
    try {
      const response = await axios.get(this.$baseUrl + '/pipelines');
      this.pipelines = response.data; //filter matching projectIds
    } catch (e) {
      this.errors.push(e);
    }
  }
}
</script>

<style scoped>

</style>
