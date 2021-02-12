import Vue from 'vue'
import VueRouter from 'vue-router'
import Bookmarks from './components/Bookmarks.vue'
import Bookmark from './components/Bookmark.vue'

Vue.use(VueRouter);

export default new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/', component: Bookmarks
    },
    {
      path: '/bookmarks', component: Bookmarks
    },
    {
      path: '/bookmarks/:id', name: 'bookmark', component: Bookmark
    }
  ]
});
