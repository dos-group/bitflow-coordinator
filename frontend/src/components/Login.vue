<template>
  <div class="container">
    <b-card>
      <b-form id="login-form">
        <b-form-group label="Username:" label-for="username">
          <b-form-input id="username" v-model="form.username" required placeholder="Enter username">
          </b-form-input>
        </b-form-group>
        <b-form-group label="Password" label-for="password">
          <b-form-input id="password" type="password" v-model="form.password" required
                        placeholder="Enter password"></b-form-input>
        </b-form-group>
        <p id="error-message" v-if="message">{{message}}</p>
        <b-button id="login-button" type="submit" variant="primary" @click="login">Login</b-button>
      </b-form>
    </b-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        message: null,
        form: {
          username: '',
          password: '',
        },
      }
    },
    methods: {
      async login (evt) {
        evt.preventDefault();
        try {
          let result = await this.$backendCli.login(this.form.username, this.form.password);
          this.$emit('userHasLoggedIn', result.user);

        } catch (err) {
          if (err.response && err.response.status === 401) {
            this.message = "Please check your username and password.";
          } else {
            this.$notifyError(err);
          }

        }
      },
    }
  }
</script>
<style scoped>
  .container {
    width: 50%;
    float: none;
    margin-left: auto;
    margin-right: auto;
    margin-top: 50px;
  }
</style>