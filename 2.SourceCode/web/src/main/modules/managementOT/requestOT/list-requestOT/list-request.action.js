import requestOTService from "../request.service";
import loaderAction from "../../../../../shared/common/action/loading.action";
import actionUtil, {
  STORE_REGISTER_REQUEST
} from "../../../../../shared/utils/action.util";
export const GET_REQUEST_LIST_REQUEST = "GET_REQUEST_LIST_REQUEST";
export const GET_REQUEST_LIST_SUCCESS = "REQUEST_LIST_SUCCESS";
export const GET_REQUEST_LIST_FAILURE = "REQUEST_LIST_FAILURE";
export const GET_PROJECT_NAME_LIST_REQUEST = "GET_PROJECT_NAME_LIST_REQUEST";
export const GET_PROJECT_NAME_LIST_SUCCESS = "GET_PROJECT_NAME_LIST_SUCCESS";
export const GET_PROJECT_NAME_LIST_FAILURE = "GET_PROJECT_NAME_LIST_FAILURE";

import handleErrorAction from "../../../../../shared/common/action/error.action";
import commonUtil from "../../../../../shared/utils/common.util";
const getListRequestOTAction = {};

/**
 * Get list request
 * @param data
 */
getListRequestOTAction.getListRequest = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(GET_REQUEST_LIST_REQUEST));
    const result = await requestOTService.getRequestOTList(data);
    // Check result is not undefined
    if (result) {
      if (result.status !== 200) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(GET_REQUEST_LIST_FAILURE, result));
      } else {
        dispatch(actionUtil.success(GET_REQUEST_LIST_SUCCESS, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

/**
 * Get list request
 * @param data
 */
getListRequestOTAction.getProjectNameList = () => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(GET_PROJECT_NAME_LIST_REQUEST));
    const result = await requestOTService.getProjectNameList();
    // Check result is not undefined
    if (result) {
      if (result.status !== 200) {
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(GET_PROJECT_NAME_LIST_FAILURE, result));
      } else {
        dispatch(actionUtil.failure(GET_PROJECT_NAME_LIST_SUCCESS, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

export default getListRequestOTAction;
