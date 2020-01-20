import React, { Component } from "react";
import { connect } from "react-redux";
import ListProjectComponent from "./list-project.component";
import page from "../../../../shared/constants/page.constant";
import { search } from "./list-project.action";
import { dateTimePattern } from "../../../../shared/constants/date-time-pattern.constant";
import moment from "moment";
import commonUtil from "../../../../shared/utils/common.util";
import history from "../../../../config/history.config";
import { storage } from "../../../../shared/constants/storage.constant";

class ListProjectPage extends Component {
  /**
   * Constructor
   *
   * @param props
   */
  constructor(props) {
    super(props);
    this.state = {
      querySearch: {
        projectName: "",
        startDate: "",
        endDate: "",
        page: 1,
        totalRecordOfPage: page.RECORD_PER_PAGE_5
      }
    };
  }

  componentDidMount() {
    const { querySearch } = this.state;
    let querySearchStorage = sessionStorage.getItem(storage.SEARCH_PROJECT);

    // Search project by querysearch in session storage or all
    if (querySearchStorage) {
      this.props.search(JSON.parse(querySearchStorage));
    } else {
      // Register condition search project into session storage
      sessionStorage.setItem(
        storage.SEARCH_PROJECT,
        JSON.stringify(querySearch)
      );
      this.props.search(querySearch);
    }
  }

  onClickSearch = querySearch => {
    // Register condition search project into session storage
    sessionStorage.setItem(storage.SEARCH_PROJECT, JSON.stringify(querySearch));
    this.props.search(querySearch);
  };

  render() {
    // Re-set session project when currentUri === session and diff project/detail and project/edit
    let prevUri = sessionStorage.getItem(storage.PREV_URI);
    let querySearchStorage = null;
    if (
      commonUtil.getPathNameUri() ===
        sessionStorage.getItem(storage.CURRENT_URI) ||
      prevUri.includes("/project")
    ) {
      querySearchStorage = sessionStorage.getItem(storage.SEARCH_PROJECT);
    } else {
      sessionStorage.removeItem(storage.SEARCH_PROJECT);
    }
    // Search project by querysearch in session storage or all
    if (querySearchStorage) {
      querySearchStorage = JSON.parse(querySearchStorage);
    }
    return (
      <ListProjectComponent
        projectStore={this.props.projectStore}
        messageStore={this.props.messageStore}
        userStore={this.props.userStore}
        onClickSearch={this.onClickSearch}
        querySearchStorage={querySearchStorage}
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
    // Get project
    search: querySearch => {
      dispatch(search(querySearch));
    }
  };
}
export default connect(mapStateToProps, mapDispatchToProps)(ListProjectPage);
