import React, { Component } from "react";
import "./detail-project.css";
import AlertComponent from "../../../../shared/common/component/alert.component";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { message } from "../../../../shared/constants/message.constant";
import { buttonType } from "../../../../shared/constants/button-type.constant";
import { Redirect, Link } from "react-router-dom";
import history from "../../../../config/history.config";
import { role, position } from "../../../../shared/constants/role.constant";
import DatePicker from "react-datepicker";
import { dateTimePattern } from "../../../../shared/constants/date-time-pattern.constant";
import commonUtil from "../../../../shared/utils/common.util";
import actionUtil from "../../../../shared/utils/action.util";
import ModalComponent from "../../../../shared/common/component/modal.component";
import validateUtil from "../../../../shared/utils/validate.util";
import moment from "moment";
import page from "../../../../shared/constants/page.constant";
import DetailProjectComponent from "./detail-project.component";
import { getDetail } from "./detail-project.action";
import { connect } from "react-redux";

class DetailProjectPage extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount = () => {
    this.props.getDetailProject(this.props.projectCode);
  };
  render() {
    return (
      <DetailProjectComponent
        projectStore={this.props.projectStore}
        messageStore={this.props.messageStore}
        userStore={this.props.userStore}
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
    getDetailProject: projectCode => {
      dispatch(getDetail(projectCode));
    }
  };
}
export default connect(mapStateToProps, mapDispatchToProps)(DetailProjectPage);
