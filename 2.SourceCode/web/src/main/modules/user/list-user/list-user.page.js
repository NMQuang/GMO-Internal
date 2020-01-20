import React, { Component } from "react";
import { connect } from "react-redux";
import ListUserComponent from "./list-user.component";
import { search, exportEmployee } from "./list-user.action";
import page from "../../../../shared/constants/page.constant";

class ListUserPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      querySearch: {
        employeeCode: "",
        empployeeName: "",
        birthday: "",
        position: "",
        division: "",
        probationaryDay: "",
        officialDay: "",
        workContact: "",
        status: "",
        page: 1,
        totalRecordOfPage: page.RECORD_PER_PAGE_5,
        searchOrExportFlag: 1,
        headerOptionFlag: 0,
        headerList: []
      }
    };
  }

  componentDidMount() {
    this.props.search(this.state.querySearch);
  }

  onClickSearch = querySearch => {
    this.props.search(querySearch);
  };

  onExport = querySearch => {
    this.props.export(querySearch);
  };

  render() {
    return (
      <ListUserComponent
        userStore={this.props.userStore}
        onClickSearch={this.onClickSearch}
        onExport={this.onExport}
        exportStore={this.props.exportStore}
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
    userStore: state.userReducer.dataResponse,
    exportStore: state.userReducer.dataExport
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
    search: querySearch => {
      dispatch(search(querySearch));
    },
    export: querySearch => {
      dispatch(exportEmployee(querySearch));
    }
  };
}
export default connect(mapStateToProps, mapDispatchToProps)(ListUserPage);
