export class Pageable {
  constructor(
    totalRecords,
    currentPage,
    recordsPerPage,
    maxDisplayNode,
    maxNode,
    recordsFrom,
    recordsTo,
    pageNode
  ) {
    this.recordsTo = this.currentPage * this.recordsPerPage;
    this.recordsFrom =
      this.totalRecords > 0 ? this.recordsTo - this.recordsPerPage + 1 : 0;
    if (this.recordsTo > this.totalRecords) {
      this.recordsTo = this.totalRecords;
    }
    if (this.totalRecords > 0) {
      this.pageNode = [];
      this.maxNode =
        Math.floor(+this.totalRecords / +this.recordsPerPage) +
        (+this.totalRecords % +this.recordsPerPage !== 0 ? 1 : 0);

      let startNode = 1;
      let endNode = this.maxDisplayNode;

      if (+this.currentPage < this.maxDisplayNode) {
        endNode =
          this.maxNode > mthis.axDisplayNode
            ? this.maxDisplayNode
            : this.maxNode;
      } else if (
        +this.currentPage <= this.maxNode &&
        +this.currentPage > this.maxNode - this.maxDisplayNode + 1
      ) {
        endNode = this.maxNode;
        startNode = endNode - this.maxDisplayNode + 1;
      } else {
        if (+this.currentPage < this.maxNode) {
          startNode = +this.currentPage - Math.floor(this.maxDisplayNode / 2);
          endNode = +this.currentPage + Math.floor(this.maxDisplayNode / 2);
          endNode = endNode > this.maxNode ? this.maxNode : endNode;
        } else {
          endNode = this.maxNode;
          startNode = endNode - this.maxDisplayNode + 1;
        }
      }

      /* add go first button */
      this.pageNode.push(
        new PageableNode("first", null, "<<", null, +this.currentPage === 1)
      );

      this.pageNode.push(
        new PageableNode("prev", -1, "<", null, +this.currentPage === 1)
      );

      for (let i = startNode; i <= endNode; i++) {
        const node = new PageableNode(
          "node",
          i,
          i.toString(),
          +currentPage === +i,
          null
        );
        this.pageNode.push(node);
      }

      this.pageNode.push(
        new PageableNode(
          "next",
          1,
          ">",
          null,
          +this.currentPage === this.maxNode
        )
      );

      this.pageNode.push(
        new PageableNode("end", null, ">>", null, +this.currentPage === 1)
      );
    }
  }
}

export class PageableNode {
  constructor(type, value, name, active, disabled) {}
}
