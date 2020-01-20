// define url of server
const apiConfig = {};

// url of api
apiConfig.url = "http://10.1.10.252:8089/gzr-internal";

// end point of url
apiConfig.endpoint = {
  // endpoint of user
  USER_LOGIN: "api/authenticate",
  // reset password
  USER_RESET_PASSWORD: "api/reset-password",
  // change password
  USER_CHANGE_PASSWORD: "api/change-password",
  // update employee
  EMPLOYEES_UPDATE: "api/employees/update",
  // get detail employee
  EMPLOYEES_GET_DETAIL: "api/employees/detail",
  // search employee
  EMPLOYEES_SEARCH: "api/employees/search",
  // export employee
  EMPLOYEES_EXPORT: "api/employees/export",
  // import employee
  EMPLOYEES_IMPORT: "api/employees/import",
  // export employee
  EMPLOYEES_EXPORT: "api/employees/export",
  // create request ot
  CREATE_REQUEST_OT: "api/request-ots/create",
  // get employee list
  GET_EMPLOYEES_LIST: "api/employees/list",
  // update request ot
  GET_DETAIL_REQUEST_OT: "api/request-ots/detail",
  // update request ot
  UPDATE_REQUEST_OT: "api/request-ots/update",
  // cancel request ot
  CANCEL_REQUEST_OT: "api/request-ots/cancel",
  // approval request ot
  APPROVAL_REQUEST_OT: "api/request-ots/get-approval",
  // feedback
  SEND_EMAIL_REQUEST_OT: "api/email/send",
  // get list request
  GET_LIST_REQUEST_OT: "api/request-ots/list",
  // get list project by employee
  GET_LIST_PROJECT_IN_REQUEST_OT: "api/request-ots/project-list",
  // get list employee by project
  GET_LIST_EMPLOYEE_IN_REQUEST_OT: "api/request-ots/employee-list",
  // get list project name
  GET_LIST_PROJECT_NAME: "api/request-ots/get-projects",
  // get list approval
  GET_LIST_APPROVAL: "api/request-ots/get-list-approval",
  // export data
  EXPORT_DATA: "api/request-ots/export-list-approval",
  // create project
  CREATE_PROJECT: "api/project/create",
  //Get project rank
  GET_PROJECT_RANK: "api/project/list-rank",
  // Get list project
  GET_PROJECT_LIST: "api/project/search",
  // Get detail project
  GET_PROJECT_DETAIL: "api/project/detail",
  // Edit project
  EDIT_PROJECT: "api/project/update",
  // get list position
  GET_POSITION_BY_EMPLOYEE: "api/request-ots/position-list"
};

// header of url
apiConfig.header = {
  HEADER_JSON: {
    "Content-Type": "application/json"
  }
};

// method of url
apiConfig.method = {
  METHOD_POST: "POST",
  METHOD_GET: "GET"
};

// time out
apiConfig.timeout = 60000;

export default apiConfig;
