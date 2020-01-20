import React, { Component } from "react";

/**
 * Inline Error Component
 * Component use to show error behind the input component
 */
class ErrorComponent extends Component {
  /**
   * render
   */
  render() {
    // error: error when check in client
    // errorResponse: error when check in server
    // field: attribute name of tag input
    const { error, errorResponse, field } = this.props;
    let errorList = [];
    // if error check in client => create errorList is error check in client
    // else create errorList is error check in server
    if (error) {
      errorList.push(
        <span key={error} className="text-danger">
          {error}
          <br />
        </span>
      );
    } else if (errorResponse) {
      for (let i = 0; i < errorResponse.length; i++) {
        if (errorResponse[i].fieldName === field) {
          errorList.push(
            <span key={i} className="text-danger">
              {errorResponse[i].message}
              <br />
            </span>
          );
        }
      }
    }
    return <React.Fragment>{errorList}</React.Fragment>;
  }
}

export default ErrorComponent;
