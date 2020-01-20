import React, { Component } from "react";
/**
 * Alert Component
 */
class AlertComponent extends Component {
  /**
   * Constructor
   * @param {*} props
   */
  constructor(props) {
    super(props);
  }

  /**
   * render
   */
  render() {
    const { messageObj } = this.props;

    const styleClass = `alert alert-${messageObj.type}`;
    return (
      <div className={styleClass}>
        <strong>{messageObj.message}</strong>
      </div>
    );
  }
}

export default AlertComponent;
