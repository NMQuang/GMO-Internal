export const HTTP_NOT_FOUND_ERROR = "HTTP_NOT_FOUND_ERROR";
export const HTTP_AUTHORIZATION_ERROR = "HTTP_AUTHORIZATION_ERROR";
export const HTTP_TIMEOUT_ERROR = "HTTP_TIMEOUT_ERROR";
export const HTTP_OTHER_ERROR = "HTTP_OTHER_ERROR";
export const HTTP_INTERNAL_SERVER_ERROR = "HTTP_INTERNAL_SERVER_ERROR";
export const HTTP_FORBIDDEN_ERROR = "HTTP_FORBIDDEN_ERROR";

// define action of handle error
const handleErrorAction = {};

handleErrorAction.handleError = error => {
  if (typeof error === "undefined") {
    return {
      type: HTTP_TIMEOUT_ERROR
    };
  } else {
    switch (error.status) {
      case 404:
        return {
          type: HTTP_NOT_FOUND_ERROR
        };
      case 500:
        return {
          type: HTTP_INTERNAL_SERVER_ERROR
        };
      case 403:
        return {
          type: HTTP_FORBIDDEN_ERROR
        };
      case 401:
        return {
          type: HTTP_AUTHORIZATION_ERROR
        };
      case 408:
        return {
          type: HTTP_TIMEOUT_ERROR
        };
      default:
        return {
          type: HTTP_TIMEOUT_ERROR
        };
    }
  }
};

export default handleErrorAction;
