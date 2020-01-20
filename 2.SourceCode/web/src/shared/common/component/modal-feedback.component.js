import React from "react";
import { Modal } from "react-bootstrap";
import CKEditor from "ckeditor4-react";
import validateUtil from "../../utils/validate.util";
import { message } from "../../constants/message.constant";
import ErrorComponent from "./error.component";
import { buttonType } from "../../constants/button-type.constant";
class ModalFeedBackComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      rows: [
        {
          id: (+new Date() + Math.floor(Math.random() * 999999)).toString(36),
          actionSend: "",
          emailSend: "",
          error: []
        }
      ],
      dataSendMail: {
        emailTo: [],
        emailToCC: [],
        emailToBcc: [],
        subject: "",
        content: "",
        error: {
          subject: "",
          content: "",
          emailTo: ""
        }
      }
    };
  }
  /**
   * On click send modal
   */
  onClickSendModal = () => {
    const { email } = this.props;
    let rows = this.state.rows;
    let data = {
      emailTo: [],
      emailToCC: [],
      emailToBcc: [],
      subject: "",
      content: ""
    };
    data.subject = this.state.dataSendMail.subject;
    data.content = this.state.dataSendMail.content;
    data.emailTo.push(email);
    rows.forEach(row => {
      // Action send mail to
      if (row.actionSend == "to") {
        data.emailTo.push(row.emailSend);
      }
      // Action send mail cc
      if (row.actionSend == "cc") {
        data.emailToCC.push(row.emailSend);
      }
      // Action send mail bcc
      if (row.actionSend == "bcc") {
        data.emailToBcc.push(row.emailSend);
      }
    });
    // Check validate
    if (this.checkValidate()) {
      this.props.onClick(data);
    }
  };
  /**
   * Check validate
   */
  checkValidate = () => {
    let isValid = true;
    let rows = this.state.rows;
    let dataSendMail = this.state.dataSendMail;

    // Check empty subject
    if (validateUtil.isEmpty(dataSendMail.subject)) {
      dataSendMail.error.subject = message.MSG_ERROR_001;
      isValid = false;
    }
    // Check empty content
    if (validateUtil.isEmpty(dataSendMail.content)) {
      dataSendMail.error.content = message.MSG_ERROR_001;
      isValid = false;
    }
    let countMailTo = 0;
    rows.forEach(row => {
      // Check format emailSend
      // Check row >0 because row=0 is set default
      if (countMailTo > 0) {
        if (!validateUtil.checkEmail(row.emailSend)) {
          row.error.emailSend = message.MSG_ERROR_006;
          isValid = false;
        }
        // Check empty actionSend
        if (validateUtil.isEmpty(row.actionSend)) {
          row.error.actionSend = message.MSG_ERROR_001;
          isValid = false;
        }
        // Check empty emailSend
        if (validateUtil.isEmpty(row.emailSend)) {
          row.error.emailSend = message.MSG_ERROR_001;
          isValid = false;
        }
      }
      countMailTo++;
    });

    this.setState({
      dataSendMail: dataSendMail
    });
    return isValid;
  };
  /**
   * On hide send modal
   */
  onHideSendModal = () => {
    this.props.onHideSendModal();
  };
  /**
   * Add row
   */
  addRow = () => {
    let rows = this.state.rows;
    var id = (+new Date() + Math.floor(Math.random() * 999999)).toString(36);
    rows.push({
      id,
      actionSend: "",
      emailSend: "",
      error: []
    });
    this.setState({
      rows: rows
    });
  };
  /**
   * Remove row
   */
  removeRow = id => {
    let rows = this.state.rows;
    rows = rows.filter(row => row.id != id);
    this.setState({
      rows: rows
    });
  };
  /**
   * On change editor
   */
  onEditorChange = e => {
    let dataSendMail = this.state.dataSendMail;
    dataSendMail.content = e.editor.getData();
    this.checkValidateInput(e.editor.getData(), "content", dataSendMail);
    this.setState({
      dataSendMail: dataSendMail
    });
  };
  /**
   * Check validate input on change
   *
   * @param value
   * @param name
   */
  checkValidateInput = (value, name, dataSendMail) => {
    // Check empty
    if (validateUtil.isEmpty(value)) {
      dataSendMail.error[name] = message.MSG_ERROR_001;
    } else {
      dataSendMail.error[name] = "";
    }
  };
  /**
   * Check validate input table on change
   *
   * @param value
   * @param name
   * @param row
   */
  checkValidateInputTable = (value, name, row) => {
    // Check empty
    if (validateUtil.isEmpty(value)) {
      row.error[name] = message.MSG_ERROR_001;
    } else {
      row.error[name] = "";
      if (name == "emailSend") {
        // Check is not time OT
        if (!validateUtil.checkEmail(value)) {
          row.error[name] = message.MSG_ERROR_006;
        } else {
          row.error[name] = "";
        }
      }
    }
  };
  /**
   * On change handle
   */
  onHandleChange = (id, event) => {
    let { rows, dataSendMail } = this.state;
    var target = event.target;
    var name = target.name;
    var value = target.type === "checkbox" ? target.checked : target.value;
    if (id == null) {
      dataSendMail[name] = value;
      this.checkValidateInput(value, name, dataSendMail);
      this.setState({
        dataSendMail: dataSendMail
      });
    } else {
      rows.forEach(row => {
        if (row.id == id) {
          row[name] = value;
          this.checkValidateInputTable(value, name, row);
        }
      });
    }
    this.setState({
      rows: rows
    });
  };
  /**
   * Render
   */
  render() {
    const { showSendModal, email } = this.props;
    const { error } = this.state.dataSendMail;
    return (
      <>
        <Modal
          show={showSendModal}
          size="lg"
          onHide={this.onHideSendModal}
          animation={true}
        >
          <Modal.Header closeButton></Modal.Header>
          <Modal.Body>
            <div className="container">
              <div className="row">
                {this.state.rows.map((row, index) => (
                  <div className="col-12" key={index}>
                    {index == 0 ? (
                      <div className="row  mt-4">
                        <div className="col-2">
                          <select
                            className="form-control mr-2"
                            onChange={this.onHandleChange.bind(this, row.id)}
                            name="actionSend"
                            value="to"
                            disabled={true}
                          >
                            <option value="to">TO</option>
                          </select>
                        </div>
                        <div className="col-9">
                          <input
                            disabled={true}
                            className="form-control"
                            onChange={this.onHandleChange.bind(this, row.id)}
                            name="emailSend"
                            value={email}
                          />
                        </div>
                        <button
                          className="btn btn-primary btn-icon-add-remove-feedback mt-0"
                          onClick={this.addRow}
                        >
                          <i className="fa fa-plus btn-icon-add-remove"></i>
                        </button>
                      </div>
                    ) : (
                      <div className="row  mt-4">
                        <div className="col-2">
                          <select
                            className="form-control mr-2"
                            onChange={this.onHandleChange.bind(this, row.id)}
                            name="actionSend"
                            value={row.actionSend}
                          >
                            <option value=""></option>
                            <option value="to">TO</option>
                            <option value="cc">CC</option>
                            <option value="bcc">BCC</option>
                          </select>
                          {row.error.actionSend && (
                            <ErrorComponent error={row.error.actionSend} />
                          )}
                        </div>
                        <div className="col-9">
                          <input
                            className="form-control"
                            onChange={this.onHandleChange.bind(this, row.id)}
                            name="emailSend"
                            value={row.emailSend}
                          />
                          {row.error.emailSend && (
                            <ErrorComponent error={row.error.emailSend} />
                          )}
                        </div>
                        <button
                          className="btn btn-danger btn-icon-add-remove-feedback mt-0"
                          onClick={this.removeRow.bind(this, row.id)}
                        >
                          <i className="fa fa-minus btn-icon-add-remove"></i>
                        </button>
                      </div>
                    )}
                    <div>
                      {error.emailTo.length > 0 && (
                        <ErrorComponent error={error.emailTo} />
                      )}
                    </div>
                  </div>
                ))}

                <div className="col-12">
                  <div className="row mt-4">
                    <label className="col-2">Subject</label>
                    <div className="col-10">
                      <input
                        className="form-control col-12"
                        onChange={this.onHandleChange.bind(this, null)}
                        name="subject"
                      />
                      {error.subject.length > 0 && (
                        <ErrorComponent error={error.subject} />
                      )}
                    </div>
                  </div>
                </div>
                <div className="col-12">
                  <div className="row">
                    <div className="mt-4 col-12">
                      <CKEditor
                        onBeforeLoad={CKEDITOR =>
                          (CKEDITOR.disableAutoInline = true)
                        }
                        data={this.state.dataSendMail.content}
                        onChange={this.onEditorChange}
                      />
                      {error.content.length > 0 && (
                        <ErrorComponent error={error.content} />
                      )}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Modal.Body>
          <Modal.Footer>
            <div className="col-12 text-center">
              <button
                className="btn btn-primary"
                onClick={this.onClickSendModal}
                style={{ fontWeight: "bold" }}
              >
                {buttonType.BUTTON_SEND}
              </button>
            </div>
          </Modal.Footer>
        </Modal>
      </>
    );
  }
}
export default ModalFeedBackComponent;
