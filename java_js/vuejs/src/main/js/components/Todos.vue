<template>
  <div>
    <header class="header">
      <h1>todos</h1>
      <form @submit.prevent>
        <div class="input-group mb-3">
          <input v-model="text" type="text" autofocus placeholder="add new todo" class="form-control">
          <div class="input-group-prepend">
            <button @click="addTodo" type="submit" class="btn btn-primary">add</button>
          </div>
        </div>
      </form>
    </header>
    <hr>

    <section class="main">
      <ul class="list-unstyled">
        <li v-for="todo in todos" class="input-group">
          <div class="input-group-prepend">
            <div class="input-group-text">
              <input :checked="todo.done" @click="updateTodo(todo)" type="checkbox" title="done">
            </div>
          </div>
          <div :class="{[$style.done] : todo.done}" class="form-control input-lg">
            <router-link :to="{ name: 'todo', params : { id: todo.id }}">{{todo.text}}</router-link>
          </div>
          <div class="input-group-prepend">
            <div class="input-group-text">
              <button @click="deleteTodo(todo)" type="button" class="close" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </div>
        </li>
      </ul>
    </section>

    <footer class="footer">
      <button @click="deleteTodos" type="button" class="btn btn-danger">Clear completed</button>
    </footer>
  </div>
</template>

<style module>
  .done {
    text-decoration: line-through;
  }
</style>

<script>
  import {mapActions, mapGetters} from 'vuex'

  export default {
    data() {
      return {
        text: ''
      }
    },
    computed: mapGetters(['todos']),
    methods: {
      addTodo() {
        if (this.text) {
          const data = {
            text: this.text,
            done: false
          };
          this.$store.dispatch('addTodo', data);
          this.text = ''
        }
      },
      updateTodo(todo) {
        const data = {
          id: todo.id,
          text: todo.text,
          done: !todo.done
        };
        this.$store.dispatch('updateTodo', data);
      },
      deleteTodo(todo) {
        const data = {
          id: todo.id,
        };
        this.$store.dispatch('deleteTodo', data);
      },
      ...mapActions(['deleteTodos'])
    }
  }
</script>
