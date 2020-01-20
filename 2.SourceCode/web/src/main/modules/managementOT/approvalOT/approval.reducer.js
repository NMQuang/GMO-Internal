import {
  GET_APPROVAL_LIST_REQUEST,
  GET_APPROVAL_LIST_SUCCESS,
  GET_APPROVAL_LIST_FAILURE,
  EXPORT_DATA_REQUEST,
  EXPORT_DATA_FAILURE,
  EXPORT_DATA_SUCCES
} from "./../approvalOT/list-approvalOT/list-approval.action";

// Define init state
let initialState = {
  projectNameList: [],
  //list approval
  listApproval: [],
  //export date
  isExportData: null
};
/**
 * Request reducer
 *
 * @param state
 * @param action
 */
export const approvalReducer = (state = initialState, action) => {
  switch (action.type) {
    // Get list approval request
    case GET_APPROVAL_LIST_REQUEST: {
      state.listApproval = [];
      return {
        ...state
      };
    }
    // Get list approval success
    case GET_APPROVAL_LIST_SUCCESS: {
      state.listApproval = action.payload.data.response;
      return {
        ...state
      };
    }
    // Get list approval failed
    case GET_APPROVAL_LIST_FAILURE: {
      state.listApproval = [];
      return {
        ...state
      };
    }
    // Exprot data request
    case EXPORT_DATA_REQUEST: {
      state.isExportData = false;
      return {
        ...state
      };
    }
    // Exprot data success
    case EXPORT_DATA_SUCCES: {
      state.isExportData = true;
      return {
        ...state
      };
    }
    // Exprot data failed
    case EXPORT_DATA_FAILURE: {
      state.isExportData = false;
      return {
        ...state
      };
    }
    default:
      return state;
  }
};
