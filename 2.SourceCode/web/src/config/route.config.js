import ListRequestPage from "../main/modules/managementOT/requestOT/list-requestOT/list-request-page";
import ListApprovalPage from "../main/modules/managementOT/approvalOT/list-approvalOT/list-approval-page";
import Main from "../shared/layouts/main/main.component";
import { role } from "../shared/constants/role.constant.js";
import UserPage from "../main/modules/user/user.page.js";
import NotFoundPage from "../shared/errors/not-found/not-found.page";
import RequestPage from "../main/modules/managementOT/requestOT/request.page";
import ListUserPage from "../main/modules/user/list-user/list-user.page";
import ChangePasswordPage from "../main/modules/user/change-password/change-password.component";
import InternalServerPage from "../shared/errors/internal-server/internal-server.page";
import ForbiddenPage from "../shared/errors/forbidden/forbidden.page";
import RequestTimeoutPage from "../shared/errors/request-timeout/request-timeout.page";
import ListTimekeepingPage from "../main/modules/report/timekeeping/list-timekeeping/list-timekeeping.page";
import CreateProjectPage from "../main/modules/project/create-project/create-project.page";
import ListProjectPage from "../main/modules/project/list-project/list-project.page";
import ProjectPage from "../main/modules/project/project.page";
import DetailUserPage from "../main/modules/user/detail-user/detail-user.page";

/**
 * define route of screen
 */
export const routes = [
  {
    path: "/login",
    exact: true,
    main: () => <UserPage isResetPass={false} />,
    isAuthenticated: false,
    isInitial: true
  },
  {
    path: "/reset-password",
    exact: true,
    main: () => <UserPage isResetPass={true} />,
    isAuthenticated: false,
    isInitial: false
  },
  {
    path: "/change-password",
    exact: true,
    main: () => <ChangePasswordPage />,
    isAuthenticated: true,
    isInitial: true
  },
  {
    path: "/user/detail/:employeeCode",
    exact: true,
    main: employeeCode => (
      <DetailUserPage employeeCode={employeeCode} isEdit={false} />
    ),
    isAuthenticated: true,
    hasAnyAuthorities: [],
    isInitial: true
  },
  {
    path: "/user/edit/:employeeCode",
    exact: true,
    main: employeeCode => (
      <UserPage employeeCode={employeeCode} isEdit={true} />
    ),
    isAuthenticated: true,
    hasAnyAuthorities: [],
    isInitial: true
  },
  {
    path: "/user/list",
    exact: true,
    main: () => <ListUserPage />,
    isAuthenticated: true,
    hasAnyAuthorities: [],
    isInitial: true
  },
  {
    path: "/",
    exact: true,
    main: () => <Main />,
    isAuthenticated: true,
    isInitial: true
  },
  {
    path: "/request/create",
    exact: true,
    main: () => <RequestPage />,
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_USER],
    isInitial: true
  },
  {
    path: "/request/edit/:requestId",
    exact: true,
    main: requestId => <RequestPage requestId={requestId} isUpdate={true} />,
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_ADMIN, role.ROLE_QA, role.ROLE_USER],
    isInitial: true
  },
  {
    path: "/request/list",
    exact: true,
    main: () => <ListRequestPage />,
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_ADMIN, role.ROLE_QA, role.ROLE_USER],
    isInitial: true
  },
  {
    path: "/approval/list",
    exact: true,
    main: () => <ListApprovalPage />,
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_ADMIN, role.ROLE_QA, role.ROLE_USER],
    isInitial: true
  },
  {
    path: "/timekeeping/list",
    exact: true,
    main: () => <ListTimekeepingPage />,
    isAuthenticated: true,
    hasAnyAuthorities: [],
    isInitial: true
  },
  {
    path: "/project/create",
    exact: true,
    main: () => <CreateProjectPage />,
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_ADMIN, role.ROLE_QA],
    isInitial: true
  },
  {
    path: "/project/list",
    exact: true,
    main: () => <ListProjectPage />,
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_ADMIN, role.ROLE_USER, role.ROLE_QA],
    isInitial: true
  },
  {
    path: "/project/detail/:projectCode",
    exact: true,
    main: projectCode => (
      <ProjectPage projectCode={projectCode} isEdit={false} />
    ),
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_ADMIN, role.ROLE_USER, role.ROLE_QA],
    isInitial: true
  },
  {
    path: "/project/edit/:projectCode",
    exact: true,
    main: projectCode => (
      <ProjectPage projectCode={projectCode} isEdit={true} />
    ),
    isAuthenticated: true,
    hasAnyAuthorities: [role.ROLE_ADMIN, role.ROLE_USER],
    isInitial: true
  },
  {
    path: "/not-found",
    exact: true,
    main: () => <NotFoundPage />
  },
  {
    path: "/error-page",
    exact: true,
    main: () => <InternalServerPage />
  },
  {
    path: "/permission",
    exact: true,
    main: () => <ForbiddenPage />
  },
  {
    path: "/request-timeout",
    exact: true,
    main: () => <RequestTimeoutPage />
  },
  {
    path: "*",
    exact: false,
    main: () => <NotFoundPage />
  }
];
