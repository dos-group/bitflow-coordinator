<template>
  <div class="page-content">
    <h1>{{ title }}</h1>
    <div class="top-content">
      <b-card-group deck>
        <b-card :title="numberOfAgents">
          <p class="card-text">Active Agents</p>
        </b-card>
        <b-card :title="numberOfOnlineAgents">
          <p class="card-text">Online Agents</p>
        </b-card>
        <b-card :title="numberOfOfflineAgents">
          <p class="card-text">Offline Agents</p>
        </b-card>
      </b-card-group>
    </div>
    <b-tabs>
      <b-tab title="Agents" active>
        <b-table striped responsive :items="agents" :fields="agentFields">
          <template slot="Tags" slot-scope="row">
            <b-button size="sm" @click.stop="row.toggleDetails" class="mr-2">
              {{ row.detailsShowing ? 'Hide' : 'Show'}} Tags
            </b-button>
          </template>
          <template slot="row-details" slot-scope="row">
            <b-card>
              <!-- TODO: for demo only. reactivate to show real data
              <b-row v-for="tag in row.item.Tags" :key="tag.Slots" class="mb-2">
            -->
            <b-row v-for="tag in testTags" :key="testTags.indexOf(tag)" class="mb-2">
              <b-col sm="2" class="text-sm-right">
                <b-row v-for="tagKey in Object.keys(tag)" :key="Object.keys(tag).indexOf(tagKey)" class="mb-2">
                  <b>{{ tagKey }}</b>
                </b-row>
              </b-col>
              <b-col sm="2" class="text-sm-right">
                <b-row v-for="tagValue in Object.values(tag)" :key="Object.values(tag).indexOf(tagValue)" class="mb-2">
                  {{ tagValue }}
                </b-row>
              </b-col>
            </b-row>
          </b-card>
        </template>
        <template slot="usedCpuCores" slot-scope="data">
          {{ data.item.UsedCpuCores.join(', ') }}
        </template>
      </b-table>
    </b-tab>
    <b-tab title="Pipelines">
      <div class="project-dropdown">
        <select v-model="selectedProject" class="form-control " @change="filterPipeline()">
          <option value="" disabled hidden>Filter according to project</option>
          <option v-for="project in projects" v-bind:value="project.ID">
            {{ project.Name }}
          </option>
        </select>
      </div>
      <ul class="list-items">
        <li v-for="item in pipelines" :key="item.ID">
          <div class="list-item">
            <router-link :to="{ path: '/project/' + selectedProject + '/pipelines/' + item.ID + '/editor' }" class="list-item-link">
              {{ item.Name ? item.Name : "Unnamed Pipeline" }}
            </router-link>
            <div>last execution: {{ pipelineExecutionDates[item.ID] }}</div>
          </div>
        </li>
      </ul>
    </b-tab>
  </b-tabs>
</div>
</template>

<script>
import { formatISODate } from '../utils';
export default {
  name: "Infrastructure",
  data() {
    return {
      title: "Infrastructure",
      numberOfAgents: null,
      numberOfOnlineAgents: null,
      numberOfOfflineAgents: null,
      projects: [],
      pipelines: [],
      pipelineExecutionDates: [],
      selectedProject: '',
      agents: [],
      agentFields: [
        { key: "Hostname", label: "Host Name" },
        { key: "Tags", label: "Tags" },
        { key: "UsedMem", label: "Used Memory" },
        { key: "TotalMem", label: "Total Memory" },
        { key: "UsedCpu", label: "Used CPU" },
        { key: "UsedCpuCores", label: "Used CPU Cores" },
        { key: "NumCores", label: "Cores" },
        { key: "NumProcs", label: "Procedures" },
        { key: "Goroutines", label: "Goroutines" }
      ],
      testTags: [{key1: "value1", key2: "value2"}] //TODO: remove
    };
  },
  async created() {
    try {
      const projectsResp = await this.$backendCli.getProjects();
      this.projects = projectsResp.data;
      const infoResponse = await this.$backendCli.getInfo();
      const info = infoResponse.data;
      this.numberOfAgents = info.NumberOfAgents == null ? "?" : String(info.NumberOfAgents);
      this.numberOfOnlineAgents = info.NumberOfOnlineAgents == null ? "?" : String(info.NumberOfOnlineAgents);
      this.numberOfOfflineAgents = info.NumberOfOfflineAgents == null ? "?" : String(info.NumberOfOfflineAgents);
      this.agents = info.Agents;
    } catch (e) {
      this.$notifyError(e);
    }
  },
  methods:{
    async filterPipeline() {
      try {
        const pipelineResp = await this.$backendCli.getPipelines(this.selectedProject);
        this.pipelines = pipelineResp.data;
        const exeDatesResp = await this.$backendCli.getLastExecutionDateForPipelinesInProject(this.selectedProject);
        this.pipelineExecutionDates = exeDatesResp;
      } catch (e) {
        this.$notifyError(e);
      }
    }
  }
};
</script>

<style scoped>
.top-content {
  padding: 30px 0px 40px 0px;
}
.card-text {
  font-size: 20px;
}
.pipeline-list {
  list-style-type: none;
  padding: 30px 50px 60px 50px;
}
.pipeline-box {
  padding: 15px 0px 15px 0px;
}
.project-dropdown{
  padding: 10px;
  width: 30%;
}
</style>
