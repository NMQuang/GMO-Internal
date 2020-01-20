import React, { Component } from "react";
import "./detail-user.css";
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

/**
 * DetailUserComponent
 */
class DetailUserComponent extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
  }
  onClickBack = () => {
    history.push("/project/list");
  };

  render() {
    const { messageStore, userStore } = this.props;
    const roleLogin = userStore.data.resultData.roleId;
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
            <option value={division.id} key={index}>
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
            <option value={position.id} key={index}>
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
            <option value={status.id} key={index}>
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
            <option value={typeContact.id} key={index}>
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
            <b className="font-weigth">CHI TIẾT THÔNG TIN</b>
          </h2>
          <div className="row">
            <div className="col-12 text-center">
              {!userStore.isDetailError && employee.length <= 0 && (
                <ErrorComponent error={message.MSG_INFO_015} />
              )}
            </div>
          </div>
          <div className="card mb-4">
            <div className="card-body">
              <div className="row">
                {employee[0].gender === 1 ? (
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
                          value={
                            employee.length > 0
                              ? employee[0].employeeCode
                              : "N/A"
                          }
                          disabled
                        ></input>
                      </div>
                    </div>
                    <div className="col-8">
                      <div className="row">
                        <label className="col-4 col-form-label">
                          Họ và Tên
                        </label>
                        <input
                          className="form-control col-8 form-control-custom"
                          type="text"
                          name="employeeName"
                          value={
                            employee.length > 0
                              ? employee[0].employeeName
                              : "N/A"
                          }
                          disabled
                        ></input>
                      </div>
                    </div>
                  </div>
                  <div className="row row-mg">
                    <div className="col-4">
                      <div className="row">
                        <label className="col-6 col-form-label">
                          Giới Tính
                        </label>
                        <input
                          className="form-control col-6"
                          type="text"
                          name="employeeCode"
                          value={
                            employee.length > 0
                              ? employee[0].gender === 1
                                ? "Nam"
                                : "Nữ"
                              : "N/A"
                          }
                          disabled
                        ></input>
                        {/* <div className="col-6 col-radio ">
                          {employee.length > 0 ? (
                            <React.Fragment>
                              <div className="custom-control custom-radio custom-control-inline">
                                <input
                                  type="radio"
                                  className="custom-control-input"
                                  id="defaultInline2"
                                  name="Nam"
                                  checked={
                                    employee[0].gender === 1 ? true : false
                                  }
                                  readOnly
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
                                  name="Nữ"
                                  checked={
                                    employee[0].gender === 0 ? true : false
                                  }
                                  readOnly
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
                        </div> */}
                      </div>
                    </div>
                    <div className="col-8">
                      <div className="row">
                        <label className="col-4 col-form-label">
                          Ngày Sinh
                        </label>

                        <input
                          className="form-control col-8 form-control-custom"
                          type="text"
                          name="birthday"
                          value={
                            employee.length > 0
                              ? employee[0].birthday
                                ? employee[0].birthday
                                : "0000-00-00"
                              : "0000-00-00"
                          }
                          disabled
                        ></input>
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
                            employee.length > 0
                              ? employee[0].phoneNumber
                                ? employee[0].phoneNumber
                                : ""
                              : "N/A"
                          }
                          disabled
                        ></input>
                      </div>
                    </div>
                    <div className="col-8">
                      <div className="row">
                        <label className="col-4 col-form-label">Email</label>
                        <input
                          className="form-control col-8 form-control-custom"
                          type="text"
                          onChange={this.onHandleChange}
                          name="email"
                          value={
                            employee.length > 0 ? employee[0].email : "N/A"
                          }
                          disabled
                        ></input>
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
                      value={
                        employee.length > 0
                          ? employee[0].address
                            ? employee[0].address
                            : ""
                          : "N/A"
                      }
                      disabled
                    ></textarea>
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
                    </label>

                    <input
                      className="form-control col-7 form-control-custom"
                      type="text"
                      name="division"
                      value={employee.length > 0 ? employee[0].division : "N/A"}
                      disabled
                    />
                  </div>
                </div>
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Ngày Thử Việc
                    </label>

                    <input
                      className="form-control col-7 form-control-custom"
                      type="text"
                      name="probationaryDay"
                      value={
                        employee.length > 0
                          ? employee[0].probationaryDay
                          : "0000-00-00"
                      }
                      disabled
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row row-mg">
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Vị Trí Công Việc
                    </label>

                    <input
                      className="form-control col-7 form-control-custom"
                      type="text"
                      name="position"
                      value={employee.length > 0 ? employee[0].position : "N/A"}
                      disabled
                    />
                  </div>
                </div>
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Ngày Chính Thức
                    </label>

                    <input
                      className="form-control col-7 form-control-custom"
                      type="text"
                      name="officialDay"
                      value={
                        employee.length > 0
                          ? employee[0].officialDay
                          : "0000-00-00"
                      }
                      disabled
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row row-mg">
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">Trạng Thái</label>

                    <input
                      className="form-control col-7 form-control-custom"
                      type="text"
                      name="status"
                      value={employee.length > 0 ? employee[0].status : "N/A"}
                      disabled
                    />
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
                        employee.length > 0
                          ? employee[0].workLocation
                            ? employee[0].workLocation
                            : ""
                          : "N/A"
                      }
                      disabled
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row row-mg">
                <div className="col-6">
                  <div className="row">
                    <label className="col-5 col-form-label">
                      Loại Hợp Đồng
                    </label>

                    <input
                      className="form-control col-7 form-control-custom"
                      type="text"
                      name="typeContract"
                      value={
                        employee.length > 0 ? employee[0].typeContract : "N/A"
                      }
                      disabled
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      );
    } else {
      return (
        <div className="container-fluid mt-4 fix-padding-bottom">
          <h2 className="text-center my-4">
            <b className="font-weigth">CHI TIẾT THÔNG TIN</b>
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

export default DetailUserComponent;
