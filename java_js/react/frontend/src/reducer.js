import {handleActions} from 'redux-actions';
import {message} from './actions';

export const defaultState = {data : {text: "Loading"}};

export const reducer = handleActions({
  [message]: (state, action) => ({
    data: action.payload.data,
    error: action.payload.error
  }),
}, defaultState);
