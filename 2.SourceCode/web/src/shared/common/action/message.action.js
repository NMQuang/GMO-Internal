// action message
export const SHOW_MESSAGE = "SHOW_MESSAGE";
export const HIDE_MESSAGE = "HIDE_MESSAGE";

// define action of message
const messageAction = {};

/**
 * show message
 * @return {} dispatch
 */
messageAction.showMessage = (message, typeMsg) => {
  return dispatch => {
    dispatch(messageAction.showMessageRequest(message, typeMsg));
    setTimeout(() => {
      dispatch(messageAction.hideMessageRequest());
    }, 3000);
  };
};

/**
 * show message request
 * @return {Action Creator}
 */
messageAction.showMessageRequest = (message, typeMsg) => {
  return {
    type: SHOW_MESSAGE,
    message,
    typeMsg
  };
};

/**
 * hide message request
 * @return {Action Creator}
 */
messageAction.hideMessageRequest = () => {
  return {
    type: HIDE_MESSAGE
  };
};

export default messageAction;
