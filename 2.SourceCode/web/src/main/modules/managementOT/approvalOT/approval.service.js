import apiUtil from "../../../../shared/utils/api.util";
import apiConfig from "../../../../config/api.config";
import { fileName } from "../../../../shared/constants/file-name.constant";
const ApprovalOTService = {};
/**
 * Get list request
 *
 * @return result
 */
ApprovalOTService.getApprovalOTList = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApi(
      apiConfig.endpoint.GET_LIST_APPROVAL,
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
 * Export list request
 *
 * @return result
 */
ApprovalOTService.exportData = async data => {
  const method = apiConfig.method.METHOD_POST;
  const body = JSON.stringify(data);
  let token = localStorage.accessToken;
  let nameFile = fileName.LIST_APPROVAL;
  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
  try {
    const result = await apiUtil.callApiDowloadFile(
      apiConfig.endpoint.EXPORT_DATA,
      method,
      body,
      headers,
      nameFile
    );
    return result;
  } catch (error) {
    return error.response;
  }
};
export default ApprovalOTService;
