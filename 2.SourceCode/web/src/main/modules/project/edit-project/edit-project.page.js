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
import { connect } from "react-redux";
import EditProjectComponent from "./edit-project.component";
import { getDetail } from "../detail-project/detail-project.action";
import { editProject } from "./edit-project.action";

class EditProjectPage extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount = () => {
    let roleId = null;
    let isProjectRole = null;
    const { userStore } = this.props;
    if (userStore.data.resultData) {
      roleId = userStore.data.resultData.roleId;
      const objectProjectRole = userStore.data.resultData.projectInfo.filter(
        projectRole =>
          +projectRole.projectCode === +this.props.projectCode &&
          +projectRole.positionCode === +position.POSITION_PM
      );
      isProjectRole = objectProjectRole.length > 0 ? true : false;
      // Check permission
      if (
        +roleId === +role.ROLE_ADMIN ||
        (+roleId === +role.ROLE_USER && isProjectRole)
      ) {
        this.props.getDetailProject(this.props.projectCode);
      } else {
        history.push("/permission");
      }
    }
  };

  onLoadInit = () => {
    this.props.getDetailProject(this.props.projectCode);
  };

  onUpdate = projectInfo => {
    this.props.editProject(projectInfo);
  };
  render() {
    let roleId = null;
    let isProjectRole = null;
    const { userStore } = this.props;
    if (userStore.data.resultData) {
      roleId = userStore.data.resultData.roleId;
      const objectProjectRole = userStore.data.resultData.projectInfo.filter(
        projectRole =>
          +projectRole.projectCode === +this.props.projectCode &&
          +projectRole.positionCode === +position.POSITION_PM
      );
      isProjectRole = objectProjectRole.length > 0 ? true : false;

      // Check permission
      if (
        +roleId === +role.ROLE_ADMIN ||
        (+roleId === +role.ROLE_USER && isProjectRole)
      ) {
        return (
          <EditProjectComponent
            projectStore={this.props.projectStore}
            // messageStore={this.props.messageStore}
            userStore={this.props.userStore}
            onUpdate={this.onUpdate}
            onLoadInit={this.onLoadInit}
          />
        );
      } else {
        return (
          <Redirect
            to={{
              pathname: "/permission"
            }}
          />
        );
      }
    } else {
      return (
        <Redirect
          to={{
            pathname: "/permission"
          }}
        />
      );
    }
  }
}

/**
 * map state to props of component
 * @param {Object} state
 * @return {Object} props
 */
function mapStateToProps(state) {
  return {
    // messageStore: state.messageReducer,
    projectStore: state.projectReducer,
    userStore: state.userReducer
  };
}
/**
 * map dispatch to props
 * @param {ActionCreator} dispatch
 * @param {String} props
 * @return {ActionCreator}
 */
function mapDispatchToProps(dispatch) {
  return {
    getDetailProject: projectCode => {
      dispatch(getDetail(projectCode));
    },
    editProject: projectInfo => {
      dispatch(editProject(projectInfo));
    }
  };
}
export default connect(mapStateToProps, mapDispatchToProps)(EditProjectPage);
