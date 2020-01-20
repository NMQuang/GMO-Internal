import userService from "../user.service";
import actionUtil from "../../../../shared/utils/action.util";
import loaderAction from "../../../../shared/common/action/loading.action";
import messageAction from "../../../../shared/common/action/message.action";
import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";

export const EMPLOYEE_SEARCH_REQUEST = "EMPLOYEE_SEARCH_REQUEST";
export const EMPLOYEE_SEARCH_SUCCESS = "EMPLOYEE_SEARCH_SUCCESS";
export const EMPLOYEE_SEARCH_FAILURE = "EMPLOYEE_SEARCH_FAILURE";

export const EMPLOYEE_EXPORT_REQUEST = "EMPLOYEE_EXPORT_REQUEST";
export const EMPLOYEE_EXPORT_SUCCESS = "EMPLOYEE_EXPORT_SUCCESS";
export const EMPLOYEE_EXPORT_FAILURE = "EMPLOYEE_EXPORT_FAILURE";

export const search = querySearch => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(EMPLOYEE_SEARCH_REQUEST));
    const result = await userService.search(querySearch);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(EMPLOYEE_SEARCH_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_009, type.TYPE_DANGER)
        );
      } else {
        dispatch(actionUtil.success(EMPLOYEE_SEARCH_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_012, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

export const exportEmployee = querySearch => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(EMPLOYEE_EXPORT_REQUEST));
    const result = await userService.export(querySearch);
    // check status response. if 4xx dispatch failure else dispatch success
    if (result.status !== 200) {
      dispatch(actionUtil.failure(EMPLOYEE_EXPORT_FAILURE, result));
      dispatch(
        messageAction.showMessage(message.MSG_ERROR_009, type.TYPE_DANGER)
      );
    } else {
      dispatch(actionUtil.success(EMPLOYEE_EXPORT_SUCCESS, result));
      dispatch(
        messageAction.showMessage(message.MSG_INFO_012, type.TYPE_SUCCESS)
      );
    }
    dispatch(loaderAction.hideLoader());
  };
};
