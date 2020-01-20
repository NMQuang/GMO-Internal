import React, { Component } from "react";
import { BrowserRouter, Link, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import "./login.css";
import { login } from "./login.action";
import { role } from "../../../../shared/constants/role.constant.js";
import validateUtil from "../../../../shared/utils/validate.util";
import commonUtil from "../../../../shared/utils/common.util";
import { message } from "../../../../shared/constants/message.constant";
import { field } from "../../../../shared/constants/field.constant";
import ErrorComponent from "../../../../shared/common/component/error.component";
import AlertComponent from "../../../../shared/common/component/alert.component";
import { buttonType } from "../../../../shared/constants/button-type.constant";
import actionUtil from "../../../../shared/utils/action.util";
import history from "../../../../config/history.config";

// define login Component
class LoginComponent extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      error: {
        email: [],
        password: []
      }
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
        email: [],
        password: []
      };

      // validate field email
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

      //validate field password
      if (validateUtil.isEmpty(this.state.password)) {
        error.password.push(
          commonUtil.parseMessage(message.MSG_ERROR_004, [field.PASSWORD])
        );
      } else {
        if (!validateUtil.checkPassword(this.state.password)) {
          error.password.push(
            commonUtil.parseMessage(message.MSG_ERROR_007, [field.PASSWORD])
          );
        } else if (validateUtil.inRange(this.state.password, 6, 128)) {
          error.password.push(
            commonUtil.parseMessage(message.MSG_ERROR_005, [
              field.PASSWORD,
              "6",
              "128"
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
    const { email, password } = this.state;
    if (this.checkValidateForm()) {
      this.props.loginAction(email, password);
    }
  };

  /**
   * check validate when login
   */
  checkValidateForm = () => {
    const { email, password } = this.state;

    let error = {
      email: [],
      password: []
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

    /**
     * validate field password
     * check isEmpty
     * check length
     * check pattern is password
     *
     */
    if (validateUtil.isEmpty(password)) {
      error.password.push(
        commonUtil.parseMessage(message.MSG_ERROR_004, [field.PASSWORD])
      );
      isValid = false;
      this.setState({ error: error });
    } else if (!validateUtil.checkPassword(password)) {
      error.password.push(
        commonUtil.parseMessage(message.MSG_ERROR_007, [field.PASSWORD])
      );
      isValid = false;
      this.setState({ error: error });
    } else if (validateUtil.inRange(password, 6, 128)) {
      error.password.push(
        commonUtil.parseMessage(message.MSG_ERROR_005, [
          field.PASSWORD,
          "6",
          "128"
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
    // get error
    const { email, password, error } = this.state;
    // get value userStore
    const { userStore, messageStore } = this.props;

    return (
      <form className="login100-form validate-form">
        {userStore.error && <AlertComponent messageObj={messageStore} />}
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
        <div
          className="wrap-input100 validate-input m-b-15"
          data-validate="Password is required"
        >
          <span className="label-input100">Password</span>
          <input
            className="input100"
            type="password"
            name="password"
            value={password}
            placeholder="Enter password"
            onChange={this.handleChange}
          />
          <span className="focus-input100"></span>
        </div>
        {error.password.length > 0 && <ErrorComponent error={error.password} />}
        <div className="flex-sb-m w-full p-b-30">
          <div className="contact100-form-checkbox">
            <input
              className="input-checkbox100"
              id="ckb1"
              type="checkbox"
              name="remember-me"
            />
            <label className="label-checkbox100" htmlFor="ckb1">
              Remember me
            </label>
          </div>

          <div>
            <a href="/reset-password" className="txt1">
              Forgot Password?
            </a>
          </div>
        </div>

        <div className="container-login100-form-btn">
          <button className="login100-form-btn" onClick={this.handleSubmit}>
            <b>{buttonType.BUTTON_LOGIN}</b>
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
    loginAction: (email, password) => {
      dispatch(login(email, password));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(LoginComponent);
