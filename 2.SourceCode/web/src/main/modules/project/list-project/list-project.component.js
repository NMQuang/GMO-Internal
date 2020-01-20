import React, { Component } from "react";
import "./list-project.css";
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
import Select from "react-select";
import constant from "../../../../shared/constants/page.constant";

class ListProjectComponent extends Component {
  /**
   * Constructor
   *
   * @param props
   */
  constructor(props) {
    super(props);
    this.state = {
      isShowIcon: true,
      querySearch: {
        projectName: "",
        startDate: "",
        endDate: "",
        page: 1,
        totalRecordOfPage: page.RECORD_PER_PAGE_5
      },
      projectList: []
    };
  }

  // Set state of project by querysearch in session storage
  componentWillReceiveProps = () => {
    const { querySearchStorage } = this.props;
    if (querySearchStorage) {
      this.setState({
        ...this.state,
        querySearch: {
          projectName: querySearchStorage.projectName,
          startDate: querySearchStorage.startDate
            ? new Date(
                moment(querySearchStorage.startDate).format(
                  dateTimePattern.YYYYMMDD
                )
              )
            : "",
          endDate: querySearchStorage.endDate
            ? new Date(
                moment(querySearchStorage.endDate).format(
                  dateTimePattern.YYYYMMDD
                )
              )
            : "",
          page: querySearchStorage.page,
          totalRecordOfPage: querySearchStorage.totalRecordOfPage
        }
      });
    }
  };

  showIcon = () => {
    this.setState({
      isShowIcon: !this.state.isShowIcon
    });
  };

  onChangePage = currentPage => {
    this.setState(
      {
        ...this.state,
        querySearch: {
          ...this.state.querySearch,
          page: currentPage
        }
      },
      () => {
        this.onClickSearch(currentPage);
      }
    );
  };

  onHandleChangeDate = (dateName, date) => {
    /**
     * Check date is null or empty
     * If date is empty, null then search all by date
     */
    if (date == null || date === "") {
      this.setState({
        querySearch: {
          ...this.state.querySearch,
          [dateName]: ""
        }
      });
    } else {
      this.setState({
        querySearch: {
          ...this.state.querySearch,
          [dateName]: new Date(moment(date).format(dateTimePattern.YYYYMMDD))
        }
      });
    }
  };

  onHandleChangeProject = object => {
    this.setState({
      querySearch: {
        ...this.state.querySearch,
        projectName:
          object.projectName === constant.VALUE_ALL ? "" : object.projectName
      }
    });
  };

  /**
   * Handle get status name
   * @param maxDisplayPage
   * @param currentPage
   * @param maxPage
   */
  paginationRequest = (maxDisplayPage, currentPage, maxPage) => {
    var offset = Math.floor(maxDisplayPage / 2);
    var start = 0,
      end = 0;
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
    let maxPageRender = [];

    /**
     * Check if have not data then set page default is 1
     */
    if (+start == 0 && +end == 0) {
      maxPageRender.push(
        <li className={"active-page" + " page-item"} key="1">
          <a className="page-link">1</a>
        </li>
      );
    } else {
      /**
       * Loop to render page
       */
      for (let i = start; i < end; i++) {
        if (+currentPage === i + 1) {
          maxPageRender.push(
            <li
              className={"active-page" + " page-item"}
              key={i + 1}
              onClick={e => this.onChangePage(i + 1)}
            >
              <a className="page-link">{i + 1}</a>
            </li>
          );
        } else {
          maxPageRender.push(
            <li
              className=" page-item"
              key={i + 1}
              onClick={e => this.onChangePage(i + 1)}
            >
              <a className="page-link">{i + 1}</a>
            </li>
          );
        }
      }
    }
    return maxPageRender;
  };

  /**
   * Handle get Max page
   * @param totalRecords
   * @param recordsPerPage
   */
  handleGetMaxPage = (totalRecords, recordsPerPage) => {
    let maxPage = 0;
    maxPage =
      Math.floor(totalRecords / recordsPerPage) +
      (totalRecords % recordsPerPage !== 0 ? 1 : 0);
    return maxPage;
  };

  onChangeClickPage = flagPage => {
    const { page, totalRecordOfPage } = this.state.querySearch;
    const {
      totalRecord
    } = this.props.projectStore.projectList.resultData.pageDto;
    const maxPage = this.handleGetMaxPage(totalRecord, totalRecordOfPage);
    switch (flagPage) {
      case "first":
        // Set currentPage = 1
        this.setState(
          {
            ...this.state,
            querySearch: {
              ...this.state.querySearch,
              page: 1
            }
          },
          () => this.onClickSearch(this.state.querySearch.page)
        );

        break;

      case "previous":
        if (page > 1) {
          // Set currentPage = currentPage - 1
          this.setState(
            {
              ...this.state,
              querySearch: {
                ...this.state.querySearch,
                page: page - 1
              }
            },
            () => this.onClickSearch(this.state.querySearch.page)
          );
        }
        break;
      case "next":
        if (page < maxPage) {
          // Set currentPage = currentPage + 1
          this.setState(
            {
              ...this.state,
              querySearch: {
                ...this.state.querySearch,
                page: page + 1
              }
            },
            () => this.onClickSearch(this.state.querySearch.page)
          );
        }
        break;
      case "last":
        // Set currentPage = maxRecord
        this.setState(
          {
            ...this.state,
            querySearch: {
              ...this.state.querySearch,
              page: maxPage
            }
          },
          () => this.onClickSearch(this.state.querySearch.page)
        );
        break;
      default:
        break;
    }
  };

  /**
   * Handle when click button search
   */
  onClickSearch = page => {
    const { querySearch } = this.state;

    const projectReq = {
      ...this.state.querySearch,
      startDate: querySearch.startDate
        ? moment(
            moment(querySearch.startDate, dateTimePattern.YYYYMMDD).toDate()
          ).format(dateTimePattern.YYYYMMDD)
        : "",
      endDate: querySearch.endDate
        ? moment(
            moment(querySearch.endDate, dateTimePattern.YYYYMMDD).toDate()
          ).format(dateTimePattern.YYYYMMDD)
        : "",
      page: page ? page : 1,
      totalRecordOfPage: querySearch.totalRecordOfPage
    };
    this.props.onClickSearch(projectReq);
  };

  render() {
    const {
      projectStore,
      messageStore,
      userStore,
      querySearchStorage
    } = this.props;
    const { querySearch } = this.state;
    let projectList = [];
    let projectDetailList = [];
    // Pagination
    let recordsTo = 0;
    let recordsFrom = 0;
    let totalRecords = 0;
    let maxPage = 0;
    let maxDisplayPage = page.MAX_DISPLAY_PAGE;
    let setclassLimitPageNext = "";
    let setclassLimitPagePre = "";
    let maxPageRender = [];
    let pageDto = {};
    if (projectStore.projectList != null) {
      // Get list project in combobox
      projectList.push({ projectName: constant.VALUE_ALL });
      projectStore.projectList.resultData.dataResult.projectList.forEach(
        project => {
          project = commonUtil.unescapeHtmlObject(project);

          // custom project name
          if (project.projectNameVN !== "" && project.projectNameJP === "") {
            projectList.push({ projectName: project.projectNameVN });
          } else if (
            project.projectNameVN === "" &&
            project.projectNameJP !== ""
          ) {
            projectList.push({ projectName: project.projectNameJP });
          } else {
            projectList.push({ projectName: project.projectNameVN });
          }
        }
      );

      // Get detail project
      projectStore.projectList.resultData.dataResult.projectDetailList.forEach(
        projectDetail => {
          projectDetail = commonUtil.unescapeHtmlObject(projectDetail);
        }
      );
      projectDetailList =
        projectStore.projectList.resultData.dataResult.projectDetailList;
      // Get page
      pageDto = { ...projectStore.projectList.resultData.pageDto };
      totalRecords = pageDto.totalRecord;
      recordsTo = pageDto.currentPage * pageDto.totalRecordAtPage;
      recordsFrom =
        totalRecords > 0 ? recordsTo - pageDto.totalRecordAtPage + 1 : 0;
      maxPage = this.handleGetMaxPage(totalRecords, pageDto.totalRecordAtPage);

      if (maxDisplayPage > maxPage) {
        maxDisplayPage = maxPage;
      }

      if (recordsTo > totalRecords) {
        recordsTo = totalRecords;
      }

      if (pageDto.currentPage >= maxPage) {
        setclassLimitPageNext = "pointer-events";
      }
      if (pageDto.currentPage <= 1) {
        setclassLimitPagePre = "pointer-events";
      }
      maxPageRender = this.paginationRequest(
        maxDisplayPage,
        pageDto.currentPage,
        maxPage
      );
    }
    let roleId = userStore.data.resultData.roleId;
    let showMessage = null;
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
          <b className="font-weigth">DANH SÁCH DỰ ÁN</b>
        </h2>

        <div className="row">
          <div className="col-12 fix-padding">
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
                            options={projectList}
                            getOptionLabel={option => `${option.projectName}`}
                            getOptionValue={option => `${option.projectName}`}
                            onChange={this.onHandleChangeProject}
                            isSearchable={true}
                            name="projectName"
                            value={
                              this.state.querySearch.projectName === ""
                                ? projectList.filter(
                                    option =>
                                      option.projectName === constant.VALUE_ALL
                                  )
                                : projectList.filter(
                                    option =>
                                      option.projectName ===
                                      this.state.querySearch.projectName
                                  )
                            }
                          />
                        </div>
                      </div>
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label lable-front">
                          Ngày Bắt Đầu<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <DatePicker
                            className="form-control combobox reset-date-picker"
                            selected={
                              querySearch.startDate ? querySearch.startDate : ""
                            }
                            placeholderText={dateTimePattern.YYYYMMDD}
                            peekNextMonth
                            showMonthDropdown
                            showYearDropdown
                            isClearable
                            dateFormat={dateTimePattern.yyyyMMdd}
                            dropdownMode="select"
                            onChange={value =>
                              this.onHandleChangeDate("startDate", value)
                            }
                          />
                        </div>
                      </div>
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label lable-front">
                          Ngày Kết Thúc<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <DatePicker
                            className="form-control combobox reset-date-picker"
                            selected={querySearch.endDate}
                            placeholderText={dateTimePattern.YYYYMMDD}
                            peekNextMonth
                            showMonthDropdown
                            showYearDropdown
                            isClearable
                            dateFormat={dateTimePattern.yyyyMMdd}
                            dropdownMode="select"
                            onChange={value =>
                              this.onHandleChangeDate("endDate", value)
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
                      onClick={() => this.onClickSearch(1)}
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
          <div className="d-flex justify-content-between mb-1">
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
                  className="text-center align-middle"
                >
                  <b>Họ và Tên</b>
                </th>
                <th
                  width="15%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Vai Trò Dự Án</b>
                </th>
                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Ngày Bắt Đầu</b>
                </th>
                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                >
                  <b>Ngày Kết Thúc</b>
                </th>

                <th
                  width="10%"
                  scope="col"
                  className="text-center align-middle"
                ></th>
              </tr>
            </thead>
            {projectDetailList.length > 0 && (
              <tbody>
                {projectDetailList.map((projectDetail, index) => {
                  return (
                    <tr key={index}>
                      <td className="text-center align-middle">
                        {(index = index + 1)}
                      </td>
                      <td className="text-center align-middle">
                        {projectDetail.projectName}
                      </td>
                      <td className="align-middle">
                        {projectDetail.memberName}
                      </td>
                      <td className="text-center align-middle">
                        {projectDetail.position}
                      </td>
                      <td className="text-center align-middle">
                        {projectDetail.startDate}
                      </td>
                      <td className="text-center align-middle">
                        {projectDetail.endDate === ""
                          ? "N/A"
                          : projectDetail.endDate}
                      </td>
                      <td className="text-center align-middle">
                        <Link
                          to={"/project/detail/" + projectDetail.projectCode}
                        >
                          <button
                            type="button"
                            className="btn btn-info btn-none-pm mr-3"
                            // onClick={() =>
                            //   this.onShowDetail(projectDetail.projectCode)
                            // }
                          >
                            <i className="fa fa-eye"></i>
                          </button>
                        </Link>
                        {+roleId === +role.ROLE_ADMIN ||
                        (+roleId === +role.ROLE_USER &&
                          userStore.data.resultData.projectInfo.filter(
                            projectRole =>
                              +projectRole.projectCode ===
                                +projectDetail.projectCode &&
                              +projectRole.positionCode ===
                                +position.POSITION_PM
                          ).length > 0) ? (
                          <Link
                            to={"/project/edit/" + projectDetail.projectCode}
                          >
                            <button
                              type="button"
                              className="btn btn-primary btn-none-pm mr-3"
                            >
                              <i className="fa fa-pencil-square-o"></i>
                            </button>
                          </Link>
                        ) : (
                          <React.Fragment></React.Fragment>
                        )}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            )}
            {/* <tbody>{listTmpRender}</tbody> */}
          </table>
          <div className="text-center">
            {projectDetailList.length <= 0 && (
              <ErrorComponent error={message.MSG_INFO_005} />
            )}
          </div>
          <div>
            <div className="d-flex justify-content-between">
              <div className="pt-1"></div>
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
        </div>
      </div>
    );
  }
}

export default ListProjectComponent;
