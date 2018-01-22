// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import BootstrapVue from "bootstrap-vue";
import Icon from 'vue-awesome/components/Icon.vue';
import 'vue-awesome/icons';
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import * as backendCli from "./utils/backend-client";

const url = "http://10.200.1.139:8080/bitflow";

backendCli.initialize(url);

Vue.prototype.$baseUrl = url;
Vue.prototype.$backendCli = backendCli;
Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.component('icon', Icon);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
});
