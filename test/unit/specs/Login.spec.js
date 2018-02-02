import Login from "@/components/Login";
import Vue from "vue";

describe('Login.vue', () => {
  it('should render password field and username field', () => {
    const Constructor = Vue.extend(Login);
    
    const vm = new Constructor().$mount();
    
    expect(vm.$el.querySelector('#password')).to.be.ok;
    expect(vm.$el.querySelector('#username')).to.be.ok;
  })
  
  it('should display error message if message is set', () => {
    const Constructor = Vue.extend(Login);
    const vm = new Constructor({
      data(){
        return {
          message: "Error happened",
        }
      }
    }).$mount();
    
    
    expect(vm.$el.querySelector('#error-message')).to.be.ok;
    expect(vm.$el.querySelector('#error-message').textContent).to.be.equal("Error happened");
  })
  
})
