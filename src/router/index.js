import Vue from "vue";
import Router from "vue-router";
import Projects from "@/components/Projects";
import Infrastructure from "@/components/Infrastructure";
import Pipelines from "@/components/Pipelines";
import Editor from "@/components/Editor";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Projects
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
      path: '/project/:project_id/pipelines',
      name: 'Pipelines',
      component: Pipelines
    },
    {
      path: '/project/:project_id/pipeline/:pipeline_id/editor',
      name: 'Editor',
      component: Editor
    },
      {
          path: '/infrastructure/:project_id/pipeline/:pipeline_id/editor',
          name: 'Editor',
          component: Editor
      }
  ],
  mode: "history"
})
