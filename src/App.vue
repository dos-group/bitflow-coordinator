<template>
  <div id="app">
    <header class="header">
      <nav class="inner">
        <span v-if="loggedInUser">
        <router-link to="/projects">Projects</router-link>
        <router-link to="/infrastructure">Infrastructure</router-link>
          <!-- Example single danger button -->
        <transition name="fade" mode="out-in">
        </transition>
        <div class="navbar-top-right">
          {{loggedInUser.Name}}
          <span v-on:click="logout" class="clickable-area">Logout
          <icon name="sign-out" class="inline"/>
            </span>
        </div>
          </span>
      </nav>

    </header>

    <transition v-if="loggedInUser" name="fade" mode="out-in">
      <router-view class="view"></router-view>
    </transition>
    <transition v-if="!loggedInUser" name="fade" mode="out-in">
      <div style="text-align: center;">
        username: "john", password: "doe"<br>
        username: "tester", password: "test"
        <Login v-on:userHasLoggedIn="updateLoggedInUser"></Login>
      </div>
    </transition>
  </div>
</template>

<script>
  import Login from "./components/Login.vue"

  export default {
    name: 'app',
    data(){
      return {
        originRoute: this.$router.currentRoute,
        loggedInUser: this.$backendCli.getLoggedInUser()
      }
    },
    methods: {
      updateLoggedInUser: function (user) {
        this.loggedInUser = user;
      },
      logout: function () {
        this.$backendCli.logout();
        this.loggedInUser = null;
      }
    },
    components: {Login}
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Open+Sans');

  body {
    font-family: 'Open Sans', Helvetica, Arial, sans-serif;
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

  .navbar-top-right {
    color: white;
    vertical-align: middle;
    float: right;
    padding-left: 0.2em;
  }

  .clickable-area {
    margin-left: 1em;
    cursor: pointer;
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
    color: #0d8e53;
    font-size: 1.25em;
  }

  .action-button {
    margin: 5px 5px 10px 5px;
  }

  .inline {
    vertical-align: middle;
  }
</style>
