import React, { Component } from "react";
import "./request-timeout.css";

class RequestTimeoutPageComponent extends Component {
  render() {
    return (
      <div id="request-timeout">
        <div className="request-timeout">
          <div className="request-timeout-408">
            <h1>408</h1>
          </div>
          <h2>Oops! Request timeout</h2>
          <p>
            This request takes too long to process, it is timed out by the
            server. If it should not be timed out. please contact administrator
            Of this web site to increase 'Connection Timeout'
          </p>
          <a href="/request/list">Back to homepage</a>
        </div>
      </div>
    );
  }
}

export default RequestTimeoutPageComponent;
