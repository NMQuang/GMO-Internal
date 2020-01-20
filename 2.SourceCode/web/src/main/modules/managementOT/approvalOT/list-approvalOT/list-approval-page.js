import React, { Component } from "react";
import ListApprovalComponent from "./list-approval.component";
import { connect } from "react-redux";
import moment from "moment";
import page from "../../../../../shared/constants/page.constant";
import listRequestOTAction from "./../../requestOT/list-requestOT/list-request.action";
import listApprovalAction from "./list-approval.action";
import { role, position } from "../../../../../shared/constants/role.constant";
import { Redirect } from "react-router-dom";
class ApprovalPageComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      date: new Date(),
      dateOld: new Date()
    };
  }
  /**
   * Component Will Mount
   */
  componentDidMount() {
    let date = new Date();
    date = moment(date).format("YYYY-MM");
    let initData = {
      currentPage: "1",
      totalRecords: page.RECORD_PER_PAGE_5,
      requestDate: date
    };
    this.setState({ dateOld: moment(date).format("YYYY-MM") });
    this.props.getProjectNameListAction();
    this.props.getListApprovalAction(initData);
  }
  /**
   * Handle call back from the child to search data
   * @param searchDate
   * @param searchStatus
   * @param searchProjectName
   * @param currentPage
   */
  onHandle = (searchDate, searchProjectName, currentPage) => {
    let data = {};
    let projectName = "";
    /**
     * Check if searchProjectName = null or empty then have not search by project name
     */
    if (searchProjectName === "" || searchProjectName == null) {
      projectName = "";
    } else {
      projectName = searchProjectName;
    }
    data = {
      currentPage: currentPage,
      totalRecords: page.RECORD_PER_PAGE_5,
      projectName: projectName,
      requestDate: searchDate
    };
    this.setState({ dateOld: moment(searchDate).format("YYYY-MM") });
    this.props.getListApprovalAction(data);
  };
  /**
   * Handle call back from the child to export data
   * @param searchDate
   * @param searchProjectName
   */
  handleExprotData = (searchDate, searchProjectName) => {
    let data = {};
    /**
     * Check if searchProjectName = null or empty then have not search by project name
     */
    if (searchProjectName === "" || searchProjectName == null) {
      data = {
        requestDate: searchDate
      };
    } else {
      data = {
        projectName: searchProjectName,
        requestDate: searchDate
      };
    }
    this.props.exportData(data);
  };
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
        const { dateOld } = this.state;
        let listApproval = "";
        /**
         * Check exist project name
         */
        if (this.props.projectNameList) {
          listProjectName = this.props.projectNameList.resultData;
        }
        if (this.props.approvalList) {
          listApproval = this.props.approvalList.resultData;
        }
        return (
          <ListApprovalComponent
            handleExprotData={this.handleExprotData}
            dateOl={dateOld}
            onHandle={this.onHandle}
            listApproval={listApproval}
            userStore={this.props.userStore}
            requestStore={this.props.requestStore}
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
    exportData: state.approvalReducer.isExportData,
    requestStore: state.requestReducer,
    approvalList: state.approvalReducer.listApproval,
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
    //Exprot data
    exportData: data => {
      dispatch(listApprovalAction.exportData(data));
    },
    // Get list approval action
    getListApprovalAction: data => {
      dispatch(listApprovalAction.getListApproval(data));
    },
    // Get list project name
    getProjectNameListAction: () => {
      dispatch(listRequestOTAction.getProjectNameList());
    }
  };
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ApprovalPageComponent);
