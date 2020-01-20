import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";
import projectService from "../project.service";
import loaderAction from "../../../../shared/common/action/loading.action";
import actionUtil from "../../../../shared/utils/action.util";
import messageAction from "../../../../shared/common/action/message.action";

// Get list project
export const PROJECT_GET_DETAIL_REQUEST = "PROJECT_GET_DETAIL_REQUEST";
export const PROJECT_GET_DETAIL_SUCCESS = "PROJECT_GET_DETAIL_SUCCESS";
export const PROJECT_GET_DETAIL_FAILURE = "PROJECT_GET_DETAIL_FAILURE";

export const getDetail = projectCode => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(PROJECT_GET_DETAIL_REQUEST));
    const result = await projectService.getDetail(projectCode);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(PROJECT_GET_DETAIL_FAILURE, result));
      } else {
        dispatch(actionUtil.success(PROJECT_GET_DETAIL_SUCCESS, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
