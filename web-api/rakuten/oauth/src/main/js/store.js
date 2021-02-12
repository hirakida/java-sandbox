import Vue from 'vue'
import Vuex from 'vuex'
import api from './api'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    bookmarks: [],
    ranking: []
  },
  getters: {
    bookmarks: state => state.bookmarks,
    ranking: state => state.ranking
  },
  mutations: {
    bookmarks: (state, bookmarks) => state.bookmarks = bookmarks,
    deleteBookmark: (state, bookmarkId) => {
      state.bookmarks.some((bookmark, i) => {
        if (bookmark.bookmarkId === bookmarkId) {
          state.bookmarks.splice(i, 1);
        }
      });
    },
    ranking: (state, ranking) => state.ranking = ranking
  },
  actions: {
    bookmarks: context => {
      api.getBookmarks(response => {
        context.commit('bookmarks', response.data)
      });
    },
    addBookmark: (context, itemCode) => {
      api.addBookmark(itemCode, response => {
        context.dispatch('bookmarks');
      });
    },
    deleteBookmark: (context, bookmarkId) => {
      api.deleteBookmark(bookmarkId, response => {
        context.commit('deleteBookmark', bookmarkId);
      });
    },
    ranking: context => {
      api.getRanking(response => {
        context.commit('ranking', response.data.Items)
      });
    }
  }
});
