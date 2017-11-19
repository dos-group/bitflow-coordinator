import Vue from 'vue'
import Router from 'vue-router'
import Pipelines from '@/views/Pipelines'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Pipelines',
      component: Pipelines
    }
  ]
})
