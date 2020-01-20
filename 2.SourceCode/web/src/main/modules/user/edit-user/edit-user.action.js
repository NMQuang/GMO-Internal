import userService from "../user.service";
import actionUtil from "../../../../shared/utils/action.util";
import loaderAction from "../../../../shared/common/action/loading.action";
import messageAction from "../../../../shared/common/action/message.action";
import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";

export const EMPLOYEE_GET_DETAIL_REQUEST = "EMPLOYEE_GET_DETAIL_REQUEST";
export const EMPLOYEE_GET_DETAIL_SUCCESS = "EMPLOYEE_GET_DETAIL_SUCCESS";
export const EMPLOYEE_GET_DETAIL_FAILURE = "EMPLOYEE_GET_DETAIL_FAILURE";

export const EMPLOYEE_UPDATE_REQUEST = "EMPLOYEE_UPDATE_REQUEST";
export const EMPLOYEE_UPDATE_SUCCESS = "EMPLOYEE_UPDATE_SUCCESS";
export const EMPLOYEE_UPDATE_FAILURE = "EMPLOYEE_UPDATE_FAILURE";

export const getDetailEmployee = employeeCode => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(EMPLOYEE_GET_DETAIL_REQUEST));
    const result = await userService.getDetailEmployee(employeeCode);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(EMPLOYEE_GET_DETAIL_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_019, type.TYPE_DANGER)
        );
      } else {
        dispatch(actionUtil.success(EMPLOYEE_GET_DETAIL_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_014, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

export const updateEmployee = employeeInfo => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(EMPLOYEE_UPDATE_REQUEST));
    const result = await userService.updateEmployee(employeeInfo);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(EMPLOYEE_UPDATE_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_033, type.TYPE_DANGER)
        );
      } else {
        dispatch(actionUtil.success(EMPLOYEE_UPDATE_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_022, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
