<template>
  <div class="container">
    <b-card>
      <b-form @submit="onSubmit">
        <b-form-group id="emailaddress" label="Email address:" label-for="emailaddress">
          <b-form-input id="emailaddress" type="email" v-model="form.email" required
                        placeholder="Enter email">
          </b-form-input>
        </b-form-group>
        <b-form-group id="password" label="Your Name:" label-for="password">
          <b-form-input id="password" type="password" v-model="form.password" required
                        placeholder="Enter password"></b-form-input>
        </b-form-group>
        <p class="error-message" v-if="message">
          {{message}}
        </p>
        <b-button type="submit" variant="primary">Login</b-button>
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
          email: '',
          password: '',
        },
      }
    },
    methods: {
      async onSubmit (evt) {
        evt.preventDefault();

        let result = await this.$backendCli.login(this.form.email, this.form.password);
        if (result.user) {
          this.$emit('userHasLoggedIn', result.user);
        } else {
          this.message = "Please check your username and password.";
        }
      },
    }
  }
</script>
<style>
  .error-message {
    color: red;
  }

  .container {
    width: 50%;
    float: none;
    margin-left: auto;
    margin-right: auto;
    margin-top: 50px;
  }
</style>