import {
  PROJECT_CREATE_REQUEST,
  PROJECT_CREATE_SUCCESS,
  PROJECT_CREATE_FAILURE,
  PROJECT_GET_RANK_REQUEST,
  PROJECT_GET_RANK_SUCCESS,
  PROJECT_GET_RANK_FAILURE
} from "./create-project/create-project.action";
import {
  PROJECT_GET_LIST_REQUEST,
  PROJECT_GET_LIST_SUCCESS,
  PROJECT_GET_LIST_FAILURE
} from "./list-project/list-project.action";
import {
  PROJECT_GET_DETAIL_REQUEST,
  PROJECT_GET_DETAIL_SUCCESS,
  PROJECT_GET_DETAIL_FAILURE
} from "./detail-project/detail-project.action";
import {
  PROJECT_EDIT_REQUEST,
  PROJECT_EDIT_SUCCESS,
  PROJECT_EDIT_FAILURE
} from "./edit-project/edit-project.action";

// initial state
let initialState = {
  isCreate: null,
  projectRank: null,
  projectList: null,
  projectDetail: null,
  isEdit: null
};

/**
 * reducer of user!
 * @param {Object} state
 * @param {Action Creator} action
 */
export const projectReducer = (state = initialState, action) => {
  switch (action.type) {
    // Create project
    case PROJECT_CREATE_REQUEST: {
      return {
        ...state
      };
    }

    case PROJECT_CREATE_SUCCESS: {
      return {
        ...state,
        isCreate: true
      };
    }

    case PROJECT_CREATE_FAILURE: {
      return {
        ...state,
        isCreate: false
      };
    }

    // Get project rank
    case PROJECT_GET_RANK_REQUEST: {
      return {
        ...state
      };
    }

    case PROJECT_GET_RANK_SUCCESS: {
      return {
        ...state,
        projectRank: action.payload.data.response
      };
    }

    case PROJECT_GET_RANK_FAILURE: {
      return {
        ...state
      };
    }

    // Get list project
    case PROJECT_GET_LIST_REQUEST: {
      return {
        ...state,
        isCreate: null,
        isEdit: null
      };
    }

    case PROJECT_GET_LIST_SUCCESS: {
      return {
        ...state,
        projectList: action.payload.data.response
      };
    }

    case PROJECT_GET_LIST_FAILURE: {
      return {
        ...state,
        projectList: []
      };
    }

    // Get detail project
    case PROJECT_GET_DETAIL_REQUEST: {
      return {
        ...state
      };
    }

    case PROJECT_GET_DETAIL_SUCCESS: {
      return {
        ...state,
        projectDetail: action.payload.data.response
      };
    }

    case PROJECT_GET_DETAIL_FAILURE: {
      return {
        ...state,
        projectDetail: []
      };
    }

    // Edit project
    case PROJECT_EDIT_REQUEST: {
      return {
        ...state
      };
    }

    case PROJECT_EDIT_SUCCESS: {
      return {
        ...state,
        isEdit: true
      };
    }

    case PROJECT_EDIT_FAILURE: {
      return {
        ...state,
        isEdit: false
      };
    }

    default:
      return state;
  }
};
