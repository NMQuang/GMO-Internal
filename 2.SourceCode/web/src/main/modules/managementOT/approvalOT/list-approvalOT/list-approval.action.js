export const GET_APPROVAL_LIST_REQUEST = "GET_APPROVAL_LIST_REQUEST";
export const GET_APPROVAL_LIST_SUCCESS = "GET_APPROVAL_LIST_SUCCESS";
export const GET_APPROVAL_LIST_FAILURE = "GET_APPROVAL_LIST_FAILURE";
export const EXPORT_DATA_SUCCES = "EXPORT_DATA_SUCCESS";
export const EXPORT_DATA_FAILURE = "EXPORT_DATA_FAILURE";
export const EXPORT_DATA_REQUEST = "EXPORT_DATA_REQUEST";
import loaderAction from "../../../../../shared/common/action/loading.action";
import actionUtil from "../../../../../shared/utils/action.util";
import ApprovalOTService from "../../approvalOT/approval.service";
import { fileName } from "../../../../../shared/constants/file-name.constant";
import handleErrorAction from "../../../../../shared/common/action/error.action";
const listApproval = () => {};
/**
 * Get list request
 * @param data
 */
listApproval.getListApproval = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(GET_APPROVAL_LIST_REQUEST));
    const result = await ApprovalOTService.getApprovalOTList(data);
    // Check result is not undefined
    if (result) {
      // Check if status 200 success
      if (result.status == 200) {
        dispatch(actionUtil.success(GET_APPROVAL_LIST_SUCCESS, result));
      }
      // Check if failure
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(GET_APPROVAL_LIST_FAILURE, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
/**
 * Export list request
 * @param data
 */
listApproval.exportData = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(EXPORT_DATA_REQUEST));
    const result = await ApprovalOTService.exportData(data);
    const date = data.requestDate;
    // Check result is not undefined
    if (result) {
      if (result.status === 200) {
        const data = await result.blob();
        if (data) {
          let url = window.URL.createObjectURL(data);
          let element = document.createElement("a");
          element.href = url;
          element.download =
            fileName.FILE_NAME + "_" + date + fileName.FILE_EXTENSION;
          element.click();
          dispatch(loaderAction.hideLoader());
        }
        dispatch(actionUtil.success(EXPORT_DATA_SUCCES, result));
      } else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(EXPORT_DATA_FAILURE, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};
export default listApproval;
