<template>
  <div id="app">
    
    <!-- Navbar -->
    <nav class= "navbar navbar-default navbar-static-top">
      <div class="container-fluid">
        <div class= "navbar-header">
          <a class="navbar-brand" href="#">Bitflow</a>
        </div>
        <ul class="nav navbar-nav">
          <li class="active"><a href="#">Home</a></li>
          <li><a href="#">Page 1</a></li>
          <li><a href="#">Page 2</a></li>
        </ul>
      </div>
    </nav>

    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  font-weight: 500;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
