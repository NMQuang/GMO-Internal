import React, { Component } from "react";
import "./edit-user.css";
import AlertComponent from "../../../../shared/common/component/alert.component";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { message } from "../../../../shared/constants/message.constant";
import { buttonType } from "../../../../shared/constants/button-type.constant";
import { Redirect } from "react-router-dom";
import history from "../../../../config/history.config";
import { role } from "../../../../shared/constants/role.constant";
import DatePicker from "react-datepicker";
import { dateTimePattern } from "../../../../shared/constants/date-time-pattern.constant";
import commonUtil from "../../../../shared/utils/common.util";
import actionUtil from "../../../../shared/utils/action.util";
import moment from "moment";
import validateUtil from "../../../../shared/utils/validate.util";

/**
 * DetailUserComponent
 */
class EditUserComponent extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
    this.state = {
      employeeInfo: {},
      error: {
        employeeName: "",
        gender: "",
        birthday: "",
        phoneNumber: "",
        email: "",
        address: "",
        division: "",
        probationaryDay: "",
        position: "",
        officialDay: "",
        status: "",
        workLocation: "",
        typeContract: ""
      }
    };
  }

  /**
   * Component will receive props
   */
  componentWillReceiveProps() {
    const { userStore } = this.props;
    if (userStore.detailEmployee) {
      let { employee } = userStore.detailEmployee.resultData;
      employee = commonUtil.unescapeHtmlObject(employee);
      this.setState({
        employeeInfo: employee[0]
      });
    }
  }

  onClickBack = () => {
    history.push("/project/list");
  };

  onHandleChange = e => {
    this.setState(
      {
        employeeInfo: {
          ...this.state.employeeInfo,
          [e.target.name]: e.target.value
        }
      },
      () => {
        let error = {};
        let { employeeInfo } = this.state;

        // Check employeeName
        if (validateUtil.isEmpty(employeeInfo.employeeName)) {
          error.employeeName = message.MSG_ERROR_001;
        } else if (!validateUtil.checkString(employeeInfo.employeeName)) {
          error.employeeName = message.MSG_ERROR_020;
        }

        // Check phoneNumber
        if (employeeInfo.phoneNumber) {
          if (validateUtil.maxLength(employeeInfo.phoneNumber, 11)) {
            error.phoneNumber = commonUtil.parseMessage(message.MSG_ERROR_023, [
              11
            ]);
          } else if (!validateUtil.checkPhoneNumber(employeeInfo.phoneNumber)) {
            error.phoneNumber = message.MSG_ERROR_021;
          }
        }

        // Check email
        if (validateUtil.isEmpty(employeeInfo.email)) {
          error.email = message.MSG_ERROR_001;
          isValid = false;
        } else if (!validateUtil.checkEmail(employeeInfo.email)) {
          error.email = commonUtil.parseMessage(message.MSG_ERROR_006, [
            "email"
          ]);
        }

        // Check address
        if (
          employeeInfo.address &&
          !validateUtil.checkAddress(employeeInfo.address)
        ) {
          error.address = message.MSG_ERROR_020;
        }

        // Check location
        if (
          employeeInfo.workLocation &&
          !validateUtil.checkString(employeeInfo.workLocation)
        ) {
          error.workLocation = message.MSG_ERROR_020;
        }
        // Set state
        this.setState({
          error: {
            ...this.state.error,
            employeeName: error.employeeName,
            gender: error.gender,
            phoneNumber: error.phoneNumber,
            email: error.email,
            address: error.address,
            division: error.division,
            position: error.position,
            status: error.status,
            workLocation: error.workLocation,
            typeContract: error.typeContract
          }
        });
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
        employeeInfo: {
          ...this.state.employeeInfo,
          [dateName]: ""
        }
      });
    } else {
      this.setState(
        {
          employeeInfo: {
            ...this.state.employeeInfo,
            [dateName]: new Date(moment(date).format(dateTimePattern.YYYYMMDD))
          }
        },
        () => {
          let error = {};
          let { employeeInfo } = this.state;

          // Check birthday
          if (
            !validateUtil.checkDateValid(
              employeeInfo.birthday,
              new Date(),
              dateTimePattern.YYYYMMDD
            )
          ) {
            error.birthday = commonUtil.parseMessage(message.MSG_ERROR_022, [
              "ngày sinh"
            ]);
          }

          // Check probationaryDay
          if (
            !validateUtil.checkDateValid(
              employeeInfo.probationaryDay,
              new Date(),
              dateTimePattern.YYYYMMDD
            )
          ) {
            error.probationaryDay = commonUtil.parseMessage(
              message.MSG_ERROR_022,
              ["ngày thử việc"]
            );
          }

          // Check officialDay
          if (
            !validateUtil.checkDateValid(
              employeeInfo.officialDay,
              new Date(),
              dateTimePattern.YYYYMMDD
            )
          ) {
            error.officialDay = commonUtil.parseMessage(message.MSG_ERROR_022, [
              "ngày chính thức"
            ]);
          } else if (
            !validateUtil.checkDateValid(
              employeeInfo.probationaryDay,
              employeeInfo.officialDay,
              dateTimePattern.YYYYMMDD
            )
          ) {
            error.officialDay = commonUtil.parseMessage(message.MSG_ERROR_024, [
              "ngày chính thức",
              "ngày thử việc"
            ]);
          }
          // Set state
          this.setState({
            error: {
              ...this.state.error,
              birthday: error.birthday,
              probationaryDay: error.probationaryDay,
              officialDay: error.officialDay
            }
          });
        }
      );
    }
  };

  onCheckValidate = () => {
    let isValid = true;
    let error = {};
    let { employeeInfo } = this.state;
    employeeInfo = commonUtil.unescapeHtmlObject(employeeInfo);
    // Check employeeName
    if (validateUtil.isEmpty(employeeInfo.employeeName)) {
      error.employeeName = message.MSG_ERROR_001;
      isValid = false;
    } else if (!validateUtil.checkString(employeeInfo.employeeName)) {
      error.employeeName = message.MSG_ERROR_020;
      isValid = false;
    }

    // Check birthday
    if (
      !validateUtil.checkDateValid(
        employeeInfo.birthday,
        new Date(),
        dateTimePattern.YYYYMMDD
      )
    ) {
      error.birthday = commonUtil.parseMessage(message.MSG_ERROR_022, [
        "ngày sinh"
      ]);
      isValid = false;
    }

    // Check phoneNumber
    if (employeeInfo.phoneNumber) {
      if (validateUtil.maxLength(employeeInfo.phoneNumber, 11)) {
        error.phoneNumber = commonUtil.parseMessage(message.MSG_ERROR_023, [
          11
        ]);
        isValid = false;
      } else if (!validateUtil.checkPhoneNumber(employeeInfo.phoneNumber)) {
        error.phoneNumber = message.MSG_ERROR_021;
        isValid = false;
      }
    }
    // Check email
    if (validateUtil.isEmpty(employeeInfo.email)) {
      error.email = message.MSG_ERROR_001;
      isValid = false;
    } else if (!validateUtil.checkEmail(employeeInfo.email)) {
      error.email = commonUtil.parseMessage(message.MSG_ERROR_006, ["email"]);
      isValid = false;
    }

    // Check address
    if (
      employeeInfo.address &&
      !validateUtil.checkAddress(employeeInfo.address)
    ) {
      error.address = message.MSG_ERROR_020;
      isValid = false;
    }

    // Check probationaryDay
    if (
      !validateUtil.checkDateValid(
        employeeInfo.probationaryDay,
        new Date(),
        dateTimePattern.YYYYMMDD
      )
    ) {
      error.probationaryDay = commonUtil.parseMessage(message.MSG_ERROR_022, [
        "ngày thử việc"
      ]);
      isValid = false;
    }

    // Check officialDay
    if (
      !validateUtil.checkDateValid(
        employeeInfo.officialDay,
        new Date(),
        dateTimePattern.YYYYMMDD
      )
    ) {
      error.officialDay = commonUtil.parseMessage(message.MSG_ERROR_022, [
        "ngày chính thức"
      ]);
      isValid = false;
    } else if (
      !validateUtil.checkDateValid(
        employeeInfo.probationaryDay,
        employeeInfo.officialDay,
        dateTimePattern.YYYYMMDD
      )
    ) {
      error.officialDay = commonUtil.parseMessage(message.MSG_ERROR_024, [
        "ngày chính thức",
        "ngày thử việc"
      ]);
      isValid = false;
    }

    // CHeck location
    if (
      employeeInfo.workLocation &&
      !validateUtil.checkString(employeeInfo.workLocation)
    ) {
      error.workLocation = message.MSG_ERROR_020;
      isValid = false;
    }
    // Set state
    this.setState({
      ...this.state,
      error: error
    });
    return isValid;
  };

  onClickUpdate = () => {
    const { employeeInfo } = this.state;
    if (this.onCheckValidate()) {
      this.onCustomState();
      this.props.onUpdate(employeeInfo);
    }
  };

  onCustomState = () => {
    const { employeeInfo } = this.state;
    const { userStore } = this.props;
    if (userStore.detailEmployee) {
      // Load data division
      userStore.detailEmployee.resultData.divisionList.forEach(division => {
        division = commonUtil.unescapeHtmlObject(division);
        return division.divisionName === employeeInfo.division;
      });
      const divisionId = userStore.detailEmployee.resultData.divisionList.filter(
        division => division.divisionName === employeeInfo.division
      );
      //Load data position
      userStore.detailEmployee.resultData.positionList.forEach(position => {
        position = commonUtil.unescapeHtmlObject(position);
      });
      const positionId = userStore.detailEmployee.resultData.positionList.filter(
        position => position.positionName === employeeInfo.position
      );

      //Load data status
      userStore.detailEmployee.resultData.statusList.forEach(status => {
        status = commonUtil.unescapeHtmlObject(status);
      });
      const statusId = userStore.detailEmployee.resultData.statusList.filter(
        status => status.unitName === employeeInfo.status
      );

      //Load data typeContact
      userStore.detailEmployee.resultData.typeContactList.forEach(
        typeContact => {
          typeContact = commonUtil.unescapeHtmlObject(typeContact);
        }
      );
      const typeContactId = userStore.detailEmployee.resultData.typeContactList.filter(
        typeContact => typeContact.typeContactName === employeeInfo.typeContract
      );

      this.setState({
        ...this.state,
        employeeInfo: {
          ...this.state.employeeInfo,
          division: divisionId.length > 0 ? divisionId[0].id : 0,
          position: positionId.length > 0 ? positionId[0].id : 0,
          status: statusId.length > 0 ? statusId[0].id : 0,
          typeContact: typeContactId.length > 0 ? typeContactId[0].id : 0
        }
      });
    }
  };

  render() {
    const { messageStore, userStore } = this.props;
    const roleLogin = userStore.data.resultData.roleId;
    const { employeeInfo, error } = this.state;
    let divisionList = [];
    let positionList = [];
    let statusList = [];
    let typeContactList = [];
    if (userStore.detailEmployee) {
      // Load data division
      userStore.detailEmployee.resultData.divisionList.forEach(division => {
        division = commonUtil.unescapeHtmlObject(division);
      });
      divisionList = userStore.detailEmployee.resultData.divisionList.map(
        (division, index) => {
          return (
            <option value={division.divisionName} key={index}>
              {division.divisionName}
            </option>
          );
        }
      );
      //Load data position
      userStore.detailEmployee.resultData.positionList.forEach(position => {
        position = commonUtil.unescapeHtmlObject(position);
      });
      positionList = userStore.detailEmployee.resultData.positionList.map(
        (position, index) => {
          return (
            <option value={position.positionName} key={index}>
              {position.positionName}
            </option>
          );
        }
      );
      //Load data status
      userStore.detailEmployee.resultData.statusList.forEach(status => {
        status = commonUtil.unescapeHtmlObject(status);
      });
      statusList = userStore.detailEmployee.resultData.statusList.map(
        (status, index) => {
          return (
            <option value={status.unitName} key={index}>
              {status.unitName}
            </option>
          );
        }
      );
      //Load data typeContact
      userStore.detailEmployee.resultData.typeContactList.forEach(
        typeContact => {
          typeContact = commonUtil.unescapeHtmlObject(typeContact);
        }
      );
      typeContactList = userStore.detailEmployee.resultData.typeContactList.map(
        (typeContact, index) => {
          return (
            <option value={typeContact.typeContactName} key={index}>
              {typeContact.typeContactName}
            </option>
          );
        }
      );
    }

    if (userStore.detailEmployee) {
      const { employee } = userStore.detailEmployee.resultData;
      return (
        <div className="container-fluid mt-4 fix-padding-bottom">
          <h2 className="text-center my-4">
            <b className="font-weigth">INFORMATION USER EDIT</b>
          </h2>
          <div className="row">
            <div className="col-12 text-center">
              {<AlertComponent messageObj={messageStore} />}
            </div>
          </div>
          <div className="card mb-4">
            <div className="card-body">
              <div className="row">
                {+employeeInfo.gender === 1 ? (
                  <div className="col-3 avatar-men"></div>
                ) : (
                  <div className="col-3 avatar-women"></div>
                )}

                <div className="col-9">
                  <div className="row row-mg">
                    <div className="col-4">
                      <div className="row">
                        <label className="col-6 col-form-label">
                          Mã Nhân Viên
                        </label>
                        <input
                          className="form-control col-6"
                          type="text"
                          name="employeeCode"
                          value={employeeInfo.employeeCode}
                          disabled
                        ></input>
                      </div>
                    </div>
                    <div className="col-8">
                      <div className="row">
                        <label className="col-4 col-form-label">
                          Họ và Tên<span className="text-danger">*</span>
                        </label>
                        <input
                          className="form-control col-8 form-control-custom"
                          type="text"
                          name="employeeName"
                          value={employeeInfo.employeeName}
                          onChange={this.onHandleChange}
                        ></input>
                      </div>
                      <div className="row">
                        <div className="col-4"></div>
                        <div className="col-8 form-control-custom">
                          {error.employeeName && (
                            <ErrorComponent error={error.employeeName} />
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row row-mg">
                    <div className="col-4">
                      <div className="row">
                        <label className="col-6 col-form-label">
                          Giới Tính
                        </label>
                        <div className="col-6 col-radio ">
                          {employee.length > 0 ? (
                            <React.Fragment>
                              <div className="custom-control custom-radio custom-control-inline">
                                <input
                                  type="radio"
                                  className="custom-control-input"
                                  id="defaultInline2"
                                  name="gender"
                                  value="1"
                                  checked={
                                    +employeeInfo.gender === 1 ? true : false
                                  }
                                  onChange={this.onHandleChange}
                                />
                                <label
                                  className="custom-control-label"
                                  htmlFor="defaultInline2"
                                >
                                  Nam
                                </label>
                              </div>
                              <div className="custom-control custom-radio custom-control-inline">
                                <input
                                  type="radio"
                                  className="custom-control-input"
                                  id="defaultInline3"
                                  name="gender"
                                  value="0"
                                  checked={
                                    +employeeInfo.gender === 0 ? true : false
                                  }
                                  onChange={this.onHandleChange}
                                />
                                <label
                                  className="custom-control-label"
                                  htmlFor="defaultInline3"
                                >
                                  Nữ
                                </label>
                              </div>
                            </React.Fragment>
                          ) : (
                            "N/A"
                          )}
                        </div>
                      </div>
                    </div>
                    <div className="col-8">
                      <div className="row">
                        <label className="col-4 col-form-label">
                          Ngày Sinh
                        </label>
                        <DatePicker
                          className="form-control combobox reset-date-picker form-control-custom"
                          name="birthday"
                          selected={new Date(employeeInfo.birthday)}
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
                      <div className="row">
                        <div className="col-4"></div>
                        <div className="col-8 form-control-custom">
                          {error.birthday && (
                            <ErrorComponent error={error.birthday} />
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row row-mg">
                    <div className="col-4">
                      <div className="row">
                        <label className="col-6 col-form-label">
                          Điện Thoại
                        </label>
                        <input
                          className="form-control col-6"
                          type="text"
                          onChange={this.onHandleChange}
                          name="phoneNumber"
                          value={
                            employeeInfo.phoneNumber
                              ? employeeInfo.phoneNumber
                              : ""
                          }
                        ></input>
                      </div>
                      <div className="row">
                        <div className="col-6"></div>
                        <div className="col-6">
                          {error.phoneNumber && (
                            <ErrorComponent error={error.phoneNumber} />
                          )}
                        </div>
                      </div>
                    </div>
                    <div className="col-8">
                      <div className="row">
                        <label className="col-4 col-form-label">
                          Email
                          <span className="text-danger">*</span>
                        </label>
                        <input
                          className="form-control col-8 form-control-custom"
                          type="text"
                          onChange={this.onHandleChange}
                          name="email"
                          value={employeeInfo.email ? employeeInfo.email : ""}
                        ></input>
                      </div>
                      <div className="row">
                        <div className="col-4"></div>
                        <div className="col-8 form-control-custom">
                          {error.email && (
                            <ErrorComponent error={error.email} />
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row row-mg">
                    <label className="col-3 col-form-label">Địa Chỉ</label>
                    <textarea
                      className="form-control col-9 form-control-custom text-area-custom"
                      rows="5"
                      onChange={this.onHandleChange}
                      name="address"
                      value={employeeInfo.address ? employeeInfo.address : ""}
                    ></textarea>
                  </div>
                  <div className="row">
                    <div className="col-3"></div>
                    <div className="col-9 form-control-custom">
                      {error.address && (
                        <ErrorComponent error={error.address} />
                      )}
                    </div>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-12 row-mg-br">
                  <h5> Thông Tin Công Việc</h5>
                </div>
              </div>
              <div className="row row-mg">
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Đơn Vị Công Tác
                      <span className="text-danger">*</span>
                    </label>
                    <select
                      className="form-control combobox form-control-custom"
                      name="division"
                      value={employeeInfo.division}
                      onChange={this.onHandleChange}
                    >
                      {divisionList}
                    </select>
                  </div>
                  <div className="row">
                    <div className="col-4"></div>
                    <div className="col-8 form-control-custom">
                      {error.division && (
                        <ErrorComponent error={error.division} />
                      )}
                    </div>
                  </div>
                </div>
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Ngày Thử Việc
                      <span className="text-danger">*</span>
                    </label>
                    <DatePicker
                      className="form-control combobox reset-date-picker form-control-custom"
                      name="probationaryDay"
                      selected={new Date(employeeInfo.probationaryDay)}
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
                  <div className="row">
                    <div className="col-5"></div>
                    <div className="col-7 form-control-custom">
                      {error.probationaryDay && (
                        <ErrorComponent error={error.probationaryDay} />
                      )}
                    </div>
                  </div>
                </div>
              </div>
              <div className="row row-mg">
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Vị Trí Công Việc
                      <span className="text-danger">*</span>
                    </label>
                    <select
                      className="form-control combobox form-control-custom"
                      name="position"
                      value={employeeInfo.position}
                      onChange={this.onHandleChange}
                    >
                      {positionList}
                    </select>
                  </div>
                  <div className="row">
                    <div className="col-5"></div>
                    <div className="col-7 form-control-custom">
                      {error.position && (
                        <ErrorComponent error={error.position} />
                      )}
                    </div>
                  </div>
                </div>
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Ngày Chính Thức
                      <span className="text-danger">*</span>
                    </label>
                    <DatePicker
                      className="form-control combobox reset-date-picker form-control-custom"
                      name="biofficialDayrthday"
                      selected={new Date(employeeInfo.officialDay)}
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
                  <div className="row">
                    <div className="col-5"></div>
                    <div className="col-7 form-control-custom">
                      {error.officialDay && (
                        <ErrorComponent error={error.officialDay} />
                      )}
                    </div>
                  </div>
                </div>
              </div>
              <div className="row row-mg">
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Trạng Thái
                      <span className="text-danger">*</span>
                    </label>
                    <select
                      className="form-control combobox form-control-custom"
                      name="status"
                      value={employeeInfo.status}
                      onChange={this.onHandleChange}
                    >
                      {statusList}
                    </select>
                  </div>
                  <div className="row">
                    <div className="col-5"></div>
                    <div className="col-7 form-control-custom">
                      {error.status && <ErrorComponent error={error.status} />}
                    </div>
                  </div>
                </div>
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Địa Điểm Làm Việc
                    </label>
                    <input
                      className="form-control col-7 form-control-custom"
                      type="text"
                      name="workLocation"
                      value={
                        employeeInfo.workLocation
                          ? employeeInfo.workLocation
                          : ""
                      }
                      onChange={this.onHandleChange}
                    ></input>
                  </div>
                  <div className="row">
                    <div className="col-5"></div>
                    <div className="col-7 form-control-custom">
                      {error.workLocation && (
                        <ErrorComponent error={error.workLocation} />
                      )}
                    </div>
                  </div>
                </div>
              </div>
              <div className="row row-mg">
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Loại Hợp Đồng
                      <span className="text-danger">*</span>
                    </label>
                    <select
                      className="form-control combobox form-control-custom"
                      name="typeContract"
                      value={employeeInfo.typeContract}
                      onChange={this.onHandleChange}
                    >
                      {typeContactList}
                    </select>
                  </div>
                  <div className="row">
                    <div className="col-5"></div>
                    <div className="col-7 form-control-custom">
                      {error.typeContract && (
                        <ErrorComponent error={error.typeContract} />
                      )}
                    </div>
                  </div>
                </div>

                <div className="col-6">
                  <div className="row">
                    <label className="col-12 col-form-label">
                      <span className="text-danger">
                        *: Các field không được phép để trống.
                      </span>
                    </label>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="mt-4">
            <div className="text-center">
              <button
                className="btn btn-info btn-send"
                onClick={this.onClickBack}
              >
                <b>{buttonType.BUTTON_BACK}</b>
              </button>
              {roleLogin === role.ROLE_ADMIN && (
                <button
                  className="btn btn-primary btn-send"
                  onClick={this.onClickUpdate}
                >
                  <b>{buttonType.BUTTON_UPDATE}</b>
                </button>
              )}
            </div>
          </div>
        </div>
      );
    } else {
      return (
        <div className="container-fluid mt-4 fix-padding-bottom">
          <h2 className="text-center my-4">
            <b className="font-weigth">INFORMATION USER DETAIL</b>
          </h2>
          <div className="row">
            <div className="col-12">
              {userStore.isDetailError && (
                <AlertComponent messageObj={messageStore} />
              )}
            </div>
          </div>
        </div>
      );
    }
  }
}

export default EditUserComponent;
