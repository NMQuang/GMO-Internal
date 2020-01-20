import React from "react";
import { Button, Modal } from "react-bootstrap";
import { buttonType } from "../../constants/button-type.constant";
/**
 * Modal component
 */
class ModalComponent extends React.Component {
  /**
   * Constructor
   *
   * @param props
   */
  constructor(props) {
    super(props);
  }
  /**
   * On click ok
   */
  onClickOk = () => {
    this.props.onClick(true);
  };
  /**
   * On hide
   */
  onHide = () => {
    this.props.onHide();
  };
  /**
   * Render
   */
  render() {
    // Get value modal
    const { showModal } = this.props;
    return (
      <>
        <Modal show={showModal.show} onHide={this.onHide} animation={true}>
          <Modal.Header closeButton>
            <Modal.Title>{showModal.title}</Modal.Title>
          </Modal.Header>
          <Modal.Body>{showModal.content}</Modal.Body>
          <Modal.Footer>
            <Button
              variant="danger"
              onClick={this.onHide}
              style={{ fontWeight: "bold" }}
            >
              {buttonType.BUTTON_CANCEL}
            </Button>
            <Button
              variant="primary"
              onClick={this.onClickOk}
              style={{ fontWeight: "bold" }}
            >
              {buttonType.BUTTON_OK}
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    );
  }
}
export default ModalComponent;
