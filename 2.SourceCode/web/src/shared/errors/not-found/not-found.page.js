import React, { Component } from "react";
import "./not-found.css";

class NotFoundPageComponent extends Component {
  render() {
    return (
      <div id="notfound">
        <div className="notfound">
          <div className="notfound-404">
            <h1>
              {/* 4<span></span>4 */}
              404
            </h1>
          </div>
          <h2>Oops! Page Not Be Found</h2>
          <p>
            The specified page cannot be found. The page may have been moved or
            deleted, or the URL may have been entered incorrectly.
          </p>
          <a href="/request/list">Back to homepage</a>
        </div>
      </div>
    );
  }
}

export default NotFoundPageComponent;
