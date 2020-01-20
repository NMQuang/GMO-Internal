import React, { Component } from "react";
import { connect } from "react-redux";
import EditUserComponent from "./edit-user.component";
import { getDetailEmployee, updateEmployee } from "./edit-user.action";

/**
 * Change password page component
 */
class EditUserPage extends Component {
  constructor(props) {
    super(props);
  }
  componentDidMount = () => {
    // get detail employee
    this.props.getDetailEmployeeAction(this.props.employeeCode);
  };

  onUpdateEmployee = employeeInfo => {
    //update employee
    this.props.updateEmployeeAction(employeeInfo);
  };

  render() {
    return (
      <EditUserComponent
        userStore={this.props.userStore}
        messageStore={this.props.messageStore}
        employeeCurrent={this.props.employeeCode}
        onUpdate={this.onUpdateEmployee}
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
    },
    // update employee
    updateEmployeeAction: employeeInfo => {
      dispatch(updateEmployee(employeeInfo));
    }
  };
}
export default connect(mapStateToProps, mapDispatchToProps)(EditUserPage);
