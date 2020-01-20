import React, { Component } from "react";
import "./edit-project.css";
import AlertComponent from "../../../../shared/common/component/alert.component";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { message } from "../../../../shared/constants/message.constant";
import { buttonType } from "../../../../shared/constants/button-type.constant";
import { Redirect, Link } from "react-router-dom";
import history from "../../../../config/history.config";
import { role, position } from "../../../../shared/constants/role.constant";
import DatePicker from "react-datepicker";
import { dateTimePattern } from "../../../../shared/constants/date-time-pattern.constant";
import commonUtil from "../../../../shared/utils/common.util";
import actionUtil from "../../../../shared/utils/action.util";
import ModalComponent from "../../../../shared/common/component/modal.component";
import validateUtil from "../../../../shared/utils/validate.util";
import moment from "moment";
import page from "../../../../shared/constants/page.constant";
import detailUserPage from "../../user/detail-user/detail-user.page";
import Select from "react-select";
import { type } from "../../../../shared/constants/type-style.constant";
import constant from "../../../../shared/constants/page.constant";

class EditProjectComponent extends Component {
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
        projectCode: "",
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
        emailCC: [],
        memberList: [
          {
            employeeCode: "",
            positionCode: ""
          }
        ]
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
        emailCC: "",
        memberList: false,
        isDuplicatePM: false,
        isDuplicateTL: false
      },
      rows: [
        {
          id: "",
          checkbox: false,
          employeeCode: "",
          employeeName: "",
          positionCode: "",
          positionCodeName: ""
        }
      ],
      emailRequest: [
        {
          id: "",
          emailCC: "",
          errorCC: ""
        }
      ]
    };
  }

  onClickOk = () => {
    this.setState({
      modal: {
        show: false
      }
    });
    const { projectInfo, error } = this.state;

    if (
      this.onCheckValidate() &&
      !error.memberList &&
      !error.isDuplicatePM &&
      !error.isDuplicateTL
    ) {
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

  onClickUpdate = () => {
    const { error } = this.state;
    if (
      this.onCheckValidate() &&
      !error.memberList &&
      !error.isDuplicatePM &&
      !error.isDuplicateTL
    ) {
      this.setState({
        modal: {
          show: true,
          title: message.MSG_TITLE_001,
          content: message.MSG_INFO_023
        }
      });
    }
  };

  componentDidMount = () => {
    this.props.onLoadInit();
  };

  componentWillReceiveProps = nextProps => {
    const { projectStore } = this.props;

    if (projectStore.projectDetail) {
      const { resultData } = projectStore.projectDetail;

      let projectDetail = [];
      let project = {};
      let members = [];
      let email = [];
      let projectRankList = [];
      // Get project rank
      resultData.projectRankList.forEach(projectRank => {
        projectRank = commonUtil.unescapeHtmlObject(projectRank);
      });
      projectRankList = resultData.projectRankList;
      // Get project detail
      resultData.projectDetail.forEach(projectDetail => {
        let id = (+new Date() + Math.floor(Math.random() * 999999)).toString(
          36
        );
        projectDetail = commonUtil.unescapeHtmlObject(projectDetail);
        projectDetail.id = id;
        projectDetail.checkbox = false;
      });
      projectDetail = resultData.projectDetail;
      // Get project
      project = commonUtil.unescapeHtmlObject(resultData.project);
      // Set ptoject rank
      projectRankList.forEach(projectRank => {
        if (projectRank.rank === project.projectRank) {
          project.projectRank = projectRank.id;
        }
      });
      // Set BrSE, TL, PM
      projectDetail.forEach(projectDetail => {
        // Set member list
        let member = {
          employeeCode: "",
          positionCode: ""
        };
        member.employeeCode = projectDetail.employeeCode + "";
        member.positionCode = projectDetail.positionCode + "";
        members.push(member);
      });
      // Set position is PM
      const projectManagement = projectDetail.filter(
        projectDetail => +projectDetail.positionCode === +position.POSITION_PM
      );
      project.projectManagement =
        projectManagement.length > 0 ? projectManagement[0].employeeName : "";

      // Set position is BrSE
      const BrSE = projectDetail.filter(
        projectDetail => +projectDetail.positionCode === +position.POSITION_BRSE
      );
      project.brSE = BrSE.length > 0 ? BrSE[0].employeeName : "";

      // Set position is TL
      const teamLead = projectDetail.filter(
        projectDetail => +projectDetail.positionCode === +position.POSITION_TL
      );
      project.teamLead = teamLead.length > 0 ? teamLead[0].employeeName : "";
      // Set member
      project.memberList = members;
      let emailList = project.emailCC;

      // Get email
      if (emailList.indexOf(",") != -1) {
        emailList.split(",").forEach(item => {
          let object = {};
          let id = (+new Date() + Math.floor(Math.random() * 999999)).toString(
            36
          );
          object.id = id;
          object.emailCC = item;
          object.errorCC = "";
          email.push(object);
        });
        project.emailCC = emailList.split(",");
      } else {
        if (typeof emailList === "object") {
          emailList.forEach(item => {
            let object = {};
            let id = (
              +new Date() + Math.floor(Math.random() * 999999)
            ).toString(36);
            object.id = id;
            object.emailCC = item;
            object.errorCC = "";
            email.push(object);
          });
        } else {
          let object = {};
          let id = (+new Date() + Math.floor(Math.random() * 999999)).toString(
            36
          );
          object.id = id;
          object.emailCC = emailList;
          object.errorCC = "";
          email.push(object);
          project.emailCC = emailList === "" ? [] : emailList;
        }
      }
      this.setState({
        ...this.state,
        emailRequest: email,
        projectInfo: project,
        rows: projectDetail
      });
    }
  };

  onClickBack = () => {
    history.push("/project/list");
  };

  onHandleChangeDetailProject = (key, object, name) => {
    let { rows } = this.state;
    let rowsResult = [];

    rows.forEach(row => {
      if (row.id === key) {
        let rowRequest = {};
        rowRequest.id = (
          +new Date() + Math.floor(Math.random() * 999999)
        ).toString(36);
        rowRequest.checkbox = false;
        // Set value for employee
        if (name.name === "employeeName") {
          rowRequest.employeeCode = object.employeeCode + "";
          rowRequest.employeeName = object.employeeName + "";
        } else {
          rowRequest.employeeCode = row.employeeCode + "";
          rowRequest.employeeName = row.employeeName + "";
        }
        // Set value for position
        if (name.name === "positionCodeName") {
          rowRequest.positionCode = object.id + "";
          rowRequest.positionCodeName = object.positionName + "";
        } else {
          rowRequest.positionCode = row.positionCode + "";
          rowRequest.positionCodeName = row.positionCodeName + "";
        }

        rowsResult.push(rowRequest);
      } else {
        rowsResult.push(row);
      }
    });
    this.setState(
      {
        ...this.state,
        rows: rowsResult
      },
      () => {
        const { rows } = this.state;
        // Check validate duplicate member list
        let rowsCustom = rows.map(
          row => row.employeeCode + "" + row.positionCode
        );
        let indexDuplicate = [];
        let isDuplicate = rowsCustom.some((item, index) => {
          if (rowsCustom.indexOf(item) != index) {
            indexDuplicate.push(index);
            indexDuplicate.push(rowsCustom.indexOf(item));
          }
          return rowsCustom.indexOf(item) != index;
        });

        // Check validate duplicate position ProjectManagement
        let rowPM = rows.filter(row => {
          if (+row.positionCode === +position.POSITION_PM) {
            return row.positionCode;
          }
        });
        rowPM = rowPM.map(row => row.positionCode);
        let indexDuplicatePM = [];
        let isDuplicatePM = rowPM.some((item, index) => {
          if (rowPM.indexOf(item) != index) {
            indexDuplicatePM.push(index);
            indexDuplicatePM.push(rowPM.indexOf(item));
          }
          return rowPM.indexOf(item) != index;
        });

        // Check validate duplicate position TeamLead
        let rowTL = rows.filter(row => {
          if (+row.positionCode === +position.POSITION_TL) {
            return row.positionCode;
          }
        });
        rowTL = rowTL.map(row => row.positionCode);
        let indexDuplicateTL = [];
        let isDuplicateTL = rowTL.some((item, index) => {
          if (rowTL.indexOf(item) != index) {
            indexDuplicateTL.push(index);
            indexDuplicateTL.push(rowTL.indexOf(item));
          }
          return rowTL.indexOf(item) != index;
        });

        let projectManagement = null;
        let brSE = null;
        let teamLead = null;
        let members = [];
        rows.forEach(row => {
          let member = {
            employeeCode: "",
            positionCode: ""
          };
          member.employeeCode = row.employeeCode + "";
          member.positionCode = row.positionCode + "";
          members.push(member);
          // Set position is PM
          if (+row.positionCode === position.POSITION_PM) {
            projectManagement = row.employeeName + "";
          }
          // Set position is BrSE
          else if (+row.positionCode === position.POSITION_BRSE) {
            brSE = row.employeeName + "";
          }
          // Set position is TL
          else if (+row.positionCode === position.POSITION_TL) {
            teamLead = row.employeeName + "";
          }
        });
        this.setState(
          {
            ...this.state,
            error: {
              ...this.error,
              memberList: isDuplicate,
              isDuplicatePM,
              isDuplicateTL
            },
            projectInfo: {
              ...this.state.projectInfo,
              memberList: members,
              projectManagement: projectManagement,
              brSE: brSE,
              teamLead: teamLead
            }
          },
          () => {
            const { projectInfo } = this.state;
            let error = {};
            // Check project management
            if (projectInfo.projectManagement) {
              if (validateUtil.isEmpty(projectInfo.projectManagement)) {
                error.projectManagement = message.MSG_ERROR_001;
              } else if (
                validateUtil.maxLength(projectInfo.projectManagement, 50)
              ) {
                error.projectManagement = commonUtil.parseMessage(
                  message.MSG_ERROR_023,
                  [50]
                );
              }
            } else {
              error.projectManagement = message.MSG_ERROR_001;
            }

            // Check brSE
            if (projectInfo.brSE) {
              if (validateUtil.maxLength(projectInfo.brSE, 50)) {
                error.brSE = commonUtil.parseMessage(message.MSG_ERROR_023, [
                  50
                ]);
              }
            }

            // Check teamLead
            if (projectInfo.teamLead) {
              if (validateUtil.isEmpty(projectInfo.teamLead)) {
                error.teamLead = message.MSG_ERROR_001;
              } else if (validateUtil.maxLength(projectInfo.teamLead, 50)) {
                error.teamLead = commonUtil.parseMessage(
                  message.MSG_ERROR_023,
                  [50]
                );
              }
            } else {
              error.teamLead = message.MSG_ERROR_001;
            }

            // Set state
            this.setState({
              error: {
                ...this.state.error,
                projectManagement: error.projectManagement,
                brSE: error.brSE,
                teamLead: error.teamLead
              }
            });
          }
        );
      }
    );
  };

  onHandleChangeProjectRank = object => {
    this.setState({
      ...this.state,
      projectInfo: {
        ...this.state.projectInfo,
        projectRank: object.id ? object.id : 0
      }
    });
  };

  onChangeCheckBox = event => {
    let { rows } = this.state;

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

  onChangeSelectAllCheckBox = event => {
    let { rows } = this.state;
    rows.forEach(row => (row.checkbox = event.target.checked));
    this.setState({
      rows: rows
    });
  };

  checkCheckBoxAll() {
    let { rows } = this.state;
    let count = 0;
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

  onClickAddRow = () => {
    let { rows } = this.state;
    // Init object row
    let id = (+new Date() + Math.floor(Math.random() * 999999)).toString(36);
    var objRequest = {
      id,
      checkbox: false,
      employeeCode: "",
      employeeName: "",
      positionCode: "",
      positionCodeName: ""
    };
    rows.push(objRequest);
    this.setState({
      rows: rows
    });
  };

  onClickDeleteRow = () => {
    let { rows } = this.state;
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

  onAddRow = e => {
    e.preventDefault();
    const { emailRequest } = this.state;
    const id = (+new Date() + Math.floor(Math.random() * 999999)).toString(36);
    let email = [];
    email = [...emailRequest];
    email.push({
      id: id,
      emailCC: "",
      errorCC: ""
    });
    this.setState({
      ...this.state,
      emailRequest: email,
      projectInfo: {
        ...this.state.projectInfo,
        emailCC: email
      }
    });
  };

  onRemoveRow = (e, id) => {
    e.preventDefault();
    let { emailRequest } = this.state;
    emailRequest = emailRequest.filter(email => email.id != id);
    this.setState({
      ...this.state,
      emailRequest: emailRequest,
      projectInfo: {
        ...this.state.projectInfo,
        emailCC: emailRequest
      }
    });
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

  onHandleChange = e => {
    this.setState(
      {
        projectInfo: {
          ...this.state.projectInfo,
          [e.target.name]: e.target.value
        }
      },
      () => {
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
              error.projectNameJP = commonUtil.parseMessage(
                message.MSG_ERROR_023,
                [150]
              );
            }
          }
          // Check project name VN
          if (projectInfo.projectNameVN) {
            if (validateUtil.maxLength(projectInfo.projectNameVN, 150)) {
              error.projectNameVN = commonUtil.parseMessage(
                message.MSG_ERROR_023,
                [150]
              );
            }
          }
        }

        // Check billable effort
        if (validateUtil.isEmpty(projectInfo.billableEffort)) {
          error.billableEffort = message.MSG_ERROR_001;
        } else if (validateUtil.maxLength(projectInfo.billableEffort, 250)) {
          error.billableEffort = commonUtil.parseMessage(
            message.MSG_ERROR_023,
            [250]
          );
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

        // Check objectives
        if (validateUtil.isEmpty(projectInfo.objectives)) {
          error.objectives = message.MSG_ERROR_001;
        } else if (validateUtil.maxLength(projectInfo.objectives, 300)) {
          error.objectives = commonUtil.parseMessage(message.MSG_ERROR_023, [
            300
          ]);
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
            projectRank: error.projectRank,
            objectives: error.objectives,
            scope: error.scope
          }
        });
      }
    );
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
            let emailCC = [];
            emailRequest.forEach(email => {
              if (email.emailCC) {
                if (email.id == id && !validateUtil.checkEmail(email.emailCC)) {
                  email.errorCC = message.MSG_ERROR_028;
                } else {
                  email.errorCC = "";
                  emailCC.push(email.emailCC);
                }
              } else {
                email.errorCC = "";
              }
              this.setState({
                // ...this.state,
                projectInfo: {
                  ...this.state.projectInfo,
                  emailCC: emailCC
                },
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

  onCustomState = () => {
    const { projectInfo } = this.state;
    // Set emailCC
    let emailCC = [];
    if (
      typeof projectInfo.emailCC === "object" &&
      projectInfo.emailCC.length > 0
    ) {
      projectInfo.emailCC.forEach(email => {
        if (email.emailCC) {
          emailCC.push(email.emailCC);
        } else {
          emailCC.push(email);
        }
      });
    }

    let projectReq = null;
    projectReq = {
      ...projectInfo,
      emailCC: emailCC,
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
    this.props.onUpdate(projectReq);
    this.onHide();
  };

  render() {
    const { projectStore, userStore } = this.props;
    const { rows, emailRequest, projectInfo, error } = this.state;
    // Check checkbox checked all
    let isChecked = this.checkCheckBoxAll();
    let positionList = [];
    let projectRankList = [];
    let employeeList = [];
    let roleId = userStore.data.resultData.roleId;
    if (projectStore.projectDetail) {
      const { resultData } = projectStore.projectDetail;
      // Get position
      resultData.positionList.forEach(position => {
        position = commonUtil.unescapeHtmlObject(position);
      });
      positionList = resultData.positionList;
      // Add disabled for value= PM
      positionList.forEach(positionVal => {
        if (
          +roleId === +role.ROLE_USER &&
          +positionVal.id === +position.POSITION_PM
        ) {
          positionVal.isDisabled = true;
        } else {
          positionVal.isDisabled = false;
        }
        return positionVal;
      });
      // Get project rank
      resultData.projectRankList.forEach(projectRank => {
        projectRank = commonUtil.unescapeHtmlObject(projectRank);
        projectRankList.push(projectRank);
      });
      projectRankList.push({
        id: 0,
        rank: constant.VALUE_NA,
        description: constant.VALUE_NA
      });
      // Get employee
      resultData.employeeList.forEach(employee => {
        employee = commonUtil.unescapeHtmlObject(employee);
      });
      employeeList = resultData.employeeList;

      if (projectStore.isEdit) {
        return (
          <Redirect
            to={{
              pathname: "/project/list",
              state: {
                messageObject: {
                  message: message.MSG_INFO_022,
                  type: type.TYPE_SUCCESS
                }
              }
            }}
          />
        );
      }

      return (
        <div className="container-fluid mt-4 fix-padding-bottom">
          <h2 className="text-center my-4">
            <b className="font-weigth">CẬP NHẬT THÔNG TIN DỰ ÁN</b>
          </h2>
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
                        name="projectNameJP"
                        value={
                          projectInfo.projectNameJP
                            ? projectInfo.projectNameJP
                            : ""
                        }
                        onChange={this.onHandleChange}
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
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
                      Project Management
                      <span className="text-danger">*</span>
                    </label>
                    <div className="col-sm-7 ml-20">
                      <input
                        type="text"
                        className="form-control"
                        id="projectManagement"
                        name="projectManagement"
                        value={
                          projectInfo.projectManagement
                            ? projectInfo.projectManagement
                            : ""
                        }
                        disabled
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
                      Project Name (VN)<span className="text-danger"> </span>
                    </label>
                    <div className="col-sm-7 ml-50">
                      <input
                        type="text"
                        className="form-control"
                        id="projectNameVN"
                        name="projectNameVN"
                        value={
                          projectInfo.projectNameVN
                            ? projectInfo.projectNameVN
                            : ""
                        }
                        onChange={this.onHandleChange}
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
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
                      BrSE<span className="text-danger"> </span>
                    </label>
                    <div className="col-sm-7 ml-20">
                      <input
                        type="text"
                        className="form-control"
                        id="brSE"
                        name="brSE"
                        value={projectInfo.brSE ? projectInfo.brSE : ""}
                        disabled
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
                        name="billableEffort"
                        value={
                          projectInfo.billableEffort
                            ? projectInfo.billableEffort
                            : ""
                        }
                        onChange={this.onHandleChange}
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
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
                      <input
                        type="text"
                        className="form-control "
                        id="teamLead"
                        name="teamLead"
                        value={projectInfo.teamLead ? projectInfo.teamLead : ""}
                        disabled
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
                    {+roleId !== +role.ROLE_ADMIN ? (
                      <div className="col-sm-7 ml-50">
                        <DatePicker
                          className="form-control datepicker reset-date-picker"
                          name="startDate"
                          selected={
                            projectInfo.startDate === ""
                              ? ""
                              : new Date(projectInfo.startDate)
                          }
                          onChange={value =>
                            this.onHandleChangeDate("startDate", value)
                          }
                          placeholderText={dateTimePattern.YYYYMMDD}
                          peekNextMonth
                          showMonthDropdown
                          showYearDropdown
                          dateFormat={dateTimePattern.yyyyMMdd}
                          dropdownMode="select"
                          disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
                        />
                      </div>
                    ) : (
                      <div className="col-sm-7 ml-50">
                        <DatePicker
                          className="form-control datepicker reset-date-picker"
                          name="startDate"
                          selected={
                            projectInfo.startDate === ""
                              ? ""
                              : new Date(projectInfo.startDate)
                          }
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
                          disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
                        />
                      </div>
                    )}
                    {error.startDate && (
                      <React.Fragment>
                        <label className="col-5 col-form-label"></label>
                        <div className="col-sm-5 ml-50">
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
                    {+roleId !== +role.ROLE_ADMIN ? (
                      <div className="col-sm-7 ml-20">
                        <input
                          type="text"
                          className="form-control"
                          id="projectRank"
                          name="projectRank"
                          value={
                            projectInfo.projectRank
                              ? projectRankList.filter(
                                  projectRank =>
                                    projectRank.id === projectInfo.projectRank
                                )[0].rank
                              : constant.VALUE_NA
                          }
                          disabled
                        />
                        {error.projectRank && (
                          <ErrorComponent error={error.projectRank} />
                        )}
                      </div>
                    ) : (
                      <div className="col-sm-7 ml-20">
                        <Select
                          className="react-selectcomponent"
                          options={projectRankList}
                          getOptionLabel={option => `${option.rank}`}
                          getOptionValue={option => `${option.rank}`}
                          onChange={this.onHandleChangeProjectRank}
                          isSearchable={true}
                          value={
                            projectInfo.projectRank
                              ? projectRankList.filter(
                                  projectRank =>
                                    projectRank.id === projectInfo.projectRank
                                )
                              : projectRankList.filter(
                                  projectRank =>
                                    projectRank.rank === constant.VALUE_NA
                                )
                          }
                          name="projectRank"
                        />
                        {error.projectRank && (
                          <ErrorComponent error={error.projectRank} />
                        )}
                      </div>
                    )}
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
                        selected={
                          projectInfo.endDate === ""
                            ? ""
                            : new Date(projectInfo.endDate)
                        }
                        onChange={value =>
                          this.onHandleChangeDate("endDate", value)
                        }
                        placeholderText={
                          projectInfo.endDate === ""
                            ? "N/A"
                            : dateTimePattern.YYYYMMDD
                        }
                        peekNextMonth
                        showMonthDropdown
                        showYearDropdown
                        dateFormat={dateTimePattern.yyyyMMdd}
                        dropdownMode="select"
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
                      />
                    </div>
                    {error.endDate && (
                      <React.Fragment>
                        <label className="col-5 col-form-label"></label>
                        <div className="col-sm-7 ml-50">
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
                        name="objectives"
                        value={
                          projectInfo.objectives ? projectInfo.objectives : ""
                        }
                        onChange={this.onHandleChange}
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
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
                        name="customerName"
                        value={
                          projectInfo.customerName
                            ? projectInfo.customerName
                            : ""
                        }
                        onChange={this.onHandleChange}
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
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
                        name="scope"
                        value={projectInfo.scope ? projectInfo.scope : ""}
                        onChange={this.onHandleChange}
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
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
                      Sale<span className="text-danger"> </span>
                    </label>
                    <div className="col-sm-7 ml-50">
                      <input
                        type="text"
                        className="form-control"
                        id="sale"
                        name="sale"
                        value={projectInfo.sale ? projectInfo.sale : ""}
                        onChange={this.onHandleChange}
                        disabled={+roleId !== +role.ROLE_ADMIN ? true : false}
                      />
                      {error.sale && <ErrorComponent error={error.sale} />}
                    </div>
                  </div>
                </div>
                <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                  {+roleId !== +role.ROLE_ADMIN ? (
                    <React.Fragment>
                      {emailRequest.length > 0 ? (
                        <React.Fragment>
                          {emailRequest.map((email, index) => {
                            return (
                              <React.Fragment key={index}>
                                <div className="form-group row">
                                  {index === 0 ? (
                                    <label className="col-5 col-form-label lable-front">
                                      EmailCC
                                      <span className="text-danger"> </span>
                                    </label>
                                  ) : (
                                    <label className="col-5 col-form-label">
                                      <span className="text-danger"> </span>
                                    </label>
                                  )}

                                  <div className="col-sm-7 ml-20">
                                    <input
                                      type="text"
                                      className="form-control"
                                      id={`email + ${index}`}
                                      name={`email + ${index}`}
                                      value={email.emailCC}
                                      onChange={this.onHandleChangeEmail.bind(
                                        this,
                                        email.id
                                      )}
                                      disabled={
                                        +roleId !== +role.ROLE_ADMIN
                                          ? true
                                          : false
                                      }
                                    />
                                  </div>
                                </div>
                              </React.Fragment>
                            );
                          })}
                        </React.Fragment>
                      ) : (
                        <div className="form-group row">
                          <label className="col-4 col-form-label lable-front">
                            EmailCC<span className="text-danger"> </span>
                          </label>
                          <div className="col-sm-7 ml-20">
                            <input
                              type="text"
                              className="form-control"
                              id="email"
                              name="email"
                              value=""
                              onChange={this.onHandleChangeEmail.bind(this, 0)}
                              disabled={
                                +roleId !== +role.ROLE_ADMIN ? true : false
                              }
                            />
                          </div>
                        </div>
                      )}
                    </React.Fragment>
                  ) : (
                    <React.Fragment>
                      {emailRequest.length > 0 ? (
                        <React.Fragment>
                          {emailRequest.map((email, index) => {
                            return (
                              <React.Fragment key={index}>
                                <div className="form-group row">
                                  {index === 0 ? (
                                    <label className="col-5 col-form-label lable-front">
                                      EmailCC
                                      <span className="text-danger"> </span>
                                    </label>
                                  ) : (
                                    <label className="col-5 col-form-label">
                                      <span className="text-danger"> </span>
                                    </label>
                                  )}

                                  <div className="col-sm-5 ml-20">
                                    <input
                                      type="text"
                                      className="form-control"
                                      id={`email + ${index}`}
                                      name={`email + ${index}`}
                                      value={email.emailCC}
                                      onChange={this.onHandleChangeEmail.bind(
                                        this,
                                        email.id
                                      )}
                                      disabled={
                                        +roleId !== +role.ROLE_ADMIN
                                          ? true
                                          : false
                                      }
                                    />
                                  </div>

                                  {index === 0 ? (
                                    <button
                                      className="btn btn-primary btn-icon-add-remove-feedback mt-0"
                                      onClick={this.onAddRow}
                                    >
                                      <i className="fa fa-plus btn-icon-add-remove"></i>
                                    </button>
                                  ) : (
                                    <button
                                      className="btn btn-danger btn-icon-add-remove-feedback mt-0"
                                      onClick={(e, id) =>
                                        this.onRemoveRow(e, email.id)
                                      }
                                    >
                                      <i className="fa fa-minus btn-icon-add-remove"></i>
                                    </button>
                                  )}
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
                            );
                          })}
                        </React.Fragment>
                      ) : (
                        <div className="form-group row">
                          <label className="col-4 col-form-label lable-front">
                            EmailCC<span className="text-danger"> *</span>
                          </label>
                          <div className="col-sm-5 ml-20">
                            <input
                              type="text"
                              className="form-control"
                              id="email"
                              name="email"
                              value=""
                              onChange={this.onHandleChangeEmail.bind(this, 0)}
                              disabled={
                                +roleId !== +role.ROLE_ADMIN ? true : false
                              }
                            />
                          </div>

                          <button
                            className="btn btn-primary btn-icon-add-remove-feedback mt-0"
                            onClick={this.onAddRow}
                          >
                            <i className="fa fa-plus btn-icon-add-remove"></i>
                          </button>
                        </div>
                      )}
                    </React.Fragment>
                  )}
                </div>
              </div>
            </div>
          </div>
          <div className="mt-4">
            <label>
              <b className="font-weigth">Thành Viên Của Dự Án</b>
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
              {error.memberList && (
                <ErrorComponent error={message.MSG_ERROR_034} />
              )}
              {error.isDuplicatePM && (
                <ErrorComponent
                  error={commonUtil.parseMessage(message.MSG_ERROR_035, [
                    "Project Management"
                  ])}
                />
              )}
              {error.isDuplicateTL && (
                <ErrorComponent
                  error={commonUtil.parseMessage(message.MSG_ERROR_035, [
                    "Team Lead"
                  ])}
                />
              )}
            </div>

            <div>
              <table
                className="table table-bordered table-hover"
                style={{ width: "65%" }}
              >
                <thead className="thead-dark">
                  <tr className="text-center">
                    <th
                      width="2%"
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
                          className="custom-control-label "
                          htmlFor="customCheckAll"
                        ></label>
                      </div>
                    </th>
                    <th
                      width="3%"
                      scope="col"
                      className="text-center align-middle"
                    >
                      <b>No</b>
                    </th>
                    <th
                      width="30%"
                      scope="col"
                      className="text-center align-middle"
                    >
                      <b>Họ và Tên</b>
                    </th>

                    <th
                      width="20%"
                      scope="col"
                      className="text-center align-middle"
                    >
                      <b>Vai Trò</b>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {rows.length > 0 &&
                    rows.map((row, index) => {
                      return (
                        <tr key={index}>
                          {+roleId === +role.ROLE_ADMIN ? (
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
                              <td className="align-middle">
                                <Select
                                  className="react-selectcomponent"
                                  options={employeeList}
                                  getOptionLabel={option =>
                                    `${option.employeeName}`
                                  }
                                  getOptionValue={option =>
                                    `${option.employeeName}`
                                  }
                                  onChange={this.onHandleChangeDetailProject.bind(
                                    this,
                                    row.id
                                  )}
                                  isSearchable={true}
                                  value={employeeList.filter(
                                    employee =>
                                      employee.employeeCode === row.employeeCode
                                  )}
                                  name="employeeName"
                                />
                              </td>
                              <td className="align-middle">
                                <Select
                                  className="react-selectcomponent"
                                  options={positionList}
                                  getOptionLabel={option =>
                                    `${option.positionName}`
                                  }
                                  getOptionValue={option =>
                                    `${option.positionName}`
                                  }
                                  onChange={this.onHandleChangeDetailProject.bind(
                                    this,
                                    row.id
                                  )}
                                  isSearchable={true}
                                  value={positionList.filter(
                                    position =>
                                      +position.id === +row.positionCode
                                  )}
                                  name="positionCodeName"
                                />
                              </td>
                            </React.Fragment>
                          ) : (
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
                                    disabled={
                                      +row.positionCode ===
                                      +position.POSITION_PM
                                        ? true
                                        : false
                                    }
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
                              <td className="align-middle">
                                <Select
                                  className="react-selectcomponent"
                                  options={employeeList}
                                  getOptionLabel={option =>
                                    `${option.employeeName}`
                                  }
                                  getOptionValue={option =>
                                    `${option.employeeName}`
                                  }
                                  onChange={this.onHandleChangeDetailProject.bind(
                                    this,
                                    row.id
                                  )}
                                  isSearchable={true}
                                  value={employeeList.filter(
                                    employee =>
                                      employee.employeeCode === row.employeeCode
                                  )}
                                  name="employeeName"
                                  isDisabled={
                                    +row.positionCode === +position.POSITION_PM
                                      ? true
                                      : false
                                  }
                                />
                              </td>
                              <td className="align-middle">
                                <Select
                                  className="react-selectcomponent"
                                  options={positionList}
                                  getOptionLabel={option =>
                                    `${option.positionName}`
                                  }
                                  getOptionValue={option =>
                                    `${option.positionName}`
                                  }
                                  onChange={this.onHandleChangeDetailProject.bind(
                                    this,
                                    row.id
                                  )}
                                  isSearchable={true}
                                  value={positionList.filter(
                                    position =>
                                      +position.id === +row.positionCode
                                  )}
                                  name="positionCodeName"
                                  isDisabled={
                                    +row.positionCode === +position.POSITION_PM
                                      ? true
                                      : false
                                  }
                                />
                              </td>
                            </React.Fragment>
                          )}
                        </tr>
                      );
                    })}
                </tbody>
              </table>
            </div>
            <div className="text-center">
              <button
                className="btn btn-info btn-send"
                onClick={this.onClickBack}
              >
                <b>{buttonType.BUTTON_BACK}</b>
              </button>
              <button
                className="btn btn-primary btn-send"
                onClick={this.onClickUpdate}
              >
                <b>{buttonType.BUTTON_UPDATE}</b>
              </button>
            </div>
            <ModalComponent
              onClick={this.onClickOk}
              showModal={this.state.modal}
              onHide={this.onHide}
            />
          </div>
        </div>
      );
    } else {
      return (
        <div className="container-fluid mt-4 fix-padding-bottom">
          <h2 className="text-center my-4">
            <b className="font-weigth">CẬP NHẬT THÔNG TIN DỰ ÁN</b>
          </h2>
          <div className="row">
            <div className="col-12">
              {/* {<AlertComponent messageObj={messageStore} />} */}
            </div>
          </div>
          <div className="text-center">
            <button
              className="btn btn-info btn-send"
              onClick={this.onClickBack}
            >
              <b>{buttonType.BUTTON_BACK}</b>
            </button>
            <button
              className="btn btn-primary btn-send"
              onClick={this.onClickUpdate}
            >
              <b>{buttonType.BUTTON_UPDATE}</b>
            </button>
          </div>
        </div>
      );
    }
  }
}

export default EditProjectComponent;
