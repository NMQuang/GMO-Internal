import React, { Component } from "react";
import LoginComponent from "./login/login.component";
import ResetPasswordComponent from "./reset-password/reset-password.component";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";
import DetailUserComponent from "./detail-user/detail-user.component";
import DetailUserPage from "./detail-user/detail-user.page";
import handleErrorAction from "../../../shared/common/action/error.action";
import actionUtil from "../../../shared/utils/action.util";
import EditUserPage from "./edit-user/edit-user.page";
import history from "../../../config/history.config";
import { storage } from "../../../shared/constants/storage.constant";

/**
 * User Page
 */
class UserPage extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
  }

  /**
   * render
   */
  render() {
    // get value isResetPass
    const { isResetPass, userStore, employeeCode, isEdit } = this.props;
    let employeeParam = null;
    // Redirect to prev page after re-login
    const uri = sessionStorage.getItem(storage.CURRENT_URI);
    if (uri && localStorage.accessToken) {
      return <Redirect to={uri} />;
    }

    // redirect to page InfoUser
    if (employeeCode) {
      employeeParam = this.props.employeeCode.match.params.employeeCode;
      return isEdit ? (
        <EditUserPage employeeCode={employeeParam} />
      ) : (
        <DetailUserPage employeeCode={employeeParam} />
      );
    }
    /**
     * todo
     * check authentication to redirect
     */
    if (userStore.isAuthenticated) {
      const { roleId } = userStore.data.resultData;
      actionUtil.handleChangePage(roleId);
    }
    return (
      <React.Fragment>
        {employeeCode ? (
          isEdit ? (
            <EditUserPage employeeCode={employeeParam} />
          ) : (
            <DetailUserPage employeeCode={employeeParam} />
          )
        ) : (
          <div className="limiter">
            <div className="container-login100">
              <div className="wrap-login100">
                <div className="login100-form-title">
                  <span className="login100-form-title-1">
                    <img
                      src="https://runsystem.net/wp-content/uploads/image_gmo/logo.png"
                      className="rounded mx-auto d-block"
                      alt="..."
                    />
                  </span>
                </div>
                {isResetPass ? <ResetPasswordComponent /> : <LoginComponent />}
              </div>
            </div>
          </div>
        )}
      </React.Fragment>
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
    userStore: state.userReducer
  };
};

export default connect(mapStateToProps, null)(UserPage);
