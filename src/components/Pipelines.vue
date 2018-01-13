<template>
  <div class="page-content">
    <div class="row">
      <div class="col-sm-8">
        <h1>{{ title }}</h1>
      </div>
      <div class="col-sm-4">
          <b-btn v-b-modal.add-pipeline-modal
            type="button"
						class="btn btn-success btn-lg float-right add-button"
					>Create new pipeline</b-btn>
      </div>
    </div>
    <ul class="list-items list-group">
      <li v-for="item in pipelines" :key="item.id">
        <div class="list-item list-group-item">
          <b-btn v-b-modal.delete-pipeline-modal
						type="button" 
						class="btn btn-danger btn-md float-right action-button"
						@click="selectedId = item.id"
					>Delete</b-btn>
          <b-btn v-b-modal.clone-pipeline-modal
						type="button" 
						class="btn btn-secondary btn-md float-right action-button"
						@click="selectedId = item.id"
					>Clone</b-btn>
          <div>
						<router-link :to="{path: '/projects/' + projectId + '/pipelines/' + item.id + '/editor'}" 
							class="list-item-link"
						>{{ item.name }}</router-link>
					</div>
          <div>last changed: {{ item.lastChanged }}</div>
        </div>
      </li>
    </ul>

		<!-- modals -->

		<b-modal
			id="add-pipeline-modal"
			ref="createModal"
			title="New Pipeline"
			@ok="createPipeline"
			@shown="clearName"
		>
			<form @submit.stop.prevent="handleSubmit">
				<b-form-input type="text" placeholder="New Pipeline Name" v-model="name"/>
			</form>
		</b-modal>

		<b-modal
			id="clone-pipeline-modal"
			ref="cloneModal"
			title="Name for cloned Pipeline"
			@ok="clonePipeline(selectedId)"
			@shown="clearName"
		>
			<form @submit.stop.prevent="handleSubmit">
				<b-form-input type="text" placeholder="New Pipeline Name" v-model="name"/>
			</form>
		</b-modal>

		<b-modal id="delete-pipeline-modal" ref="deleteModal" title="Delete Pipeline?" @ok="deletePipeline(selectedId)"/>
  </div>
</template>

<script>
import createCurrentTimeFormatted from '../utils';

export default {
  name: "Pipelines",
  data() {
    return {
			title: "Existing Pipelines",
			name: "",
			selectedId: null,
      pipelines: [],
			projectId: this.$router.history.current.fullPath.split('/')[2]
    };
  },
  async created() {
    try {
			const response = await this.$backendCli.getPipelines();
			const pid = this.$router.history.current.fullPath.split('/')[2];
			this.pipelines = response.data.filter(pip => pip.projectId == pid);
    } catch (e) {
      alert(e);
    }
	},
	methods: {
		clearName() {
			this.name = "";
		},
		async createPipeline(evt) {
			evt.preventDefault();
			if (!this.name) {
				alert("Please enter a name");
			} else {
				const pipeline = {
					name: this.name,
					projectId: this.projectId,
					lastChanged: createCurrentTimeFormatted()
				};
				try {
					const resp = await this.$backendCli.createPipeline(pipeline);
					this.pipelines.push(resp.data);
					this.clearName;
					this.$refs.createModal.hide();
				} catch (e) {
					alert(e);
				}
			}  
		},
		async clonePipeline(id) {
			if (!this.name) {
				alert("Please enter a name");
			} else {
				try {
				const template = this.pipelines.find(pip => pip.id === id);
				const clone = {
					name: this.name,
					projectId: template.projectId,
					lastChanged: createCurrentTimeFormatted()
				}
				const resp = await this.$backendCli.createPipeline(clone);
				this.pipelines.push(resp.data);
				this.clearName;
				this.$refs.cloneModal.hide();
				} catch (e) {
					alert(e);
				}
			}
		},
		async deletePipeline(id) {
			try {
				await this.$backendCli.deletePipeline(id);
				this.pipelines = this.pipelines.filter(pip => pip.id !== id);
				this.$refs.deleteModal.hide();
			} catch (e) {
				alert(e);
			}
		}
	}
};
</script>

<style scoped />
