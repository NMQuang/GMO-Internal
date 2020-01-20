import React, { Component } from "react";
import DetailProjectPage from "./detail-project/detail-project.page";
import EditProjectPage from "./edit-project/edit-project.page";
import { connect } from "react-redux";
import { role, position } from "../../../shared/constants/role.constant";
import { Redirect } from "react-router-dom";

class ProjectPage extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    const { data } = this.props.userStore;
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
      // Check permission
      if (
        +data.resultData.roleId === +role.ROLE_ADMIN ||
        +data.resultData.roleId === +role.ROLE_QA ||
        (+data.resultData.roleId === +role.ROLE_USER &&
          (isProjectManagement.length > 0 || isTeamLead.length > 0))
      ) {
        // get value in param url
        const { projectCode, isEdit } = this.props;
        let projectParam = null;
        if (projectCode) {
          projectParam = this.props.projectCode.match.params.projectCode;
          return isEdit ? (
            <React.Fragment>
              <EditProjectPage projectCode={projectParam} />
            </React.Fragment>
          ) : (
            <React.Fragment>
              <DetailProjectPage projectCode={projectParam} />
            </React.Fragment>
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

function mapStateToProps(state) {
  return {
    userStore: state.userReducer
  };
}
export default connect(mapStateToProps, null)(ProjectPage);
