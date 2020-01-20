import React, { Component } from "react";
/**
 * Loading Component
 */
class LoadingComponent extends Component {
  /**
   * render
   */
  render() {
    const loaderStyle = {
      position: "fixed",
      zIndex: 999,
      margin: "auto",
      top: 0,
      left: 0,
      bottom: 0,
      right: 0,
      backgroundColor: "white",
      opacity: 0.7
    };
    return (
      <div className="loader op-0-7 w-100 h-100">
        <div className="pos-absolute spinner">
          <div
            className="spinner-border text-succes"
            style={loaderStyle}
            role="status"
          ></div>
        </div>
      </div>
    );
  }
}

export default LoadingComponent;
