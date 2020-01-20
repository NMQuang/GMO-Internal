import React, { Component } from "react";
import { connect } from "react-redux";

/**
 * Footer Component
 */
class Footer extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
  }

  /**
   * render
   */
  render() {
    const { isAuthenticated } = this.props.userStore;
    return (
      <React.Fragment>
        {isAuthenticated && (
          <footer className="py-4 bg-dark text-white layout-footer">
            <div className="container text-center">
              <small>
                Copyright &copy; 2005-2019 GMO-Z.com RUNSYSTEM. All rights
                reserved
              </small>
            </div>
          </footer>
        )}
      </React.Fragment>
    );
  }
}

/**
 * map state to props of component
 * @param {Object} state
 */
const mapStateToProps = state => {
  return {
    userStore: state.userReducer
  };
};

/**
 * map dispatch to props of component
 * @param {Action Creator} dispatch
 * @param {Object} props
 */
const mapDispatchToProps = (dispatch, props) => {
  return {};
};

// export Header component
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Footer);
