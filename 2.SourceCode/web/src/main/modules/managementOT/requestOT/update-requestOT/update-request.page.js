import React, { Component } from "react";
import UpdateRequestComponent from "./update-request.component";
import { connect } from "react-redux";
import createRequestOTAction from "../create-requestOT/create-request.action";
import updateRequestOTAction from "./update-request.action";
import moment from "moment";
import { dateTimePattern } from "../../../../../shared/constants/date-time-pattern.constant";
/**
 * Request update request page component
 */
class UpdateRequestPageComponent extends Component {
  /**
   * constructor
   * @param props
   */
  constructor(props) {
    super(props);
  }
  /**
   * Handle update request OT
   * @param data
   */
  updateRequestOT = data => {
    this.props.updateRequestOTAction(data);
  };
  /**
   * Handle calcel request OT
   * @param data
   */
  cancleRequestOT = data => {
    this.props.cancleRequestOTAction(data);
  };
  /**
   * Handle approval request OT
   * @param data
   */
  approvalRequestOT = data => {
    this.props.approvalRequestOTAction(data);
  };
  /**
   * Handle verify request OT
   * @param data
   */
  verifyRequestOT = data => {
    this.props.verifyRequestOTAction(data);
  };
  /**
   * Handle send email
   * @param data
   */
  sendEmailRequestOT = data => {
    this.props.sendEmailRequestOTAction(data);
  };
  /**
   * Component did mount
   */
  componentDidMount() {
    // this.props.getProjectByEmployee();
    let dateSearch =
      this.props.dateSearch === ""
        ? moment(new Date()).format(dateTimePattern.YYYYMM)
        : this.props.dateSearch;
    // Get detail request OT
    this.props.getDetailRequestOTAction(this.props.requestId, dateSearch);
  }

  onLoadInit = () => {
    let dateSearch = moment(new Date()).format(dateTimePattern.YYYYMM);
    this.props.getDetailRequestOTAction(this.props.requestId, dateSearch);
  };

  /**
   * Render
   */
  render() {
    let requestId = "";
    requestId = this.props.requestId;
    const { requestStore, messageStore, userStore } = this.props;
    const { errorUpdateRequest } = requestStore;
    return (
      <UpdateRequestComponent
        requestId={requestId}
        requestStore={requestStore}
        // messageStore={messageStore}
        errorUpdateRequest={errorUpdateRequest}
        userStore={userStore}
        updateRequestOT={this.updateRequestOT}
        cancleRequestOT={this.cancleRequestOT}
        approvalRequestOT={this.approvalRequestOT}
        sendEmailRequestOT={this.sendEmailRequestOT}
        onLoadInit={this.onLoadInit}
        verifyRequestOT={this.verifyRequestOT}
      />
    );
  }
}

/**
 * Map state to props
 * @param state
 */
function mapStateToProps(state) {
  return {
    messageStore: state.messageReducer,
    requestStore: state.requestReducer,
    userStore: state.userReducer,
    notFoundStore: state.notFoundReducer
  };
}
/**
 * Map dispatch to prop
 *
 * @param dispatch
 */
function mapDispatchToProps(dispatch) {
  return {
    // Get detail request
    getDetailRequestOTAction: (requestId, dateSearch) => {
      dispatch(updateRequestOTAction.getDetailRequestOT(requestId, dateSearch));
    },
    // Update request ot
    updateRequestOTAction: data => {
      dispatch(updateRequestOTAction.updateRequestOT(data));
    },
    // Cancle request ot
    cancleRequestOTAction: data => {
      dispatch(updateRequestOTAction.cancelRequestOT(data));
    },
    // Approval request ot
    approvalRequestOTAction: data => {
      dispatch(updateRequestOTAction.approvalRequestOT(data));
    },
    // Send email request ot
    sendEmailRequestOTAction: data => {
      dispatch(updateRequestOTAction.sendEmailRequestOT(data));
    },
    getProjectByEmployee: () => {
      dispatch(createRequestOTAction.getProjectByEmployee());
    },
    // Verify request ot
    verifyRequestOTAction: data => {
      dispatch(updateRequestOTAction.verifyRequestOT(data));
    }
  };
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UpdateRequestPageComponent);
