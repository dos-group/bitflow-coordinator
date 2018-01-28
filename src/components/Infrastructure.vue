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
							<b-row v-for="tag in row.item.Tags" :key="tag.Slots" class="mb-2">
								<b-col sm="2" class="text-sm-right"><b>Resources:</b></b-col>
								<b-col>{{ tag.Resources }}</b-col>
								<b-col sm="2" class="text-sm-right"><b>Slots:</b></b-col>
								<b-col>{{ tag.Slots }}</b-col>
							</b-row>
						</b-card>
					</template>
					<template slot="usedCpuCores" slot-scope="data">
						{{ data.item.UsedCpuCores.join(', ') }}
					</template>
				</b-table>
			</b-tab>
			<b-tab title="Running Pipelines">
				<ul class="pipeline-list list-group">
					<li v-for="item in runningPipelines" :key="item.ID">
						<div class="list-item list-group-item">
							<div class="pipeline-box">
								<!--TODO: item.Project is null for all test data -->
								<router-link :to="{path: '/projects/' + item.Project + '/pipelines/' + item.ID + '/editor'}"
									class="list-item-link"
								>{{ item.Name }}</router-link>
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
			runningPipelines: []
    };
  },
  async created() {
    try {
			const infoResponse = await this.$backendCli.getInfo();
			const info = infoResponse.data;
      this.numberOfAgents = info.NumberOfAgents == null ? "?" : String(info.NumberOfAgents);
      this.numberOfIdleAgents = info.NumberOfIdleAgents == null ? "?" : String(info.NumberOfIdleAgents);
			this.runningPipelinesCount = "?"; //TODO: not provided by API yet
			this.agents = info.Agents;
			this.runningPipelines = await this.$backendCli.getRunningPipelinesOfAllProjects();
			//TODO: add filter to backendCli function once API is ready to provide the state of pipelines	
			console.log(this.runningPipelines[0].Project);
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