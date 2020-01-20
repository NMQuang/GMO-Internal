import React, { Component } from "react";
import "./forbidden.css";

class ForbiddenPageComponent extends Component {
  render() {
    return (
      <div id="forbidden">
        <div className="forbidden">
          <div className="forbidden-403">
            <h1>403</h1>
          </div>
          <h2>Oops! Forbidden</h2>
          <p>Sorry but you are not authorized to access this page.</p>
          <a href="/request/list">Back to homepage</a>
        </div>
      </div>
    );
  }
}

export default ForbiddenPageComponent;
