import React, { Component } from "react";
import "./create-project.css";
import AlertComponent from "../../../../shared/common/component/alert.component";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { message } from "../../../../shared/constants/message.constant";
import { buttonType } from "../../../../shared/constants/button-type.constant";
import { Redirect } from "react-router-dom";
import history from "../../../../config/history.config";
import { role, position } from "../../../../shared/constants/role.constant";
import DatePicker from "react-datepicker";
import { dateTimePattern } from "../../../../shared/constants/date-time-pattern.constant";
import commonUtil from "../../../../shared/utils/common.util";
import actionUtil from "../../../../shared/utils/action.util";
import ModalComponent from "../../../../shared/common/component/modal.component";
import validateUtil from "../../../../shared/utils/validate.util";
import moment from "moment";
import { type } from "../../../../shared/constants/type-style.constant";
import Select from "react-select";
import constant from "../../../../shared/constants/page.constant";

class CreateProjectComponent extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
    this.state = {
      modal: {
        show: false,
        title: "",
        content: ""
      },
      projectInfo: {
        projectNameJP: "",
        projectNameVN: "",
        billableEffort: "",
        startDate: new Date(),
        endDate: "",
        customerName: "",
        sale: "",
        projectManagement: "",
        brSE: "",
        teamLead: "",
        projectRank: "",
        objectives: "",
        scope: "",
        emailCC: []
      },
      error: {
        projectNameJP: "",
        projectNameVN: "",
        billableEffort: "",
        startDate: "",
        endDate: "",
        customerName: "",
        sale: "",
        projectManagement: "",
        brSE: "",
        teamLead: "",
        projectRank: "",
        objectives: "",
        scope: "",
        emailCC: ""
      },
      emailRequest: [
        {
          id: (+new Date() + Math.floor(Math.random() * 999999)).toString(36),
          emailCC: "",
          errorCC: ""
        }
      ]
    };
  }

  onHandleChangeProjectRank = object => {
    this.setState(
      {
        projectInfo: {
          ...this.state.projectInfo,
          projectRank: object.id ? object.id : 0
        }
      },
      () => {
        this.onCheckValidateWhenHover();
      }
    );
  };

  onHandleChangeEmployee = (object, name) => {
    this.setState(
      {
        projectInfo: {
          ...this.state.projectInfo,
          [name.name]: object.employeeCode
        }
      },
      () => {
        this.onCheckValidateWhenHover();
      }
    );
  };

  onHandleChange = e => {
    this.setState(
      {
        projectInfo: {
          ...this.state.projectInfo,
          [e.target.name]: e.target.value
        }
      },
      () => {
        this.onCheckValidateWhenHover();
      }
    );
  };

  onCheckValidateWhenHover = () => {
    let error = {};
    let { projectInfo } = this.state;

    // Check project name JP and project name VN
    if (
      validateUtil.isEmpty(projectInfo.projectNameJP) &&
      validateUtil.isEmpty(projectInfo.projectNameVN)
    ) {
      error.projectNameJP = message.MSG_ERROR_027;
      error.projectNameVN = message.MSG_ERROR_027;
    } else {
      // Check project name JP
      if (projectInfo.projectNameJP) {
        if (validateUtil.maxLength(projectInfo.projectNameJP, 150)) {
          error.projectNameJP = commonUtil.parseMessage(message.MSG_ERROR_023, [
            150
          ]);
        }
      }
      // Check project name VN
      if (projectInfo.projectNameVN) {
        if (validateUtil.maxLength(projectInfo.projectNameVN, 150)) {
          error.projectNameVN = commonUtil.parseMessage(message.MSG_ERROR_023, [
            150
          ]);
        }
      }
    }

    // Check billable effort
    if (validateUtil.isEmpty(projectInfo.billableEffort)) {
      error.billableEffort = message.MSG_ERROR_001;
    } else if (validateUtil.maxLength(projectInfo.billableEffort, 250)) {
      error.billableEffort = commonUtil.parseMessage(message.MSG_ERROR_023, [
        250
      ]);
    }

    // Check customer name
    if (validateUtil.isEmpty(projectInfo.customerName)) {
      error.customerName = message.MSG_ERROR_001;
    } else if (validateUtil.maxLength(projectInfo.customerName, 150)) {
      error.customerName = commonUtil.parseMessage(message.MSG_ERROR_023, [
        150
      ]);
    }

    // Check sale
    if (projectInfo.sale) {
      if (validateUtil.maxLength(projectInfo.sale, 50)) {
        error.sale = commonUtil.parseMessage(message.MSG_ERROR_023, [50]);
      }
    }

    // Check project management
    if (validateUtil.isEmpty(projectInfo.projectManagement)) {
      error.projectManagement = message.MSG_ERROR_001;
    } else if (validateUtil.maxLength(projectInfo.projectManagement, 50)) {
      error.projectManagement = commonUtil.parseMessage(message.MSG_ERROR_023, [
        50
      ]);
    }

    // Check brSE
    if (projectInfo.brSE) {
      if (validateUtil.maxLength(projectInfo.brSE, 50)) {
        error.brSE = commonUtil.parseMessage(message.MSG_ERROR_023, [50]);
      }
    }

    // Check teamLead
    if (validateUtil.isEmpty(projectInfo.teamLead)) {
      error.teamLead = message.MSG_ERROR_001;
    } else if (validateUtil.maxLength(projectInfo.teamLead, 50)) {
      error.teamLead = commonUtil.parseMessage(message.MSG_ERROR_023, [50]);
    }

    // Check project rank
    if (projectInfo.projectRank) {
      if (validateUtil.maxLength(projectInfo.projectRank, 1)) {
        error.projectRank = commonUtil.parseMessage(message.MSG_ERROR_023, [1]);
      }
    }

    // Check objectives
    if (validateUtil.isEmpty(projectInfo.objectives)) {
      error.objectives = message.MSG_ERROR_001;
    } else if (validateUtil.maxLength(projectInfo.objectives, 300)) {
      error.objectives = commonUtil.parseMessage(message.MSG_ERROR_023, [300]);
    }

    // Check scope
    if (validateUtil.isEmpty(projectInfo.scope)) {
      error.scope = message.MSG_ERROR_001;
    } else if (validateUtil.maxLength(projectInfo.scope, 300)) {
      error.scope = commonUtil.parseMessage(message.MSG_ERROR_023, [300]);
    }

    // Set state
    this.setState({
      error: {
        ...this.state.error,
        projectNameJP: error.projectNameJP,
        projectNameVN: error.projectNameVN,
        billableEffort: error.billableEffort,
        customerName: error.customerName,
        sale: error.sale,
        projectManagement: error.projectManagement,
        brSE: error.brSE,
        teamLead: error.teamLead,
        projectRank: error.projectRank,
        objectives: error.objectives,
        scope: error.scope
      }
    });
  };

  onHandleChangeDate = (dateName, date) => {
    /**
     * Check date is null or empty
     * If date is empty, null then search all by date
     */
    if (date == null || date === "") {
      this.setState(
        {
          projectInfo: {
            ...this.state.projectInfo,
            [dateName]: ""
          }
        },
        () => {
          let error = {};
          let { projectInfo } = this.state;

          // Check startDate
          if (!projectInfo.startDate) {
            error.startDate = message.MSG_ERROR_001;
          }

          // Check endDate
          if (projectInfo.endDate) {
            if (
              !validateUtil.checkDateValid(
                projectInfo.startDate,
                projectInfo.endDate,
                dateTimePattern.YYYYMMDD
              )
            ) {
              error.endDate = commonUtil.parseMessage(message.MSG_ERROR_024, [
                "ngày kết thúc",
                "ngày bắt đầu"
              ]);
            }
          }
          // Set state
          this.setState({
            error: {
              ...this.state.error,
              startDate: error.startDate,
              endDate: error.endDate
            }
          });
        }
      );
    } else {
      this.setState(
        {
          projectInfo: {
            ...this.state.projectInfo,
            [dateName]: new Date(moment(date).format(dateTimePattern.YYYYMMDD))
          }
        },
        () => {
          let error = {};
          let { projectInfo } = this.state;

          // Check startDate
          if (!projectInfo.startDate) {
            error.startDate = message.MSG_ERROR_001;
          }

          // Check endDate
          if (projectInfo.endDate) {
            if (
              !validateUtil.checkDateValid(
                projectInfo.startDate,
                projectInfo.endDate,
                dateTimePattern.YYYYMMDD
              )
            ) {
              error.endDate = commonUtil.parseMessage(message.MSG_ERROR_024, [
                "ngày kết thúc",
                "ngày bắt đầu"
              ]);
            }
          }
          // Set state
          this.setState({
            error: {
              ...this.state.error,
              startDate: error.startDate,
              endDate: error.endDate
            }
          });
        }
      );
    }
  };

  onCheckValidate = () => {
    let isValid = true;
    let error = {};
    let { projectInfo } = this.state;

    // Check project name JP and project name VN
    if (
      validateUtil.isEmpty(projectInfo.projectNameJP) &&
      validateUtil.isEmpty(projectInfo.projectNameVN)
    ) {
      error.projectNameJP = message.MSG_ERROR_027;
      error.projectNameVN = message.MSG_ERROR_027;
      isValid = false;
    } else {
      // Check project name JP
      if (projectInfo.projectNameJP) {
        if (validateUtil.maxLength(projectInfo.projectNameJP, 150)) {
          error.projectNameJP = commonUtil.parseMessage(message.MSG_ERROR_023, [
            150
          ]);
          isValid = false;
        }
      }
      // Check project name VN
      if (projectInfo.projectNameVN) {
        if (validateUtil.maxLength(projectInfo.projectNameVN, 150)) {
          error.projectNameVN = commonUtil.parseMessage(message.MSG_ERROR_023, [
            150
          ]);
          isValid = false;
        }
      }
    }

    // Check billable effort
    if (validateUtil.isEmpty(projectInfo.billableEffort)) {
      error.billableEffort = message.MSG_ERROR_001;
      isValid = false;
    } else if (validateUtil.maxLength(projectInfo.billableEffort, 250)) {
      error.billableEffort = commonUtil.parseMessage(message.MSG_ERROR_023, [
        250
      ]);
      isValid = false;
    }

    // Check startDate
    if (!projectInfo.startDate) {
      error.startDate = message.MSG_ERROR_001;
      isValid = false;
    }

    // Check endDate
    if (projectInfo.endDate) {
      if (
        !validateUtil.checkDateValid(
          projectInfo.startDate,
          projectInfo.endDate,
          dateTimePattern.YYYYMMDD
        )
      ) {
        error.endDate = commonUtil.parseMessage(message.MSG_ERROR_024, [
          "ngày kết thúc",
          "ngày bắt đầu"
        ]);
        isValid = false;
      }
    }

    // Check customer name
    if (validateUtil.isEmpty(projectInfo.customerName)) {
      error.customerName = message.MSG_ERROR_001;
      isValid = false;
    } else if (validateUtil.maxLength(projectInfo.customerName, 150)) {
      error.customerName = commonUtil.parseMessage(message.MSG_ERROR_023, [
        150
      ]);
      isValid = false;
    }

    // Check sale
    if (projectInfo.sale) {
      if (validateUtil.maxLength(projectInfo.sale, 50)) {
        error.sale = commonUtil.parseMessage(message.MSG_ERROR_023, [50]);
        isValid = false;
      }
    }

    // Check project management
    if (projectInfo.projectManagement) {
      if (validateUtil.isEmpty(projectInfo.projectManagement)) {
        error.projectManagement = message.MSG_ERROR_001;
        isValid = false;
      } else if (validateUtil.maxLength(projectInfo.projectManagement, 50)) {
        error.projectManagement = commonUtil.parseMessage(
          message.MSG_ERROR_023,
          [50]
        );
        isValid = false;
      }
    } else {
      error.projectManagement = message.MSG_ERROR_001;
      isValid = false;
    }

    // Check brSE
    if (projectInfo.brSE) {
      if (validateUtil.maxLength(projectInfo.brSE, 50)) {
        error.brSE = commonUtil.parseMessage(message.MSG_ERROR_023, [50]);
        isValid = false;
      }
    }

    // Check teamLead
    if (projectInfo.teamLead) {
      if (validateUtil.isEmpty(projectInfo.teamLead)) {
        error.teamLead = message.MSG_ERROR_001;
        isValid = false;
      } else if (validateUtil.maxLength(projectInfo.teamLead, 50)) {
        error.teamLead = commonUtil.parseMessage(message.MSG_ERROR_023, [50]);
        isValid = false;
      }
    } else {
      error.teamLead = message.MSG_ERROR_001;
      isValid = false;
    }

    // Check project rank
    if (projectInfo.projectRank) {
      if (validateUtil.maxLength(projectInfo.projectRank, 1)) {
        error.projectRank = commonUtil.parseMessage(message.MSG_ERROR_023, [1]);
        isValid = false;
      }
    }

    // Check objectives
    if (validateUtil.isEmpty(projectInfo.objectives)) {
      error.objectives = message.MSG_ERROR_001;
      isValid = false;
    } else if (validateUtil.maxLength(projectInfo.objectives, 300)) {
      error.objectives = commonUtil.parseMessage(message.MSG_ERROR_023, [300]);
      isValid = false;
    }

    // Check scope
    if (validateUtil.isEmpty(projectInfo.scope)) {
      error.scope = message.MSG_ERROR_001;
      isValid = false;
    } else if (validateUtil.maxLength(projectInfo.scope, 300)) {
      error.scope = commonUtil.parseMessage(message.MSG_ERROR_023, [300]);
      isValid = false;
    }

    // Set state
    this.setState({
      ...this.state,
      error: {
        ...this.state.error,
        projectNameJP: error.projectNameJP,
        projectNameVN: error.projectNameVN,
        billableEffort: error.billableEffort,
        customerName: error.customerName,
        sale: error.sale,
        projectManagement: error.projectManagement,
        brSE: error.brSE,
        teamLead: error.teamLead,
        projectRank: error.projectRank,
        objectives: error.objectives,
        scope: error.scope,
        startDate: error.startDate,
        endDate: error.endDate
      }
    });
    return isValid;
  };

  onHandleChangeEmail = (id, e) => {
    const { emailRequest } = this.state;
    let error = {};
    emailRequest.forEach(email => {
      if (email.id == id) {
        email.emailCC = e.target.value;
        this.setState(
          {
            ...this.state,
            emailRequest: emailRequest
          },
          () => {
            const { emailRequest } = this.state;
            emailRequest.forEach(email => {
              if (email.emailCC) {
                if (email.id == id && !validateUtil.checkEmail(email.emailCC)) {
                  email.errorCC = message.MSG_ERROR_028;
                } else {
                  email.errorCC = "";
                }
              } else {
                email.errorCC = "";
              }
              this.setState({
                error: {
                  ...this.state.error,
                  emailCC: email.errorCC
                }
              });
            });
          }
        );
      }
    });
  };

  onAddRow = e => {
    e.preventDefault();
    const { emailRequest } = this.state;
    const id = (+new Date() + Math.floor(Math.random() * 999999)).toString(36);
    emailRequest.push({
      id,
      emailCC: ""
    });
    this.setState({ emailRequest: emailRequest });
  };

  onRemoveRow = (e, id) => {
    e.preventDefault();
    let { emailRequest } = this.state;
    emailRequest = emailRequest.filter(email => email.id != id);
    this.setState({
      emailRequest: emailRequest
    });
  };

  onClickSend = () => {
    if (this.onCheckValidate()) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_INFO_017
        }
      });
    }
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
    if (this.onCheckValidate()) {
      this.onCustomState();
    }
  };

  onHide = () => {
    this.setState({
      modal: {
        show: false
      }
    });
  };

  onCustomState = () => {
    const { emailRequest, projectInfo } = this.state;

    let emails = [];
    emailRequest.forEach(email => {
      emails.push(email.emailCC);
    });

    this.setState(
      {
        ...this.state,
        projectInfo: {
          ...this.state.projectInfo,
          emailCC: emails[0] === "" ? [] : emails
        }
      },
      () => {
        const { projectInfo } = this.state;
        let projectReq = null;
        projectReq = {
          ...projectInfo,
          startDate: moment(
            moment(projectInfo.startDate, dateTimePattern.YYYYMMDD).toDate()
          ).format(dateTimePattern.YYYYMMDD),
          endDate:
            projectInfo.endDate === "" || projectInfo.endDate === null
              ? ""
              : moment(
                  moment(projectInfo.endDate, dateTimePattern.YYYYMMDD).toDate()
                ).format(dateTimePattern.YYYYMMDD),
          projectRank:
            projectInfo.projectRank === "" || projectInfo.projectRank === null
              ? 0
              : projectInfo.projectRank
        };
        this.props.onCreate(projectReq);

        this.onHide();
      }
    );
  };

  render() {
    const { error, projectInfo, emailRequest } = this.state;
    const { employeeStore, projectStore, messageStore } = this.props;
    let employeeList = [];
    let projectRankList = [];

    if (employeeStore.employeeList.length > 0) {
      employeeStore.employeeList.forEach(employee => {
        employee = commonUtil.unescapeHtmlObject(employee);
      });
      employeeList = employeeStore.employeeList;
    }

    if (projectStore.projectRank) {
      projectStore.projectRank.resultData.forEach(rank => {
        rank = commonUtil.unescapeHtmlObject(rank);
        projectRankList.push(rank);
      });
      projectRankList.push({
        id: 0,
        rank: constant.VALUE_NA,
        description: constant.VALUE_NA
      });
    }

    if (projectStore.isCreate) {
      return <Redirect to="/project/list" />;
    }
    return (
      <div className="container-fluid mt-4 fix-padding-bottom">
        <h2 className="text-center my-4">
          <b className="font-weigth">TẠO MỚI DỰ ÁN</b>
        </h2>
        <div className="row">
          <div className="col-12">
            {projectStore.isCreate !== null && (
              <AlertComponent messageObj={messageStore} />
            )}
          </div>
        </div>
        <div className="card mb-4">
          <div className="card-body">
            <div className="row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Project Name (JP)<span className="text-danger"></span>
                  </label>
                  <div className="col-sm-7 ml-50">
                    <input
                      type="text"
                      className="form-control"
                      id="projectNameJP"
                      placeholder="Project Name (JP)"
                      name="projectNameJP"
                      value={projectInfo.projectNameJP}
                      onChange={this.onHandleChange}
                    />
                    {error.projectNameJP && (
                      <ErrorComponent error={error.projectNameJP} />
                    )}
                  </div>
                </div>
              </div>
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Project Management<span className="text-danger">*</span>
                  </label>
                  <div className="col-sm-7 ml-20">
                    <Select
                      className="react-selectcomponent"
                      options={employeeList}
                      getOptionLabel={option => `${option.employeeName}`}
                      getOptionValue={option => `${option.employeeName}`}
                      onChange={this.onHandleChangeEmployee}
                      isSearchable={true}
                      name="projectManagement"
                      placeholder="Project Management"
                    />

                    {error.projectManagement && (
                      <ErrorComponent error={error.projectManagement} />
                    )}
                  </div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Project Name (VN)<span className="text-danger"></span>
                  </label>
                  <div className="col-sm-7 ml-50">
                    <input
                      type="text"
                      className="form-control"
                      id="projectNameVN"
                      placeholder="Project Name (VN)"
                      name="projectNameVN"
                      value={projectInfo.projectNameVN}
                      onChange={this.onHandleChange}
                    />
                    {error.projectNameVN && (
                      <ErrorComponent error={error.projectNameVN} />
                    )}
                  </div>
                </div>
              </div>
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    BrSE<span className="text-danger"></span>
                  </label>
                  <div className="col-sm-7 ml-20">
                    <Select
                      className="react-selectcomponent"
                      options={employeeList}
                      getOptionLabel={option => `${option.employeeName}`}
                      getOptionValue={option => `${option.employeeName}`}
                      onChange={this.onHandleChangeEmployee}
                      isSearchable={true}
                      name="brSE"
                      placeholder="BrSE"
                    />
                    {error.brSE && <ErrorComponent error={error.brSE} />}
                  </div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Billable Effort<span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-7 ml-50">
                    <input
                      type="text"
                      className="form-control"
                      id="billableEffort"
                      placeholder="Billable Effort"
                      name="billableEffort"
                      value={projectInfo.billableEffort}
                      onChange={this.onHandleChange}
                    />
                    {error.billableEffort && (
                      <ErrorComponent error={error.billableEffort} />
                    )}
                  </div>
                </div>
              </div>
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Team Lead<span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-7 ml-20">
                    <Select
                      className="react-selectcomponent "
                      options={employeeList}
                      getOptionLabel={option => `${option.employeeName}`}
                      getOptionValue={option => `${option.employeeName}`}
                      onChange={this.onHandleChangeEmployee}
                      isSearchable={true}
                      name="teamLead"
                      placeholder="Team Lead"
                    />
                    {error.teamLead && (
                      <ErrorComponent error={error.teamLead} />
                    )}
                  </div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Start Date<span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-7 ml-50">
                    <DatePicker
                      className="form-control reset-date-picker datepicker"
                      name="startDate"
                      selected={projectInfo.startDate}
                      onChange={value =>
                        this.onHandleChangeDate("startDate", value)
                      }
                      placeholderText={dateTimePattern.YYYYMMDD}
                      peekNextMonth
                      showMonthDropdown
                      showYearDropdown
                      isClearable
                      dateFormat={dateTimePattern.yyyyMMdd}
                      dropdownMode="select"
                    />
                  </div>
                  {error.startDate && (
                    <React.Fragment>
                      <label className="col-5 col-form-label"></label>
                      <div className="col-sm-7 ml-50">
                        <ErrorComponent error={error.startDate} />
                      </div>
                    </React.Fragment>
                  )}
                </div>
              </div>
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Project Rank<span className="text-danger"> </span>
                  </label>
                  <div className="col-sm-7 ml-20">
                    <Select
                      className="react-selectcomponent"
                      options={projectRankList}
                      getOptionLabel={option => `${option.rank}`}
                      getOptionValue={option => `${option.rank}`}
                      onChange={this.onHandleChangeProjectRank}
                      isSearchable={true}
                      name="projectRank"
                      placeholder="Project Rank"
                    />
                    {error.projectRank && (
                      <ErrorComponent error={error.projectRank} />
                    )}
                  </div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    End Date<span className="text-danger"> </span>
                  </label>
                  <div className="col-sm-7 ml-50">
                    <DatePicker
                      className="form-control datepicker reset-date-picker"
                      name="endDate"
                      selected={projectInfo.endDate}
                      onChange={value =>
                        this.onHandleChangeDate("endDate", value)
                      }
                      placeholderText={dateTimePattern.YYYYMMDD}
                      peekNextMonth
                      showMonthDropdown
                      showYearDropdown
                      isClearable
                      dateFormat={dateTimePattern.yyyyMMdd}
                      dropdownMode="select"
                    />
                  </div>
                  {error.endDate && (
                    <React.Fragment>
                      <label className="col-4 col-form-label"></label>
                      <div className="col-sm-8">
                        <ErrorComponent error={error.endDate} />
                      </div>
                    </React.Fragment>
                  )}
                </div>
              </div>
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Objectives<span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-7 ml-20">
                    <input
                      type="text"
                      className="form-control"
                      id="objectives"
                      placeholder="Objectives"
                      name="objectives"
                      onChange={this.onHandleChange}
                    />
                    {error.objectives && (
                      <ErrorComponent error={error.objectives} />
                    )}
                  </div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Customer Name<span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-7 ml-50">
                    <input
                      type="text"
                      className="form-control"
                      id="customerName"
                      placeholder="Customer Name"
                      name="customerName"
                      onChange={this.onHandleChange}
                    />
                    {error.customerName && (
                      <ErrorComponent error={error.customerName} />
                    )}
                  </div>
                </div>
              </div>
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Scope<span className="text-danger"> *</span>
                  </label>
                  <div className="col-sm-7 ml-20">
                    <input
                      type="text"
                      className="form-control"
                      id="scope"
                      placeholder="Scope"
                      name="scope"
                      onChange={this.onHandleChange}
                    />
                    {error.scope && <ErrorComponent error={error.scope} />}
                  </div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-5 col-form-label lable-front">
                    Sale<span className="text-danger"></span>
                  </label>
                  <div className="col-sm-7 ml-50">
                    <input
                      type="text"
                      className="form-control"
                      id="sale"
                      placeholder="Sale"
                      name="sale"
                      onChange={this.onHandleChange}
                    />
                    {error.sale && <ErrorComponent error={error.sale} />}
                  </div>
                </div>
              </div>
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                {emailRequest.map((email, index) => (
                  <React.Fragment key={index}>
                    {index == 0 ? (
                      <React.Fragment>
                        <div className="form-group row">
                          <label className="col-5 col-form-label lable-front">
                            Email CC<span className="text-danger"></span>
                          </label>
                          <div className="col-sm-5 ml-20">
                            <input
                              type="text"
                              className="form-control"
                              id={`email + ${index}`}
                              placeholder="Email CC"
                              name="emailCC"
                              value={email.emailCC}
                              onChange={this.onHandleChangeEmail.bind(
                                this,
                                email.id
                              )}
                            />
                          </div>
                          <button
                            className="btn btn-primary btn-icon-add-remove-feedback mt-0"
                            onClick={this.onAddRow}
                          >
                            <i className="fa fa-plus btn-icon-add-remove"></i>
                          </button>
                        </div>
                        {email.errorCC && (
                          <div className="form-group row">
                            <label className="col-4 col-form-label"></label>
                            <div className="col-sm-8 form-custom">
                              <ErrorComponent error={email.errorCC} />
                            </div>
                          </div>
                        )}
                      </React.Fragment>
                    ) : (
                      <React.Fragment>
                        <div className="form-group row">
                          <label className="col-5 col-form-label lable-front">
                            <span className="text-danger"></span>
                          </label>
                          <div className="col-sm-5 ml-20">
                            <input
                              type="text"
                              className="form-control"
                              id={`email + ${index}`}
                              placeholder="Email CC"
                              name="emailCC"
                              value={email.emailCC}
                              onChange={this.onHandleChangeEmail.bind(
                                this,
                                email.id
                              )}
                            />
                          </div>
                          <button
                            className="btn btn-danger btn-icon-add-remove-feedback mt-0"
                            onClick={(e, id) => this.onRemoveRow(e, email.id)}
                          >
                            <i className="fa fa-minus btn-icon-add-remove"></i>
                          </button>
                        </div>
                        {email.errorCC && (
                          <div className="form-group row">
                            <label className="col-4 col-form-label"></label>
                            <div className="col-sm-8 form-custom">
                              <ErrorComponent error={email.errorCC} />
                            </div>
                          </div>
                        )}
                      </React.Fragment>
                    )}
                  </React.Fragment>
                ))}
              </div>
            </div>
          </div>
        </div>
        <div className="text-center">
          <button
            className="btn btn-primary btn-send"
            onClick={this.onClickSend}
          >
            <b>{buttonType.BUTTON_CREATE}</b>
          </button>
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

export default CreateProjectComponent;
