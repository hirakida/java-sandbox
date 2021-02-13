import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {connect} from 'react-redux'
import {message} from '../actions'
import api from '../api'

class App extends Component {

  componentDidMount() {
    this.props.getData();
  }

  render() {
    return (
      <div>
        <p>
          {typeof this.props.data === 'undefined' ? this.props.error.message : this.props.data.text}
        </p>
      </div>
    );
  }
}

App.propTypes = {
  data: PropTypes.object,
  error: PropTypes.instanceOf(Error),
  getData: PropTypes.func.isRequired
};

export default connect(
  state => ({
    data: state.data,
    error: state.error,
  }),
  dispatch => ({
    getData: () => api.getMessage()
                      .then(data => dispatch(message({data: data})))
                      .catch(error => dispatch(message({error: error})))
  })
)(App);
