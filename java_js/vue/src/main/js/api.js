import axios from 'axios'

export default {
  getTodos(cb) {
    axios({
      method: 'get',
      url: '/api/todos'
    }).then(cb)
  },
  addTodo(data, cb) {
    axios({
      method: 'post',
      url: '/api/todos',
      data: data
    }).then(cb)
  },
  updateTodo(data, cb) {
    axios({
      method: 'put',
      url: '/api/todos/' + data.id,
      data: data
    }).then(cb)
  },
  deleteTodo(data, cb) {
    axios({
      method: 'delete',
      url: '/api/todos/' + data.id
    }).then(cb)
  },
  deleteTodos(cb) {
    axios({
      method: 'delete',
      url: '/api/todos'
    }).then(cb)
  }
}
