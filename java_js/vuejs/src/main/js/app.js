import Vue from 'vue'
import App from './components/App.vue'
import router from './router'
import store from './store'

Vue.config.strict = true;
Vue.config.errorHandler = (err, vm, info) => {
  console.error(`This component threw an error (in '${info}'):`, vm, this);
  console.error(err);
};

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
});
