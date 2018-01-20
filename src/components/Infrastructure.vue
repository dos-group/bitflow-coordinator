<template>
  <div class="page-content">
		<h1>{{ title }}</h1>
		<div class="top-content">
			<b-card-group deck>
				<b-card :title="numberOfAgents">
					<p class="card-text">Active Agents</p>
				</b-card>
				<b-card :title="numberOfIdleAgents">
					<p class="card-text">Idle Agents</p>
				</b-card>
				<b-card :title="runningPipelinesCount">
					<p class="card-text">Running Pipelines</p>
				</b-card>
			</b-card-group>
		</div>
		<b-tabs>
			<b-tab title="Agents" active>
				<b-table striped responsive :items="agents" :fields="agentFields">
					<template slot="tags" slot-scope="row">
						<b-button size="sm" @click.stop="row.toggleDetails" class="mr-2">
						{{ row.detailsShowing ? 'Hide' : 'Show'}} Tags
						</b-button>
					</template>
					<template slot="row-details" slot-scope="row">
						<b-card>
							<b-row v-for="tag in row.item.tags" :key="tag.slots" class="mb-2">
								<b-col sm="2" class="text-sm-right"><b>Resources:</b></b-col>
								<b-col>{{ tag.resources }}</b-col>
								<b-col sm="2" class="text-sm-right"><b>Slots:</b></b-col>
								<b-col>{{ tag.slots }}</b-col>
							</b-row>
						</b-card>
					</template>
					<template slot="usedCpuCores" slot-scope="data">
						{{ data.item.usedCpuCores.join(', ') }}
					</template>
				</b-table>
			</b-tab>
			<b-tab title="Running Pipelines">
				<ul class="pipeline-list list-group">
					<li v-for="item in runningPipelines" :key="item.id">
						<div class="list-item list-group-item">
							<div class="pipeline-box">
								<router-link :to="{path: '/projects/' + item.projectId + '/pipelines/' + item.id + '/editor'}"
									class="list-item-link"
								>{{ item.name }}</router-link>
							</div>
						</div>
					</li>
				</ul>
			</b-tab>
		</b-tabs>
  </div>
</template>

<script>
export default {
  name: "Infrastructure",
  data() {
    return {
      title: "Infrastructure",
      numberOfAgents: null,
      numberOfIdleAgents: null,
			runningPipelinesCount: null,
      agents: [],
      agentFields: [
        { key: "hostName", label: "Host Name" },
        { key: "apiPort", label: "API Port" }, //TODO: Will we keep that? Depends on API
        { key: "tags", label: "Tags" },
        { key: "usedMem", label: "Used Memory" },
        { key: "totalMem", label: "Total Memory" },
        { key: "usedCpu", label: "Used CPU" },
        { key: "usedCpuCores", label: "Used CPU Cores" },
        { key: "numCores", label: "Cores" },
        { key: "numProcs", label: "Procedures" },
        { key: "goroutines", label: "Goroutines" }
			],
			runningPipelines: []
    };
  },
  async created() {
    try {
      const infoResponse = await this.$backendCli.getInfo();
      const info = infoResponse.data[0];
      this.numberOfAgents = String(info.numberOfAgents);
      this.numberOfIdleAgents = String(info.numberOfIdleAgents);
			this.runningPipelinesCount = "?"; //TODO: can't read from API yet
			this.agents = info.agents;
            const pipResponse = await this.$backendCli.getPipelines();
			this.runningPipelines = pipResponse.data; //TODO: only get running ones, API not ready
    } catch (e) {
      alert(e);
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
</style>