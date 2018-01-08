<template>
    <div class="form-group row ">
        <div class="col-xs-2 btn-group">
            <b-card>
        <b-form @submit="onSubmit" v-if="show" >
            <b-form-group id="emailaddress" label="Email address:"label-for="emailaddress" >
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
//                alert("Hello");
                this.$router.push('projects');
            },
        }
    }
</script>