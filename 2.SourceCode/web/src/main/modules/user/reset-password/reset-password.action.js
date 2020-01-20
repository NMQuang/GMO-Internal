import userService from "../user.service";
import actionUtil from "../../../../shared/utils/action.util";
import loaderAction from "../../../../shared/common/action/loading.action";
import messageAction from "../../../../shared/common/action/message.action";
import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";

// action type forget password
export const USERS_RESET_PASSWORD_REQUEST = "USERS_RESET_PASSWORD_REQUEST";
export const USERS_RESET_PASSWORD_SUCCESS = "USERS_RESET_PASSWORD_SUCCESS";
export const USERS_RESET_PASSWORD_FAILURE = "USERS_RESET_PASSWORD_FAILURE";

/**
 * reset password
 * @param {*} emial
 * @param {*} password
 */
export const resetPassword = email => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(USERS_RESET_PASSWORD_REQUEST));
    const result = await userService.resetPassword(email);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        if (result.status === 400) {
          dispatch(loaderAction.hideLoader());
          dispatch(actionUtil.failure(USERS_RESET_PASSWORD_FAILURE, result));
          dispatch(
            messageAction.showMessage(message.MSG_INFO_003, type.TYPE_DANGER)
          );
        } else {
          // handle error
          dispatch(handleErrorAction.handleError(result));
        }
      } else {
        dispatch(actionUtil.success(USERS_RESET_PASSWORD_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_004, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
