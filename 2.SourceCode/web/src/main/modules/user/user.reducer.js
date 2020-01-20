import {
  USERS_LOGIN_REQUEST,
  USERS_LOGIN_SUCCESS,
  USERS_LOGIN_FAILURE,
  USERS_LOGOUT_REQUEST
} from "./login/login.action";
import {
  USERS_RESET_PASSWORD_REQUEST,
  USERS_RESET_PASSWORD_SUCCESS,
  USERS_RESET_PASSWORD_FAILURE
} from "./reset-password/reset-password.action";

import {
  EMPLOYEE_SEARCH_REQUEST,
  EMPLOYEE_SEARCH_SUCCESS,
  EMPLOYEE_SEARCH_FAILURE,
  EMPLOYEE_EXPORT_REQUEST,
  EMPLOYEE_EXPORT_SUCCESS,
  EMPLOYEE_EXPORT_FAILURE
} from "./list-user/list-user.action";

import {
  EMPLOYEE_CHANGE_PASSWORD_REQUEST,
  EMPLOYEE_CHANGE_PASSWORD_FAILURE,
  EMPLOYEE_CHANGE_PASSWORD_SUCCESS,
  USERS_CLEAR_TOKEN_REQUEST
} from "./change-password/change-password.action";
import { message } from "../../../shared/constants/message.constant";
import {
  EMPLOYEE_GET_DETAIL_REQUEST,
  EMPLOYEE_GET_DETAIL_SUCCESS,
  EMPLOYEE_GET_DETAIL_FAILURE
} from "./detail-user/detail-user.action";
import {
  EMPLOYEE_UPDATE_REQUEST,
  EMPLOYEE_UPDATE_SUCCESS,
  EMPLOYEE_UPDATE_FAILURE
} from "./edit-user/edit-user.action";
import history from "../../../config/history.config";
import { STORE_REGISTER_REQUEST } from "../../../shared/utils/action.util";
import { storage } from "../../../shared/constants/storage.constant";
// initial state
let initialState = {
  isResetPassword: false,
  isAuthenticated: localStorage.getItem("user") ? true : false,
  data: localStorage.getItem("user")
    ? JSON.parse(localStorage.getItem("user"))
    : {},
  error: null,
  dataResponse: null,
  dataExport: null,
  isChangePassword: false,
  detailEmployee: null,
  isDetailError: false,
  isUpdate: false
};

/**
 * reducer of user!
 * @param {Object} state
 * @param {Action Creator} action
 */
export const userReducer = (state = initialState, action) => {
  switch (action.type) {
    // login request
    case USERS_LOGIN_REQUEST: {
      return {
        ...state
      };
    }
    // login success
    case USERS_LOGIN_SUCCESS: {
      localStorage.setItem(
        "user",
        JSON.stringify(action.payload.data.response)
      );
      localStorage.setItem(
        "accessToken",
        action.payload.data.response.resultData.jwt.idToken
      );
      state.data = action.payload.data.response;
      state.isAuthenticated = true;
      return {
        ...state,
        error: null
      };
    }

    // login failure
    case USERS_LOGIN_FAILURE: {
      state.error = action.payload.data.response;
      state.isAuthenticated = false;
      return {
        data: {},
        ...state
      };
    }

    // logout
    case USERS_LOGOUT_REQUEST: {
      localStorage.removeItem("user");
      localStorage.removeItem("accessToken");
      sessionStorage.removeItem(storage.CURRENT_URI);
      sessionStorage.removeItem(storage.PREV_URI);
      sessionStorage.removeItem(storage.SEARCH_PROJECT);
      sessionStorage.removeItem(storage.SEARCH_REQUEST);
      history.push("/login");
      return {
        isAuthenticated: false,
        data: {},
        error: null
      };
    }

    // logout
    case USERS_CLEAR_TOKEN_REQUEST: {
      localStorage.removeItem("user");
      localStorage.removeItem("accessToken");
      sessionStorage.removeItem(storage.CURRENT_URI);
      sessionStorage.removeItem(storage.PREV_URI);
      sessionStorage.removeItem(storage.SEARCH_PROJECT);
      sessionStorage.removeItem(storage.SEARCH_REQUEST);
      return {
        isAuthenticated: false,
        data: {},
        error: null
      };
    }

    // reset password request
    case USERS_RESET_PASSWORD_REQUEST: {
      return {
        ...state,
        error: null
      };
    }

    // reset password success
    case USERS_RESET_PASSWORD_SUCCESS: {
      state.isResetPassword = true;
      return {
        ...state
      };
    }

    // reset password failure
    case USERS_RESET_PASSWORD_FAILURE: {
      state.error = action.payload.data.response;
      return {
        ...state
      };
    }

    // search employee request
    case EMPLOYEE_SEARCH_REQUEST: {
      return {
        ...state
      };
    }

    // search employee success
    case EMPLOYEE_SEARCH_SUCCESS: {
      state.dataResponse = action.payload.data.response;
      return {
        ...state
      };
    }

    // search employee failure
    case EMPLOYEE_SEARCH_FAILURE: {
      return {
        ...state
      };
    }

    // search employee request
    case EMPLOYEE_EXPORT_REQUEST: {
      return {
        ...state
      };
    }

    // search employee success
    case EMPLOYEE_EXPORT_SUCCESS: {
      state.dataExport = action.payload.data.response;
      return {
        ...state
      };
    }

    // search employee failure
    case EMPLOYEE_EXPORT_FAILURE: {
      return {
        ...state
      };
    }

    // search employee request
    case EMPLOYEE_CHANGE_PASSWORD_REQUEST: {
      return {
        ...state
      };
    }

    // search employee success
    case EMPLOYEE_CHANGE_PASSWORD_SUCCESS: {
      state.isChangePassword = true;

      return {
        ...state
      };
    }

    // search employee failure
    case EMPLOYEE_CHANGE_PASSWORD_FAILURE: {
      state.error = action.payload.data.response;
      return {
        ...state
      };
    }

    // get detail employee request
    case EMPLOYEE_GET_DETAIL_REQUEST: {
      return {
        ...state
      };
    }

    // get detail employee success
    case EMPLOYEE_GET_DETAIL_SUCCESS: {
      state.detailEmployee = action.payload.data.response;
      return {
        ...state
      };
    }

    // get detail employee failure
    case EMPLOYEE_GET_DETAIL_FAILURE: {
      state.isDetailError = true;
      return {
        ...state
      };
    }

    // update employee request
    case EMPLOYEE_UPDATE_REQUEST: {
      return {
        ...state
      };
    }

    // update employee success
    case EMPLOYEE_UPDATE_SUCCESS: {
      return {
        ...state,
        isUpdate: true
      };
    }

    // update employee failure
    case EMPLOYEE_UPDATE_FAILURE: {
      return {
        ...state
      };
    }

    case STORE_REGISTER_REQUEST: {
      sessionStorage.setItem(storage.CURRENT_URI);
      return {
        ...state
      };
    }

    default:
      return state;
  }
};
