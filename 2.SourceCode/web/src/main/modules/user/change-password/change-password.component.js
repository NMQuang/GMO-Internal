import React, { Component } from "react";
import { message } from "../../../../shared/constants/message.constant";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { changePassword } from "./change-password.action";
import { connect } from "react-redux";
import validateUtil from "../../../../shared/utils/validate.util";
import commonUtil from "../../../../shared/utils/common.util";
import { field } from "../../../../shared/constants/field.constant";
import AlertComponent from "../../../../shared/common/component/alert.component";
import "./change-password.css";
import { buttonType } from "../../../../shared/constants/button-type.constant";
import actionUtil from "../../../../shared/utils/action.util";
import history from "../../../../config/history.config";
/**
 * InfoUser component
 */
class ChangePasswordComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      userInfo: {
        passwordCurrent: "",
        passwordNew: "",
        passwordConfirm: ""
      },
      error: {
        passwordCurrent: [],
        passwordNew: [],
        passwordConfirm: []
      }
    };
  }

  onClickBack = () => {
    history.push("/project/list");
  };

  onHandleChange = e => {
    const { value, name } = e.target;
    this.setState(
      {
        ...this.state,
        userInfo: {
          ...this.state.userInfo,
          [name]: value
        }
      },
      () => {
        let error = {
          passwordCurrent: [],
          passwordNew: [],
          passwordConfirm: []
        };
        //validate field passwordCurent
        if (validateUtil.isEmpty(this.state.userInfo.passwordCurrent)) {
          error.passwordCurrent.push(
            commonUtil.parseMessage(message.MSG_ERROR_004, [
              field.PASSWORD_CURRENT
            ])
          );
        } else {
          if (
            !validateUtil.checkPassword(this.state.userInfo.passwordCurrent)
          ) {
            error.passwordCurrent.push(
              commonUtil.parseMessage(message.MSG_ERROR_007, [
                field.PASSWORD_CURRENT
              ])
            );
          } else if (
            validateUtil.inRange(this.state.userInfo.passwordCurrent, 6, 128)
          ) {
            error.passwordCurrent.push(
              commonUtil.parseMessage(message.MSG_ERROR_005, [
                field.PASSWORD_CURRENT,
                "6",
                "128"
              ])
            );
          }
        }

        //validate field passwordNew
        if (validateUtil.isEmpty(this.state.userInfo.passwordNew)) {
          error.passwordNew.push(
            commonUtil.parseMessage(message.MSG_ERROR_004, [field.PASSWORD_NEW])
          );
        } else {
          if (!validateUtil.checkPassword(this.state.userInfo.passwordNew)) {
            error.passwordNew.push(
              commonUtil.parseMessage(message.MSG_ERROR_007, [
                field.PASSWORD_NEW
              ])
            );
          } else if (
            validateUtil.inRange(this.state.userInfo.passwordNew, 6, 128)
          ) {
            error.passwordNew.push(
              commonUtil.parseMessage(message.MSG_ERROR_005, [
                field.PASSWORD_NEW,
                "6",
                "128"
              ])
            );
          }
        }

        //validate field passwordNew
        if (validateUtil.isEmpty(this.state.userInfo.passwordConfirm)) {
          error.passwordConfirm.push(
            commonUtil.parseMessage(message.MSG_ERROR_004, [
              field.PASSWORD_CONFIRM
            ])
          );
        } else if (
          validateUtil.inRange(this.state.userInfo.passwordConfirm, 6, 128)
        ) {
          error.passwordConfirm.push(
            commonUtil.parseMessage(message.MSG_ERROR_005, [
              field.PASSWORD_CONFIRM,
              "6",
              "128"
            ])
          );
        } else {
          if (
            this.state.userInfo.passwordNew !==
            this.state.userInfo.passwordConfirm
          ) {
            error.passwordConfirm.push(
              commonUtil.parseMessage(message.MSG_ERROR_018, [
                field.PASSWORD_CONFIRM,
                field.PASSWORD_NEW
              ])
            );
          }
        }
        this.setState({ error: error });
      }
    );
  };

  onHandleSubmit = e => {
    e.preventDefault();
    const { userInfo } = this.state;
    if (this.checkValidateForm()) {
      this.props.changePasswordAction(userInfo);

      setTimeout(() => {
        this.props.userStore.error = null;
      }, 2000);
    }
  };

  resetForm = () => {
    this.setState({
      userInfo: {
        passwordCurrent: "",
        passwordNew: "",
        passwordConfirm: ""
      }
    });
  };

  checkValidateForm = () => {
    const { userInfo } = this.state;
    let error = {
      passwordCurrent: [],
      passwordNew: [],
      passwordConfirm: []
    };

    let isValid = true;

    /**
     * validate field passwordCurrent
     * check isEmpty
     * check length
     * check pattern is passwordCurrent
     *
     */
    if (validateUtil.isEmpty(userInfo.passwordCurrent)) {
      error.passwordCurrent.push(
        commonUtil.parseMessage(message.MSG_ERROR_004, [field.PASSWORD_CURRENT])
      );
      isValid = false;
      this.setState({ error: error });
    } else {
      if (!validateUtil.checkPassword(userInfo.passwordCurrent)) {
        error.passwordCurrent.push(
          commonUtil.parseMessage(message.MSG_ERROR_007, [
            field.PASSWORD_CURRENT
          ])
        );
        isValid = false;
        this.setState({ error: error });
      } else if (validateUtil.inRange(userInfo.passwordCurrent, 6, 128)) {
        error.passwordCurrent.push(
          commonUtil.parseMessage(message.MSG_ERROR_005, [
            field.PASSWORD_CURRENT,
            "6",
            "128"
          ])
        );
        isValid = false;
        this.setState({ error: error });
      }
    }

    /**
     * validate field passwordNew
     * check isEmpty
     * check length
     * check pattern is passwordNew
     *
     */
    if (validateUtil.isEmpty(userInfo.passwordNew)) {
      error.passwordNew.push(
        commonUtil.parseMessage(message.MSG_ERROR_004, [field.PASSWORD_NEW])
      );
      isValid = false;
      this.setState({ error: error });
    } else {
      if (!validateUtil.checkPassword(userInfo.passwordNew)) {
        error.passwordNew.push(
          commonUtil.parseMessage(message.MSG_ERROR_007, [field.PASSWORD_NEW])
        );
        isValid = false;
        this.setState({ error: error });
      } else if (validateUtil.inRange(userInfo.passwordNew, 6, 128)) {
        error.passwordNew.push(
          commonUtil.parseMessage(message.MSG_ERROR_005, [
            field.PASSWORD_NEW,
            "6",
            "128"
          ])
        );
        isValid = false;
        this.setState({ error: error });
      }
    }

    /**
     * compare passwordConfirm and passwordNew
     */
    if (validateUtil.isEmpty(userInfo.passwordConfirm)) {
      error.passwordConfirm.push(
        commonUtil.parseMessage(message.MSG_ERROR_004, [field.PASSWORD_CONFIRM])
      );
    } else if (
      validateUtil.inRange(this.state.userInfo.passwordConfirm, 6, 128)
    ) {
      error.passwordConfirm.push(
        commonUtil.parseMessage(message.MSG_ERROR_005, [
          field.PASSWORD_CONFIRM,
          "6",
          "128"
        ])
      );
    } else {
      if (userInfo.passwordNew !== userInfo.passwordConfirm) {
        error.passwordConfirm.push(
          commonUtil.parseMessage(message.MSG_ERROR_018, [
            field.PASSWORD_CONFIRM,
            field.PASSWORD_NEW
          ])
        );
        isValid = false;
        this.setState({ error: error });
      }
    }
    return isValid;
  };

  render() {
    const { error, userInfo } = this.state;
    const { messageStore, userStore } = this.props;

    return (
      <div className="container-fluid mt-4 fix-padding-bottom">
        <h2 className="text-center my-4">
          <b className="font-weigth">THAY ĐỔI MẬT KHẨU</b>
        </h2>
        <div className="row">
          <div className="col-12">
            {userStore.isChangePassword && (
              <AlertComponent messageObj={messageStore} />
            )}
          </div>
        </div>

        <div className="card mb-4">
          <div className="card-body">
            <div className="form-row">
              <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div className="form-group row">
                  <label className="col-sm-5 col-form-label">
                    Mật khẩu hiện tại<span className="text-danger">*</span>
                  </label>
                  <div className="col-sm-7">
                    <input
                      className="form-control col-10"
                      type="password"
                      name="passwordCurrent"
                      value={userInfo.passwordCurrent}
                      onChange={this.onHandleChange}
                    />
                  </div>
                </div>
                {error.passwordCurrent.length > 0 && (
                  <div className="form-group row">
                    <label className="col-sm-5 col-form-label"></label>
                    <div className="col-sm-7 ">
                      {error.passwordCurrent.length > 0 && (
                        <ErrorComponent error={error.passwordCurrent} />
                      )}
                    </div>
                  </div>
                )}
                {userStore.error && (
                  <div className="form-group row">
                    <label className="col-sm-5 col-form-label"></label>
                    <div className="col-sm-7 ">
                      {userStore.error && (
                        <ErrorComponent
                          errorResponse={userStore.error.errorData}
                          field="passwordCurrent"
                        />
                      )}
                    </div>
                  </div>
                )}

                <div className="form-group row">
                  <label className="col-sm-5 col-form-label">
                    Mật khẩu mới<span className="text-danger">*</span>
                  </label>
                  <div className="col-sm-7 ">
                    <input
                      className="form-control col-10"
                      type="password"
                      name="passwordNew"
                      value={userInfo.passwordNew}
                      onChange={this.onHandleChange}
                    />
                  </div>
                </div>
                {error.passwordNew.length > 0 && (
                  <div className="form-group row">
                    <label className="col-sm-5 col-form-label"></label>
                    <div className="col-sm-7 ">
                      {error.passwordNew.length > 0 && (
                        <ErrorComponent error={error.passwordNew} />
                      )}
                    </div>
                  </div>
                )}
                {userStore.error && (
                  <div className="form-group row">
                    <label className="col-sm-5 col-form-label"></label>
                    <div className="col-sm-7 ">
                      {userStore.error && (
                        <ErrorComponent
                          errorResponse={userStore.error.errorData}
                          field="passwordNew"
                        />
                      )}
                    </div>
                  </div>
                )}

                <div className="form-group row">
                  <label className="col-sm-5 col-form-label">
                    Xác nhận mật khẩu mới
                    <span className="text-danger">*</span>
                  </label>
                  <div className="col-sm-7">
                    <input
                      className="form-control col-10"
                      type="password"
                      name="passwordConfirm"
                      value={userInfo.passwordConfirm}
                      onChange={this.onHandleChange}
                    />
                  </div>
                </div>
                {error.passwordConfirm.length > 0 && (
                  <div className="form-group row">
                    <label className="col-sm-5 col-form-label"></label>
                    <div className="col-sm-7 ">
                      {error.passwordConfirm.length > 0 && (
                        <ErrorComponent error={error.passwordConfirm} />
                      )}
                    </div>
                  </div>
                )}
                {userStore.error && (
                  <div className="form-group row">
                    <label className="col-sm-5 col-form-label"></label>
                    <div className="col-sm-7 ">
                      {userStore.error && (
                        <ErrorComponent
                          errorResponse={userStore.error.errorData}
                          field="passwordConfirm"
                        />
                      )}
                    </div>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
        <div className="mt-4">
          <div className="text-center">
            <button
              className="btn btn-primary btn-w-13"
              type="button"
              onClick={this.onHandleSubmit}
            >
              <b>{buttonType.BUTTON_UPDATE}</b>
            </button>
          </div>
        </div>
      </div>
    );
  }
}
/**
 * map state to props of component
 * @param {Object} state
 * @return {Object} props
 */
const mapStateToProps = state => {
  return {
    userStore: state.userReducer,
    messageStore: state.messageReducer
  };
};

/**
 * map dispatch to props
 * @param {ActionCreator} dispatch
 * @param {String} props
 * @return {ActionCreator}
 */
const mapDispatchToProps = (dispatch, props) => {
  return {
    changePasswordAction: userInfo => {
      dispatch(changePassword(userInfo));
    }
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ChangePasswordComponent);
