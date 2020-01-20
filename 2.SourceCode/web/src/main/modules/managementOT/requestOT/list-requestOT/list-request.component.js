import React, { Component } from "react";
import DatePicker from "react-datepicker";
import moment from "moment";
import "react-datepicker/dist/react-datepicker.css";
import "./list-request.css";
import page from "../../../../../shared/constants/page.constant";
import { dateTimePattern } from "../../../../../shared/constants/date-time-pattern.constant";
import ErrorComponent from "../../../../../shared/common/component/error.component";
import { message } from "../../../../../shared/constants/message.constant";
import { Link } from "react-router-dom";
import validateUtil from "../../../../../shared/utils/validate.util";
import { dateTime } from "../../../../../shared/constants/date-time.constant";
import AlertComponent from "../../../../../shared/common/component/alert.component";
import commonUtil from "../../../../../shared/utils/common.util";
import Pagination from "react-bootstrap/Pagination";
import { buttonType } from "../../../../../shared/constants/button-type.constant";
import Select from "react-select";
import { statusOT } from "../../../../../shared/constants/status-ot.constant";
import constant from "../../../../../shared/constants/page.constant";
import { storage } from "../../../../../shared/constants/storage.constant";
/**
 * List request component
 */
class ListRequestComponent extends Component {
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
      statusChange: "",
      projectNameChange: "",
      currentPage: 1,
      recordsPerPage: page.RECORD_PER_PAGE_5,
      status: statusOT,
      isSearch: false,
      statusOld: "",
      projectNameOld: "",
      dateOld: new Date(),
      error: {
        date: false
      }
    };
  }

  // Set state of request by querysearch in session storage
  componentWillReceiveProps = () => {
    const { querySearchStorage } = this.props;
    if (querySearchStorage) {
      this.setState({
        ...this.state,
        startDate: querySearchStorage.requestOtDate
          ? new Date(
              moment(querySearchStorage.requestOtDate).format(
                dateTimePattern.YYYYMMDD
              )
            )
          : "",
        statusChange: querySearchStorage.status,
        projectNameChange: querySearchStorage.projectName,
        currentPage: querySearchStorage.currentPage,
        recordsPerPage: querySearchStorage.totalRecords
      });
    }
  };

  showIcon = () => {
    this.setState({
      isShowIcon: !this.state.isShowIcon
    });
  };
  /**
   * Handle when choose date
   * @param date
   */
  handleChange = date => {
    /**
     * Check date is null or empty
     * If date is empty, null then search all by date
     */
    if (date == null || date === "") {
      this.setState({
        startDate: "",
        isSearch: false,
        error: { date: false }
      });
    } else {
      let year = moment(date).format(dateTimePattern.YYYY);
      /**
       * Compare min year, max year.
       * If return false then show error
       */
      if (validateUtil.inValidYear(dateTime.MINYEAR, dateTime.MAXYEAR, year)) {
        this.setState({
          startDate: date,
          isSearch: false,
          error: { date: false }
        });
      } else {
        this.setState({ error: { date: true } });
      }
    }
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
   * Handle when choose status
   *
   * @param e
   */
  onHandleChangeStatus = object => {
    this.setState({
      statusChange: object.statusCode,
      isSearch: false
    });
  };
  /**
   * Handle when click button search
   */
  onClickSearch = () => {
    const { startDate, statusChange, projectNameChange } = this.state;
    this.setState({
      currentPage: 1,
      isSearch: true,
      statusOld: statusChange,
      projectNameOld: projectNameChange,
      dateOld: startDate
    });
    let dateRequestFormat = this.checkDateSearch(startDate);
    const querySearch = {
      currentPage: 1,
      totalRecords: page.RECORD_PER_PAGE_5,
      projectName: projectNameChange,
      status: statusChange,
      requestOtDate: dateRequestFormat
    };
    // Register condition search request into session storage
    sessionStorage.setItem(storage.SEARCH_REQUEST, JSON.stringify(querySearch));
    this.props.onHandle(dateRequestFormat, statusChange, projectNameChange, 1);
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
    this.setState({ currentPage: current });
    const {
      startDate,
      statusChange,
      projectNameChange,
      isSearch,
      statusOld,
      dateOld,
      projectNameOld
    } = this.state;
    let status = "";
    let projectName = "";
    let date = "";
    this.checkDateSearch(startDate);
    /**
     * If is search then set date = date choose else date choose befor
     */
    // if (isSearch) {
    status = statusChange;
    projectName = projectNameChange;
    date = this.checkDateSearch(startDate);
    // } else {
    //   status = statusOld;
    //   projectName = projectNameOld;
    //   date = this.checkDateSearch(dateOld);
    // }
    const querySearch = {
      currentPage: current,
      totalRecords: page.RECORD_PER_PAGE_5,
      projectName: projectName,
      status: status,
      requestOtDate: date
    };
    // Register condition search request into session storage
    sessionStorage.setItem(storage.SEARCH_REQUEST, JSON.stringify(querySearch));
    this.props.onHandle(date, status, projectName, current);
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

  onChangeClickPage = flagPage => {
    const { currentPage, recordsPerPage } = this.state;
    let totalRecords = this.props.listRequestOT.pageDto.totalRecord;
    let maxPage = this.handleGetMaxPage(totalRecords, recordsPerPage);
    switch (flagPage) {
      case "first":
        this.setState(
          {
            currentPage: 1
          },
          () => {
            this.changePageWhenClick(1);
          }
        );
        break;

      case "previous":
        if (currentPage > 1) {
          // Set currentPage = currentPage - 1
          this.setState(
            {
              currentPage: currentPage - 1
            },
            () => {
              this.changePageWhenClick(currentPage - 1);
            }
          );
        }
        break;
      case "next":
        if (currentPage < maxPage) {
          // Set currentPage = currentPage + 1
          this.setState(
            {
              currentPage: currentPage + 1
            },
            () => {
              this.changePageWhenClick(currentPage + 1);
            }
          );
        }
        break;
      case "last":
        // Set currentPage = maxRecord
        this.setState(
          {
            currentPage: maxPage
          },
          () => {
            this.changePageWhenClick(maxPage);
          }
        );
        break;
      default:
        break;
    }
  };

  /**
   * Handle when click page
   * @param currentPage
   */
  changePageWhenClick = currentPage => {
    const {
      startDate,
      statusChange,
      projectNameChange,
      isSearch,
      statusOld,
      dateOld,
      projectNameOld
    } = this.state;
    let status = "";
    let projectName = "";
    let date = "";
    // if (isSearch) {
    status = statusChange;
    projectName = projectNameChange;
    date = this.checkDateSearch(startDate);
    // } else {
    //   status = statusOld;
    //   projectName = projectNameOld;
    //   date = this.checkDateSearch(dateOld);
    // }
    const querySearch = {
      currentPage: currentPage,
      totalRecords: page.RECORD_PER_PAGE_5,
      projectName: projectName,
      status: status,
      requestOtDate: date
    };

    // Register condition search request into session storage
    sessionStorage.setItem(storage.SEARCH_REQUEST, JSON.stringify(querySearch));
    this.props.onHandle(date, status, projectName, currentPage);
  };
  /**
   * Handle get status name
   * @param statusNumber
   */
  getStatusName = statusCode => {
    const { status } = this.state;
    const sttResult = status.filter(stt => +stt.statusCode === +statusCode);
    return sttResult.length > 0 ? sttResult[0].statusName : "";
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
     * Check if have not data then set page default is 1
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
   * Handle merger by project name
   * @param dataResult
   */
  handleMergerByProjectName = dataResult => {
    let listDataresultTemp = [];
    dataResult.map(item => {
      if (listDataresultTemp.length > 0) {
        let isExist = false;
        for (let i = 0; i < listDataresultTemp.length; i++) {
          let dataTemp = listDataresultTemp[i];
          if (dataTemp.projectName === item.projectName) {
            listDataresultTemp[i].dataResult = [
              ...listDataresultTemp[i].dataResult,
              item
            ];
            isExist = true;
          }
        }
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
    return listDataresultTemp;
  };
  /**
   * Handle render list request
   * @param dataResult
   */
  handleRenderListRequest = (data, rowSpan, first, key) => {
    let listInfoRequest = [];
    let statusName = this.getStatusName(data.status);
    const dateRequestFormat = moment(data.createAt).format("YYYY-MM-DD");
    const dateOT = moment(data.dateOt).format("YYYY-MM");
    const startDate = moment(this.state.startDate).format(
      dateTimePattern.YYYYMM
    );

    /**
     * Check if first =0 then set row span
     */
    if (first == 0) {
      listInfoRequest.push(
        <tr key={key}>
          <td className="text-center align-middle">{key}</td>
          <td rowSpan={rowSpan} className="text-center align-middle">
            {commonUtil.escapeHTML(data.projectName)}
          </td>
          <td rowSpan={rowSpan} className="align-middle">
            {commonUtil.escapeHTML(data.projectManagement)}
          </td>
          <td className="align-middle">{data.employeeName}</td>
          <td className="text-center align-middle">{dateOT}</td>
          <td className="text-center align-middle">{dateRequestFormat}</td>
          <td className="text-center align-middle">{data.totalRequest}</td>
          <td className="text-center align-middle">{data.totalApproval}</td>
          <td className="text-center align-middle">{statusName}</td>
          <td className="text-center align-middle">
            <Link
              className=""
              to={{
                pathname: "/request/edit/" + data.requestId,
                query: { startDate }
              }}
            >
              <button className="btn-primary btn-edit-style">
                <i className="fa fa-fw fa-edit fa-color" />
              </button>
            </Link>
          </td>
        </tr>
      );
    } else {
      listInfoRequest.push(
        <tr key={key}>
          <td className="text-center align-middle">{key}</td>
          <td className="align-middle">{data.employeeName}</td>
          <td className="text-center align-middle">{dateOT}</td>
          <td className="text-center align-middle">{dateRequestFormat}</td>
          <td className="text-center align-middle">{data.totalRequest}</td>
          <td className="text-center align-middle">{data.totalApproval}</td>
          <td className="text-center align-middle">{statusName}</td>
          <td className="text-center align-middle">
            <Link
              className=""
              to={{
                pathname: "/request/edit/" + data.requestId,
                query: { startDate }
              }}
            >
              <button className="btn-primary btn-edit-style">
                <i className="fa fa-fw fa-edit fa-color" />
              </button>
            </Link>
          </td>
        </tr>
      );
    }
    return listInfoRequest;
  };

  /**
   * Render
   */
  render() {
    const { currentPage, status, recordsPerPage } = this.state;
    let recordsTo = "";
    let recordsFrom = "";
    let listProjectName = [];
    let maxPage = 0;
    let maxPageRender = [];
    const { listRequestOT, messageStore, requestStore, userStore } = this.props;
    let dataResult = [];
    let maxDisplayPage = page.MAX_DISPLAY_PAGE;
    let setclassLimitPageNext = "";
    let setclassLimitPagePre = "";
    let totalRecords = "";
    let listTmpRender = [];

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
     * Check data exist data of page
     */
    if (listRequestOT.pageDto) {
      totalRecords = listRequestOT.pageDto.totalRecord;
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
    }
    /**
     * Call funtion pagination to get list request
     * @param maxDisplayPage
     * @param currentPage
     * @param maxPage
     */
    maxPageRender = this.paginationRequest(
      maxDisplayPage,
      currentPage,
      maxPage
    );

    /**
     * Check data exist data of list request
     */
    if (listRequestOT.dataResult) {
      dataResult = listRequestOT.dataResult;
      let listRender = this.handleMergerByProjectName(dataResult);
      let count = 0;
      /**
       * Loop to get list value by each project Name
       */
      for (let i = 0; i < listRender.length; i++) {
        let listTmp = listRender[i].dataResult;
        let countLength = listTmp.length;
        let rowSpan = countLength;
        /**
         * Loop to push value
         */
        for (let j = 0; j < countLength; j++) {
          let first = j;
          listTmpRender.push(
            this.handleRenderListRequest(
              listTmp[j],
              rowSpan,
              first,
              recordsFrom + count
            )
          );
          count++;
        }
      }
    }

    let showMessage = false;
    if (
      messageStore.message !== null &&
      messageStore.type !== null &&
      messageStore.message !== message.MSG_INFO_002
    ) {
      showMessage = true;
    }
    return (
      <div className="container-fluid mt-4 fix-padding-bottom">
        <h2 className="text-center my-4">
          <b className="font-weigth">DANH SÁCH ĐĂNG KÝ OT</b>
        </h2>
        <div className="row">
          <div className="col-12  fix-padding">
            {showMessage && <AlertComponent messageObj={messageStore} />}
          </div>
        </div>
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
                        <label className="col-sm-4 col-form-label lable-front">
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
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label lable-front">
                          Tháng OT<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
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
                        <div className="col-sm-8">
                          {this.state.error.date && (
                            <ErrorComponent error={message.MSG_ERROR_014} />
                          )}
                        </div>
                      </div>
                    </div>
                    <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label lable-front">
                          Status<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <Select
                            className="react-selectcomponent form-custom"
                            options={statusOT}
                            getOptionLabel={option => `${option.statusName}`}
                            getOptionValue={option => `${option.statusName}`}
                            onChange={this.onHandleChangeStatus}
                            isSearchable={true}
                            name="status"
                            value={
                              this.state.statusChange === ""
                                ? statusOT.filter(
                                    option => option.statusCode === ""
                                  )
                                : statusOT.filter(
                                    option =>
                                      option.statusCode ===
                                      this.state.statusChange
                                  )
                            }
                          />
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
          <table className="table table-bordered table-hover">
            <thead className="thead-dark">
              <tr className="text-center">
                <th width="5%" scope="col" className="text-center align-middle">
                  <b>No</b>
                </th>
                <th
                  width="15%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Dự Án</b>
                </th>
                <th
                  width="20%"
                  scope="col"
                  className="text-center  align-middle"
                >
                  <b>PM</b>
                </th>
                <th
                  width="20%"
                  scope="col"
                  className="text-center  align-middle"
                >
                  <b>Người Gửi</b>
                </th>
                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Tháng OT</b>
                </th>
                <th
                  width="13%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Ngày Gửi</b>
                </th>
                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Total Request</b>
                </th>
                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Total Approval</b>
                </th>
                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Status</b>
                </th>
                <th
                  width="5%"
                  scope="col"
                  className="text-center align-middle"
                ></th>
              </tr>
            </thead>
            <tbody>{listTmpRender}</tbody>
          </table>
          <div className="text-center">
            {dataResult.length <= 0 && (
              <ErrorComponent error={message.MSG_INFO_005} />
            )}
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

export default ListRequestComponent;
