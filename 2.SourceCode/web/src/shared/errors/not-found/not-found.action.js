export const IS_NOT_FOUND = "IS_NOT_FOUND";
export const CLEAR_NOT_FOUND = "CLEAR_NOT_FOUND";

const notFoundAction = {};
/**
 * Request not found
 *
 */
notFoundAction.isNotFound = () => {
  return dispatch => {
    dispatch(notFoundAction.notFoundRequest());
  };
};
notFoundAction.clearNotFound = () => {
  return dispatch => {
    dispatch(notFoundAction.clearNotFoundRequest());
  };
};

notFoundAction.clearNotFoundRequest = () => {
  return {
    type: CLEAR_NOT_FOUND
  };
};

notFoundAction.notFoundRequest = () => {
  return {
    type: IS_NOT_FOUND
  };
};

export default notFoundAction;
