import React, { Component } from "react";
import { connect } from "react-redux";
import DetailUserComponent from "./detail-user.component";
import { getDetailEmployee } from "./detail-user.action";

/**
 * Change password page component
 */
class DetailUserPage extends Component {
  constructor(props) {
    super(props);
  }
  componentDidMount = () => {
    const { employeeCode } = this.props;
    let employeeParam = null;
    employeeParam = employeeCode.match.params.employeeCode;
    // get detail employee
    this.props.getDetailEmployeeAction(employeeParam);
  };
  render() {
    const { employeeCode } = this.props;
    let employeeParam = null;
    employeeParam = employeeCode.match.params.employeeCode;
    return (
      <DetailUserComponent
        userStore={this.props.userStore}
        messageStore={this.props.messageStore}
        employeeCurrent={employeeParam}
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
    userStore: state.userReducer,
    messageStore: state.messageReducer
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
    // get detail employee
    getDetailEmployeeAction: employeeCode => {
      dispatch(getDetailEmployee(employeeCode));
    }
  };
}
export default connect(mapStateToProps, mapDispatchToProps)(DetailUserPage);
