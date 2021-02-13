<template>
  <div>
    <div>
      <nav class="navbar navbar-default">
        <div class="navbar-header">
          <router-link to="/" class="navbar-brand">todos</router-link>
        </div>
      </nav>
    </div>

    <h1>ToDo detail</h1>
    <table class="table" v-if="todo">
      <thead>
      <tr>
      </tr>
      </thead>
      <tbody>
      <tr>
        <th>id</th>
        <td>{{todo.id}}</td>
      </tr>
      <tr>
        <th>text</th>
        <td>{{todo.text}}</td>
      </tr>
      <tr>
        <th>done</th>
        <td>{{todo.done}}</td>
      </tr>
      <tr>
        <th>createdAt</th>
        <td>{{todo.createdAt}}</td>
      </tr>
      <tr>
        <th>updatedAt</th>
        <td>{{todo.updatedAt}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  export default {
    computed: {
      todo() {
        const id = Number(this.$route.params.id);
        return this.$store.getters.todo(id)
      }
    }
  }
</script>
