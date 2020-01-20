import { saveAs } from "file-saver";
import { Workbook } from "exceljs";

const formatDate = date => {
  const now = new Date(date);
  let month = "" + (now.getMonth() + 1);
  let day = "" + now.getDate();
  const year = now.getFullYear();

  month = month.length < 2 ? "0" + month : month;
  day = day.length < 2 ? "0" + day : day;

  return [day, month, year].join("");
};

const createFileName = fileName => {
  const now = new Date();
  const dateFormat = formatDate(now);
  return `${fileName}_${dateFormat}.xlsx`;
};

export const createFileExcel = (datas, filename) => {
  const workbook = new Workbook();
  const blobType =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
  let worksheet = workbook.addWorksheet("Data");
  worksheet.columns = [
    { header: "Mã nhân viên (*)", key: "employeeCode" },
    { header: "Họ và tên (*)", key: "employeeName" },
    { header: "Giới tính", key: "gender" },
    { header: "Ngày sinh", key: "birthday" },
    { header: "Chỗ ở hiện nay", key: "address" },
    { header: "ĐT di động", key: "phoneNumber" },
    { header: "Email cơ quan", key: "email" },
    { header: "Nơi sinh", key: "birthplace" },
    {
      header: "Tỉnh/Thành phố (hộ khẩu thường trú)",
      key: "provincePr"
    },
    { header: "Quận/Huyện (Hộ khẩu thường trú)", key: "districtPr" },
    { header: "Xã/Phường (Hộ khẩu thường trú)", key: "wardPr" },
    { header: "Quốc gia (chỗ ở hiện nay)", key: "nationCa" },
    { header: "Quận/Huyện (Chỗ ở hiện nay)", key: "districtCa" },
    { header: "Xã/Phường (Chỗ ở hiện nay)", key: "wardCa" },
    { header: "Ngày thử việc", key: "probationaryDay" },
    { header: "Ngày chính thức", key: "officialDay" },
    { header: "Địa điểm làm việc", key: "workLocation" },
    { header: "Loại hợp đồng", key: "typeContract" },
    { header: "Trạng thái lao động (*)", key: "status" },
    { header: "Lý do nghỉ", key: "reasonLeave" },
    { header: "Ngày nghỉ việc", key: "dayOff" },
    { header: "Tên vị trí công việ", key: "division" },
    { header: "Tên đơn vị công tác", key: "position" }
  ];

  // Set width for each column
  worksheet.columns.forEach(column => {
    column.width = column.header.length < 20 ? 20 : column.header.length;
  });

  // Add a couple of Rows by key-value, after the last current row, using the column keys
  datas.forEach(data => {
    worksheet.addRow(data);
  });

  // Format file excel
  worksheet.eachRow({ includeEmpty: true }, function(row, rowNumber) {
    row.eachCell({ includeEmpty: true }, function(cell, colNumber) {
      // Format header
      if (rowNumber === 1) {
        cell.style = { font: { bold: true } };
        cell.alignment = { vertical: "middle", horizontal: "center" };
        cell.fill = {
          type: "pattern",
          pattern: "lightGray",
          fgColor: { argb: "FF00FF00" }
        };
      }
      // Format column messageContent and messageContentAscii
      if (rowNumber !== 1) {
        if (
          colNumber === 1 ||
          colNumber === 3 ||
          colNumber === 4 ||
          colNumber === 6 ||
          colNumber === 15 ||
          colNumber === 16 ||
          colNumber === 21
        ) {
          cell.alignment = { vertical: "middle", horizontal: "center" };
        } else {
          cell.alignment = {
            wrapText: true,
            vertical: "middle",
            horizontal: "left"
          };
        }
      }
      // Format all cell
      cell.border = {
        top: { style: "thin" },
        left: { style: "thin" },
        bottom: { style: "thin" },
        right: { style: "thin" }
      };
    });
  });
  // Write file excel
  workbook.xlsx.writeBuffer().then(data => {
    let blob = new Blob([data], { type: blobType });
    saveAs(blob, createFileName(filename));
  });
};
