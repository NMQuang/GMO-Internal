import requestOTService from "../request.service";
import messageAction from "../../../../../shared/common/action/message.action";
import loaderAction from "../../../../../shared/common/action/loading.action";
import { message } from "../../../../../shared/constants/message.constant";
import { type } from "../../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../../shared/common/action/error.action";
import actionUtil from "../../../../../shared/utils/action.util";
// Create request OT type
export const REQUEST_CREATE_REQUEST = "REQUEST_CREATE_REQUEST";
export const REQUEST_CREATE_SUCCESS = "REQUEST_CREATE_SUCCESS";
export const REQUEST_CREATE_FAILURE = "REQUEST_CREATE_FAILURE";
export const REQUEST_CREATE_RESET = "REQUEST_CREATE_RESET";
// Get employee type
export const GET_EMPLOYEE_LIST_REQUEST = "GET_EMPLOYEE_LIST_REQUEST";
export const GET_EMPLOYEE_LIST_SUCCESS = "GET_EMPLOYEE_LIST_SUCCESS";
export const GET_EMPLOYEE_LIST_FAILURE = "GET_EMPLOYEE_LIST_FAILURE";

export const GET_PROJECT_BY_EMPLOYEE_REQUEST =
  "GET_PROJECT_BY_EMPLOYEE_REQUEST";
export const GET_PROJECT_BY_EMPLOYEE_SUCCESS =
  "GET_PROJECT_BY_EMPLOYEE_SUCCESS";
export const GET_PROJECT_BY_EMPLOYEE_FAILURE =
  "GET_PROJECT_BY_EMPLOYEE_FAILURE";

export const GET_EMPLOYEE_BY_PROJECT_REQUEST =
  "GET_EMPLOYEE_BY_PROJECT_REQUEST";
export const GET_EMPLOYEE_BY_PROJECT_SUCCESS =
  "GET_EMPLOYEE_BY_PROJECT_SUCCESS";
export const GET_EMPLOYEE_BY_PROJECT_FAILURE =
  "GET_EMPLOYEE_BY_PROJECT_FAILURE";
const createRequestOTAction = {};

/**
 * Get employee list
 */
createRequestOTAction.getEmployeeList = () => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(GET_EMPLOYEE_LIST_REQUEST));
    const result = await requestOTService.getEmployeeList();
    // Check result is not undefined
    if (result) {
      // Check if status 200 success
      if (result.status === 200) {
        dispatch(actionUtil.success(GET_EMPLOYEE_LIST_SUCCESS, result));
      }
      // Check if failure
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(GET_EMPLOYEE_LIST_FAILURE, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
/**
 * Create request OT
 *
 * @param data
 */
createRequestOTAction.createRequestOT = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(REQUEST_CREATE_REQUEST));
    const result = await requestOTService.createRequestOT(data);
    // Check result is not undefined
    if (result) {
      // Check if status 201 create success
      if (result.status === 201) {
        dispatch(actionUtil.success(REQUEST_CREATE_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_006, type.TYPE_SUCCESS)
        );
      }
      // Check if failure
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(REQUEST_CREATE_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_008, type.TYPE_DANGER)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

createRequestOTAction.getProjectByEmployee = () => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(GET_PROJECT_BY_EMPLOYEE_REQUEST));
    const result = await requestOTService.getProjectByEmployee();
    // Check result is not undefined
    if (result) {
      // Check if status 200 success
      if (result.status === 200) {
        dispatch(actionUtil.failure(GET_PROJECT_BY_EMPLOYEE_SUCCESS, result));
      }
      // Check if failure
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(GET_PROJECT_BY_EMPLOYEE_FAILURE, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

createRequestOTAction.getEmployeeByProject = projectCode => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(GET_EMPLOYEE_BY_PROJECT_REQUEST));
    const result = await requestOTService.getEmployeeByProject(projectCode);
    // Check result is not undefined
    if (result) {
      // Check if status 200 success
      if (result.status === 200) {
        dispatch(actionUtil.failure(GET_EMPLOYEE_BY_PROJECT_SUCCESS, result));
      }
      // Check if failure
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(GET_EMPLOYEE_BY_PROJECT_FAILURE, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

export default createRequestOTAction;
