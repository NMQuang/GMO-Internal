import React, { Component } from "react";
import { connect } from "react-redux";
import CreateRequestComponent from "./create-request.component";
import createRequestOTAction from "./create-request.action";
/**
 * Request page component
 */
class CreateRequestPageComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      date: new Date()
    };
  }
  /**
   * Component Did Mount
   */
  componentDidMount() {
    this.props.getProjectByEmployee();
  }
  /**
   * Handle call back from the child
   * @param data
   */
  onHandleCreateRequest = data => {
    // Create request OT
    this.props.createRequestOTAction(data);
  };

  onHandleChangeProject = projectCode => {
    this.props.getEmployeeByProject(projectCode);
  };

  /**
   * Render
   */
  render() {
    let userStore = [];
    const { messageStore } = this.props;
    /**
     * Check exist userStore
     */
    if (this.props.userStore) {
      userStore = this.props.userStore;
    }

    return (
      <CreateRequestComponent
        messageStore={messageStore}
        onHandleCreateRequest={this.onHandleCreateRequest}
        userStore={userStore}
        requestStore={this.props.requestStore}
        onHandleProject={this.onHandleChangeProject}
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
    userStore: state.userReducer,
    requestStore: state.requestReducer
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
    // Create request OT action
    createRequestOTAction: data => {
      dispatch(createRequestOTAction.createRequestOT(data));
    },
    getProjectByEmployee: () => {
      dispatch(createRequestOTAction.getProjectByEmployee());
    },
    getEmployeeByProject: projectCode => {
      dispatch(createRequestOTAction.getEmployeeByProject(projectCode));
    }
  };
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CreateRequestPageComponent);
