import React, { Component } from "react";
import CreateRequestPageComponent from "./create-requestOT/create-request-page";
import UpdateRequestPageComponent from "./update-requestOT/update-request.page";
import { connect } from "react-redux";
import { role, position } from "../../../../shared/constants/role.constant";
import { Redirect } from "react-router-dom";
/**
 * Request page component
 */
class RequestPageComponent extends Component {
  /**
   * constructor
   * @param props
   */
  constructor(props) {
    super(props);
  }
  /**
   * Render
   */
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
        const { isUpdate } = this.props;
        let requestId = "";
        let dateSearch = "";
        if (isUpdate) {
          requestId = this.props.requestId.match.params.requestId;
          dateSearch =
            typeof this.props.requestId.location.query === "undefined"
              ? ""
              : this.props.requestId.location.query.startDate;
        }
        return (
          <div>
            {isUpdate ? (
              <UpdateRequestPageComponent
                requestId={requestId}
                dateSearch={dateSearch}
              />
            ) : (
              <CreateRequestPageComponent />
            )}
          </div>
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

function mapStateToProps(state) {
  return {
    userStore: state.userReducer
  };
}
export default connect(mapStateToProps, null)(RequestPageComponent);
