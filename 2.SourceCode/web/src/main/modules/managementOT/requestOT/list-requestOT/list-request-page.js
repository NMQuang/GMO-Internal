import React, { Component } from "react";
import { connect } from "react-redux";
import ListRequestComponent from "./list-request.component";
import listRequestOTAction from "./list-request.action";
import moment from "moment";
import page from "../../../../../shared/constants/page.constant";
import { position, role } from "../../../../../shared/constants/role.constant";
import { Redirect } from "react-router-dom";
import commonUtil from "../../../../../shared/utils/common.util";
import { storage } from "../../../../../shared/constants/storage.constant";
/**
 * Request page component
 */
class ListRequestPageComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      querySearch: {
        currentPage: 1,
        totalRecords: page.RECORD_PER_PAGE_5,
        projectName: "",
        status: "",
        requestOtDate: ""
      }
    };
  }
  /**
   * Component Did Mount
   */
  componentDidMount() {
    let date = new Date();
    date = moment(date).format("YYYY-MM");

    this.setState(
      {
        querySearch: {
          ...this.state.querySearch,
          requestOtDate: date
        }
      },
      () => {
        const { querySearch } = this.state;
        this.props.getProjectNameListAction();
        // Re-set session request when currentUri === session and diff request/edit
        let querySearchStorage = sessionStorage.getItem(storage.SEARCH_REQUEST);

        // Search request by querysearch in session storage or all
        if (querySearchStorage) {
          this.props.getRequestListAction(JSON.parse(querySearchStorage));
        } else {
          this.props.getRequestListAction(querySearch);
          // Register condition search request into session storage
          sessionStorage.setItem(
            storage.SEARCH_REQUEST,
            JSON.stringify(querySearch)
          );
        }
      }
    );
  }
  /**
   * Handle call back from the child
   * @param searchDate
   * @param searchStatus
   * @param searchProjectName
   * @param currentPage
   */
  onHandle = (searchDate, searchStatus, searchProjectName, currentPage) => {
    this.setState(
      {
        querySearch: {
          ...this.state.querySearch,
          currentPage: currentPage,
          projectName: searchProjectName,
          status: searchStatus,
          requestOtDate: searchDate
        }
      },
      () => {
        const { querySearch } = this.state;
        this.props.getRequestListAction(querySearch);
      }
    );
  };

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
        let listProjectName = [];
        let requestOTList = [];
        /**
         * Check exist project name
         */
        if (this.props.projectNameList) {
          listProjectName = this.props.projectNameList.resultData;
        }
        /**
         * Check exist request OT List
         */
        if (this.props.requestOTList) {
          requestOTList = this.props.requestOTList;
        }

        // Re-set session request when currentUri === session and diff request/edit
        let prevUri = sessionStorage.getItem(storage.PREV_URI);
        let currentUri = sessionStorage.getItem(storage.CURRENT_URI);
        let querySearchStorage = null;
        if (
          commonUtil.getPathNameUri() === currentUri ||
          (currentUrl.includes("/request/edit") &&
            prevUrl.includes("/request/list"))
        ) {
          querySearchStorage = sessionStorage.getItem(storage.SEARCH_REQUEST);
        } else {
          sessionStorage.removeItem(storage.SEARCH_REQUEST);
        }

        // Search request by querysearch in session storage or all
        if (querySearchStorage) {
          querySearchStorage = JSON.parse(querySearchStorage);
        }

        return (
          <ListRequestComponent
            onHandle={this.onHandle}
            requestStore={this.props.requestStore}
            listRequestOT={requestOTList}
            messageStore={this.props.messageStore}
            userStore={this.props.userStore}
            querySearchStorage={querySearchStorage}
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
    userStore: state.userReducer,
    messageStore: state.messageReducer,
    requestStore: state.requestReducer,
    projectNameList: state.requestReducer.projectNameList,
    requestOTList: state.requestReducer.requestList.resultData
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
    // Get list request action
    getRequestListAction: data => {
      dispatch(listRequestOTAction.getListRequest(data));
    },
    getProjectNameListAction: () => {
      dispatch(listRequestOTAction.getProjectNameList());
    }
  };
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ListRequestPageComponent);
