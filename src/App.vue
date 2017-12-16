<template>
  <div id="app">
    <header class="header">
      <nav class="inner">
        <router-link to="/projects">Projects</router-link>
        <router-link to="/infrastructure">Infrastructure</router-link>
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
  padding-top: 75px;
  overflow-y: scroll;
}
h1 {
  font-weight: 500;
}
h2 {
  font-weight: 300;
}
a {
  color: #42b983;
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
  margin: 0px 50px;
  position: relative;
}
.fade-enter-active, .fade-leave-active {
  transition: all .2s ease;
}
.fade-enter, .fade-leave-active {
  opacity: 0;
}
.add-button {
  background-color: #42b983;
}
.list-items {
  list-style-type: none;
  padding: 80px 50px 0px 50px;
}
.list-item {
  margin: 10px 0px 10px 0px; 
  padding: 15px 20px 10px 20px;
}
.list-item-link {
  color: #000;
  font-size: 1.25em;
}
.action-button {
  margin-top: 10px;
}
.edit-button {
  margin-right: 10px;
}
</style>
