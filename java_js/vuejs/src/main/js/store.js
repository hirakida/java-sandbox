import Vue from 'vue'
import Vuex from 'vuex'
import api from './api'

Vue.use(Vuex);

export default new Vuex.Store({
  strict: true,
  state: {
    todos: []
  },
  getters: {
    todos: state => state.todos,
    todo: state => (id) => {
      return state.todos.find(todo => todo.id === id)
    },
  },
  mutations: {
    todos: (state, todos) => state.todos = todos
  },
  actions: {
    todos: context => {
      api.getTodos(response => {
        context.commit('todos', response.data);
      });
    },
    addTodo: (context, data) => {
      api.addTodo(data, () => {
        context.dispatch('todos');
      });
    },
    updateTodo: (context, data) => {
      api.updateTodo(data, () => {
        context.dispatch('todos');
      });
    },
    deleteTodo: (context, data) => {
      api.deleteTodo(data, () => {
        context.dispatch('todos');
      });
    },
    deleteTodos: (context) => {
      api.deleteTodos(() => {
        context.dispatch('todos');
      });
    }
  }
});
