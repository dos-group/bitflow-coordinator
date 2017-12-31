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
			</b-card-group>
		</div>
		<b-table outlined small stacked :items="agents"/>
  </div>
</template>

<script>
import axios from "axios";

export default {
	name: 'Infrastructure',
	data() {
		return {
			title: "Infrastructure",
			numberOfAgents: null,
			numberOfIdleAgents: null,
			agents: []
		};
	},
	async created() {
		try {
			const response = await axios.get(this.$baseUrl + "/info");
			const info = response.data[0];
			this.numberOfAgents = String(info.numberOfAgents);
			this.numberOfIdleAgents = String(info.numberOfIdleAgents);
			this.agents = info.agents;
		} catch (e) {
			alert(e);
		}
	}
}
</script>

<style scoped>
.top-content {
	padding: 30px 0px 40px 0px;
}
.card-text {
	font-size: 20px;
}
</style>