import React, { Component } from "react";
import { BrowserRouter, Link, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { logout } from "../../../main/modules/user/login/login.action";
import { role, position } from "../../../shared/constants/role.constant";
import history from "../../../config/history.config";
import actionUtil from "../../utils/action.util";
import "../../../shared/layouts/header/header.css";
/**
 * Header Component
 */
class Header extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
    this.state = {
      referrer: null
    };
  }

  /**
   * redirectToLogin
   */
  redirectToLogin = e => {
    e.preventDefault();
    // set url to login page
    this.setState({ referrer: "/login" });
    this.props.logoutAction();
  };

  onRedirectPage = () => {
    const { roleId } = this.props.userStore.data.resultData;
    actionUtil.handleChangePage(roleId);
  };

  /**
   * render
   */
  render() {
    const { isAuthenticated, data } = this.props.userStore;
    const { referrer } = this.state;
    //redirect to LoginPage
    if (referrer && !isAuthenticated) {
      return <Redirect to={referrer} />;
    }
    // Set role for PM and TL
    let isProjectManagement = [];
    let isTeamLead = [];
    if (data.resultData) {
      if (data.resultData.projectInfo.length > 0) {
        isProjectManagement = data.resultData.projectInfo.filter(
          project => +project.positionCode === +position.POSITION_PM
        );

        isTeamLead = data.resultData.projectInfo.filter(
          project => +project.positionCode === +position.POSITION_TL
        );
      }
    }

    return (
      <React.Fragment>
        {isAuthenticated && (
          <header>
            <nav className="navbar navbar-expand-sm navbar-dark bg-dark">
              <img
                src="https://runsystem.net/wp-content/uploads/image_gmo/logo.png"
                alt=""
                className="navbar-brand cursor-header"
                onClick={this.onRedirectPage}
              />
              <div className="collapse navbar-collapse" id="collapsibleNavId">
                <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
                  {/* {(+data.resultData.roleId === +role.ROLE_ADMIN ||
                    +data.resultData.roleId === +role.ROLE_SUB_ADMIN) && (
                    <li className="nav-item">
                      <a href="/user/list" className="nav-link">
                        Nhân Viên
                      </a>
                    </li>
                  )} */}

                  {/* <li className="nav-item">
                    <div className="nav-link">Check In-Out</div>
                  </li>
                  <li className="nav-item dropdown">
                    <div
                      className="nav-link dropdown-toggle"
                      id="dropdownId"
                      data-toggle="dropdown"
                    >
                      Báo Cáo
                    </div>
                    <div className="dropdown-menu">
                      <a href="" className="dropdown-item">
                        Chấm Công
                      </a>
                      <a href="" className="dropdown-item">
                        Thống Kê Phép
                      </a>
                    </div>
                  </li> */}
                  <li className="nav-item dropdown">
                    {(+data.resultData.roleId === +role.ROLE_ADMIN ||
                      +data.resultData.roleId === +role.ROLE_QA ||
                      +data.resultData.roleId === +role.ROLE_USER) && (
                      <React.Fragment>
                        <div
                          className="nav-link dropdown-toggle"
                          id="dropdownProject"
                          data-toggle="dropdown"
                        >
                          Dự Án
                        </div>
                        <div className="dropdown-menu">
                          {(+data.resultData.roleId === +role.ROLE_ADMIN ||
                            +data.resultData.roleId === +role.ROLE_QA) && (
                            <a href="/project/create" className="dropdown-item">
                              Tạo Mới Dự Án
                            </a>
                          )}
                          <a href="/project/list" className="dropdown-item">
                            Danh Sách Dự Án
                          </a>
                        </div>
                      </React.Fragment>
                    )}
                  </li>

                  <li className="nav-item dropdown">
                    {(+data.resultData.roleId === +role.ROLE_ADMIN ||
                      +data.resultData.roleId === +role.ROLE_QA ||
                      (+data.resultData.roleId === +role.ROLE_USER &&
                        (isProjectManagement.length > 0 ||
                          isTeamLead.length > 0))) && (
                      <React.Fragment>
                        <div
                          className="nav-link dropdown-toggle"
                          id="dropdownId"
                          data-toggle="dropdown"
                        >
                          Quản Lý OT
                        </div>
                        <div className="dropdown-menu">
                          {+data.resultData.roleId === +role.ROLE_USER &&
                            (isProjectManagement.length > 0 ||
                              isTeamLead.length > 0) && (
                              <a
                                href="/request/create"
                                className="dropdown-item"
                              >
                                Đăng Ký OT
                              </a>
                            )}
                          <a href="/request/list" className="dropdown-item">
                            Danh Sách Đăng Ký OT
                          </a>
                          <a href="/approval/list" className="dropdown-item">
                            Danh Sách Approval OT
                          </a>
                        </div>
                      </React.Fragment>
                    )}
                  </li>
                </ul>
                <ul className="navbar-nav mt-2 mt-lg-0">
                  <li className="nav-item dropdown">
                    <div
                      className="nav-link dropdown-toggle"
                      id="dropdownId"
                      data-toggle="dropdown"
                    >
                      <i className="fa fa-user-circle mr-2"></i>
                      {data.resultData.employeeName}
                    </div>
                    <div className="dropdown-menu dropdown-menu-right">
                      <a
                        href={"/user/detail/" + data.resultData.employeeCode}
                        className="dropdown-item"
                      >
                        Thông Tin
                      </a>
                      <a href="/change-password" className="dropdown-item">
                        Thay đổi mật khẩu
                      </a>
                      <a
                        onClick={this.redirectToLogin}
                        className="dropdown-item"
                      >
                        Đăng Xuất
                      </a>
                    </div>
                  </li>
                </ul>
              </div>
            </nav>
          </header>
        )}
      </React.Fragment>
    );
  }
}

/**
 * map state to props of component
 * @param {Object} state
 */
const mapStateToProps = state => {
  return {
    userStore: state.userReducer
  };
};

/**
 * map dispatch to props of component
 * @param {Action Creator} dispatch
 * @param {Object} props
 */
const mapDispatchToProps = (dispatch, props) => {
  return {
    logoutAction: () => {
      dispatch(logout());
    }
  };
};

// export Header component
export default connect(mapStateToProps, mapDispatchToProps)(Header);
