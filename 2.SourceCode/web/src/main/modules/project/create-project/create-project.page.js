import React, { Component } from "react";
import { connect } from "react-redux";
import CreateProjectComponent from "./create-project.component";
import createRequestOTAction from "../../managementOT/requestOT/create-requestOT/create-request.action";
import { createProject, getProjectRank } from "./create-project.action";
class ProjectPageComponent extends Component {
  constructor(props) {
    super(props);
  }
  componentDidMount = () => {
    // Get list employee
    this.props.getEmployeeList();
    // Get project rank
    this.props.getProjectRank();
  };

  onCreateProject = projectInfo => {
    this.props.createProject(projectInfo);
  };
  render() {
    return (
      <CreateProjectComponent
        employeeStore={this.props.requestStore}
        projectStore={this.props.projectStore}
        onCreate={this.onCreateProject}
        messageStore={this.props.messageStore}
      />
    );
  }
}

/**
 * map state to props of component
 * @param {Object} state
 * @return {Object} props
 */
function mapStateToProps(state) {
  return {
    messageStore: state.messageReducer,
    requestStore: state.requestReducer,
    projectStore: state.projectReducer
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
    // Get employee
    getEmployeeList: () => {
      dispatch(createRequestOTAction.getEmployeeList());
    },
    // Create project
    createProject: projectInfo => {
      dispatch(createProject(projectInfo));
    },
    // Get project rank
    getProjectRank: () => {
      dispatch(getProjectRank());
    }
  };
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectPageComponent);
