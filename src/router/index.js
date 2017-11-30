import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Projects from '@/components/Projects'
import Infrastructure from '@/components/Infrastructure'
import Pipelines from '@/components/Pipelines'
import Editor from '@/components/Editor'

Vue.use(Router)

export default new Router({
  routes: [
  	{
  		path: '/',
  		name: 'Login',
  		component: Login
  	},
    {
      path: '/projects',
      name: 'Projects',
      component: Projects
    },
    {
      path: '/infrastructure',
      name: 'Infrastructure',
      component: Infrastructure
    },
    {
     	path: '/pipelines',
      name: 'Pipelines',
      component: Pipelines
    },
    {
    	path: '/editor',
    	name: 'Editor',
    	component: Editor
    }
  ]
})
