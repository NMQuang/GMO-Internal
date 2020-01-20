import apiUtil from "../../../../shared/utils/api.util";
import apiConfig from "../../../../config/api.config";

const requestOTService = {};
/**
 * Create request OT
 *
 * @param data
 * @return result
 */
requestOTService.createRequestOT = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.CREATE_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
/**
 * Get employee
 *
 * @return result
 */
requestOTService.getEmployeeList = async () => {
  const method = apiConfig.method.METHOD_POST;
  const body = {};
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_EMPLOYEES_LIST,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
/**
 * Get detail requestOT
 *
 * @param requestId
 */
requestOTService.getDetailRequestOT = async (requestId, dateSearch) => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify({
    requestId,
    dateSearch
  });
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_DETAIL_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
/**
 * Update request OT
 *
 * @param data
 */
requestOTService.updateRequestOT = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.UPDATE_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
/**
 * Cancel request OT
 */
requestOTService.cancelRequestOT = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.CANCEL_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
/**
 * Approval request OT
 */
requestOTService.approvalRequestOT = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.APPROVAL_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
/**
 * Send email feedback request OT
 */
requestOTService.sendEmailRequestOT = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.SEND_EMAIL_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

requestOTService.getProjectByEmployee = async () => {
  const method = apiConfig.method.METHOD_POST;
  const body = {};
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_LIST_PROJECT_IN_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

requestOTService.getEmployeeByProject = async projectCode => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify({ projectCode });
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_LIST_EMPLOYEE_IN_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

/**
 * Get list project name
 *
 * @return result
 */
requestOTService.getProjectNameList = async () => {
  const method = apiConfig.method.METHOD_POST;
  const body = {};
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_LIST_PROJECT_NAME,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
/**
 * Get list request
 *
 * @return result
 */
requestOTService.getRequestOTList = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_LIST_REQUEST_OT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

export default requestOTService;
