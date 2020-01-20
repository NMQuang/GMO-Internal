import React, { Component } from "react";
import DatePicker from "react-datepicker";
import moment from "moment";
import { dateTimePattern } from "../../../../../shared/constants/date-time-pattern.constant";
import page from "../../../../../shared/constants/page.constant";
import "./list-approval.css";
import ErrorComponent from "../../../../../shared/common/component/error.component";
import { message } from "../../../../../shared/constants/message.constant";
import { role } from "../../../../../shared/constants/role.constant";
import commonUtil from "../../../../../shared/utils/common.util";
import Pagination from "react-bootstrap/Pagination";
import { buttonType } from "../../../../../shared/constants/button-type.constant";
import Select from "react-select";
import constant from "../../../../../shared/constants/page.constant";
class ListApprovalComponent extends Component {
  /**
   * Constructor
   *
   * @param props
   */
  constructor(props) {
    super(props);
    this.state = {
      isShowIcon: true,
      startDate: new Date(),
      dayOfMonth: "",
      projectNameChange: "",
      currentPage: 1,
      recordsPerPage: page.RECORD_PER_PAGE_5,
      isSearch: false,
      projectNameOld: "",
      error: false
    };
  }

  showIcon = () => {
    this.setState({
      isShowIcon: !this.state.isShowIcon
    });
  };

  /**
   * Handle get number
   * @param year
   * @param month
   */
  handleGetNumberDate = (year, month) => {
    return new Date(year, month, 0).getDate();
  };

  /**
   * Handle when choose date
   * @param date
   */
  handleChange = date => {
    this.handleSetDayOfMonth(date);
    /**
     * Check if date is null or empty then set error= true else false
     */
    if (date == null || date === "") {
      this.setState({ error: true });
    } else {
      this.setState({ error: false });
    }
    this.setState({
      startDate: date,
      isSearch: false
    });
  };

  /**
   * Handle when choose project name
   * @param e
   */
  onHandleChangeProject = object => {
    this.setState({
      projectNameChange:
        object.projectName === constant.VALUE_ALL ? "" : object.projectName,
      isSearch: false
    });
  };

  /**
   * Handle set day of month
   * @param date
   */
  handleSetDayOfMonth = date => {
    const year = moment(date).format("YYYY");
    const monthOfYear = moment(date).format("MM");
    let dayOfMonthOfYear = this.handleGetNumberDate(year, monthOfYear);
    this.setState({
      dayOfMonth: dayOfMonthOfYear
    });
  };

  /**
   * Handle when click button search
   */
  onClickSearch = () => {
    const { startDate, projectNameChange } = this.state;

    let dateRequestFormat = moment(startDate).format(dateTimePattern.YYYYMM);
    /**
     * If date is null then date= ""
     */
    this.handleSetDayOfMonth(startDate);
    if (startDate == null) {
      dateRequestFormat = "";
    }
    /**
     * Check validdate send data
     */
    if (this.checkValidate()) {
      this.setState({
        isSearch: true,
        projectNameOld: projectNameChange,
        currentPage: 1
      });
      this.props.onHandle(dateRequestFormat, projectNameChange, 1);
    }
  };
  /**
   * handle Exprot data
   */
  handleExprotData = () => {
    const { startDate, projectNameChange } = this.state;
    let dateRequestFormat = moment(startDate).format(dateTimePattern.YYYYMM);
    /**
     * If date is null then date= ""
     */
    this.handleSetDayOfMonth(startDate);
    if (startDate == null) {
      dateRequestFormat = "";
    }
    /**
     * Check validate
     */
    if (this.checkValidate()) {
      this.props.handleExprotData(dateRequestFormat, projectNameChange);
    }
  };
  /**
   * Check validate
   */
  checkValidate = () => {
    const { startDate } = this.state;
    /**
     * Check if date is null or empty then set error= true and return false
     */
    if (startDate == null || startDate === "") {
      this.setState({ error: true });
      return false;
    }
    return true;
  };
  /**
   * Handle process list approval
   * @param listApproval
   */
  handleProcess = listApproval => {
    let listApprovalTemp = [];
    let listApprovalConvert = [];
    let listApprovalResult = [];
    let totalRow = {
      dateApprove: {},
      totalPlanOT: 0,
      totalApproval: 0
    };
    // listApproval.map(item => {
    //   let dateValue = moment(item.dateOT).format("D");
    //   listApprovalConvert.push({
    //     totalPlanOT: item.planTimeOT,
    //     totalApproval: item.approvalTimeOT,
    //     employeeCode: item.employeeCode,
    //     employeeName: item.employeeName,
    //     positionName: item.positionName,
    //     projectName: item.projectName,
    //     dateApprove: {
    //       [dateValue]: item.planTimeOT
    //     }
    //   });
    // });

    // listApproval.forEach(approval => {
    //   listApprovalConvert.map(item => {
    //     if (
    //       item.employeeCode === approval.employeeCode &&
    //       item.positionName === approval.positionName &&
    //       item.projectName === approval.projectName
    //     ) {
    //     } else {
    //     }
    //   });
    // });
    // Iterate list approve to re-arrange data
    listApproval.map(item => {
      let dateValue = moment(item.dateOT).format("D");

      //If temp list exist
      if (listApprovalTemp.length > 0) {
        let isExist = false;
        for (let i = 0; i < listApprovalTemp.length; i++) {
          let approvalTmp = listApprovalTemp[i];

          // If exist, update old item and append new date
          if (
            item.employeeCode === approvalTmp.employeeCode &&
            item.positionName === approvalTmp.positionName &&
            item.projectName === approvalTmp.projectName
          ) {
            approvalTmp.totalPlanOT += item.planTimeOT;
            approvalTmp.totalApproval += item.approvalTimeOT;
            approvalTmp.dateApprove = {
              ...approvalTmp.dateApprove,
              [dateValue]: item.planTimeOT
            };

            // Total row
            totalRow.dateApprove[dateValue] =
              totalRow.dateApprove[dateValue] === undefined
                ? item.planTimeOT
                : totalRow.dateApprove[dateValue] + item.planTimeOT;
            totalRow.totalPlanOT += item.planTimeOT;
            totalRow.totalApproval += item.approvalTimeOT;
            isExist = true;
          }
        }

        if (!isExist) {
          // Add new approve item
          listApprovalTemp.push({
            totalPlanOT: item.planTimeOT,
            totalApproval: item.approvalTimeOT,
            employeeCode: item.employeeCode,
            employeeName: item.employeeName,
            positionName: item.positionName,
            projectName: item.projectName,
            dateApprove: {
              [dateValue]: item.planTimeOT
            }
          });

          // Total row
          totalRow.dateApprove[dateValue] =
            totalRow.dateApprove[dateValue] === undefined
              ? item.planTimeOT
              : totalRow.dateApprove[dateValue] + item.planTimeOT;
          totalRow.totalPlanOT += item.planTimeOT;
          totalRow.totalApproval += item.approvalTimeOT;
        }
      } else {
        listApprovalTemp.push({
          totalPlanOT: item.planTimeOT,
          totalApproval: item.approvalTimeOT,
          employeeCode: item.employeeCode,
          employeeName: item.employeeName,
          positionName: item.positionName,
          projectName: item.projectName,
          dateApprove: {
            [dateValue]: item.planTimeOT
          }
        });
        // Total row
        totalRow.dateApprove[dateValue] = item.planTimeOT;
        totalRow.totalPlanOT = item.planTimeOT;
        totalRow.totalApproval = item.approvalTimeOT;
      }
    });

    // Add total row to the last
    listApprovalTemp.push(totalRow);

    return listApprovalTemp;
  };

  /**
   * Handle order by project name list approval
   * @param listFillDataDate
   * @param rowTotal
   */
  handleSameProjectName = (listFillDataDate, rowTotal) => {
    let listDataresultTemp = [];
    listFillDataDate.map(item => {
      /**
       * Check if list > then check exist
       */
      if (listDataresultTemp.length > 0) {
        let isExist = false;
        for (let i = 0; i < listDataresultTemp.length; i++) {
          let dataTemp = listDataresultTemp[i];
          /**
           * Check if exist then add
           */
          if (dataTemp.projectName === item.projectName) {
            listDataresultTemp[i].dataResult = [
              ...listDataresultTemp[i].dataResult,
              item
            ];
            isExist = true;
          }
        }
        /**
         * Check if not exist then add new
         */
        if (isExist == false) {
          listDataresultTemp.push({
            projectName: item.projectName,
            dataResult: [item]
          });
        }
      } else {
        listDataresultTemp.push({
          projectName: item.projectName,
          dataResult: [item]
        });
      }
    });
    listDataresultTemp.push(rowTotal);
    return listDataresultTemp;
  };
  /**
   * Handle process render list approval
   */
  handleProcessRow = (
    recordsFrom,
    dataResult,
    numberDate,
    isLastIndex,
    year,
    month
  ) => {
    let lisApprovalRender = [];
    let approveTime = [];
    let myDate = new Date();
    myDate.setFullYear(year);
    myDate.setMonth(month - 1);
    /**
     * If is last index then show row total else show row contain value
     */
    if (isLastIndex) {
      for (let i = 1; i <= numberDate; i++) {
        myDate.setDate(i);
        /**
         * Check if data have key same date then add value
         */
        if (dataResult.dateApprove[i]) {
          /**
           * Check if day is sunday or saturday then add class name
           */
          if (myDate.getDay() == 6 || myDate.getDay() == 0) {
            approveTime.push(
              <th
                key={i + "total"}
                className="table-danger text-center align-middle"
              >
                {dataResult.dateApprove[i]}
              </th>
            );
          } else {
            approveTime.push(
              <th key={i} className="table-danger text-center align-middle">
                {dataResult.dateApprove[i]}
              </th>
            );
          }
        } else {
          /**
           * Check if day is sunday or saturday then add class name
           */
          if (myDate.getDay() == 6 || myDate.getDay() == 0) {
            approveTime.push(
              <th
                key={i + "total"}
                className="table-danger text-center align-middle"
              >
                0
              </th>
            );
          } else {
            approveTime.push(
              <th
                key={i + "total"}
                className="table-danger text-center align-middle"
              >
                0
              </th>
            );
          }
        }
      }
      // lisApprovalRender.push(
      //   <tr key={recordsFrom + "row total"} className="action">
      //     <th colSpan="5" className="table-danger text-center align-middle">
      //       Tổng cộng
      //     </th>

      //     {approveTime}
      //     <th className="table-danger text-center align-middle">
      //       {dataResult.totalPlanOT}
      //     </th>
      //     <th className="table-danger text-center align-middle">
      //       {dataResult.totalApproval}
      //     </th>
      //   </tr>
      // );
    } else {
      /**
       * Loop process data in date
       */
      for (let j = 0; j < dataResult.dataResult.length; j++) {
        let dateApprove = dataResult.dataResult[j].dateApprove;
        approveTime = [];
        for (let i = 1; i <= numberDate; i++) {
          myDate.setDate(i);
          /**
           * Check if data have key same date then add value
           */
          if (dateApprove[i]) {
            /**
             * Check if day is sunday or saturday then add class name
             */
            if (myDate.getDay() == 6 || myDate.getDay() == 0) {
              approveTime.push(
                <th
                  key={i + "numday"}
                  className="style-weekend text-center align-middle"
                >
                  {dateApprove[i]}
                </th>
              );
            } else {
              approveTime.push(
                <th key={i + "numday"} className="text-center align-middle">
                  {dateApprove[i]}
                </th>
              );
            }
          } else {
            /**
             * Check if day is sunday or saturday then add class name
             */
            if (myDate.getDay() == 6 || myDate.getDay() == 0) {
              approveTime.push(
                <th
                  key={i + "numday"}
                  className="style-weekend text-center align-middle"
                >
                  0
                </th>
              );
            } else {
              approveTime.push(
                <th key={i + "numday"} className="text-center align-middle">
                  0
                </th>
              );
            }
          }
        }
        /**
         * If first is load then set row span else not set row span
         */
        if (j == 0) {
          lisApprovalRender.push(
            <tr key={j + "key-record"}>
              <th className="text-center align-middle">{recordsFrom + j}</th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].employeeCode}
              </th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].employeeName}
              </th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].positionName}
              </th>
              <th
                rowSpan={dataResult.dataResult.length}
                className="text-center align-middle"
              >
                {commonUtil.escapeHTML(dataResult.projectName)}
              </th>
              {approveTime}
              <th className="text-center align-middle">
                {dataResult.dataResult[j].totalPlanOT}
              </th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].totalApproval}
              </th>
            </tr>
          );
        } else {
          lisApprovalRender.push(
            <tr key={j + "key-record"}>
              <th className="text-center align-middle">{recordsFrom + j}</th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].employeeCode}
              </th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].employeeName}
              </th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].positionName}
              </th>
              {approveTime}
              <th className="text-center align-middle">
                {dataResult.dataResult[j].totalPlanOT}
              </th>
              <th className="text-center align-middle">
                {dataResult.dataResult[j].totalApproval}
              </th>
            </tr>
          );
        }
      }
    }
    return lisApprovalRender;
  };

  /**
   * Handle render list approval
   * @param listApproval
   * @param munberDate
   * @param year
   * @param month
   * @param recordsFrom
   */
  handleRenderListApproval = (
    listApproval,
    munberDate,
    year,
    month,
    recordsFrom
  ) => {
    let listFillDataDate = [];
    listFillDataDate = this.handleProcess(listApproval, munberDate);
    let lastIndex = listFillDataDate.length - 1;
    let rowTotal = listFillDataDate[lastIndex];
    let isLastIndex = false;
    let lisApprovalRender = [];
    let lisApproval = this.handleSameProjectName(
      listFillDataDate.filter((_, i) => i !== lastIndex),
      rowTotal
    );
    lastIndex = lisApproval.length - 1;
    /**
     * Loop process render list approval
     */
    let index = 0;
    for (let i = 0; i < lisApproval.length; i++) {
      /**
       * Count index
       */
      /**
       * Check if last index then set isLastIndex=true
       */
      if (lastIndex == i) {
        isLastIndex = true;
        lisApprovalRender.push(
          this.handleProcessRow(
            index + recordsFrom,
            rowTotal,
            munberDate,
            isLastIndex,
            year,
            month
          )
        );
      } else {
        // Init current project row
        if (i == 0) {
          index = 0;
        } else {
          index += lisApproval[i - 1].dataResult.length;
        }
        isLastIndex = false;
        lisApprovalRender.push(
          this.handleProcessRow(
            index + recordsFrom,
            lisApproval[i],
            munberDate,
            isLastIndex,
            year,
            month
          )
        );
      }
    }

    return lisApprovalRender;
  };
  /**
   * Handle get Max page
   * @param totalRecords
   * @param recordsPerPage
   */
  handleGetMaxPage = (totalRecords, recordsPerPage) => {
    let maxPage = "";
    maxPage =
      Math.floor(totalRecords / recordsPerPage) +
      (totalRecords % recordsPerPage !== 0 ? 1 : 0);
    return maxPage;
  };
  /**
   * Handle get status name
   * @param maxDisplayPage
   * @param currentPage
   * @param maxPage
   */
  paginationRequest = (maxDisplayPage, currentPage, maxPage) => {
    var offset = Math.floor(maxDisplayPage / 2);

    var start, end;

    /**
     * Funtion compare show page , maxPage
     */
    if (currentPage + offset > maxPage) {
      start = Math.max(0, maxPage - maxDisplayPage);
      end = maxPage;
    } else if (currentPage + offset == maxPage) {
      start = Math.max(0, maxPage - maxDisplayPage);
      end = maxPage;
    } else {
      start = Math.max(0, currentPage - offset);
      end = Math.min(start + maxDisplayPage, maxPage);
    }

    let setActivePage = "";
    let maxPageRender = [];

    /**
     * Check if have not data then page default is 1
     */
    if (+start == 0 && +end == 0) {
      maxPageRender.push(
        <li className={"active-page" + " page-item"} key={"page"}>
          <a className="page-link">1</a>
        </li>
      );
    } else {
      /**
       * Loop to render page
       */
      for (let i = start; i < end; i++) {
        maxPageRender.push(
          <Pagination.Item
            onClick={e => this.changePage(i + 1)}
            key={i + 1}
            active={i + 1 === currentPage}
            className={setActivePage + " page-item "}
          >
            <span className={setActivePage + " page-item"}>{i + 1}</span>
          </Pagination.Item>
        );
      }
    }
    return maxPageRender;
  };
  /**
   * Check date and convert date
   */
  checkDateSearch = dateSearch => {
    if (dateSearch == null || dateSearch === "") {
      return "";
    } else {
      let date = moment(dateSearch).format(dateTimePattern.YYYYMM);
      return date;
    }
  };
  /**
   * Handle when change Page
   * @param current
   */
  changePage = current => {
    if (this.checkValidate()) {
      this.setState({ currentPage: current });
      const {
        startDate,
        projectNameChange,
        projectNameOld,
        isSearch
      } = this.state;
      let projectName = "";
      const { dateOl } = this.props;
      let date = "";
      /**
       * Check if action is search then set state date = date choose else date= date before choose
       */
      if (isSearch) {
        date = startDate;
        projectName = projectNameChange;
      } else {
        date = dateOl;
        projectName = projectNameOld;
      }
      let dateRequestFormat = this.checkDateSearch(date);
      this.props.onHandle(dateRequestFormat, projectName, current);
    }
  };

  onChangeClickPage = flagPage => {
    const { currentPage, recordsPerPage } = this.state;
    let totalRecords = this.props.listApproval.pageDto.totalRecord;
    let maxPage = this.handleGetMaxPage(totalRecords, recordsPerPage);
    switch (flagPage) {
      case "first":
        if (this.checkValidate()) {
          this.setState(
            {
              currentPage: 1
            },
            () => {
              this.changePage(1);
            }
          );
        }
        break;

      case "previous":
        if (this.checkValidate() && currentPage > 1) {
          // Set currentPage = currentPage - 1
          this.setState(
            {
              currentPage: currentPage - 1
            },
            () => {
              this.changePage(currentPage - 1);
            }
          );
        }
        break;
      case "next":
        if (this.checkValidate() && currentPage < maxPage) {
          // Set currentPage = currentPage + 1
          this.setState(
            {
              currentPage: currentPage + 1
            },
            () => {
              this.changePage(currentPage + 1);
            }
          );
        }
        break;
      case "last":
        if (this.checkValidate()) {
          // Set currentPage = maxRecord
          this.setState(
            {
              currentPage: maxPage
            },
            () => {
              this.changePage(maxPage);
            }
          );
        }
        break;
      default:
        break;
    }
  };

  render() {
    const {
      dayOfMonth,
      startDate,
      currentPage,
      recordsPerPage,
      isSearch,
      error
    } = this.state;
    const { listApproval, dateOl, userStore, requestStore } = this.props;
    let listProjectName = [];
    let listDateRender = [];
    let recordsTo = "";
    let recordsFrom = "";
    let maxPage = 0;
    let setclassLimitPageNext = "";
    let setclassLimitPagePre = "";
    let maxDisplayPage = page.MAX_DISPLAY_PAGE;
    let munberDate = "";
    let maxPageRender = [];
    let lisApprovalRender = [];
    let totalRecords = "";
    let date = "";
    let dataLenght = false;
    const { roleId } = userStore.data.resultData;
    if (requestStore.projectNameList.resultData) {
      requestStore.projectNameList.resultData.forEach(project => {
        project = commonUtil.unescapeHtmlObject(project);
      });

      listProjectName.push({ projectName: constant.VALUE_ALL });
      requestStore.projectNameList.resultData.forEach(project => {
        let projectObject = {};
        projectObject.projectCode = project.projectCode;
        projectObject.projectName = project.projectName;
        projectObject.projectManagementCode = project.projectManagementCode;
        projectObject.projectManagementName = project.projectManagementName;
        listProjectName.push(projectObject);
      });
    }
    /**
     * Check if click handle search then render data by condition
     */
    if (isSearch) {
      date = startDate;
    } else {
      if (dateOl) {
        date = dateOl;
      }
    }
    let year = moment(date).format("YYYY");
    let monthOfYear = moment(date).format("MM");
    let dayOfMonthOfYear = this.handleGetNumberDate(year, monthOfYear);
    /**
     * Check if click handle search then render data by condition
     */
    if (isSearch) {
      if (dayOfMonth !== "") {
        munberDate = dayOfMonth;
      }
    } else {
      munberDate = dayOfMonthOfYear;
    }

    /**
     * Lop to render date
     */
    for (let i = 1; i <= munberDate; i++) {
      listDateRender.push(
        <th className="text-center align-middle" key={i}>
          {i}
        </th>
      );
    }

    /**
     * Check data exist data of page
     */
    if (listApproval) {
      totalRecords = listApproval.pageDto.totalRecord;
      recordsTo = currentPage * recordsPerPage;
      recordsFrom = totalRecords > 0 ? recordsTo - recordsPerPage + 1 : 0;
      maxPage = this.handleGetMaxPage(totalRecords, recordsPerPage);
      /**
       *Check page show > max page can show
       */
      if (maxDisplayPage > maxPage) {
        maxDisplayPage = maxPage;
      }
      /**
       * Check recordTo < total records
       */
      if (recordsTo > totalRecords) {
        recordsTo = totalRecords;
      }
      /**
       * Check currentPage >= maxPage then set css
       */
      if (currentPage >= maxPage) {
        setclassLimitPageNext = "pointer-events";
      }
      if (currentPage <= 1) {
        setclassLimitPagePre = "pointer-events";
      }
      maxPageRender = this.paginationRequest(
        maxDisplayPage,
        currentPage,
        maxPage
      );
    }

    /**
     * Check exist list approval
     */
    if (listApproval) {
      if (listApproval.dataResult.length <= 0) {
        dataLenght = true;
      }
      lisApprovalRender = this.handleRenderListApproval(
        listApproval.dataResult,
        munberDate,
        year,
        monthOfYear,
        recordsFrom
      );
    } else {
      dataLenght = true;
    }

    let tableResponsive = dataLenght
      ? "table table-bordered table-hover table-fixed table-responsive"
      : "table table-bordered table-hover table-fixed table-responsive";
    return (
      <div className="container-fluid mt-4 fix-padding-bottom">
        <h2 className="text-center my-4">
          <b className="font-weigth">DANH SÁCH APPROVAL OT</b>
        </h2>
        <div id="accordionExample">
          <div className="card">
            <div
              className="card-header"
              id="headingOne"
              onClick={this.showIcon}
            >
              <div
                data-toggle="collapse"
                data-target="#collapseOne"
                className="d-flex justify-content-between"
              >
                <span></span>
                {this.state.isShowIcon ? (
                  <i className="fa fa-chevron-down"></i>
                ) : (
                  <i className="fa fa-chevron-up"></i>
                )}
              </div>
            </div>

            <div
              id="collapseOne"
              className="collapse show"
              aria-labelledby="headingOne"
              data-parent="#accordionExample"
            >
              <div className="card-body">
                <form>
                  <div className="form-row">
                    <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label lable-front">
                          Dự Án<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <Select
                            className="react-selectcomponent form-custom"
                            options={listProjectName}
                            getOptionLabel={option => `${option.projectName}`}
                            getOptionValue={option => `${option.projectName}`}
                            onChange={this.onHandleChangeProject}
                            isSearchable={true}
                            value={
                              this.state.projectNameChange === ""
                                ? listProjectName.filter(
                                    option =>
                                      option.projectName === constant.VALUE_ALL
                                  )
                                : listProjectName.filter(
                                    option =>
                                      option.projectName ===
                                      this.state.projectNameChange
                                  )
                            }
                          />
                        </div>
                      </div>
                    </div>
                    <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label lable-front">
                          Tháng<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8 ">
                          <DatePicker
                            className="form-control combobox reset-date-picker"
                            selected={this.state.startDate}
                            onChange={this.handleChange}
                            placeholderText="YYYY-MM"
                            peekNextMonth
                            showMonthYearPicker
                            dateFormat="yyyy-MM"
                            dropdownMode="select"
                          />
                        </div>
                      </div>
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label"></label>
                        <div className="col-sm-8 ">
                          {error && (
                            <ErrorComponent error={message.MSG_ERROR_001} />
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="text-center">
                    <button
                      className="btn btn-primary btn-w-13"
                      type="button"
                      onClick={this.onClickSearch}
                    >
                      <b>{buttonType.BUTTON_SEARCH}</b>
                    </button>
                    {(+role.ROLE_ADMIN === +roleId ||
                      +role.ROLE_QA === +roleId) && (
                      <button
                        className="btn btn-success btn-w-13"
                        type="button"
                        onClick={this.handleExprotData}
                      >
                        <b>{buttonType.BUTTON_EXPORT}</b>
                      </button>
                    )}
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>

        <div className="mt-4">
          <div className="d-flex justify-content-between  mb-1">
            <div className="pt-1">
              <span>
                {recordsFrom} ~ {recordsTo} record of {totalRecords} records
              </span>
            </div>
            <div className="pt-1">
              <nav>
                <ul className="pagination reset-margin">
                  <li
                    className={"page-item " + setclassLimitPagePre}
                    onClick={() => this.onChangeClickPage("first")}
                  >
                    <a className="page-link font-weigth">First</a>
                  </li>
                  <li
                    className={"page-item " + setclassLimitPagePre}
                    onClick={() => this.onChangeClickPage("previous")}
                  >
                    <a className="page-link font-weigth">Previous</a>
                  </li>
                  {maxPageRender}
                  <li className={"page-item " + setclassLimitPageNext}>
                    <a
                      className="page-link font-weigth"
                      onClick={() => this.onChangeClickPage("next")}
                    >
                      Next
                    </a>
                  </li>
                  <li
                    className={"page-item " + setclassLimitPageNext}
                    onClick={() => this.onChangeClickPage("last")}
                  >
                    <a className="page-link font-weigth">Last</a>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
          <table className={tableResponsive}>
            <thead className="thead-dark">
              <tr className="text-center">
                <th
                  rowSpan="2"
                  width="3%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>No</b>
                </th>
                <th
                  rowSpan="2"
                  width="5%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Mã NV</b>
                </th>
                <th
                  rowSpan="2"
                  width="5%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Họ và Tên</b>
                </th>
                <th
                  rowSpan="2"
                  width="7%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Vai Trò</b>
                </th>
                <th
                  rowSpan="2"
                  width="6%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Dự Án</b>
                </th>
                <th
                  colSpan={munberDate}
                  width=""
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Thời Gian OT</b>
                </th>
                <th
                  rowSpan="2"
                  width="7%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Total Request OT</b>
                </th>
                <th
                  rowSpan="2"
                  width="8%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Total Approval OT</b>
                </th>
              </tr>
              <tr className="font-weight text-center">{listDateRender}</tr>
            </thead>
            <tbody>{!dataLenght && lisApprovalRender}</tbody>
          </table>

          <div className="text-center">
            {dataLenght && <ErrorComponent error={message.MSG_INFO_005} />}
          </div>
        </div>
        <div className="d-flex justify-content-between">
          <div className="pt-1">
            <span></span>
          </div>
          <div className="pt-1">
            <nav>
              <ul className="pagination reset-margin">
                <li
                  className={"page-item " + setclassLimitPagePre}
                  onClick={() => this.onChangeClickPage("first")}
                >
                  <a className="page-link font-weigth">First</a>
                </li>
                <li
                  className={"page-item " + setclassLimitPagePre}
                  onClick={() => this.onChangeClickPage("previous")}
                >
                  <a className="page-link font-weigth">Previous</a>
                </li>
                {maxPageRender}
                <li className={"page-item " + setclassLimitPageNext}>
                  <a
                    className="page-link font-weigth"
                    onClick={() => this.onChangeClickPage("next")}
                  >
                    Next
                  </a>
                </li>
                <li
                  className={"page-item " + setclassLimitPageNext}
                  onClick={() => this.onChangeClickPage("last")}
                >
                  <a className="page-link font-weigth">Last</a>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    );
  }
}

export default ListApprovalComponent;
