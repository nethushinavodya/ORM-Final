package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InvoiceController {

    @FXML private Label lblInvoiceNumber;
    @FXML private Label lblInvoiceDate;
    @FXML private Label lblPatientId;
    @FXML private Label lblPatientName;
    @FXML private Label lblPatientContact;
    @FXML private Label lblProgramId;
    @FXML private Label lblProgramName;
    @FXML private Label lblTherapistId;
    @FXML private Label lblSessionDate;
    @FXML private Label lblPaymentType;
    @FXML private Label lblProgramFee;
    @FXML private Label lblAmountPaid;
    @FXML private Label lblBalanceDue;
    @FXML private Button btnPrint;

    private Stage stage;

    public void initialize() {
        // Generate unique invoice number
        String invoiceNumber = "INV-" + System.currentTimeMillis();
        lblInvoiceNumber.setText("Invoice #: " + invoiceNumber);

        // Set current date
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lblInvoiceDate.setText("Date: " + currentDate);
    }

    public void setInvoiceData(String patientId, String patientName, String patientContact,
                               String programId, String programName, String therapistId,
                               String sessionDate, String programFee, String amountPaid,
                               String paymentType) {

        lblPatientId.setText(patientId);
        lblPatientName.setText(patientName);
        lblPatientContact.setText(patientContact);
        lblProgramId.setText(programId);
        lblProgramName.setText(programName);
        lblTherapistId.setText(therapistId);
        lblSessionDate.setText(sessionDate);
        lblPaymentType.setText(paymentType);

        // Format currency values
        double fee = Double.parseDouble(programFee);
        double paid = Double.parseDouble(amountPaid);
        double balance = fee - paid;

        lblProgramFee.setText(String.format("Rs. %.2f", fee));
        lblAmountPaid.setText(String.format("Rs. %.2f", paid));
        lblBalanceDue.setText(String.format("Rs. %.2f", balance));
    }

    public void setPrintAction(Stage stage) {
      /*  this.stage = stage;*/
    }

    @FXML
    public void printInvoice(ActionEvent event) {
        /*// Get the button
        Node source = (Node) event.getSource();
        // Hide the button before printing
        source.setVisible(false);

        // Set up the printer job
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            // Show printer dialog
            boolean proceed = job.showPrintDialog(stage);

            if (proceed) {
                // Get the default page layout
                Printer printer = job.getPrinter();
                PageLayout pageLayout = printer.createPageLayout(
                        Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

                // Print the node
                boolean printed = job.printPage(pageLayout, stage.getScene().getRoot());

                if (printed) {
                    job.endJob();
                }
            }
        }

        // Show the button again after printing
        source.setVisible(true);*/
    }
}