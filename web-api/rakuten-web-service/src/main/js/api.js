import axios from 'axios'

export default {
  getBookmarks(cb) {
    axios({
      method: 'get',
      url: '/api/bookmarks'
    }).then(cb)
  },
  addBookmark(itemCode, cb) {
    axios({
      method: 'post',
      url: '/api/bookmarks',
      data: {itemCode: itemCode}
    }).then(cb)
  },
  deleteBookmark(id, cb) {
    axios({
      method: 'delete',
      url: '/api/bookmarks/' + id
    }).then(cb)
  },
  getRanking(cb) {
    axios.get('/api/ichiba/ranking').then(cb)
  }
}
