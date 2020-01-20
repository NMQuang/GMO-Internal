import {
  HTTP_NOT_FOUND_ERROR,
  HTTP_AUTHORIZATION_ERROR,
  HTTP_TIMEOUT_ERROR,
  HTTP_INTERNAL_SERVER_ERROR,
  HTTP_OTHER_ERROR,
  HTTP_FORBIDDEN_ERROR
} from "../action/error.action";
import history from "../../../config/history.config";

const initialState = {
  errorMessage: ""
};

const executeNotFoundError = (state, action) => {
  history.push("/not-found");
  return { ...state };
};

const executeForbiddenError = (state, action) => {
  history.push("/permission");
  return { ...state };
};

const executeRequestTimeoutError = (state, action) => {
  history.push("/request-timeout");
  return { ...state };
};

const executeInternaleServerError = (state, action) => {
  history.push("/error-page");
  return { ...state };
};

const executeOtherError = (state, action) => {
  history.push("/login");
  return { ...state };
};

const executeAuthorizationError = (state, action) => {
  history.push("/login");
  return { ...state };
};

export const errorReducer = (state = initialState, action) => {
  switch (action.type) {
    case HTTP_NOT_FOUND_ERROR:
      return executeNotFoundError(state, action);
    case HTTP_AUTHORIZATION_ERROR:
      return executeAuthorizationError(state, action);
    case HTTP_TIMEOUT_ERROR:
      return executeRequestTimeoutError(state, action);
    case HTTP_INTERNAL_SERVER_ERROR:
      return executeInternaleServerError(state, action);
    case HTTP_FORBIDDEN_ERROR:
      return executeForbiddenError(state, action);
    case HTTP_OTHER_ERROR:
      return executeOtherError(state, action);
    default:
      return state;
  }
};
