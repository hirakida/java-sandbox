<template>
  <div id="app" class="container">
    <div class="row">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.$store.dispatch("bookmarks");
      this.$store.dispatch("ranking");
    }
  }
</script>
