<template>
  <div>
    <h1>bookmark</h1>
    <table class="table">
      <thead>
      <tr class="row">
        <th class="col-1">bookmarkId</th>
        <th class="col-2"></th>
        <th class="col-4">itemName</th>
        <th class="col-2">itemCode</th>
        <th class="col-2">shopName</th>
        <th class="col-1"></th>
      </tr>
      </thead>
      <tbody>
      <tr class="row" v-for="bookmark in bookmarks">
        <td class="col-1">
          <router-link v-bind:to="{ name : 'bookmark', params : { id: bookmark.bookmarkId }}">
            {{bookmark.bookmarkId}}
          </router-link>
        </td>
        <td class="col-2"><img v-bind:src="bookmark.mediumImageUrl"></td>
        <td class="col-4">{{bookmark.itemName}}</td>
        <td class="col-2">{{bookmark.itemCode}}</td>
        <td class="col-2">{{bookmark.shopName}}</td>
        <td class="col-1">
          <button @click="deleteBookmark(bookmark.bookmarkId)" type="button" class="btn btn-danger">delete
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <table class="table">
      <thead>
      <tr>
        <th>rank</th>
        <th></th>
        <th>itemName</th>
        <th>itemPrice</th>
        <th>itemCode</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="rank in ranking">
        <td>{{rank.rank}}</td>
        <td>
          <img v-if="rank.mediumImageUrls.length > 0" v-bind:src="rank.mediumImageUrls[0]">
        </td>
        <td>{{rank.itemName}}</td>
        <td>{{rank.itemPrice}}</td>
        <td>{{rank.itemCode}}</td>
        <td>
          <button @click="addBookmark(rank.itemCode)" type="button" class="btn btn-primary">add</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import store from '../store'

  export default {
    computed: mapGetters(['bookmarks', 'ranking']),
    methods: {
      addBookmark: itemCode => store.dispatch("addBookmark", itemCode),
      deleteBookmark: bookmarkId => store.dispatch("deleteBookmark", bookmarkId)
    }
  }
</script>
