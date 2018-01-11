<template>
  <div class="page-content">
		<h1>{{ title }}</h1>
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
							<b-table striped responsive :items="agents" :fields="ListFields" table-layout: fixed hover="hover">
								<template slot="tags" slot-scope="row">
									<b-button size="sm" @click.stop="row.toggleDetails" class="mr-2">
									{{ row.detailsShowing ? 'Hide' : 'Show'}} Tags
									</b-button>
								</template>
								<template slot="show_details" scope="row">
								      <b-button size="sm" @click="row.toggleDetails" class="mr-2">
								       {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
								      </b-button>
								 </template>
								<template slot="row-details" slot-scope="row">
									<b-card-group columns>
										<b-card v-for="(value, Key) in row.item" class="mb-2">
											<b-row sm="2" class="text-sm-right"><b>{{ Key }}</b></b-row><b-row>{{ value }}</b-row>
										</b-card>
									</b-card-group>
								</template>
								
							</b-table>
						</b-tab>
						<b-tab title="Pipelines">
							<ul class="pipeline-list list-group">
								<b-card-group columns>
								<li v-for="item in pipelines" :key="item.id">
									<b-card v-b-popover.hover="'I am popover content!'">
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
						<h5>Workers Available</h5>
							<ul class="pipeline-list list-group">
							<b-card-group columns>
								<li v-for="(item, index) in NewAgents" >
									<b-card v-b-popover.hover="'Available'">
										<p>{{item}}</p>
										<b-btn class="btn btn-outline-success float-left action-button">
											Deploy 
										</b-btn>
										<b-btn class="btn btn-outline-success action-button">
											Details
										</b-btn>
									</b-card>
								</li>
							</b-card-group>
							</ul>

					</div>
		    </div>
		    </b-row>
		
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
      NewAgents:[
      				"Worker 1",
      				"Worker 2",
      				"Worker 3"

      				],
      ListFields: [
      	{ key: "hostName", label: "Host Name" },
        { key: "apiPort", label: "API Port" },
        { key: "tags", label: "Tags" },
        { key: "hostName", label: "Host Name" },
        { key: "usedMem", label: "Used Memory" },
        'show_details'
      ],
      agentFields: [
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

	console.log(this.agents[0]);
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
.bv-example-row{
	padding-top:20px;
	padding-bottom:20px;
}
</style>