<template>
  <div id="app">
    <header class="header">
      <nav class="inner">
        <router-link to="/" exact>Home</router-link>
        <router-link to="/top">Top</router-link>
        <router-link to="/new">New</router-link>
        <router-link to="/show">Show</router-link>
        <router-link to="/ask">Ask</router-link>
        <router-link to="/job">Jobs</router-link>
      </nav>
    </header>
    <transition name="fade" mode="out-in">
      <router-view class="view"></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
body {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  font-weight: 500;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  padding-top: 55px;
  overflow-y: scroll;
}
.header {
  background-color: #42b983;
  position: fixed;
  z-index: 999;
  height: 55px;
  top: 0;
  left: 0;
  right: 0;
}
.header .inner {
  max-width: 800px;
  box-sizing: border-box;
  margin: 0px 50px;
  padding: 15px 0px;
}
.header .inner a {
  color: rgba(255, 255, 255, .6);
  line-height: 24px;
  transition: color .15s ease;
  display: inline-block;
  vertical-align: middle;
  font-weight: 400;
  letter-spacing: .075em;
  margin-right: 1.8em;
}
.header .inner .router-link-active {
  color: #fff;
  font-weight: 800;
}
.header .inner :hover {
  color: #fff;
}
.view {
  max-width: 800px;
  margin: 0px 50px;
  position: relative;
}
.fade-enter-active, .fade-leave-active {
  transition: all .2s ease;
}
.fade-enter, .fade-leave-active {
  opacity: 0;
}
</style>
