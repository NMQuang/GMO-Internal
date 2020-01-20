import {
  GET_EMPLOYEE_LIST_REQUEST,
  GET_EMPLOYEE_LIST_SUCCESS,
  GET_EMPLOYEE_LIST_FAILURE,
  REQUEST_CREATE_REQUEST,
  REQUEST_CREATE_SUCCESS,
  REQUEST_CREATE_FAILURE,
  REQUEST_CREATE_RESET,
  GET_PROJECT_BY_EMPLOYEE_REQUEST,
  GET_PROJECT_BY_EMPLOYEE_SUCCESS,
  GET_PROJECT_BY_EMPLOYEE_FAILURE,
  GET_EMPLOYEE_BY_PROJECT_REQUEST,
  GET_EMPLOYEE_BY_PROJECT_SUCCESS,
  GET_EMPLOYEE_BY_PROJECT_FAILURE
} from "./create-requestOT/create-request.action";
import {
  GET_DETAIL_REQUEST_OT_REQUEST,
  GET_DETAIL_REQUEST_OT_FAILURE,
  GET_DETAIL_REQUEST_OT_SUCCESS,
  UPDATE_REQUEST_OT_REQUEST,
  UPDATE_REQUEST_OT_SUCCESS,
  UPDATE_REQUEST_OT_FAILURE,
  APPROVAL_REQUEST_OT_REQUEST,
  APPROVAL_REQUEST_OT_SUCCESS,
  APPROVAL_REQUEST_OT_FAILURE,
  CANCEL_REQUEST_OT_FAILURE,
  CANCEL_REQUEST_OT_SUCCESS,
  CANCEL_REQUEST_OT_REQUEST,
  SEND_EMAIL_REQUEST_OT_FAILURE,
  SEND_EMAIL_REQUEST_OT_SUCCESS,
  SEND_EMAIL_REQUEST_OT_REQUEST
} from "./update-requestOT/update-request.action";
import {
  GET_REQUEST_LIST_REQUEST,
  GET_REQUEST_LIST_FAILURE,
  GET_REQUEST_LIST_SUCCESS,
  GET_PROJECT_NAME_LIST_REQUEST,
  GET_PROJECT_NAME_LIST_FAILURE,
  GET_PROJECT_NAME_LIST_SUCCESS
} from "./list-requestOT/list-request.action";

// Define init state
let initialState = {
  // Employee list
  employeeList: [],
  // List error reponse when create failed
  errorCreateRequest: null,
  // Status create request OT
  isCreateRequest: null,
  // Status get employee list
  isGetEmployeeList: null,
  // Get detail request OT
  detailRequestOT: [],
  // error detail request ot
  errorDetailRequestOT: null,
  // is detail request ot
  isDetailRequestOT: null,
  // Update request OT
  isUpdateRequest: null,
  errorUpdateRequest: [],
  // Approval request OT
  isApprovalRequest: null,
  // Cancle request OT
  isCancelRequest: null,
  // Send email
  isSendEmail: null,
  //list data when get list success
  requestList: [],
  projectNameList: [],
  projectByEmployee: [],
  employeeByProject: []
};
/**
 * Request reducer
 *
 * @param state
 * @param action
 */
export const requestReducer = (state = initialState, action) => {
  switch (action.type) {
    // Get employee request
    case GET_EMPLOYEE_LIST_REQUEST: {
      state.isGetEmployeeList = false;
      return {
        ...state
      };
    }
    // Get employee success
    case GET_EMPLOYEE_LIST_SUCCESS: {
      state.isGetEmployeeList = true;
      state.employeeList = action.payload.data.response.resultData;
      return {
        ...state
      };
    }
    // Get employee failed
    case GET_EMPLOYEE_LIST_FAILURE: {
      state.isGetEmployeeList = false;
      return {
        ...state
      };
    }
    // Create request OT request
    case REQUEST_CREATE_REQUEST: {
      return {
        ...state
      };
    }
    // Create request OT success
    case REQUEST_CREATE_SUCCESS: {
      return {
        ...state
      };
    }
    // Create request OT failed Bad Request
    case REQUEST_CREATE_FAILURE: {
      state.errorCreateRequest = action.payload.data.response.errorData;
      return {
        ...state
      };
    }
    // Get detail request ot request
    case GET_DETAIL_REQUEST_OT_REQUEST: {
      return {
        ...state,
        requestList: [],
        isDetailRequestOT: false,
        detailRequestOT: []
      };
    }
    // Get detail request ot success
    case GET_DETAIL_REQUEST_OT_SUCCESS: {
      return {
        ...state,
        isDetailRequestOT: true,
        detailRequestOT: action.payload.data.response.resultData
      };
    }
    // Get detail request ot failed 400
    case GET_DETAIL_REQUEST_OT_FAILURE: {
      state.errorDetailRequestOT = action.payload.data.response.errorData;
      return {
        ...state
      };
    }
    // Update request ot request
    case UPDATE_REQUEST_OT_REQUEST: {
      return {
        ...state,
        isUpdateRequest: false,
        isApprovalRequest: false,
        isSendEmail: false,
        isCancelRequest: false,
        errorUpdateRequest: null
      };
    }
    // Update request ot success
    case UPDATE_REQUEST_OT_SUCCESS: {
      return {
        ...state,
        isUpdateRequest: true,
        errorUpdateRequest: null
      };
    }
    // Update request ot failed
    case UPDATE_REQUEST_OT_FAILURE: {
      return {
        ...state,
        isUpdateRequest: false,
        errorUpdateRequest: action.payload.data.response.errorData
      };
    }
    // Aproval request ot request
    case APPROVAL_REQUEST_OT_REQUEST: {
      return {
        ...state,
        isApprovalRequest: false,
        isUpdateRequest: false,
        isSendEmail: false,
        isCancelRequest: false
      };
    }
    // Aproval request ot success
    case APPROVAL_REQUEST_OT_SUCCESS: {
      return {
        ...state,
        isApprovalRequest: true
      };
    }
    // Aproval request ot failed
    case APPROVAL_REQUEST_OT_FAILURE: {
      return {
        ...state,
        isApprovalRequest: false
      };
    }
    // Cancel request ot request
    case CANCEL_REQUEST_OT_REQUEST: {
      return {
        ...state,
        isApprovalRequest: false,
        isUpdateRequest: false,
        isSendEmail: false,
        isCancelRequest: false
      };
    }
    // Cancel request ot success
    case CANCEL_REQUEST_OT_SUCCESS: {
      return {
        ...state,
        isCancelRequest: true
      };
    }
    // Cancel request ot failed
    case CANCEL_REQUEST_OT_FAILURE: {
      return {
        ...state,
        isCancelRequest: false
      };
    }
    // Send mail request
    case SEND_EMAIL_REQUEST_OT_REQUEST: {
      return {
        ...state,
        isApprovalRequest: false,
        isUpdateRequest: false,
        isSendEmail: false,
        isCancelRequest: false
      };
    }
    // Send mail success
    case SEND_EMAIL_REQUEST_OT_SUCCESS: {
      return {
        ...state,
        isSendEmail: true
      };
    }
    // Send mail failed
    case SEND_EMAIL_REQUEST_OT_FAILURE: {
      return {
        ...state,
        isSendEmail: false
      };
    }
    // Rest create
    case REQUEST_CREATE_RESET: {
      state.errorCreateRequest = null;
      return {
        ...state
      };
    }
    // Get list request request
    case GET_REQUEST_LIST_REQUEST: {
      return {
        ...state,
        requestList: [],
        isApprovalRequest: false,
        isUpdateRequest: false,
        isSendEmail: false,
        isCancelRequest: false
      };
    }
    // Get list request OT success
    case GET_REQUEST_LIST_SUCCESS: {
      return {
        ...state,
        isUpdateRequest: null,
        requestList: action.payload.data.response,
        detailRequestOT: []
      };
    }
    // Get list request failed
    case GET_REQUEST_LIST_FAILURE: {
      return {
        ...state,
        requestList: []
      };
    }
    // Get list project name request
    case GET_PROJECT_NAME_LIST_REQUEST: {
      return {
        ...state,
        requestList: [],
        projectNameList: []
      };
    }
    // Get list project name success
    case GET_PROJECT_NAME_LIST_SUCCESS: {
      state.projectNameList = action.payload.data.response;

      return {
        ...state
      };
    }
    // Get list project name failed
    case GET_PROJECT_NAME_LIST_FAILURE: {
      return {
        ...state,
        requestList: [],
        projectNameList: []
      };
    }

    // Get list project name request
    case GET_PROJECT_BY_EMPLOYEE_REQUEST: {
      return {
        ...state
      };
    }
    // Get list project name success
    case GET_PROJECT_BY_EMPLOYEE_SUCCESS: {
      return {
        ...state,
        projectByEmployee: action.payload.data.response
      };
    }
    // Get list project name failed
    case GET_PROJECT_BY_EMPLOYEE_FAILURE: {
      return {
        ...state,
        projectByEmployee: []
      };
    }

    // Get list project name request
    case GET_EMPLOYEE_BY_PROJECT_REQUEST: {
      return {
        ...state
      };
    }
    // Get list project name success
    case GET_EMPLOYEE_BY_PROJECT_SUCCESS: {
      return {
        ...state,
        employeeByProject: action.payload.data.response
      };
    }
    // Get list project name failed
    case GET_EMPLOYEE_BY_PROJECT_FAILURE: {
      return {
        ...state,
        employeeByProject: []
      };
    }
    default:
      return state;
  }
};
