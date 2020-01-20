import React, { Component } from "react";
import { connect } from "react-redux";
import validateUtil from "../../../../shared/utils/validate.util";
import commonUtil from "../../../../shared/utils/common.util";
import { message } from "../../../../shared/constants/message.constant";
import { field } from "../../../../shared/constants/field.constant";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { resetPassword } from "./reset-password.action";
import { BrowserRouter, Link, Redirect } from "react-router-dom";
import AlertComponent from "../../../../shared/common/component/alert.component";
import { buttonType } from "../../../../shared/constants/button-type.constant";

/**
 * ForgetPassword Component
 */
class ResetPasswordComponent extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      error: {
        email: []
      },
      referrer: null
    };
  }

  /**
   * handle event when text change
   * @param {} value
   * @param {} name
   */
  handleChange = e => {
    const { value, name } = e.target;
    this.setState({ [name]: value }, () => {
      let error = {
        email: []
      };
      // this.checkValidateForm();
      if (validateUtil.isEmpty(this.state.email)) {
        error.email.push(
          commonUtil.parseMessage(message.MSG_ERROR_004, [field.EMAIL])
        );
      } else {
        if (!validateUtil.checkEmail(this.state.email)) {
          error.email.push(
            commonUtil.parseMessage(message.MSG_ERROR_006, [field.EMAIL])
          );
        } else if (validateUtil.inRange(this.state.email, 0, 256)) {
          error.email.push(
            commonUtil.parseMessage(message.MSG_ERROR_005, [
              field.EMAIL,
              "0",
              "256"
            ])
          );
        }
      }
      this.setState({ error: error });
    });
  };

  /**
   * handle event when submit form
   * @param {} e
   */
  handleSubmit = e => {
    e.preventDefault();
    const { email } = this.state;
    if (this.checkValidateForm()) {
      this.props.resetPasswordAction(email);
      // set url to login page
      this.setState({ referrer: "/login" });
    }
  };

  /**
   * check validate when login
   */
  checkValidateForm = () => {
    const { email } = this.state;

    let error = {
      email: []
    };

    let isValid = true;

    /**
     * validate field email
     * check isEmpty
     * check length
     * check pattern is e-mail
     *
     */
    if (validateUtil.isEmpty(email)) {
      error.email.push(
        commonUtil.parseMessage(message.MSG_ERROR_004, [field.EMAIL])
      );
      isValid = false;
      this.setState({ error: error });
    } else if (!validateUtil.checkEmail(email)) {
      error.email.push(
        commonUtil.parseMessage(message.MSG_ERROR_006, [field.EMAIL])
      );
      isValid = false;
      this.setState({ error: error });
    } else if (validateUtil.inRange(email, 0, 256)) {
      error.email.push(
        commonUtil.parseMessage(message.MSG_ERROR_005, [
          field.EMAIL,
          "0",
          "256"
        ])
      );
      isValid = false;
      this.setState({ error: error });
    }
    return isValid;
  };

  /**
   * render
   */
  render() {
    // get value userStore
    const { userStore, messageStore } = this.props;
    // get error
    const { email, error, referrer } = this.state;

    //redirect to LoginPage
    // if (userStore.isResetPassword) return <Redirect to={referrer} />;

    return (
      <form className="login100-form validate-form">
        {userStore.error && <AlertComponent messageObj={messageStore} />}
        {userStore.isResetPassword && (
          <AlertComponent messageObj={messageStore} />
        )}
        <div
          className="wrap-input100 validate-input  m-b-15"
          data-validate="Email is required"
        >
          <span className="label-input100">Email</span>
          <input
            className="input100"
            type="text"
            name="email"
            value={email}
            placeholder="Enter email"
            onChange={this.handleChange}
          />

          <span className="focus-input100"></span>
        </div>
        {error.email.length > 0 && <ErrorComponent error={error.email} />}
        <div className="flex-sb-m w-full p-b-30">
          <div>
            <a href="/login" className="txt1">
              Login with an account!
            </a>
          </div>
        </div>
        <div className="container-login100-form-btn">
          <button className="login100-form-btn" onClick={this.handleSubmit}>
            <b>{buttonType.BUTTON_SUBMIT}</b>
          </button>
        </div>
      </form>
    );
  }
}

/**
 * map state to props of component
 * @param {Object} state
 * @return {Object} props
 */
const mapStateToProps = state => {
  return {
    userStore: state.userReducer,
    messageStore: state.messageReducer
  };
};

/**
 * map dispatch to props
 * @param {ActionCreator} dispatch
 * @param {String} props
 * @return {ActionCreator}
 */
const mapDispatchToProps = (dispatch, props) => {
  return {
    resetPasswordAction: email => {
      dispatch(resetPassword(email));
    }
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ResetPasswordComponent);
