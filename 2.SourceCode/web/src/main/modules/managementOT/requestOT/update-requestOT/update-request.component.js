import React, { Component } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "./update-request.css";
import validateUtil from "../../../../../shared/utils/validate.util";
import moment from "moment";
import ModalComponent from "../../../../../shared/common/component/modal.component";
import ErrorComponent from "../../../../../shared/common/component/error.component";
import { dateTimePattern } from "../../../../../shared/constants/date-time-pattern.constant";
import { message } from "../../../../../shared/constants/message.constant";
import ModalFeedBackComponent from "../../../../../shared/common/component/modal-feedback.component";
import { Redirect } from "react-router-dom";
import { role, position } from "../../../../../shared/constants/role.constant";
import AlertComponent from "../../../../../shared/common/component/alert.component";
import { Link } from "react-router-dom";
import { type } from "../../../../../shared/constants/type-style.constant";
import constant from "../../../../../shared/constants/page.constant";
import commonUtil from "../../../../../shared/utils/common.util";
import { buttonType } from "../../../../../shared/constants/button-type.constant";
import actionUtil from "../../../../../shared/utils/action.util";
import Select from "react-select";
import history from "../../../../../config/history.config";
/**
 * Create Request Component
 */
class UpdateRequestComponent extends Component {
  /**
   * Constructor
   *
   * @param props
   */
  constructor(props) {
    super(props);
    this.state = {
      isUpdate: false,
      modal: {
        show: false,
        title: "",
        content: "",
        statusClick: ""
      },
      statusRequest: constant.statusRequest,
      showSendModal: false,
      valueSendModal: null,
      projectCode: "",
      projectName: "",
      reason: "",
      projectManagement: "",
      rows: [],
      error: {
        projectCode: "",
        reason: "",
        isDuplicate: false
      },
      isValid: false
    };
  }

  componentDidMount = () => {
    this.props.onLoadInit();
  };

  /**
   * Component will receive props
   */
  componentWillReceiveProps() {
    // Init data
    const { detailRequestOT } = this.props.requestStore;
    let rows = [];
    if (detailRequestOT.requestDetail) {
      rows = detailRequestOT.requestDetail;
      for (let i = 0; i < rows.length; i++) {
        rows[i].error = [];
        rows[i].id = rows[i].requestDetailId;
        rows[i].dateOT = new Date(rows[i].dateOT);
        rows[i].dateOTOld = new Date(rows[i].dateOT);
        rows[i].planTimeOT = rows[i].planTimeOT + "";
        rows[i].actualTimeOT = rows[i].actualTimeOT;
        rows[i].approvalTimeOT = rows[i].approvalTimeOT + "";
        rows[i].employeeCode = rows[i].employeeCode;
        rows[i].note = rows[i].note ? rows[i].note : "";
        rows[i].checkbox = false;
        rows[i].positionCode = rows[i].positionCode + "";
      }

      // Set value load page
      this.setState({
        projectCode: detailRequestOT.projectCode,
        projectName: detailRequestOT.projectName,
        projectManagement: detailRequestOT.employeeName,
        reason: detailRequestOT.reason,
        rows: rows,
        updateAt: detailRequestOT.updateAt,
        status: detailRequestOT.status
      });
    }
  }

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
      error: [],
      actualTimeOT: 0 + "",
      approvalTimeOT: 0 + "",
      positionCode: ""
    };
    rows.push(objRequest);
    this.setState({
      rows: rows,
      isUpdate: true
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
      rows: rows,
      isUpdate: true
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
      if (row.id == event.target.value) {
        row.checkbox = event.target.checked;
      }
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
    // Check empty
    if (validateUtil.isEmpty(value)) {
      row.error[name] = message.MSG_ERROR_001;
    } else {
      row.error[name] = "";
      if (name == "planTimeOT" || name == "approvalTimeOT") {
        // Check is not time OT
        if (validateUtil.isNotTimeOT(value)) {
          row.error[name] = message.MSG_ERROR_003;
        } else {
          row.error[name] = "";
        }
      }
    }
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
      rows: rows
    });
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
      this.setState({
        [name]: value
      });
      // Check validate input projectCode, pm/tl
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
    if (name == "approvalTimeOT") {
      rows = this.checkValueApproval(rows);
    }
    this.setState({
      rows: rows
    });
  };
  /**
   * Compare value approval, plan time ot
   */
  checkValueApproval = rows => {
    /**
     * Loop to check data
     */
    rows.forEach(row => {
      /**
       * Check if plantimeOT < approvalTimeOT then set error
       */
      if (+row.approvalTimeOT > +row.planTimeOT) {
        row.error.approvalTimeOT = message.MSG_ERROR_016;
      }
    });
    return rows;
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
      if (
        validateUtil.isNotDateOTUpdate(
          date,
          row.dateOTOld,
          dateTimePattern.YYYYMMDD
        )
      ) {
        row.error[name] = message.MSG_ERROR_002;
      }
    }
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
    /**
     * Check exist date ot and employee name
     */
    rows = this.checkExistDateOTEmployeeName(rows, "dateOT");
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
    // Check projectCode empty
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
      // Check date null
      if (validateUtil.isNull(row.dateOT)) {
        row.error.dateOT = message.MSG_ERROR_001;
        isValid = false;
      }
      // Check date not date ot
      if (
        validateUtil.isNotDateOTUpdate(
          row.dateOT,
          row.dateOTOld,
          dateTimePattern.YYYYMMDD
        )
      ) {
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

      // Check planTimeOT empty
      if (validateUtil.isEmpty(row.planTimeOT)) {
        row.error.planTimeOT = message.MSG_ERROR_001;
        isValid = false;
      }
      // Check planTimeOT not time
      if (validateUtil.isNotTimeOT(row.planTimeOT)) {
        row.error.planTimeOT = message.MSG_ERROR_003;
        isValid = false;
      }
      // Check approvalTime OT empty
      if (validateUtil.isEmpty(row.approvalTimeOT)) {
        row.error.approvalTimeOT = message.MSG_ERROR_001;
        isValid = false;
      }
      // Check approvalTime OT not time
      if (validateUtil.isNotTimeOT(row.approvalTimeOT)) {
        row.error.approvalTimeOT = message.MSG_ERROR_003;
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
   * On click back request list
   */
  onClickBack = () => {
    history.push("/request/list");
    // history.goBack();
  };
  /**
   * On click update
   */
  onClickUpdate = () => {
    const { statusRequest } = this.state;

    if (this.checkValidate() && !this.state.error.isDuplicate) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_MODAL_004,
          statusClick: statusRequest.UPDATE
        }
      });
    }
  };
  /**
   * On click approval
   */
  onClickApproval = () => {
    const { statusRequest, rows } = this.state;
    let isValid = true;
    rows.forEach(row => {
      /**
       * Check if value approval time ot > plan time ot then invalid
       */
      if (row.error.approvalTimeOT != "" && row.error.approvalTimeOT != null) {
        isValid = false;
      }
    });
    /**
     * If valid then send approval else not send
     */
    if (isValid) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_MODAL_003,
          statusClick: statusRequest.APPROVAL
        }
      });
    }
  };

  /**
   * On click un approval
   */
  onClickUnApproval = () => {
    const { statusRequest, rows } = this.state;
    let isValid = true;
    rows.forEach(row => {
      /**
       * Check if value approval time ot > plan time ot then invalid
       */
      if (row.error.approvalTimeOT != "" && row.error.approvalTimeOT != null) {
        isValid = false;
      }
    });
    /**
     * If valid then send approval else not send
     */
    if (isValid) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_MODAL_007,
          statusClick: statusRequest.UNAPPROVAL
        }
      });
    }
  };

  /**
   * On click verify
   */
  onClickVerify = () => {
    const { statusRequest, rows } = this.state;
    let isValid = true;
    rows.forEach(row => {
      /**
       * Check if value approval time ot > plan time ot then invalid
       */
      if (row.error.approvalTimeOT != "" && row.error.approvalTimeOT != null) {
        isValid = false;
      }
    });
    /**
     * If valid then send approval else not send
     */
    if (isValid) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_MODAL_005,
          statusClick: statusRequest.VERIFY
        }
      });
    }
  };

  /**
   * On click unverify
   */
  onClickUnVerify = () => {
    const { statusRequest, rows } = this.state;
    let isValid = true;
    rows.forEach(row => {
      /**
       * Check if value approval time ot > plan time ot then invalid
       */
      if (row.error.approvalTimeOT != "" && row.error.approvalTimeOT != null) {
        isValid = false;
      }
    });
    /**
     * If valid then send approval else not send
     */
    if (isValid) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_MODAL_006,
          statusClick: statusRequest.UNVERIFY
        }
      });
    }
  };

  /**
   * On click cancle
   */
  onClickCancel = () => {
    const { statusRequest } = this.state;
    this.setState({
      modal: {
        show: true,
        title: message.MSG_TITLE_001,
        content: message.MSG_MODAL_002,
        statusClick: statusRequest.CANCEL
      }
    });
  };
  /**
   * On click ok
   */
  onClickOk = () => {
    this.setState({
      modal: {
        show: false
      }
    });
    const { userStore } = this.props;
    let roleId = userStore.data.resultData.roleId;
    let employeeCurrent = userStore.data.resultData.employeeCode;
    let rows = "";
    const { statusRequest } = this.state;
    // Status click cancel
    if (this.state.modal.statusClick === statusRequest.CANCEL) {
      let data = {};
      data.requestId = this.props.requestId;
      data.updateAt = this.state.updateAt;
      this.props.cancleRequestOT(data);
      return;
    }
    // Check validate
    if (this.checkValidate() && !this.state.error.isDuplicate) {
      let data = {};
      // Init data
      data.requestId = this.props.requestId;
      data.projectCode = commonUtil.escapeHTML(this.state.projectCode);
      data.reason = commonUtil.escapeHTML(this.state.reason);
      data.updateAt = this.state.updateAt;
      data.requestDetail = [];
      rows = this.state.rows;
      rows.forEach(row => {
        let requestDetail = {};
        requestDetail.requestDetailId =
          typeof row.requestDetailId === "undefined" ? "" : row.requestDetailId;
        requestDetail.dateOT = moment(row.dateOT).format(
          dateTimePattern.YYYYMMDD
        );
        requestDetail.planTimeOT = row.planTimeOT;
        requestDetail.actualTimeOT = row.actualTimeOT;
        requestDetail.approvalTimeOT = row.approvalTimeOT;
        requestDetail.employeeCode = row.employeeCode;
        requestDetail.positionCode = row.positionCode;
        requestDetail.note = commonUtil.escapeHTML(row.note);
        data.requestDetail.push(requestDetail);
      });
      // Status click update
      if (this.state.modal.statusClick === statusRequest.UPDATE) {
        this.props.updateRequestOT(data);
      }
    }

    // Check roleId equal ROLE_ADMIN
    if (roleId === role.ROLE_ADMIN) {
      const { detailRequestOT } = this.props.requestStore;
      let data = {};
      data.requestId = this.props.requestId;
      data.updateAt = this.state.updateAt;
      data.projectCode = commonUtil.escapeHTML(this.state.projectCode);
      data.employeeCode = detailRequestOT.employeeCode;

      data.requestDetail = [];
      rows = this.state.rows;
      rows.forEach(row => {
        let requestDetail = {};
        requestDetail.dateOT = row.dateOT;
        requestDetail.requestDetailId = row.requestDetailId;
        requestDetail.approvalTimeOT = row.approvalTimeOT;
        requestDetail.note = row.note;
        requestDetail.employeeCode = row.employeeCode;
        requestDetail.positionCode = row.positionCode;
        data.requestDetail.push(requestDetail);
      });
      // Status click approval
      if (this.state.modal.statusClick === statusRequest.APPROVAL) {
        data.status = constant.FLAG_VERFIRY_APPROVAL;
      } // Status click un approval
      else if (this.state.modal.statusClick === statusRequest.UNAPPROVAL) {
        data.status = constant.FLAG_UN_VERFIRY_APPROVAL;
      }
      this.props.approvalRequestOT(data);
    }

    // Check roleId equal ROLE_QA
    if (roleId === role.ROLE_QA) {
      let data = {};
      data.requestId = this.props.requestId;
      data.updateAt = this.state.updateAt;
      data.requestDetail = [];
      rows = this.state.rows;
      rows.forEach(row => {
        let requestDetail = {};
        requestDetail.requestDetailId = row.requestDetailId;
        requestDetail.approvalTimeOT = row.approvalTimeOT;
        requestDetail.note = row.note;
        requestDetail.positionCode = row.positionCode;
        data.requestDetail.push(requestDetail);
      });
      // Status click verify
      if (this.state.modal.statusClick === statusRequest.VERIFY) {
        data.status = constant.FLAG_VERFIRY_APPROVAL;
      } // Status click un verify
      else if (this.state.modal.statusClick === statusRequest.UNVERIFY) {
        data.status = constant.FLAG_UN_VERFIRY_APPROVAL;
      }
      this.props.verifyRequestOT(data);
    }
  };
  /**
   * On click feedback
   */
  onClickFeedback = () => {
    this.setState({
      showSendModal: true
    });
  };
  /**
   * On hide send modal
   */
  onHideSendModal = () => {
    this.setState({
      showSendModal: false
    });
  };
  /**
   * On click send modal
   */
  onClickSendModal = data => {
    this.props.sendEmailRequestOT(data);
    this.setState({
      showSendModal: false
    });
    // Send email
  };

  /**
   * Render
   */
  render() {
    // Check checkbox checked all
    let isChecked = this.checkCheckBoxAll();
    const { errorUpdateRequest, userStore, requestStore } = this.props;

    let projectManagement = this.state.projectManagement;
    const { error } = this.state;
    let isError = false;
    let projectList = [];
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
    if (
      requestStore.isUpdateRequest ||
      requestStore.isApprovalRequest ||
      requestStore.isCancelRequest ||
      requestStore.isSendEmail
    ) {
      return (
        <Redirect
          to={{
            pathname: "/request/list"
          }}
        />
      );
    }
    let roleId = userStore.data.resultData.roleId;
    let employeeCurrent = userStore.data.resultData.employeeCode;
    //let roleId = role.ROLE_BM;
    let status = this.state.status;
    const { email } = this.props.requestStore.detailRequestOT;
    // Setting role edit OT
    let isProjectRole = false;
    if (userStore.data.resultData) {
      const objectProjectRole = userStore.data.resultData.projectInfo.filter(
        projectRole =>
          +projectRole.projectCode === +this.state.projectCode &&
          (+projectRole.positionCode === +position.POSITION_PM ||
            +projectRole.positionCode === +position.POSITION_TL)
      );
      isProjectRole = objectProjectRole.length > 0 ? true : false;
    }
    // Get employeeList

    let employeeList = [];
    if (requestStore.detailRequestOT.employeeList) {
      requestStore.detailRequestOT.employeeList.forEach(employee => {
        employee = commonUtil.unescapeHtmlObject(employee);
      });
      employeeList = requestStore.detailRequestOT.employeeList;
    }

    return (
      <div className="container-fluid mt-4 fix-padding-bottom">
        <h2 className="text-center my-4">
          <b className="font-weigth">CHI TIẾT ĐĂNG KÝ OT</b>
        </h2>
        <div className="card mb-4">
          <div className="card-body">
            {roleId === +role.ROLE_USER && isProjectRole && status === 1 ? (
              <div className="row">
                <div className="col-12">
                  <div className="row">
                    <label className="col-sm-1 col-form-label lable-front">
                      Dự Án
                    </label>
                    <div className="col-sm-6">
                      <h6 className="mt-2">
                        {commonUtil.escapeHTML(this.state.projectName)}
                      </h6>
                    </div>
                  </div>
                </div>
                <div className="col-12 mt-3">
                  <div className="row">
                    <label className="col-sm-1 col-form-label lable-front">
                      PM
                    </label>
                    <div className="col-sm-6">
                      <h6 className="mt-2">{projectManagement}</h6>
                    </div>
                  </div>
                </div>
                <div className="col-12 mt-3">
                  <div className="row">
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
                        value={commonUtil.escapeHTML(this.state.reason)}
                      ></textarea>
                      {error.reason.length > 0 && (
                        <ErrorComponent error={error.reason} />
                      )}
                      {errorUpdateRequest && (
                        <ErrorComponent
                          errorResponse={errorUpdateRequest}
                          field="reason"
                        />
                      )}
                    </div>
                  </div>
                </div>
              </div>
            ) : (
              <div className="row">
                <div className="col-12">
                  <div className="row">
                    <label className="col-sm-1 col-form-label lable-front">
                      Dự Án
                    </label>
                    <div className="col-sm-6">
                      <h6 className="mt-2">
                        {commonUtil.escapeHTML(this.state.projectName)}
                      </h6>
                    </div>
                  </div>
                </div>
                <div className="col-12 mt-3">
                  <div className="row">
                    <label className="col-sm-1 col-form-label lable-front">
                      PM
                    </label>
                    <div className="col-sm-6">
                      <h6 className="mt-2">{projectManagement}</h6>
                    </div>
                  </div>
                </div>
                <div className="col-12 mt-3">
                  <div className="row">
                    <label className="col-sm-1 col-form-label lable-front">
                      Lý Do
                    </label>
                    <div className="col-sm-6">
                      <h6 className="mt-2">
                        {commonUtil.escapeHTML(this.state.reason)}
                      </h6>
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
        <div className="mt-4">
          <label>
            <b className="font-weigth">Chi Tiết OT</b>
            {+roleId === +role.ROLE_USER && isProjectRole && status === 1 && (
              <span className="text-danger">(*)</span>
            )}
          </label>
          {+roleId === +role.ROLE_USER && isProjectRole && status === 1 && (
            <React.Fragment>
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
            </React.Fragment>
          )}

          <div>
            <table className="table table-bordered table-hover">
              <thead className="thead-dark">
                <tr className="text-center">
                  {+roleId === +role.ROLE_USER &&
                    isProjectRole &&
                    status === 1 && (
                      <th
                        width="5%"
                        scope="col"
                        className="text-center align-middle"
                      >
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
                    )}

                  <th
                    width="5%"
                    scope="col"
                    className="text-center align-middle"
                  >
                    <b>No</b>
                  </th>
                  <th
                    width="15%"
                    scope="col"
                    className="text-center align-middle"
                  >
                    <b>Ngày</b>
                  </th>
                  <th
                    width="20%"
                    scope="col"
                    className="text-center align-middle"
                  >
                    <b>Thành Viên</b>
                  </th>
                  <th
                    width="17%"
                    scope="col"
                    className="text-center align-middle"
                  >
                    <b>Vị Trí</b>
                  </th>
                  <th
                    width="12%"
                    scope="col"
                    className="text-center align-middle"
                  >
                    <b>Thời Gian OT</b>
                  </th>
                  <th
                    width="12%"
                    scope="col"
                    className="text-center align-middle"
                  >
                    <b>Approval</b>
                  </th>
                  <th scope="col" className="text-center align-middle">
                    <b>Ghi Chú</b>
                  </th>
                </tr>
              </thead>
              <tbody>
                {this.state.rows.length != 0 &&
                  this.state.rows.map((row, index) => (
                    /**
                     * Show data
                     */
                    <tr key={index}>
                      {+roleId === +role.ROLE_USER &&
                      isProjectRole &&
                      status === 1 ? (
                        <React.Fragment>
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
                          <td className="align-middle text-center">
                            {index + 1}
                          </td>
                          <td className="text-center align-middle">
                            <DatePicker
                              className="form-control"
                              name="dateOT"
                              selected={row.dateOT}
                              onChange={this.onChangeDateTimeOT.bind(
                                this,
                                row.id
                              )}
                              peekNextMonth
                              showMonthDropdown
                              showYearDropdown
                              isClearable
                              dateFormat="yyyy-MM-dd"
                              dropdownMode="select"
                            />
                            {row.error.dateOT && (
                              <ErrorComponent error={row.error.dateOT} />
                            )}
                          </td>
                          <td className="align-middle text-center">
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
                                  <option
                                    key={index}
                                    value={employee.employeeCode}
                                  >
                                    {employee.employeeName}
                                  </option>
                                ))}
                            </select>
                            {row.error.employeeCode && (
                              <ErrorComponent error={row.error.employeeCode} />
                            )}
                          </td>
                          <td className="text-center align-middle td-font">
                            <select
                              className="browser-default custom-select"
                              onChange={this.onHandleChangePosition.bind(
                                this,
                                row.id
                              )}
                              name="positionCode"
                              value={row.positionCode}
                            >
                              <option value=""></option>
                              {!employeeList.status &&
                              employeeList.filter(
                                employee =>
                                  employee.employeeCode === row.employeeCode
                              ).length > 0
                                ? employeeList
                                    .filter(
                                      employee =>
                                        employee.employeeCode ===
                                        row.employeeCode
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
                                : []}
                            </select>
                            <div className="text-center align-middle">
                              {row.error.positionCode && (
                                <ErrorComponent
                                  error={row.error.positionCode}
                                />
                              )}
                            </div>
                          </td>
                          <td className="align-middle text-center">
                            <input
                              type="number"
                              className="form-control"
                              onChange={this.onHandleChange.bind(this, row.id)}
                              name="planTimeOT"
                              value={row.planTimeOT}
                            />
                            {row.error.planTimeOT && (
                              <ErrorComponent error={row.error.planTimeOT} />
                            )}
                          </td>
                          <td className="align-middle text-center">
                            {row.approvalTimeOT}
                          </td>
                          <td className="align-middle text-center">
                            <textarea
                              className="form-control text-area-table"
                              rows="1"
                              onChange={this.onHandleChange.bind(this, row.id)}
                              name="note"
                              value={commonUtil.escapeHTML(row.note)}
                            ></textarea>
                          </td>
                        </React.Fragment>
                      ) : (
                        <React.Fragment>
                          <td className="align-middle text-center">
                            {index + 1}
                          </td>
                          <td className="align-middle text-center">
                            {moment(row.dateOT).format(
                              dateTimePattern.YYYYMMDD
                            )}
                          </td>
                          <td className="align-middle">
                            {employeeList.filter(
                              employee =>
                                employee.employeeCode === row.employeeCode
                            ).length > 0
                              ? employeeList.filter(
                                  employee =>
                                    employee.employeeCode === row.employeeCode
                                )[0].employeeName
                              : ""}
                          </td>
                          <td className="align-middle text-center">
                            {employeeList.filter(
                              employee =>
                                employee.employeeCode === row.employeeCode
                            ).length > 0
                              ? employeeList
                                  .filter(
                                    employee =>
                                      employee.employeeCode === row.employeeCode
                                  )[0]
                                  .positionByEmployeeDtoList.filter(
                                    position =>
                                      +position.positionCode ===
                                      +row.positionCode
                                  ).length > 0
                                ? employeeList
                                    .filter(
                                      employee =>
                                        employee.employeeCode ===
                                        row.employeeCode
                                    )[0]
                                    .positionByEmployeeDtoList.filter(
                                      position =>
                                        +position.positionCode ===
                                        +row.positionCode
                                    )[0].positionName
                                : ""
                              : ""}
                          </td>
                          <td className="align-middle text-center">
                            {row.planTimeOT}
                          </td>
                          {+roleId === +role.ROLE_QA && status === 1 ? (
                            <React.Fragment>
                              <td className="align-middle text-center">
                                <input
                                  type="number"
                                  className="form-control"
                                  onChange={this.onHandleChange.bind(
                                    this,
                                    row.id
                                  )}
                                  name="approvalTimeOT"
                                  value={row.approvalTimeOT}
                                />
                                {row.error.approvalTimeOT && (
                                  <ErrorComponent
                                    error={row.error.approvalTimeOT}
                                  />
                                )}
                              </td>
                              <td className="align-middle text-center">
                                <textarea
                                  className="form-control text-area-table"
                                  rows="1"
                                  onChange={this.onHandleChange.bind(
                                    this,
                                    row.id
                                  )}
                                  name="note"
                                  value={commonUtil.escapeHTML(row.note)}
                                ></textarea>
                              </td>
                            </React.Fragment>
                          ) : (
                            <React.Fragment>
                              <td className="align-middle text-center">
                                {row.approvalTimeOT}
                              </td>
                              <td className="align-middle">
                                {commonUtil.escapeHTML(row.note)}
                              </td>
                            </React.Fragment>
                          )}
                        </React.Fragment>
                      )}
                    </tr>
                  ))}
              </tbody>
            </table>
          </div>
          {errorUpdateRequest && (
            <ErrorComponent
              errorResponse={errorUpdateRequest}
              field="requestDetail"
            />
          )}
          <div className="text-center">
            <span>
              <button
                className="btn btn-info btn-send"
                onClick={this.onClickBack}
              >
                {buttonType.BUTTON_BACK}
              </button>
            </span>
            {+roleId === +role.ROLE_USER && isProjectRole && status === 1 && (
              <span>
                <button
                  className="btn btn-primary btn-send"
                  onClick={this.onClickUpdate}
                >
                  {buttonType.BUTTON_UPDATE}
                </button>
              </span>
            )}
            {+roleId === +role.ROLE_QA && status === 2 && !this.state.isUpdate && (
              <span>
                <button
                  className="btn btn-success btn-send"
                  onClick={this.onClickUnVerify}
                >
                  {buttonType.BUTTON_UNVERIFY}
                </button>
                <button
                  className="btn btn-warning btn-send"
                  onClick={this.onClickFeedback}
                >
                  {buttonType.BUTTON_FEEDBACK}
                </button>
              </span>
            )}
            {+roleId === +role.ROLE_QA && status === 1 && !this.state.isUpdate && (
              <span>
                <button
                  className="btn btn-success btn-send"
                  onClick={this.onClickVerify}
                >
                  {buttonType.BUTTON_VERIFY}
                </button>
                <button
                  className="btn btn-warning btn-send"
                  onClick={this.onClickFeedback}
                >
                  {buttonType.BUTTON_FEEDBACK}
                </button>
              </span>
            )}
            {+roleId === +role.ROLE_ADMIN &&
              !this.state.isUpdate &&
              status === 2 && (
                <span>
                  <button
                    className="btn btn-primary btn-send"
                    onClick={this.onClickApproval}
                  >
                    {buttonType.BUTTON_APPROVAL}
                  </button>

                  <button
                    className="btn btn-warning btn-send"
                    onClick={this.onClickFeedback}
                  >
                    {buttonType.BUTTON_FEEDBACK}
                  </button>
                </span>
              )}
            {+roleId === +role.ROLE_ADMIN &&
              !this.state.isUpdate &&
              status === 3 && (
                <span>
                  <button
                    className="btn btn-danger btn-send"
                    onClick={this.onClickUnApproval}
                  >
                    {buttonType.BUTTON_UNAPPROVAL}
                  </button>
                  <button
                    className="btn btn-warning btn-send"
                    onClick={this.onClickFeedback}
                  >
                    {buttonType.BUTTON_FEEDBACK}
                  </button>
                </span>
              )}
          </div>
        </div>
        <ModalComponent
          onClick={this.onClickOk}
          showModal={this.state.modal}
          onHide={this.onHide}
        />
        <ModalFeedBackComponent
          email={email}
          onClick={this.onClickSendModal}
          showSendModal={this.state.showSendModal}
          onHideSendModal={this.onHideSendModal}
          valueSendModal={this.state.valueSendModal}
        />
      </div>
    );
  }
}

export default UpdateRequestComponent;
