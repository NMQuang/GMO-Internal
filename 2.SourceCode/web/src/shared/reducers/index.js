import { combineReducers } from "redux";
import { approvalReducer } from "../../main/modules/managementOT/approvalOT/approval.reducer";
import { requestReducer } from "../../main/modules/managementOT/requestOT/request.reducer";
import { userReducer } from "../../main/modules/user/user.reducer";
import { loaderReducer } from "../common/reducer/loading.reducer";
import { messageReducer } from "../common/reducer/message.reducer";
import { errorReducer } from "../common/reducer/error.reducer";
import { projectReducer } from "../../main/modules/project/project.reducer";

/**
 * rootReducer
 */
export const rootReducer = combineReducers({
  approvalReducer,
  requestReducer,
  userReducer,
  loaderReducer,
  messageReducer,
  errorReducer,
  projectReducer
});
