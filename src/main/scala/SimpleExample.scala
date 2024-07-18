import org.apache.poi.xssf.usermodel.XSSFWorkbook
import spoiwo.model.enums.CellFill
import spoiwo.model._
import spoiwo.natures.xlsx.Model2XlsxConversions.XlsxWorkbook

import java.io.{File, FileOutputStream}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object SimpleExample {
  private case class SomeAccounting(income: Double, outCome: Double, date: LocalDate, description: String)
  def main(args: Array[String]): Unit = {

    val octoberRecord: List[SomeAccounting] = List(
      SomeAccounting(145.6, 100.3, LocalDate.of(2023, 10, 1), "We got some profit today"),
      SomeAccounting(145.6, 200.3, LocalDate.of(2023, 10, 10), "We got some loses today"),
      SomeAccounting(145.6, 10.34, LocalDate.of(2023, 10, 11), "We got some profit today"),
      SomeAccounting(155, 500.3, LocalDate.of(2023, 10, 14), "We got huge profit today"),
      SomeAccounting(90, 90, LocalDate.of(2023, 10, 15), "We broke even today")
    )

    val titleCellStyle = CellStyle(fillPattern = CellFill.Solid,fillForegroundColor = Color.AquaMarine, font = Font(bold = true))

    val workbook = Workbook(
      Sheet(name = "Accounting October")
        .withRows(
          Row(style = titleCellStyle).withCellValues("Income", "Outcome", "Date", "Description")
        )
        .addRows(
          octoberRecord.map(account =>
            Row()
            .withCellValues(account.income, account.outCome, account.date.format(DateTimeFormatter.ISO_DATE), account.description))
        )
        .withColumns(
          Column(width = new Width(20, WidthUnit.Character), autoSized = true),
          Column(width = new Width(20, WidthUnit.Character), autoSized = true),
          Column(width = new Width(50, WidthUnit.Character), autoSized = true),
          Column(width = new Width(70, WidthUnit.Character), autoSized = true)
        )
    ).convertAsXlsx()
    writer(workbook)

  }

  private def writer(workbook: XSSFWorkbook): Unit = {
    val file = new File("report.xlsx")
    val fileOutputStream = new FileOutputStream(file, true)
    try {
      workbook.write(fileOutputStream)
    } finally {
      fileOutputStream.close()
    }
  }
}