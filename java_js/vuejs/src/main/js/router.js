import Vue from 'vue'
import VueRouter from 'vue-router'
import Todos from './components/Todos.vue'
import Todo from './components/Todo.vue'

Vue.use(VueRouter);

export default new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/', component: Todos
    },
    {
      path: '/:id', component: Todo, name: 'todo'
    }
  ]
});
