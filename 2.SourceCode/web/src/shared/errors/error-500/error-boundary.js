import React, { Component } from "react";
import "./error-boundary.css";
/**
 * ErrorBoundary Component
 */
class ErrorBoundaryComponent extends Component {
  constructor(props) {
    super(props);
    this.state = { error: "", errorInfo: "" };
  }
  /**
   * componentDidCatch
   * @param {*} error
   * @param {*} errorInfo
   */
  componentDidCatch(error, errorInfo) {
    this.setState({
      error,
      errorInfo
    });
  }

  /**
   * render
   */
  render() {
    const { error, errorInfo } = this.state;
    if (errorInfo) {
      const errorDetails = (
        <details classNameName="preserve-space">
          {error && error.toString()}
          <br />
          {errorInfo.componentStack}
        </details>
      );
      return (
        <div id="internalserver">
          <div className="internalserver">
            <div className="internalserver-500">
              <h1>500</h1>
            </div>
            <h2>Oops! Internal Server Error</h2>
            <p>
              The server detected an error and could not execute it. Please try
              again after a while, or contact your administrator.
            </p>
            <a href="/request/list">Back to homepage</a>
          </div>
        </div>
      );
    }
    return this.props.children;
  }
}

export default ErrorBoundaryComponent;
