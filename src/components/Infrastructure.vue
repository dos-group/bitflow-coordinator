<template>
  <div class="page-content">
		<h1>{{ title }}</h1>
		<b-container class="bv-example-row">
			 <b-row>
		        <b-col cols="3">
						<b-card :title="numberOfAgents">
							<p class="card-text">Active Agents</p>
						</b-card>
						<b-card :title="numberOfIdleAgents">
							<p class="card-text">Idle Agents</p>
						</b-card>	
				</b-col>
		        <b-col cols="9">
		        	<b-tabs>
						<b-tab title=" Active Agents" active>
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
						<b-tab title="Pipelines">
							<ul class="pipeline-list list-group">
								<b-card-group columns>
								<li v-for="item in pipelines" :key="item.id">
									<b-card>
										<p>{{ item.name }}</p>
										<b-btn class="btn btn-outline-success float-left action-button">
											Start 
										</b-btn>
										<b-btn class="btn btn-outline-success action-button">
											Details
										</b-btn>
									</b-card>
								</li>
								</b-card-group columns>
							</ul>
						</b-tab>
					</b-tabs>
		        </b-col>
		    </b-row>
		    <b-row>
		    <div>
		    		<div class="pipeline-box">
						<h5>testing</h5>
					</div>
		    </div>
		    </b-row>

		</b-container>

		<div class="top-content">
			
			
		</div>
		
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Infrastructure",
  data() {
    return {
      title: "Infrastructure",
      numberOfAgents: null,
      numberOfIdleAgents: null,
      agents: [],
      agentFields: [
        { key: "hostName", label: "Host Name" },
        { key: "apiPort", label: "API Port" },
        { key: "tags", label: "Tags" },
        { key: "usedMem", label: "Used Memory" },
        { key: "totalMem", label: "Total Memory" },
        { key: "usedCpu", label: "Used CPU" },
        { key: "usedCpuCores", label: "Used CPU Cores" },
        { key: "numCores", label: "Cores" },
        { key: "numProcs", label: "Procedures" },
        { key: "goroutines", label: "Goroutines" }
			],
			pipelines: []
    };
  },
  async created() {
    try {
      const infoResponse = await axios.get(this.$baseUrl + "/info");
      const info = infoResponse.data[0];
      this.numberOfAgents = String(info.numberOfAgents);
      this.numberOfIdleAgents = String(info.numberOfIdleAgents);
			this.agents = info.agents;
			const pipResponse = await axios.get(this.$baseUrl + "/pipelines");
			this.pipelines = pipResponse.data;
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