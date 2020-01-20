import { SHOW_MESSAGE, HIDE_MESSAGE } from "../action/message.action";

// define init state
let initialState = {
  message: null,
  type: null
};

/**
 * reducer of message
 * @param {Object} state
 * @param {Action Creator} action
 */
export const messageReducer = (state = initialState, action) => {
  switch (action.type) {
    // show message
    case SHOW_MESSAGE: {
      return {
        message: action.message,
        type: action.typeMsg
      };
    }
    // hide message
    case HIDE_MESSAGE: {
      return {
        message: null,
        type: null
      };
    }
    default:
      return state;
  }
};
