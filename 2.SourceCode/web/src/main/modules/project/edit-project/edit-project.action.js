import { message } from "../../../../shared/constants/message.constant";
import { type } from "../../../../shared/constants/type-style.constant";
import handleErrorAction from "../../../../shared/common/action/error.action";
import projectService from "../project.service";
import loaderAction from "../../../../shared/common/action/loading.action";
import actionUtil from "../../../../shared/utils/action.util";
import messageAction from "../../../../shared/common/action/message.action";

// Get list project
export const PROJECT_EDIT_REQUEST = "PROJECT_EDIT_REQUEST";
export const PROJECT_EDIT_SUCCESS = "PROJECT_EDIT_SUCCESS";
export const PROJECT_EDIT_FAILURE = "PROJECT_EDIT_FAILURE";

export const editProject = projectInfo => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(PROJECT_EDIT_REQUEST));
    const result = await projectService.editProject(projectInfo);
    // Check result is not undefined
    if (result) {
      // check status response. if 4xx dispatch failure else dispatch success
      if (result.status !== 200) {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(PROJECT_EDIT_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_032, type.TYPE_DANGER)
        );
      } else {
        dispatch(actionUtil.success(PROJECT_EDIT_SUCCESS, result));
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
