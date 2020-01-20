import userService from "../user.service";
import actionUtil from "../../../../shared/utils/action.util";
import loaderAction from "../../../../shared/common/action/loading.action";
import messageAction from "../../../../shared/common/action/message.action";
import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";
import { USERS_LOGOUT_REQUEST } from "../login/login.action";

export const EMPLOYEE_CHANGE_PASSWORD_REQUEST =
  "EMPLOYEE_CHANGE_PASSWORD_REQUEST";
export const EMPLOYEE_CHANGE_PASSWORD_SUCCESS =
  "EMPLOYEE_CHANGE_PASSWORD_SUCCESS";
export const EMPLOYEE_CHANGE_PASSWORD_FAILURE =
  "EMPLOYEE_CHANGE_PASSWORD_FAILURE";

export const USERS_CLEAR_TOKEN_REQUEST = "USERS_CLEAR_TOKEN_REQUEST";

export const changePassword = userInfo => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(EMPLOYEE_CHANGE_PASSWORD_REQUEST));
    const result = await userService.changePassword(userInfo);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        if (result.status === 400) {
          dispatch(loaderAction.hideLoader());
          dispatch(
            actionUtil.failure(EMPLOYEE_CHANGE_PASSWORD_FAILURE, result)
          );
          dispatch(
            messageAction.showMessage(message.MSG_ERROR_017, type.TYPE_DANGER)
          );
        } else {
          // handle error
          dispatch(handleErrorAction.handleError(result));
        }
      } else {
        dispatch(actionUtil.success(EMPLOYEE_CHANGE_PASSWORD_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_013, type.TYPE_SUCCESS)
        );
        setTimeout(() => {
          dispatch(actionUtil.request(USERS_CLEAR_TOKEN_REQUEST));
        }, 2000);
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
