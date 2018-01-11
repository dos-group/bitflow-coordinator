// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import BootstrapVue from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import * as backendCli from "./utils/backend-client";

backendCli.Initialize('http://35.227.97.73:4000')

Vue.prototype.$backendCli = backendCli;

Vue.config.productionTip = false;
//Vue.prototype.$baseUrl = "http://localhost:4000";
Vue.prototype.$baseUrl = 'http://35.227.97.73:4000';
Vue.use(BootstrapVue);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
});
