import apiConfig from "../../../config/api.config";
import apiUtil from "../../../shared/utils/api.util";
// define service of user
const userService = {};

/**
 * login
 * @param {*} email
 * @param {*} password
 */
userService.login = async (email, password) => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify({ email, password });
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.USER_LOGIN,
      method,
      body
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

/**
 * reset password
 * @param {}email
 */
userService.resetPassword = async email => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify({ email });
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.USER_RESET_PASSWORD,
      method,
      body
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

/**
 * search employee
 * @param {}querySearch
 */
userService.search = async querySearch => {
  const method = apiConfig.method.METHOD_POST;
  const body = querySearch;
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.EMPLOYEES_SEARCH,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

userService.export = async querySearch => {
  const method = apiConfig.method.METHOD_POST;
  const body = querySearch;
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.EMPLOYEES_EXPORT,
      method,
      body,
      headers
    );

    return result;
  } catch (error) {
    return error.response;
  }
};

userService.changePassword = async userInfo => {
  const method = apiConfig.method.METHOD_POST;
  const body = userInfo;
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.USER_CHANGE_PASSWORD,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

userService.getDetailEmployee = async employeeCode => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify({ employeeCode });
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.EMPLOYEES_GET_DETAIL,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

userService.updateEmployee = async employeeInfo => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify({ employeeInfo });
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.EMPLOYEES_UPDATE,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

export default userService;
