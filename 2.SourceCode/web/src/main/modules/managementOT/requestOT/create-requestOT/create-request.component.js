import React, { Component } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "./create-request.css";
import validateUtil from "../../../../../shared/utils/validate.util";
import moment from "moment";
import ModalComponent from "../../../../../shared/common/component/modal.component";
import ErrorComponent from "../../../../../shared/common/component/error.component";
import { dateTimePattern } from "../../../../../shared/constants/date-time-pattern.constant";
import { message } from "../../../../../shared/constants/message.constant";
import { Redirect } from "react-router-dom";
import AlertComponent from "../../../../../shared/common/component/alert.component";
import { type } from "../../../../../shared/constants/type-style.constant";
import { buttonType } from "../../../../../shared/constants/button-type.constant";
import commonUtil from "../../../../../shared/utils/common.util";
import Select from "react-select";
/**
 * Create Request Component
 */
class CreateRequestComponent extends Component {
  /**
   * Constructor
   *
   * @param props
   */
  constructor(props) {
    super(props);
    this.state = {
      modal: {
        show: false,
        title: "",
        content: ""
      },
      projectCode: "",
      projectManagement: "",
      reason: "",
      rows: [
        {
          id: (+new Date() + Math.floor(Math.random() * 999999)).toString(36),
          checkbox: false,
          dateOT: null,
          planTimeOT: "",
          employeeCode: "",
          positionCode: "",
          note: "",
          error: []
        }
      ],
      error: {
        projectCode: "",
        reason: "",
        isDuplicate: false
      }
    };
  }
  /**
   * Check validate input on change
   *
   * @param value
   * @param name
   */
  checkValidateInput = (value, name) => {
    let error = this.state.error;
    // Check empty
    if (validateUtil.isEmpty(value)) {
      error[name] = message.MSG_ERROR_001;
    } else {
      error[name] = "";
    }
  };
  /**
   * Check validate input table on change
   *
   * @param value
   * @param name
   * @param row
   */
  checkValidateInputTable = (value, name, row) => {
    const { rows } = this.state;
    // Check empty
    if (validateUtil.isEmpty(value)) {
      row.error[name] = message.MSG_ERROR_001;
    } else {
      row.error[name] = "";
      if (name == "planTimeOT") {
        // Check is not time OT
        if (validateUtil.isNotTimeOT(value)) {
          row.error[name] = message.MSG_ERROR_003;
        } else {
          row.error[name] = "";
        }
      }
    }
  };

  onHandleChangeProject = object => {
    this.setState(
      {
        ...this.state,
        projectCode: object.projectCode,
        projectManagement: object.projectManagementName,
        reason: "",
        rows: [
          {
            id: (+new Date() + Math.floor(Math.random() * 999999)).toString(36),
            checkbox: false,
            dateOT: null,
            planTimeOT: "",
            employeeCode: "",
            positionCode: "",
            note: "",
            error: []
          }
        ]
      },
      () => {
        this.checkValidateInput(this.state.projectCode, "projectCode");
      }
    );
    this.props.onHandleProject(object.projectCode);
  };

  onHandleChangeEmployeeName = (id, event) => {
    let rows = this.state.rows;
    var target = event.target;
    var name = target.name;
    var value = target.type === "checkbox" ? target.checked : target.value;
    if (id == null) {
      this.setState({
        [name]: value
      });
      // Check validate input projectName, pm/tl
      this.checkValidateInput(value, name);
    } else {
      rows.forEach(row => {
        if (row.id == id) {
          row[name] = value;
          // Check validate table
          this.checkValidateInputTable(value, name, row);
        }
      });
    }
    rows = this.checkExistDateOTEmployeeName(rows, "employeeCode");
    this.setState({
      ...this.state,
      rows: rows
    });
  };

  onHandleChangePosition = (id, event) => {
    let rows = this.state.rows;
    var target = event.target;
    var name = target.name;
    var value = target.type === "checkbox" ? target.checked : target.value;
    if (id == null) {
      this.setState({
        [name]: value
      });
      // Check validate input projectName, pm/tl
      this.checkValidateInput(value, name);
    } else {
      rows.forEach(row => {
        if (row.id == id) {
          row[name] = value;
          // Check validate table
          this.checkValidateInputTable(value, name, row);
        }
      });
    }
    rows = this.checkExistDateOTEmployeeName(rows, "positionCode");
    this.setState({
      ...this.state,
      rows: rows
    });
  };

  /**
   * On handle change
   * @param id
   * @param event
   */
  onHandleChange = (id, event) => {
    let rows = this.state.rows;
    var target = event.target;
    var name = target.name;
    var value = target.type === "checkbox" ? target.checked : target.value;
    if (id == null) {
      this.state.reason = value;
      this.setState({ ...this.state });
      // Check validate input projectName, pm/tl
      this.checkValidateInput(value, name);
    } else {
      rows.forEach(row => {
        if (row.id == id) {
          row[name] = value;
          // Check validate table
          this.checkValidateInputTable(value, name, row);
        }
      });
    }
    this.setState({
      ...this.state,
      rows: rows
    });
  };
  /**
   * Check validate date time OT
   *
   * @param date
   * @param name
   * @param row
   */
  checkValidateDateTimeOT = (date, name, row) => {
    // Check date null
    if (validateUtil.isNull(date)) {
      row.error[name] = message.MSG_ERROR_001;
    } else {
      row.error[name] = "";
      // Check date and date now
      if (validateUtil.isNotDateOT(date, dateTimePattern.YYYYMMDD)) {
        row.error[name] = message.MSG_ERROR_002;
      }
    }
  };
  /**
   * Check exist date ot and employee code
   * @param rows
   * @param field
   */
  checkExistDateOTEmployeeName = (rows, field) => {
    let count = 0;
    let listTemp = [];
    let isDuplicate = false;
    rows.map(item => {
      count = 0;
      let keydateOT = moment(item.dateOT).format(dateTimePattern.YYYYMMDD);
      let keyEmployeeCode = item.employeeCode;
      let keyPositionCode = item.positionCode;
      /**
       * If listTemp >0 then loop to check value
       */
      if (listTemp.length > 0) {
        let isExist = false;
        /**
         * Loop to check value
         */
        for (let i = 0; i < listTemp.length; i++) {
          let dateOT = moment(listTemp[i].dateOT).format(
            dateTimePattern.YYYYMMDD
          );
          let employeeCode = listTemp[i].employeeCode;
          let positionCode = listTemp[i].positionCode;
          /**
           * If exist dateOT and employeeCode then error
           */
          if (
            dateOT === keydateOT &&
            employeeCode === keyEmployeeCode &&
            positionCode === keyPositionCode
          ) {
            count++;
            isExist = true;
          }
        }
        /**
         * If data is not exist then add
         */
        if (!isExist) {
          listTemp.push(item);
        }
      } else {
        listTemp.push(item);
      }
      /**
       * If count > 1 then error
       */
      if (count >= 1) {
        /**
         * Check if dateOT or employeeCode is not null and empty then set error
         */
        if (
          item.dateOT != "" &&
          item.dateOT != null &&
          item.employeeCode != "" &&
          item.employeeCode != null &&
          item.positionCode != "" &&
          item.positionCode != null
        ) {
          /**
           * Check if field is dateOT then set error for field dateOT else employee code
           */

          isDuplicate = true;
        }
      }
    });
    this.setState({
      ...this.state,
      error: {
        ...this.state.error,
        isDuplicate
      }
    });
    return rows;
  };
  /**
   * On change date time picker
   * @param id
   * @param date
   */
  onChangeDateTimeOT = (id, date) => {
    let rows = this.state.rows;
    rows.forEach(row => {
      if (row.id == id) {
        // Set value date in row
        row.dateOT = date;
        // Check validate date time OT
        this.checkValidateDateTimeOT(date, "dateOT", row);
      }
    });
    let field = "dateOT";
    /**
     * Check exist date ot and employee name
     */
    rows = this.checkExistDateOTEmployeeName(rows, field);
    this.setState({
      ...this.state,
      rows: rows
    });
  };
  /**
   * On click add row
   */
  onClickAddRow = () => {
    var rows = this.state.rows;
    // Init object row
    var id = (+new Date() + Math.floor(Math.random() * 999999)).toString(36);
    var objRequest = {
      id,
      checkbox: false,
      dateOT: null,
      planTimeOT: "",
      employeeCode: "",
      note: "",
      error: []
    };
    rows.push(objRequest);
    this.setState({
      rows: rows
    });
  };
  /**
   * On click delete row
   */
  onClickDeleteRow = () => {
    let rows = this.state.rows;
    // If checked all then delete all
    if (this.state.isChecked) {
      rows = [];
    } else {
      // Delete checkbox checked
      rows = rows.filter(row => row.checkbox != true);
    }
    this.setState({
      rows: rows
    });
  };
  /**
   * Check checkbox check all
   *
   * @returns boolean
   */
  checkCheckBoxAll() {
    let rows = this.state.rows;
    var count = 0;
    // Check checkbox checked all
    if (rows.length === 0) {
      return false;
    }
    rows.forEach(row => {
      if (row.checkbox === true) count++;
    });
    if (count === rows.length) {
      return true;
    } else {
      return false;
    }
  }
  /**
   * On change checkbox
   */
  onChangeCheckBox = event => {
    let rows = this.state.rows;
    rows.forEach(row => {
      // If id equal checkbox then set checked
      if (row.id === event.target.value) row.checkbox = event.target.checked;
    });
    this.setState({
      rows: rows
    });
  };
  /**
   * On change select all checkbox
   */
  onChangeSelectAllCheckBox = event => {
    let rows = this.state.rows;
    rows.forEach(row => (row.checkbox = event.target.checked));
    this.setState({
      rows: rows
    });
  };
  /**
   * Check validate
   *
   * @returns boolean
   */
  checkValidate = () => {
    let isValid = true;
    let error = {
      projectCode: "",
      reason: ""
    };
    // Check projectName empty
    if (!this.state.projectCode) {
      error.projectCode = message.MSG_ERROR_001;
      isValid = false;
    }
    // Check reason empty
    if (validateUtil.isEmpty(this.state.reason)) {
      error.reason = message.MSG_ERROR_001;
      isValid = false;
    }

    let rows = this.state.rows;
    rows.forEach(row => {
      // Check dateOT null
      if (validateUtil.isNull(row.dateOT)) {
        row.error.dateOT = message.MSG_ERROR_001;
        isValid = false;
      }
      // Check not date OT
      if (validateUtil.isNotDateOT(row.dateOT, dateTimePattern.YYYYMMDD)) {
        row.error.dateOT = message.MSG_ERROR_002;
        isValid = false;
      }
      // Check employee code empty
      if (validateUtil.isEmpty(row.employeeCode)) {
        row.error.employeeCode = message.MSG_ERROR_001;
        isValid = false;
      }
      // Check position code empty
      if (validateUtil.isEmpty(row.positionCode + "")) {
        row.error.positionCode = message.MSG_ERROR_001;
        isValid = false;
      }
      // Check plantimeOT empty
      if (validateUtil.isEmpty(row.planTimeOT)) {
        row.error.planTimeOT = message.MSG_ERROR_001;
        isValid = false;
      }
      // Check plantimeOT not time OT
      if (validateUtil.isNotTimeOT(row.planTimeOT)) {
        row.error.planTimeOT = message.MSG_ERROR_003;
        isValid = false;
      }
    });

    this.setState({
      error: {
        ...this.state.error,
        projectCode: error.projectCode,
        reason: error.reason
      }
    });
    return isValid;
  };
  /**
   * On hide
   */
  onHide = () => {
    this.setState({
      modal: {
        show: false
      }
    });
  };
  /**
   * On click send
   */
  onClickSend = () => {
    /**
     * Check validate
     */

    if (this.checkValidate() && !this.state.error.isDuplicate) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_INFO_007
        }
      });
    }
  };
  /**
   * On click ok
   */
  onClickOk = async () => {
    this.setState({
      modal: {
        show: false
      }
    });
    if (this.checkValidate() && !this.state.error.isDuplicate) {
      let data = {};
      // Init data
      data.projectCode = this.state.projectCode;
      data.reason = this.state.reason;
      data.requestDetail = [];
      let rows = this.state.rows;
      rows.forEach(row => {
        let requestDetail = {};
        requestDetail.employeeCode = row.employeeCode;
        requestDetail.positionCode = row.positionCode;
        requestDetail.dateOT = moment(row.dateOT).format(
          dateTimePattern.YYYYMMDD
        );
        requestDetail.planTimeOT = row.planTimeOT;
        requestDetail.note = row.note;
        data.requestDetail.push(requestDetail);
      });
      // Create request OT
      this.props.onHandleCreateRequest(data);
    }
  };
  /**
   * Render
   */
  render() {
    // Check checkbox checked all
    let isChecked = this.checkCheckBoxAll();
    // Get user name
    const { userStore, messageStore, requestStore } = this.props;
    let projectList = [];
    let employeeList = [];
    let userName = "";
    if (userStore) {
      userName = userStore.data.resultData.employeeName;
    }
    if (requestStore.projectByEmployee.resultData) {
      requestStore.projectByEmployee.resultData.forEach(project => {
        project = commonUtil.unescapeHtmlObject(project);
      });

      requestStore.projectByEmployee.resultData.forEach(project => {
        let projectObject = {};
        projectObject.projectCode = project.projectCode;
        projectObject.projectName = project.projectName;
        projectObject.projectManagementCode = project.projectManagementCode;
        projectObject.projectManagementName = project.projectManagementName;
        projectList.push(projectObject);
      });
    }

    if (requestStore.employeeByProject.resultData) {
      requestStore.employeeByProject.resultData.forEach(employee => {
        employee = commonUtil.unescapeHtmlObject(employee);
      });
      employeeList = requestStore.employeeByProject.resultData;
    }
    // Get requestStore
    const { errorCreateRequest } = this.props.requestStore;
    const { error } = this.state;
    let isError = false;
    if (messageStore.type === type.TYPE_SUCCESS) {
      return <Redirect to="/request/list" />;
    } else if (messageStore.type === type.TYPE_DANGER) {
      isError = true;
    }

    return (
      <div className="container-fluid mt-4 fix-padding-bottom">
        <h2 className="text-center my-4">
          <b className="font-weigth">ĐĂNG KÝ OT</b>
        </h2>
        <div className="row">
          <div className="col-12">
            {isError && <AlertComponent messageObj={messageStore} />}
          </div>
        </div>
        <div className="card mb-4">
          <div className="card-body">
            <div className="row">
              <div className="col-12">
                <div className="row">
                  <label className="col-sm-1 col-form-label lable-front">
                    Dự Án<span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-6">
                    <Select
                      className="react-selectcomponent form-custom"
                      options={projectList}
                      getOptionLabel={option => `${option.projectName}`}
                      getOptionValue={option => `${option.projectName}`}
                      onChange={this.onHandleChangeProject}
                      isSearchable={true}
                    />
                    {error.projectCode.length > 0 && (
                      <ErrorComponent error={error.projectCode} />
                    )}
                    {errorCreateRequest && (
                      <ErrorComponent
                        errorResponse={errorCreateRequest}
                        field="projectCode"
                      />
                    )}
                  </div>
                </div>
              </div>
              <div className="col-12">
                <div className="row mt-3">
                  <label className="col-sm-1 lable-front">PM</label>
                  <div className="col-sm-6">
                    <label>{this.state.projectManagement}</label>
                  </div>
                </div>
              </div>
              <div className="col-12 mt-3">
                <div className="row ">
                  <label className="col-sm-1 col-form-label lable-front">
                    Lý Do
                    <span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-6">
                    <textarea
                      className="form-control col-12 text-area-custom"
                      rows="5"
                      onChange={this.onHandleChange.bind(this, null)}
                      name="reason"
                      value={this.state.reason}
                    ></textarea>
                    {error.reason.length > 0 && (
                      <ErrorComponent error={error.reason} />
                    )}
                    {errorCreateRequest && (
                      <ErrorComponent
                        errorResponse={errorCreateRequest}
                        field="reason"
                      />
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="mt-4">
          <label className="col-form-label ">
            <b className="font-weigth">Chi Tiết OT</b>
            <span className="text-danger"> *</span>
          </label>
          <div className="">
            <button
              className="btn btn-primary btn-add-remove ml-0"
              onClick={this.onClickAddRow}
            >
              <i className="fa fa-plus btn-icon-add-remove"></i>
            </button>
            <button
              className="btn btn-danger btn-add-remove"
              onClick={this.onClickDeleteRow}
            >
              <i className="fa fa-trash btn-icon-add-remove"></i>
            </button>
          </div>
          <div className="">
            {this.state.error.isDuplicate && (
              <ErrorComponent error={message.MSG_ERROR_015} />
            )}
          </div>
          <table className="table table-bordered table-hover">
            <thead className="thead-dark">
              <tr className="text-center">
                <th width="5%" scope="col" className="text-center align-middle">
                  <div className="custom-control custom-checkbox">
                    <input
                      type="checkbox"
                      className="custom-control-input"
                      id="customCheckAll"
                      onChange={this.onChangeSelectAllCheckBox}
                      checked={isChecked}
                    />
                    <label
                      className="custom-control-label"
                      htmlFor="customCheckAll"
                    ></label>
                  </div>
                </th>
                <th
                  width="15%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Ngày</b>
                </th>
                <th
                  width="22%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Thành Viên</b>
                </th>
                <th
                  width="15%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Vị Trí</b>
                </th>
                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Thời Gian OT</b>
                </th>
                <th
                  width="20%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Ghi chú</b>
                </th>
              </tr>
            </thead>
            <tbody>
              {this.state.rows.map((row, index) => (
                <tr key={index}>
                  <td className="align-middle">
                    <div className="custom-control custom-checkbox">
                      <input
                        type="checkbox"
                        className="custom-control-input"
                        id={`checkBox${row.id}`}
                        onChange={this.onChangeCheckBox}
                        value={row.id}
                        checked={row.checkbox}
                      />
                      <label
                        className="custom-control-label"
                        htmlFor={`checkBox${row.id}`}
                      ></label>
                    </div>
                  </td>
                  <td className="text-center align-middle td-font">
                    <DatePicker
                      className="form-control"
                      name="dateOT"
                      selected={row.dateOT}
                      onChange={this.onChangeDateTimeOT.bind(this, row.id)}
                      peekNextMonth
                      showMonthDropdown
                      showYearDropdown
                      isClearable
                      dateFormat="yyyy-MM-dd"
                      dropdownMode="select"
                    />
                    {/* <div className="text-center align-middle"> */}
                    {row.error.dateOT && (
                      <ErrorComponent error={row.error.dateOT} />
                    )}
                    {/* </div> */}
                  </td>
                  <td className="text-center align-middle td-font">
                    <select
                      className="browser-default custom-select"
                      onChange={this.onHandleChangeEmployeeName.bind(
                        this,
                        row.id
                      )}
                      name="employeeCode"
                      value={row.employeeCode}
                    >
                      <option value=""></option>
                      {!employeeList.status &&
                        employeeList.map((employee, index) => (
                          <option key={index} value={employee.employeeCode}>
                            {employee.employeeName}
                          </option>
                        ))}
                    </select>
                    <div className="text-center align-middle">
                      {row.error.employeeCode && (
                        <ErrorComponent error={row.error.employeeCode} />
                      )}
                    </div>
                  </td>
                  <td className="text-center align-middle td-font">
                    <select
                      className="browser-default custom-select"
                      onChange={this.onHandleChangePosition.bind(this, row.id)}
                      name="positionCode"
                      value={row.positionCode}
                    >
                      <option value=""></option>
                      {!employeeList.status &&
                      employeeList.filter(
                        employee => employee.employeeCode === row.employeeCode
                      ).length > 0
                        ? employeeList
                            .filter(
                              employee =>
                                employee.employeeCode === row.employeeCode
                            )[0]
                            .positionByEmployeeDtoList.map(
                              (position, index) => {
                                return (
                                  <option
                                    key={index}
                                    value={position.positionCode}
                                  >
                                    {position.positionName}
                                  </option>
                                );
                              }
                            )
                        : ""}
                    </select>
                    <div className="text-center align-middle">
                      {row.error.positionCode && (
                        <ErrorComponent error={row.error.positionCode} />
                      )}
                    </div>
                  </td>
                  <td className="text-center align-middle td-font">
                    <input
                      type="number"
                      className="form-control"
                      onChange={this.onHandleChange.bind(this, row.id)}
                      name="planTimeOT"
                      value={row.planTimeOT}
                    />
                    <div className="text-center align-middle ">
                      {row.error.planTimeOT && (
                        <ErrorComponent error={row.error.planTimeOT} />
                      )}
                    </div>
                  </td>
                  <td>
                    <textarea
                      className="form-control text-area-table"
                      rows="1"
                      onChange={this.onHandleChange.bind(this, row.id)}
                      name="note"
                      value={row.note}
                    ></textarea>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {errorCreateRequest && (
            <ErrorComponent
              errorResponse={errorCreateRequest}
              field="requestDetail"
            />
          )}
          <div className="text-center">
            <button
              className="btn btn-primary btn-send"
              onClick={this.onClickSend}
            >
              <b>{buttonType.BUTTON_CREATE}</b>
            </button>
          </div>
        </div>
        <ModalComponent
          onClick={this.onClickOk}
          showModal={this.state.modal}
          onHide={this.onHide}
        />
      </div>
    );
  }
}
export default CreateRequestComponent;
