import { SHOW_LOADER, HIDE_LOADER } from "../action/loading.action";

// define init state
let initialState = {
  isLoading: false
};

/**
 * reducer of user
 * @param {Object} state
 * @param {Action Creator} action
 */
export const loaderReducer = (state = initialState, action) => {
  switch (action.type) {
    // show loader
    case SHOW_LOADER: {
      return {
        isLoading: true
      };
    }
    // hide loader
    case HIDE_LOADER: {
      return {
        isLoading: false
      };
    }
    default:
      return state;
  }
};
