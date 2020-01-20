import actionUtil from "../../../../shared/utils/action.util";
import loaderAction from "../../../../shared/common/action/loading.action";
import messageAction from "../../../../shared/common/action/message.action";
import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";
import projectService from "../project.service";

// Create project
export const PROJECT_CREATE_REQUEST = "PROJECT_CREATE_REQUEST";
export const PROJECT_CREATE_SUCCESS = "PROJECT_CREATE_SUCCESS";
export const PROJECT_CREATE_FAILURE = "PROJECT_CREATE_FAILURE";

// get project rank
export const PROJECT_GET_RANK_REQUEST = "PROJECT_GET_RANK_REQUEST";
export const PROJECT_GET_RANK_SUCCESS = "PROJECT_GET_RANK_SUCCESS";
export const PROJECT_GET_RANK_FAILURE = "PROJECT_GET_RANK_FAILURE";

export const createProject = projectInfo => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(PROJECT_CREATE_REQUEST));
    const result = await projectService.createProject(projectInfo);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 201) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(PROJECT_CREATE_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_029, type.TYPE_DANGER)
        );
      } else {
        dispatch(actionUtil.success(PROJECT_CREATE_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_018, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

export const getProjectRank = () => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(PROJECT_GET_RANK_REQUEST));
    const result = await projectService.getProjectRank();
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(PROJECT_GET_RANK_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_030, type.TYPE_DANGER)
        );
      } else {
        dispatch(actionUtil.success(PROJECT_GET_RANK_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_019, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
