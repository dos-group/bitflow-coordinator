import Login from "@/components/Login";
import {expect} from "chai";
import flushPromises from "flush-promises";
import {mount, shallow} from "@vue/test-utils";

describe('Login.vue', () => {
  
  it('should render password field and username field', () => {
    const wrapper = mount(Login);
    var vm = wrapper.vm;
    
    expect(wrapper.find('#password').exists()).to.be.ok;
    expect(wrapper.find('#username').exists()).to.be.ok;
  })
  
  it('should emit userHasLoggedIn error message if message is set', async() => {
    const wrapper = shallow(Login, {
      mocks: {
        $backendCli: {
          login(username, password){
            return Promise.resolve({user: {Name: "Abraxas"}})
          }
        }
      }
    });
    
    wrapper.find("#login-button").trigger('click');
    await flushPromises();

    console.error(wrapper.emitted())
    expect(wrapper.emitted().userHasLoggedIn.length).to.be.equal(1);
    expect(wrapper.emitted().userHasLoggedIn[0][0].Name).to.be.equal("Abraxas");
  })
  
  it('should display corresponding message when backend sends 401', async() => {
    const wrapper = shallow(Login, {
      mocks: {
        $backendCli: {
          login(username, password){
            return Promise.reject({response: {status: 401}})
          }
        }
      }
    });
    
    wrapper.find("#login-button").trigger('click');
    await flushPromises();
    
    expect(wrapper.find("#error-message").text()).to.be.equal("Please check your username and password.");
  })
  
  it('should display notification in any other error case', async() => {
    var lastNotifyErrorMessage;
    const wrapper = shallow(Login, {
      mocks: {
        $notifyError(msg){
          lastNotifyErrorMessage = msg;
        },
        $backendCli: {
          login(username, password){
            return Promise.reject("any other error")
          }
        }
      }
    });
    
    wrapper.find("#login-button").trigger('click');
    await flushPromises();
    
    expect(lastNotifyErrorMessage).to.be.equal("any other error");
  })
})
