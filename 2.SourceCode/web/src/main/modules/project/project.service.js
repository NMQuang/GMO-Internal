import apiConfig from "../../../config/api.config";
import apiUtil from "../../../shared/utils/api.util";
// define service of project
const projectService = {};

projectService.createProject = async projectInfo => {
  const method = apiConfig.method.METHOD_POST;
  const body = projectInfo;
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.CREATE_PROJECT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

projectService.getProjectRank = async () => {
  const method = apiConfig.method.METHOD_POST;
  const body = "";
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_PROJECT_RANK,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

projectService.search = async querySearch => {
  const method = apiConfig.method.METHOD_POST;
  const body = querySearch;
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_PROJECT_LIST,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

projectService.getDetail = async projectCode => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify({ projectCode });
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_PROJECT_DETAIL,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

projectService.editProject = async projectInfo => {
  const method = apiConfig.method.METHOD_POST;
  const body = projectInfo;
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.EDIT_PROJECT,
      method,
      body,
      headers
    );
    return result;
  } catch (error) {
    return error.response;
  }
};

export default projectService;
