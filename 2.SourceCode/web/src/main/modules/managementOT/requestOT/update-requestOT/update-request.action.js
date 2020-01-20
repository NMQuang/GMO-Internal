import requestOTService from "../request.service";
import loaderAction from "../../../../../shared/common/action/loading.action";
import messageAction from "../../../../../shared/common/action/message.action";
import { type } from "../../../../../shared/constants/type-style.constant";
import { message } from "../../../../../shared/constants/message.constant";
import notFoundAction from "../../../../../shared/errors/not-found/not-found.action";
import handleErrorAction from "../../../../../shared/common/action/error.action";
import actionUtil from "../../../../../shared/utils/action.util";
import constant from "../../../../../shared/constants/page.constant";

// Get detail request OT
export const GET_DETAIL_REQUEST_OT_REQUEST = "GET_DETAIL_REQUEST_OT_REQUEST";
export const GET_DETAIL_REQUEST_OT_SUCCESS = "GET_DETAIL_REQUEST_OT_SUCCESS";
export const GET_DETAIL_REQUEST_OT_FAILURE = "GET_DETAIL_REQUEST_OT_FAILURE";
// Update request OT
export const UPDATE_REQUEST_OT_REQUEST = "UPDATE_REQUEST_OT_REQUEST";
export const UPDATE_REQUEST_OT_SUCCESS = "UPDATE_REQUEST_OT_SUCCESS";
export const UPDATE_REQUEST_OT_FAILURE = "UPDATE_REQUEST_OT_FAILURE";
// Cancel approval
export const CANCEL_REQUEST_OT_REQUEST = "CANCEL_REQUEST_OT_REQUEST";
export const CANCEL_REQUEST_OT_SUCCESS = "CANCEL_REQUEST_OT_SUCCESS";
export const CANCEL_REQUEST_OT_FAILURE = "CANCLE_REQUEST_OT_FAILURE";
// Approval
export const APPROVAL_REQUEST_OT_REQUEST = "APPROVAL_REQUEST_OT_REQUEST";
export const APPROVAL_REQUEST_OT_SUCCESS = "APPROVAL_REQUEST_OT_SUCCESS";
export const APPROVAL_REQUEST_OT_FAILURE = "APPROVAL_REQUEST_OT_FAILURE";
// Feedback
export const SEND_EMAIL_REQUEST_OT_REQUEST = "SEND_EMAIL_REQUEST_OT_REQUEST";
export const SEND_EMAIL_REQUEST_OT_SUCCESS = "SEND_EMAIL_REQUEST_OT_SUCCESS";
export const SEND_EMAIL_REQUEST_OT_FAILURE = "SEND_EMAIL_REQUEST_OT_FAILURE";

const updateRequestOTAction = {};
/**
 * Get detai request OT
 *
 * @param requestId
 */
updateRequestOTAction.getDetailRequestOT = (requestId, dateSearch) => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(GET_DETAIL_REQUEST_OT_REQUEST));
    const result = await requestOTService.getDetailRequestOT(
      requestId,
      dateSearch
    );
    // Check result is not undefined
    if (result) {
      if (result.status !== 200) {
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(GET_DETAIL_REQUEST_OT_FAILURE, result));
      } else {
        dispatch(actionUtil.success(GET_DETAIL_REQUEST_OT_SUCCESS, result));
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

/**
 * Update request OT
 *
 * @param data
 */
updateRequestOTAction.updateRequestOT = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(UPDATE_REQUEST_OT_REQUEST));
    const result = await requestOTService.updateRequestOT(data);
    // Check result is not undefined
    if (result) {
      // Check if status 200
      if (result.status === 200) {
        dispatch(actionUtil.success(UPDATE_REQUEST_OT_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_008, type.TYPE_SUCCESS)
        );
      }
      // Check if status diff 200 and 400
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(UPDATE_REQUEST_OT_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_010, type.TYPE_DANGER)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

/**
 * Cancel request OT
 *
 * @param data
 */
updateRequestOTAction.cancelRequestOT = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(CANCEL_REQUEST_OT_REQUEST));
    const result = await requestOTService.cancelRequestOT(data);
    // Check result is not undefined
    if (result) {
      // Check if status 200
      if (result.status === 200) {
        dispatch(actionUtil.success(CANCEL_REQUEST_OT_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_009, type.TYPE_SUCCESS)
        );
      }
      // Check if status diff 200
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(CANCEL_REQUEST_OT_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_011, type.TYPE_DANGER)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

/**
 * Approval request OT
 *
 * @param data
 */
updateRequestOTAction.approvalRequestOT = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(APPROVAL_REQUEST_OT_REQUEST));
    const result = await requestOTService.approvalRequestOT(data);
    // Check result is not undefined
    if (result) {
      // Check if status 200
      if (result.status === 200) {
        dispatch(actionUtil.success(APPROVAL_REQUEST_OT_SUCCESS, result));
        // check data.status to set message
        if (data.status === constant.FLAG_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_INFO_011, type.TYPE_SUCCESS)
          );
        } else if (data.status === constant.FLAG_UN_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_INFO_024, type.TYPE_SUCCESS)
          );
        }
      }
      // Check if status diff 200
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(APPROVAL_REQUEST_OT_FAILURE, result));
        // check data.status to set message
        if (data.status === constant.FLAG_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_ERROR_012, type.TYPE_DANGER)
          );
        } else if (data.status === constant.FLAG_UN_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_ERROR_036, type.TYPE_SUCCESS)
          );
        }
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

/**
 * verify request OT
 *
 * @param data
 */
updateRequestOTAction.verifyRequestOT = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(APPROVAL_REQUEST_OT_REQUEST));
    const result = await requestOTService.approvalRequestOT(data);
    // Check result is not undefined
    if (result) {
      // Check if status 200
      if (result.status === 200) {
        dispatch(actionUtil.success(APPROVAL_REQUEST_OT_SUCCESS, result));
        // check data.status to set message
        if (data.status === constant.FLAG_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_INFO_025, type.TYPE_SUCCESS)
          );
        } else if (data.status === constant.FLAG_UN_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_INFO_026, type.TYPE_SUCCESS)
          );
        }
      }
      // Check if status diff 200
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(loaderAction.hideLoader());
        dispatch(actionUtil.failure(APPROVAL_REQUEST_OT_FAILURE, result));
        // check data.status to set message
        if (data.status === constant.FLAG_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_ERROR_037, type.TYPE_DANGER)
          );
        } else if (data.status === constant.FLAG_UN_VERFIRY_APPROVAL) {
          dispatch(
            messageAction.showMessage(message.MSG_ERROR_038, type.TYPE_SUCCESS)
          );
        }
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

/**
 * Send email request OT
 *
 * @param data
 */
updateRequestOTAction.sendEmailRequestOT = data => {
  return async dispatch => {
    dispatch(loaderAction.showLoader());
    dispatch(actionUtil.request(SEND_EMAIL_REQUEST_OT_REQUEST));
    const result = await requestOTService.sendEmailRequestOT(data);
    // Check result is not undefined
    if (result) {
      // Check if status 200
      if (result.status === 200) {
        dispatch(actionUtil.success(SEND_EMAIL_REQUEST_OT_SUCCESS, result));
        dispatch(
          messageAction.showMessage(message.MSG_INFO_010, type.TYPE_SUCCESS)
        );
      }
      // Check if status diff 200
      else {
        // handle error
        dispatch(handleErrorAction.handleError(result));
        dispatch(actionUtil.failure(SEND_EMAIL_REQUEST_OT_FAILURE, result));
        dispatch(
          messageAction.showMessage(message.MSG_ERROR_013, type.TYPE_SUCCESS)
        );
      }
    } else {
      // handle error
      dispatch(handleErrorAction.handleError(result));
    }
    dispatch(loaderAction.hideLoader());
  };
};

export default updateRequestOTAction;
