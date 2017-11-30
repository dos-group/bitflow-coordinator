import Vue from 'vue'
import Router from 'vue-router'
import Pipelines from '@/components/Pipelines'
import CreatePipeline from '@/components/CreatePipeline'
import Root from '@/components/Root'

Vue.use(Router)

export default new Router({
  routes: [
  	{
  		path: '/',
  		name: 'Root',
  		component: Root
  	},
    {
     	path: '/pipelines',
      	name: 'Pipelines',
      	component: Pipelines
    },
    {
    	path: '/create',
    	name: 'CreatePipeline',
    	component: CreatePipeline
    }
  ]
})
