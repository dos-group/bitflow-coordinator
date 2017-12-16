<template>
  <div class="page-content">
    <div class="row">
      <div class="col-xs-7">
        <h1>{{ title }}</h1>
      </div>
      <div class="col-xs-5">
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
    <!--debug output {{pipelines}}-->
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
      this.pipelines = response.data;
    } catch (e) {
      this.errors.push(e);
    }
  }
}
</script>

<style scoped>

</style>
