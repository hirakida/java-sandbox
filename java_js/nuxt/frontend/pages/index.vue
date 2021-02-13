<template>
  <div class="container">
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
          <div :class="{done : todo.done}" class="form-control input-lg">{{todo.text}}</div>
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

<script>
  import axios from 'axios'

  export default {
    data() {
      return {
        todos: [],
        text: ''
      }
    },
    async asyncData() {
      let {data} = await axios.get("/api/todos");
      return {todos: data};
    },
    methods: {
      async getTodos() {
        let {data} = await axios.get("/api/todos");
        this.todos = data;
      },
      async addTodo() {
        if (this.text) {
          await axios.post("/api/todos", {
            text: this.text,
            done: false
          });
          this.getTodos();
          this.text = '';
        }
      },
      async updateTodo(todo) {
        await axios.put("/api/todos/" + todo.id, {
          id: todo.id,
          text: todo.text,
          done: !todo.done
        });
        this.getTodos();
      },
      async deleteTodo(todo) {
        await axios.delete("/api/todos/" + todo.id);
        this.getTodos();
      },
      async deleteTodos() {
        await axios.delete("/api/todos");
        this.getTodos();
      }
    }
  }
</script>
