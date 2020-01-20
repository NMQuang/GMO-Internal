import React, { Component } from "react";
import "./internal-server.css";

class InternalServerPageComponent extends Component {
  render() {
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
}

export default InternalServerPageComponent;
