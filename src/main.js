// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import BootstrapVue from "bootstrap-vue";
import Notifications from "vue-notification";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import 'font-awesome/css/font-awesome.css';
import * as backendCli from "./utils/backend-client";

const url = 'http://10.200.1.139:8080/bitflow';

backendCli.initialize(url);

Vue.prototype.$baseUrl = url;
Vue.prototype.$backendCli = backendCli;
Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.use(Notifications)
const libMethod = Vue.prototype.$notify
Vue.prototype.$notifyInfo = function (message) {
  libMethod({
    group: 'global-notifier',
    title: 'Important message',
    type: 'info',
    text: message
  });
};
Vue.prototype.$notifyError = function (error) {
  console.warn("Error occurred", error);
  if (error.errorMessage) {
    error = error.errorMessage;
  } else if (error.message) {
    error = error.message;
  }
  libMethod({
    group: 'global-notifier',
    type: 'error',
    title: 'Oops... ',
    text: error
  });
}
Vue.prototype.$notify = Vue.prototype.$notifyInfo;
//Vue.component('icon', Icon);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
});
