import history from "../../config/history.config";
import { role } from "../constants/role.constant";
import { connect } from "react-redux";

export const STORE_REGISTER_REQUEST = "STORE_REGISTER_REQUEST";

// define action common
const actionUtil = {};

/**
 * request, before action excute login/register, call action request
 * @param {type action} actionType
 * @return {Action Creator}
 */
actionUtil.request = actionType => {
  return {
    type: actionType
  };
};

/**
 * success, when a process success, call action success
 * @param {type action} actionType
 * @param {Object} result
 * @return {Action Creator}
 */
actionUtil.success = (actionType, result) => {
  return {
    type: actionType,
    payload: result
  };
};

/**
 * failure, when a process failure, call action failure
 * @param {type action} actionType
 * @param {Object} error
 * @return {Action Creator}
 */
actionUtil.failure = (actionType, error) => {
  return {
    type: actionType,
    payload: error
  };
};

actionUtil.handleChangePage = roleId => {
  // return (dispatch, getState) => {
  // const userStore = getState();
  // console.log(userStore);
  switch (roleId) {
    case role.ROLE_ADMIN:
    case role.ROLE_USER:
    case role.ROLE_QA:
      history.push("/project/list");
      break;
    case role.ROLE_SUB_ADMIN:
      history.push("/user/list");
      break;
    default:
      history.push("/project/list");
      break;
  }
  // };
};

actionUtil.registerStore = (actionType, result) => {
  return {
    type: actionType,
    payload: result
  };
};

export default actionUtil;
