import jwtDecode from "jwt-decode";
import { USERS_LOGOUT_REQUEST } from "../../main/modules/user/login/login.action";
import history from "../../config/history.config";
import actionUtil from "../utils/action.util";
import commonUtil from "../utils/common.util";

export const checkTokenExpiration = store => next => action => {
  const token = localStorage.accessToken;
  const actionError = {
    type: USERS_LOGOUT_REQUEST
  };
  if (typeof token !== "undefined" && token !== "") {
    let tokenExpiration = jwtDecode(token).exp;
    if (tokenExpiration && tokenExpiration < Date.now() / 1000) {
      // localStorage.clear();
      return next(actionError);
    } else {
      return next(action);
    }
  } else {
    // localStorage.clear();
    const uri = commonUtil.getPathNameUri();
    if (uri === "/login" || uri === "/reset-password") {
      return next(action);
    } else {
      return next(actionError);
    }
  }
};
