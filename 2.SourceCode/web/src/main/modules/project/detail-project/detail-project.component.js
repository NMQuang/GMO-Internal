import React, { Component } from "react";
import "./detail-project.css";
import AlertComponent from "../../../../shared/common/component/alert.component";
import ErrorComponent from "../../../../shared/common/component/error.component";
import { message } from "../../../../shared/constants/message.constant";
import { buttonType } from "../../../../shared/constants/button-type.constant";
import { Redirect, Link } from "react-router-dom";
import history from "../../../../config/history.config";
import { role, position } from "../../../../shared/constants/role.constant";
import DatePicker from "react-datepicker";
import { dateTimePattern } from "../../../../shared/constants/date-time-pattern.constant";
import commonUtil from "../../../../shared/utils/common.util";
import actionUtil from "../../../../shared/utils/action.util";
import ModalComponent from "../../../../shared/common/component/modal.component";
import validateUtil from "../../../../shared/utils/validate.util";
import moment from "moment";
import page from "../../../../shared/constants/page.constant";
import detailUserPage from "../../user/detail-user/detail-user.page";
import constant from "../../../../shared/constants/page.constant";

class DetailProjectComponent extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
  }

  /**
   * On click back request list
   */
  onClickBack = () => {
    history.push("/project/list");
  };

  render() {
    const { projectStore, messageStore } = this.props;
    let project = {};
    let email = [];
    let projectDetail = [];
    if (projectStore.projectDetail) {
      const { resultData } = projectStore.projectDetail;
      // Get project
      project = commonUtil.unescapeHtmlObject(resultData.project);
      // Get project detail
      resultData.projectDetail.forEach(projectDetail => {
        projectDetail = commonUtil.escapeHTML(projectDetail);
      });
      projectDetail = resultData.projectDetail;

      // Set position is PM
      const projectManagement = projectDetail.filter(
        projectDetail => +projectDetail.positionCode === +position.POSITION_PM
      );
      project.projectManagement =
        projectManagement.length > 0 ? projectManagement[0].employeeName : "";

      // Set position is BrSE
      const BrSE = projectDetail.filter(
        projectDetail => +projectDetail.positionCode === +position.POSITION_BRSE
      );
      project.brSE = BrSE.length > 0 ? BrSE[0].employeeName : "";

      // Set position is TL
      const teamLead = projectDetail.filter(
        projectDetail => +projectDetail.positionCode === +position.POSITION_TL
      );
      project.teamLead = teamLead.length > 0 ? teamLead[0].employeeName : "";

      // Get email
      if (project.emailCC.indexOf(",") != -1) {
        email = project.emailCC.split(",");
      } else {
        email[0] = project.emailCC;
      }
      return (
        <div className="container-fluid mt-4 fix-padding-bottom">
          <h2 className="text-center my-4">
            <b className="font-weigth">CHI TIẾT DỰ ÁN</b>
          </h2>
          <div className="card mb-4">
            <div className="card-body">
              <form>
                <div className="form-row">
                  <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Project Name (JP)<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="projectNameJP"
                          name="projectNameJP"
                          value={project.projectNameJP}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Project Name (VN)<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="projectNameVN"
                          name="projectNameVN"
                          value={project.projectNameVN}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Billable Effort<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="billableEffort"
                          name="billableEffort"
                          value={project.billableEffort}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Start Date<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="startDate"
                          name="startDate"
                          value={project.startDate}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        End Date<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="endDate"
                          name="endDate"
                          value={
                            project.endDate === "" ? "N/A" : project.endDate
                          }
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Customer Name<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="customerName"
                          name="customerName"
                          value={project.customerName}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Sale<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="sale"
                          name="sale"
                          value={project.sale}
                          disabled
                        />
                      </div>
                    </div>
                  </div>
                  <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Project Management<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="projectManagement"
                          name="projectManagement"
                          value={project.projectManagement}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        BrSE<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8 ">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="brSE"
                          name="brSE"
                          value={project.brSE}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Team Lead<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="teamLead"
                          name="teamLead"
                          value={project.teamLead}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Project Rank<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="projectRank"
                          name="projectRank"
                          value={
                            project.projectRank
                              ? project.projectRank
                              : constant.VALUE_NA
                          }
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Objectives<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="objectives"
                          name="objectives"
                          value={project.objectives}
                          disabled
                        />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label className="col-4 col-form-label lable-front">
                        Scope<span className="text-danger"></span>
                      </label>
                      <div className="col-sm-8">
                        <input
                          type="text"
                          className="form-control form-custom"
                          id="scope"
                          name="scope"
                          value={project.scope}
                          disabled
                        />
                      </div>
                    </div>
                    {email.length > 0 ? (
                      <React.Fragment>
                        {email.map((email, index) => {
                          return (
                            <div className="form-group row" key={index}>
                              {index === 0 ? (
                                <label className="col-4 col-form-label lable-front">
                                  EmailCC<span className="text-danger"></span>
                                </label>
                              ) : (
                                <label className="col-4 col-form-label">
                                  <span className="text-danger"></span>
                                </label>
                              )}

                              <div className="col-sm-8">
                                <input
                                  type="text"
                                  className="form-control form-custom"
                                  id={`email + ${index}`}
                                  name={`email + ${index}`}
                                  value={email}
                                  disabled
                                />
                              </div>
                            </div>
                          );
                        })}
                      </React.Fragment>
                    ) : (
                      <div className="form-group row">
                        <label className="col-4 col-form-label lable-front">
                          EmailCC<span className="text-danger"></span>
                        </label>
                        <div className="col-sm-8">
                          <input
                            type="text"
                            className="form-control form-custom"
                            id={`email + ${index}`}
                            name={`email + ${index}`}
                            value=""
                            disabled
                          />
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              </form>
            </div>
          </div>
          <div className="mt-4">
            <label>
              <b className="font-weigth">Thành Viên Của Dự Án</b>
            </label>
            <div>
              <table
                className="table table-bordered table-hover"
                style={{ width: "65%" }}
              >
                <thead className="thead-dark">
                  <tr className="text-center">
                    <th
                      width="3%"
                      scope="col"
                      className="text-center align-middle"
                    >
                      <b>No</b>
                    </th>
                    <th
                      width="30%"
                      scope="col"
                      className="text-center align-middle"
                    >
                      <b>Họ và Tên</b>
                    </th>

                    <th
                      width="15%"
                      scope="col"
                      className="text-center align-middle"
                    >
                      <b>Vai Trò</b>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {projectDetail.length > 0 &&
                    projectDetail.map((project, index) => {
                      return (
                        <tr key={index}>
                          <td className="align-middle text-center">
                            {index + 1}
                          </td>
                          <td className="align-middle">
                            <span>{project.employeeName}</span>
                          </td>
                          <td className="align-middle text-center">
                            <span>{project.positionCodeName}</span>
                          </td>
                        </tr>
                      );
                    })}
                </tbody>
              </table>
            </div>
            <div className="text-center">
              <button
                className="btn btn-primary btn-send"
                onClick={this.onClickBack}
              >
                <b>{buttonType.BUTTON_BACK}</b>
              </button>
            </div>
          </div>
        </div>
      );
    } else {
      return (
        <div className="container-fluid mt-4 fix-padding-bottom">
          <h2 className="text-center my-4">
            <b className="font-weigth">CHI TIẾT DỰ ÁN</b>
          </h2>
          <div className="row">
            <div className="col-12">
              {<AlertComponent messageObj={messageStore} />}
            </div>
          </div>
          <div className="text-center">
            <button
              className="btn btn-primary btn-send"
              onClick={this.onClickBack}
            >
              <b>{buttonType.BUTTON_BACK}</b>
            </button>
          </div>
        </div>
      );
    }
  }
}

export default DetailProjectComponent;
