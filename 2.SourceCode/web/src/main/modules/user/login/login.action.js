import userService from "../user.service";
import actionUtil, {
  STORE_REGISTER_REQUEST
} from "../../../../shared/utils/action.util";
import loaderAction from "../../../../shared/common/action/loading.action";
import messageAction from "../../../../shared/common/action/message.action";
import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";
import commonUtil from "../../../../shared/utils/common.util";

// action type login
export const USERS_LOGIN_REQUEST = "USERS_LOGIN_REQUEST";
export const USERS_LOGIN_SUCCESS = "USERS_LOGIN_SUCCESS";
export const USERS_LOGIN_FAILURE = "USERS_LOGIN_FAILURE";

// action type logout
export const USERS_LOGOUT_REQUEST = "USERS_LOGOUT_REQUEST";

/**
 * login
 * @param {*} emial
 * @param {*} password
 */
export const login = (email, password) => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(USERS_LOGIN_REQUEST));
    const result = await userService.login(email, password);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        if (result.status === 400) {
          dispatch(loaderAction.hideLoader());
          dispatch(actionUtil.failure(USERS_LOGIN_FAILURE, result));
          dispatch(
            messageAction.showMessage(message.MSG_INFO_001, type.TYPE_DANGER)
          );
        } else {
          // handle error
          dispatch(handleErrorAction.handleError(result));
        }
      } else {
        dispatch(actionUtil.success(USERS_LOGIN_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_002, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

/**
 * logput
 */
export const logout = () => dispatch => {
  dispatch(actionUtil.request(USERS_LOGOUT_REQUEST));
};
