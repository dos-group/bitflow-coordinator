<template>
  <div class="page-content">
    <div class="row">
      <div class="col-xs-7">
        <h1>{{ title }}</h1>
      </div>
      <div class="col-xs-5 add-button-col">
        <router-link to="/create">
          <button type="button" class="btn btn-default btn-lg pull-right add-button">Create new pipeline</button>
        </router-link>
      </div>
    </div>
    <ul class="preview-items list-group">
      <li v-for="item in previewItems">
        <div class="preview-item list-group-item">
          <button type="button" class="btn btn-danger btn-md pull-right action-button">Delete</button>
          <button type="button" class="btn btn-warning btn-md pull-right action-button edit-button">Edit</button>
          <div><h4>{{ item.name }}</h4></div>
          <div>created at: {{ item.createdAt }}</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Pipelines',
  data () {
    return {
      title: 'Existing Pipelines',
      previewItems : [
        { id: 0, name: "Pipeline 1", createdAt: "2017-11-19 17:05" },
        { id: 1, name: "Pipeline 2", createdAt: "2017-11-19 15:14" },
        { id: 2, name: "Pipeline 3", createdAt: "2017-11-18 15:09" },
        { id: 3, name: "Pipeline 4", createdAt: "2017-11-18 09:18" },
        { id: 4, name: "Pipeline 5", createdAt: "2017-11-17 13:59" },
        { id: 5, name: "Pipeline 6", createdAt: "2017-11-17 18:10" }
      ]
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.add-button-col {
  padding-top: 17px;
}
.add-button {
  background-color: #42b983;
  border-color: #fff;
  color: #fff;
}
.preview-items {
  list-style-type: none;
  padding: 80px 80px 0px 80px;
}
.preview-item {
  margin: 10px 0px 10px 0px; 
  padding: 5px 20px 10px 20px;
}
.action-button {
  margin-top: 15px;
}
.edit-button {
  margin-right: 10px;
}
</style>
