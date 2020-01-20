import React, { Component } from "react";
import DatePicker from "react-datepicker";
import { Link } from "react-router-dom";
import commonUtil from "../../../../shared/utils/common.util";
import { dateTimePattern } from "../../../../shared/constants/date-time-pattern.constant";
import moment from "moment";
import page from "../../../../shared/constants/page.constant";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { message } from "../../../../shared/constants/message.constant";
import Pagination from "react-bootstrap/Pagination";
import "./list-user.css";
import { Pageable } from "../../../../shared/models/pageable.model";
import { createFileExcel } from "../../../../shared/utils/download.util";
import { buttonType } from "../../../../shared/constants/button-type.constant";

class ListUserComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isShowIcon: true,
      querySearch: {
        employeeCode: "",
        employeeName: "",
        birthday: "",
        position: "",
        division: "",
        probationaryDay: "",
        officialDay: "",
        workContact: "",
        status: "",
        page: 1,
        totalRecordOfPage: page.RECORD_PER_PAGE_5,
        searchOrExportFlag: 1,
        headerOptionFlag: 0,
        headerList: []
      },
      checkAll: false,
      employeeList: []
    };
  }

  showIcon = () => {
    this.setState({
      isShowIcon: !this.state.isShowIcon
    });
  };

  /**
   * Handle when click button search
   */
  onClickSearch = () => {
    const { querySearch } = this.state;
    this.props.onClickSearch(querySearch);
  };

  onExport = () => {
    this.setState(
      {
        ...this.state,
        querySearch: {
          ...this.state.querySearch,
          searchOrExportFlag: 0,
          headerOptionFlag: 1
        }
      },
      () => {
        this.props.onExport(this.state.querySearch);
      }
    );
    const fileName = "GMO";
    if (this.props.exportStore != null) {
      createFileExcel(this.props.exportStore.resultData.employeeList, fileName);
    }
  };

  onChangePage = currentPage => {
    this.state.querySearch.page = currentPage;
    this.setState({
      ...this.state
    });
    this.onClickSearch();
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

  onHandleChange = e => {
    this.setState({
      querySearch: {
        ...this.state.querySearch,
        [e.target.name]: e.target.value
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
    const { totalRecord } = this.props.userStore.resultData.pageDto;
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
          () => this.onClickSearch()
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
            () => this.onClickSearch()
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
            () => this.onClickSearch()
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
          () => this.onClickSearch()
        );
        break;
      default:
        break;
    }
  };

  onHandleCheckbox = e => {};

  onHandleCheckAll = (data, e) => {
    data.forEach(employee => {
      employee.deleteFlag = e.target.checked;
    });
    this.setState({
      ...this.state,
      employeeList: data,
      checkAll: !this.state.checkAll
    });
  };

  onHandleCheckElement = (employeeCode, e) => {
    let { employeeList } = this.state;
    employeeList.forEach(employee => {
      if (employee.employeeCode === e.target.value) {
        employee.deleteFlag = e.target.checked;
      }
    });
    this.setState({ employeeList });
  };

  render() {
    const { userStore } = this.props;
    let divisionList = [];
    let positionList = [];
    let statusList = [];
    let typeContactList = [];
    let employeeListProps = [];
    let { employeeList } = this.state;
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
    if (userStore != null) {
      // Get page
      pageDto = { ...userStore.resultData.pageDto };
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
      // Load data division
      userStore.resultData.dataResult.divisionList.forEach(division => {
        division = commonUtil.unescapeHtmlObject(division);
      });
      divisionList = userStore.resultData.dataResult.divisionList.map(
        (division, index) => {
          return (
            <option value={division.id} key={index}>
              {division.divisionName}
            </option>
          );
        }
      );
      //Load data position
      userStore.resultData.dataResult.positionList.forEach(position => {
        position = commonUtil.unescapeHtmlObject(position);
      });
      positionList = userStore.resultData.dataResult.positionList.map(
        (position, index) => {
          return (
            <option value={position.id} key={index}>
              {position.positionName}
            </option>
          );
        }
      );
      //Load data status
      userStore.resultData.dataResult.statusList.forEach(status => {
        status = commonUtil.unescapeHtmlObject(status);
      });
      statusList = userStore.resultData.dataResult.statusList.map(
        (status, index) => {
          return (
            <option value={status.id} key={index}>
              {status.unitName}
            </option>
          );
        }
      );
      //Load data typeContact
      userStore.resultData.dataResult.typeContactList.forEach(typeContact => {
        typeContact = commonUtil.unescapeHtmlObject(typeContact);
      });
      typeContactList = userStore.resultData.dataResult.typeContactList.map(
        (typeContact, index) => {
          return (
            <option value={typeContact.id} key={index}>
              {typeContact.typeContactName}
            </option>
          );
        }
      );
      //Load data employeeList
      userStore.resultData.dataResult.employeeList.forEach(employee => {
        employee = commonUtil.unescapeHtmlObject(employee);
      });
      employeeListProps = userStore.resultData.dataResult.employeeList;
      employeeList =
        +employeeList.length === 0 ? employeeListProps : employeeList;
    }

    return (
      <div className="container-fluid mt-4 fix-padding-bottom">
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
                <span>Tìm Kiếm</span>
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
                        <label className="col-sm-4 col-form-label">
                          Mã Nhân Viên<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <input
                            type="text"
                            className="form-control combobox"
                            id="inputCode"
                            placeholder="Mã Nhân Viên"
                            name="employeeCode"
                            value={this.state.querySearch.employeeCode}
                            onChange={this.onHandleChange}
                          />
                        </div>
                      </div>
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label">
                          Họ và Tên<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8 ">
                          <input
                            type="text"
                            className="form-control combobox"
                            id="inputName"
                            placeholder="Họ và Tên"
                            name="employeeName"
                            value={this.state.querySearch.employeeName}
                            onChange={this.onHandleChange}
                          />
                        </div>
                      </div>
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label">
                          Ngày Sinh<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <DatePicker
                            className="form-control combobox reset-date-picker"
                            name="birthday"
                            selected={this.state.querySearch.birthday}
                            onChange={value =>
                              this.onHandleChangeDate("birthday", value)
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
                      </div>
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label">
                          Vị Trí Công Việc<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <select
                            id="inputState"
                            className="form-control combobox"
                            name="position"
                            value={this.state.querySearch.position}
                            onChange={this.onHandleChange}
                          >
                            <option value=""></option>
                            {positionList}
                          </select>
                        </div>
                      </div>
                      <div className="form-group row">
                        <label className="col-sm-4 col-form-label">
                          Tên Đơn Vị<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <select
                            id="inputState"
                            className="form-control combobox"
                            name="division"
                            value={this.state.querySearch.division}
                            onChange={this.onHandleChange}
                          >
                            <option value=""></option>
                            {divisionList}
                          </select>
                        </div>
                      </div>
                    </div>
                    <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                      <div className="form-group row d-flex justify-content-end">
                        <label className="col-sm-4 col-form-label">
                          Ngày Thử Việc<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <DatePicker
                            className="form-control combobox reset-date-picker"
                            name="probationaryDay"
                            selected={this.state.querySearch.probationaryDay}
                            onChange={value =>
                              this.onHandleChangeDate("probationaryDay", value)
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
                      </div>
                      <div className="form-group row d-flex justify-content-end">
                        <label className="col-sm-4 col-form-label">
                          Ngày Chính Thức<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <DatePicker
                            className="form-control combobox reset-date-picker"
                            name="officialDay"
                            selected={this.state.querySearch.officialDay}
                            onChange={value =>
                              this.onHandleChangeDate("officialDay", value)
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
                      </div>
                      <div className="form-group row d-flex justify-content-end">
                        <label className="col-sm-4 col-form-label">
                          Loại Hợp Đồng<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <select
                            id="inputState"
                            className="form-control combobox"
                            name="workContact"
                            selected={this.state.querySearch.workContact}
                            onChange={this.onHandleChange}
                          >
                            <option value=""></option>
                            {typeContactList}
                          </select>
                        </div>
                      </div>
                      <div className="form-group row d-flex justify-content-end">
                        <label className="col-sm-4 col-form-label">
                          Trạng Thái LĐ<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <select
                            id="inputState"
                            className="form-control combobox"
                            name="status"
                            selected={this.state.querySearch.status}
                            onChange={this.onHandleChange}
                          >
                            <option value=""></option>
                            {statusList}
                          </select>
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
                      {buttonType.BUTTON_SEARCH}
                    </button>
                    <button className="btn btn-info btn-w-13" type="submit">
                      {buttonType.BUTTON_CLEAR}
                    </button>
                    <button
                      className="btn btn-success btn-w-13"
                      type="button"
                      data-toggle="modal"
                      data-target="#exportModal"
                    >
                      {buttonType.BUTTON_EXPORT}
                    </button>
                    <button
                      className="btn btn-danger btn-w-13"
                      type="button"
                      data-toggle="modal"
                      data-target="#importModal"
                    >
                      {buttonType.BUTTON_IMPORT}
                    </button>
                  </div>
                  <div className="modal fade" id="importModal">
                    <div className="modal-dialog" role="document">
                      <div className="modal-content">
                        <div className="modal-header">
                          <h5 className="modal-title" id="exampleModalLabel">
                            Upload File
                          </h5>
                          <button
                            type="button"
                            className="close"
                            data-dismiss="modal"
                            aria-label="Close"
                          >
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div className="modal-body">
                          <div className="custom-file">
                            <input
                              type="file"
                              className="custom-file-input"
                              id="customFile"
                            />
                            <label className="custom-file-label">
                              Choose file
                            </label>
                          </div>
                        </div>
                        <div className="modal-footer">
                          <button
                            type="button"
                            className="btn btn-blue-grey"
                            data-dismiss="modal"
                          >
                            Close
                          </button>
                          <button type="button" className="btn btn-primary">
                            Import
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="modal fade" id="exportModal">
                    <div className="modal-dialog" role="document">
                      <div className="modal-content">
                        <div className="modal-header">
                          <h5 className="modal-title" id="exampleModalLabel">
                            Export file
                          </h5>
                          <button
                            type="button"
                            className="close"
                            data-dismiss="modal"
                            aria-label="Close"
                          >
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div className="modal-footer">
                          <button
                            type="button"
                            className="btn btn-blue-grey"
                            data-dismiss="modal"
                          >
                            Close
                          </button>
                          <button
                            type="button"
                            className="btn btn-primary"
                            data-dismiss="modal"
                            onClick={this.onExport}
                          >
                            Export
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div className="mt-4">
          <div className="d-flex justify-content-between">
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
                <th scope="col">
                  <div className="custom-control custom-checkbox">
                    <input
                      type="checkbox"
                      name="checkAll"
                      checked={this.state.checkAll}
                      onChange={e =>
                        this.onHandleCheckAll(employeeListProps, e)
                      }
                      className="custom-control-input"
                      id="customCheck1"
                    />
                    <label
                      className="custom-control-label"
                      htmlFor="customCheck1"
                    ></label>
                  </div>
                </th>
                <th scope="col" width="5%" className="text-center align-middle">
                  Mã nhân viên
                </th>
                <th
                  scope="col"
                  width="30%"
                  className="text-center align-middle"
                >
                  Họ tên
                </th>
                <th
                  scope="col"
                  width="15%"
                  className="text-center align-middle"
                >
                  Ngày sinh
                </th>
                <th
                  scope="col"
                  width="15%"
                  className="text-center align-middle"
                >
                  Ngày thử việc
                </th>
                <th
                  scope="col"
                  width="15%"
                  className="text-center align-middle"
                >
                  Ngày chính thức
                </th>
                <th
                  scope="col"
                  width="12%"
                  className="text-center align-middle"
                >
                  Loại hợp đồng
                </th>
                <th
                  scope="col"
                  width="30%"
                  className="text-center align-middle"
                >
                  Trạng thái lao động
                </th>
                <th
                  scope="col"
                  width="30%"
                  className="text-center align-middle"
                >
                  ĐT di động
                </th>
                <th
                  scope="col"
                  width="30%"
                  className="text-center align-middle"
                >
                  Email
                </th>
                <th scope="col" width="7%" className="text-center align-middle">
                  Vị trí công việc
                </th>
                <th scope="col" width="9%" className="text-center align-middle">
                  Đơn vị công tác
                </th>
                <th scope="col"></th>
              </tr>
            </thead>

            {employeeList.length > 0 && (
              <tbody>
                {employeeList.map((employee, index) => {
                  return (
                    <tr key={index}>
                      <th scope="row" className="text-center align-middle">
                        <div className="custom-control custom-checkbox">
                          <input
                            type="checkbox"
                            value={employee.employeeCode}
                            checked={
                              this.state.checkAll
                                ? this.state.checkAll
                                : employee.deleteFlag
                            }
                            onChange={e =>
                              this.onHandleCheckElement(
                                employee.employeeCode,
                                e
                              )
                            }
                            className="custom-control-input"
                            id={"customCheck" + index}
                          />
                          <label
                            className="custom-control-label"
                            htmlFor="customCheck2"
                          ></label>
                        </div>
                      </th>
                      <td className="text-center align-middle">
                        {employee.employeeCode}
                      </td>
                      <td className="align-middle">{employee.employeeName}</td>
                      <td className="text-center align-middle">
                        {employee.birthday}
                      </td>
                      <td className="text-center align-middle">
                        {employee.probationaryDay}
                      </td>
                      <td className="text-center align-middle">
                        {employee.officialDay}
                      </td>
                      <td className="align-middle">{employee.typeContract}</td>
                      <td className=" align-middle">{employee.status}</td>
                      <td className="text-center align-middle">
                        {employee.phoneNumber}
                      </td>
                      <td className=" align-middle">{employee.email}</td>
                      <td className="align-middle">{employee.position}</td>
                      <td className="align-middle">{employee.division}</td>
                      <td>
                        <button
                          type="button"
                          className="btn btn-primary btn-none-pm"
                        >
                          <Link to={"/user/edit/" + employee.employeeCode}>
                            <i className="fa fa-pencil-square-o"></i>
                          </Link>
                        </button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            )}
          </table>
          <div className="text-center">
            {employeeList.length <= 0 && (
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

export default ListUserComponent;
