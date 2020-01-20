import axios from "axios";
import apiConfig from "../../config/api.config.js";
// define util of api
const apiUtil = {};

/**
 * call api
 * @param {} endpoint
 * @param {} method
 * @param {} body
 * @return {Object} call to axios
 */
apiUtil.callApi = (
  endpoint,
  method = apiConfig.method.METHOD_GET,
  body,
  header = apiConfig.header.HEADER_JSON
) => {
  return axios({
    method: method,
    url: `${apiConfig.url}/${endpoint}`,
    data: body,
    headers: header,
    timeout: apiConfig.timeout
  });
};
/**
 * call api
 * @param {} endpoint
 * @param {} method
 * @param {} body
 * @return {Object} call to fetch
 */
apiUtil.callApiDowloadFile = (
  endpoint,
  method = apiConfig.method.METHOD_GET,
  body,
  header = apiConfig.header.HEADER_JSON
) => {
  return fetch(`${apiConfig.url}/${endpoint}`, {
    credentials: "same-origin",
    method: method,
    headers: header,
    body: body,
    timeout: apiConfig.timeout
  });
};

export default apiUtil;
