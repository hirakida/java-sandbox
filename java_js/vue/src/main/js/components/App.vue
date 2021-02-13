<template>
  <div id="app" class="container">
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.$store.dispatch('todos');
    }
  }
</script>
