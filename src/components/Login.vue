<template>
    <div class="container">
        <b-card>
            <b-form @submit="onSubmit" v-if="show">
                <b-form-group id="emailaddress" label="Email address:" label-for="emailaddress" >
                <b-form-input id="emailaddress" type="email" v-model="form.email" required placeholder="Enter email">
                </b-form-input>
                </b-form-group>
                <b-form-group id="password" label="Your Name:" label-for="password">
                <b-form-input id="password" type="password" v-model="form.password" required placeholder="Enter password"></b-form-input>
                </b-form-group>
                <b-button type="submit" variant="primary">Login</b-button>
                </b-form>
        </b-card>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                form: {
                    email: '',
                    password: '',
                },
                show: true
            }
        },
        methods: {
            onSubmit (evt) {
                evt.preventDefault();
                this.$router.push('projects');
            },
        }
    }
</script>
<style>
    .container {
        width: 50%;
        float: none;
        margin-left: auto;
        margin-right: auto;
        margin-top: 50px;
    }
</style>