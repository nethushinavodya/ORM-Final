package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private ComboBox<String> cmbReportType;

    @FXML
    private DatePicker dpFromDate;

    @FXML
    private DatePicker dpToDate;

    @FXML
    private Button btnGenerateReport;

    @FXML
    private TabPane tabPaneReports;

    @FXML
    private TableView<Object> tblReportData;

    @FXML
    private AnchorPane chartContainer;

    @FXML
    private Button btnExportPDF;

    @FXML
    private Button btnExportExcel;

    @FXML
    private Button btnPrint;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set default values
        dpFromDate.setValue(LocalDate.now().minusMonths(1));
        dpToDate.setValue(LocalDate.now());
        cmbReportType.getSelectionModel().selectFirst();

        // Add listener to report type combobox
        cmbReportType.setOnAction(event -> {
            setupTableColumns();
        });

        // Initial setup
        setupTableColumns();
    }

    private void setupTableColumns() {
        tblReportData.getColumns().clear();
        String reportType = cmbReportType.getValue();

        if (reportType == null) return;

        switch (reportType) {
            case "Patient Summary":
                setupPatientSummaryColumns();
                break;
            case "Revenue Report":
                setupRevenueReportColumns();
                break;
            case "Therapist Performance":
                setupTherapistPerformanceColumns();
                break;
            case "Program Popularity":
                setupProgramPopularityColumns();
                break;
        }
    }

    private void setupPatientSummaryColumns() {
        TableColumn<Object, String> colPatientId = new TableColumn<>("Patient ID");
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        TableColumn<Object, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Object, Integer> colSessions = new TableColumn<>("Total Sessions");
        colSessions.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));

        TableColumn<Object, Double> colSpent = new TableColumn<>("Total Spent");
        colSpent.setCellValueFactory(new PropertyValueFactory<>("totalSpent"));

        TableColumn<Object, String> colLastVisit = new TableColumn<>("Last Visit");
        colLastVisit.setCellValueFactory(new PropertyValueFactory<>("lastVisit"));

        tblReportData.getColumns().addAll(colPatientId, colName, colSessions, colSpent, colLastVisit);
    }

    private void setupRevenueReportColumns() {
        TableColumn<Object, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Object, Double> colDailyRevenue = new TableColumn<>("Daily Revenue");
        colDailyRevenue.setCellValueFactory(new PropertyValueFactory<>("dailyRevenue"));

        TableColumn<Object, Integer> colSessions = new TableColumn<>("Sessions");
        colSessions.setCellValueFactory(new PropertyValueFactory<>("sessions"));

        TableColumn<Object, Double> colAvgRevenue = new TableColumn<>("Avg Revenue/Session");
        colAvgRevenue.setCellValueFactory(new PropertyValueFactory<>("avgRevenue"));

        tblReportData.getColumns().addAll(colDate, colDailyRevenue, colSessions, colAvgRevenue);
    }

    private void setupTherapistPerformanceColumns() {
        TableColumn<Object, String> colTherapistId = new TableColumn<>("Therapist ID");
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        TableColumn<Object, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Object, Integer> colSessions = new TableColumn<>("Sessions Conducted");
        colSessions.setCellValueFactory(new PropertyValueFactory<>("sessionsConducted"));

        TableColumn<Object, Double> colRevenue = new TableColumn<>("Revenue Generated");
        colRevenue.setCellValueFactory(new PropertyValueFactory<>("revenueGenerated"));

        TableColumn<Object, Integer> colPrograms = new TableColumn<>("Programs Handled");
        colPrograms.setCellValueFactory(new PropertyValueFactory<>("programsHandled"));

        tblReportData.getColumns().addAll(colTherapistId, colName, colSessions, colRevenue, colPrograms);
    }

    private void setupProgramPopularityColumns() {
        TableColumn<Object, String> colProgramId = new TableColumn<>("Program ID");
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));

        TableColumn<Object, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Object, Integer> colPatients = new TableColumn<>("Total Patients");
        colPatients.setCellValueFactory(new PropertyValueFactory<>("totalPatients"));

        TableColumn<Object, Integer> colSessions = new TableColumn<>("Total Sessions");
        colSessions.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));

        TableColumn<Object, Double> colRevenue = new TableColumn<>("Revenue Generated");
        colRevenue.setCellValueFactory(new PropertyValueFactory<>("revenueGenerated"));

        tblReportData.getColumns().addAll(colProgramId, colName, colPatients, colSessions, colRevenue);
    }

    @FXML
    void generateReport(ActionEvent event) {
        String reportType = cmbReportType.getValue();
        LocalDate fromDate = dpFromDate.getValue();
        LocalDate toDate = dpToDate.getValue();

        if (fromDate == null || toDate == null) {
            showAlert("Please select both from and to dates.");
            return;
        }

        if (fromDate.isAfter(toDate)) {
            showAlert("From date cannot be after to date.");
            return;
        }

        // Clear existing data
        tblReportData.getItems().clear();
        chartContainer.getChildren().clear();

        // Load data based on report type
        switch (reportType) {
            case "Patient Summary":
                loadPatientSummaryData(fromDate, toDate);
                createPatientSummaryChart();
                break;
            case "Revenue Report":
                loadRevenueReportData(fromDate, toDate);
                createRevenueChart();
                break;
            case "Therapist Performance":
                loadTherapistPerformanceData(fromDate, toDate);
                createTherapistPerformanceChart();
                break;
            case "Program Popularity":
                loadProgramPopularityData(fromDate, toDate);
                createProgramPopularityChart();
                break;
        }
    }

    private void loadPatientSummaryData(LocalDate fromDate, LocalDate toDate) {
        // This would typically fetch data from your database
        // For now, we'll just add some dummy data
        ObservableList<PatientSummaryDTO> data = FXCollections.observableArrayList(
                new PatientSummaryDTO("P001", "John Doe", 12, 15000.0, "2023-04-15"),
                new PatientSummaryDTO("P002", "Jane Smith", 8, 9500.0, "2023-04-10"),
                new PatientSummaryDTO("P003", "Robert Johnson", 5, 6200.0, "2023-04-12")
        );

        tblReportData.setItems(FXCollections.observableArrayList(data));
    }

    private void loadRevenueReportData(LocalDate fromDate, LocalDate toDate) {
        // Fetch data from database
        // For now, we'll add dummy data
        ObservableList<RevenueReportDTO> data = FXCollections.observableArrayList(
                new RevenueReportDTO("2023-04-01", 5200.0, 8, 650.0),
                new RevenueReportDTO("2023-04-02", 4800.0, 7, 685.7),
                new RevenueReportDTO("2023-04-03", 6100.0, 9, 677.8)
        );

        tblReportData.setItems(FXCollections.observableArrayList(data));
    }

    private void loadTherapistPerformanceData(LocalDate fromDate, LocalDate toDate) {
        // Fetch data from database
        // For now, we'll add dummy data
        ObservableList<TherapistPerformanceDTO> data = FXCollections.observableArrayList(
                new TherapistPerformanceDTO("T001", "Dr. Smith", 45, 58000.0, 3),
                new TherapistPerformanceDTO("T002", "Dr. Johnson", 38, 49200.0, 4),
                new TherapistPerformanceDTO("T003", "Dr. Williams", 52, 67500.0, 5)
        );

        tblReportData.setItems(FXCollections.observableArrayList(data));;
    }

    private void loadProgramPopularityData(LocalDate fromDate, LocalDate toDate) {
        // Fetch data from database
        // For now, we'll add dummy data
        ObservableList<ProgramPopularityDTO> data = FXCollections.observableArrayList(
                new ProgramPopularityDTO("PR001", "Physical Therapy", 28, 120, 156000.0),
                new ProgramPopularityDTO("PR002", "Speech Therapy", 15, 68, 88400.0),
                new ProgramPopularityDTO("PR003", "Occupational Therapy", 22, 95, 123500.0)
        );

        tblReportData.setItems(FXCollections.observableArrayList(data));;
    }

    private void createPatientSummaryChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setTitle("Patient Therapy Sessions");
        xAxis.setLabel("Patient");
        yAxis.setLabel("Sessions");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sessions");

        // Add data from table
        for (Object item : tblReportData.getItems()) {
            PatientSummaryDTO dto = (PatientSummaryDTO) item;
            series.getData().add(new XYChart.Data<>(dto.getName(), dto.getTotalSessions()));
        }

        barChart.getData().add(series);

        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);

        chartContainer.getChildren().add(barChart);
    }

    private void createRevenueChart() {
        // Similar implementation for revenue chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setTitle("Daily Revenue");
        xAxis.setLabel("Date");
        yAxis.setLabel("Revenue");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Revenue");

        for (Object item : tblReportData.getItems()) {
            RevenueReportDTO dto = (RevenueReportDTO) item;
            series.getData().add(new XYChart.Data<>(dto.getDate(), dto.getDailyRevenue()));
        }

        barChart.getData().add(series);

        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);

        chartContainer.getChildren().add(barChart);
    }

    private void createTherapistPerformanceChart() {
        // Implementation for therapist performance chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setTitle("Therapist Performance");
        xAxis.setLabel("Therapist");
        yAxis.setLabel("Revenue Generated");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue Generated");

        for (Object item : tblReportData.getItems()) {
            TherapistPerformanceDTO dto = (TherapistPerformanceDTO) item;
            series.getData().add(new XYChart.Data<>(dto.getName(), dto.getRevenueGenerated()));
        }

        barChart.getData().add(series);

        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);

        chartContainer.getChildren().add(barChart);
    }

    private void createProgramPopularityChart() {
        // Implementation for program popularity chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setTitle("Program Popularity");
        xAxis.setLabel("Program");
        yAxis.setLabel("Total Patients");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Patients");

        for (Object item : tblReportData.getItems()) {
            ProgramPopularityDTO dto = (ProgramPopularityDTO) item;
            series.getData().add(new XYChart.Data<>(dto.getName(), dto.getTotalPatients()));
        }

        barChart.getData().add(series);

        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);

        chartContainer.getChildren().add(barChart);
    }

    @FXML
    void exportToPDF(ActionEvent event) {
        showAlert("Exporting to PDF...");
        // Implementation for PDF export would go here
    }

    @FXML
    void exportToExcel(ActionEvent event) {
        showAlert("Exporting to Excel...");
        // Implementation for Excel export would go here
    }

    @FXML
    void printReport(ActionEvent event) {
        showAlert("Printing report...");
        // Implementation for printing would go here
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // DTO Classes for reports
    public static class PatientSummaryDTO {
        private String patientId;
        private String name;
        private int totalSessions;
        private double totalSpent;
        private String lastVisit;

        public PatientSummaryDTO(String patientId, String name, int totalSessions, double totalSpent, String lastVisit) {
            this.patientId = patientId;
            this.name = name;
            this.totalSessions = totalSessions;
            this.totalSpent = totalSpent;
            this.lastVisit = lastVisit;
        }

        public String getPatientId() { return patientId; }
        public String getName() { return name; }
        public int getTotalSessions() { return totalSessions; }
        public double getTotalSpent() { return totalSpent; }
        public String getLastVisit() { return lastVisit; }
    }

    public static class RevenueReportDTO {
        private String date;
        private double dailyRevenue;
        private int sessions;
        private double avgRevenue;

        public RevenueReportDTO(String date, double dailyRevenue, int sessions, double avgRevenue) {
            this.date = date;
            this.dailyRevenue = dailyRevenue;
            this.sessions = sessions;
            this.avgRevenue = avgRevenue;
        }

        public String getDate() { return date; }
        public double getDailyRevenue() { return dailyRevenue; }
        public int getSessions() { return sessions; }
        public double getAvgRevenue() { return avgRevenue; }
    }

    public static class TherapistPerformanceDTO {
        private String therapistId;
        private String name;
        private int sessionsConducted;
        private double revenueGenerated;
        private int programsHandled;

        public TherapistPerformanceDTO(String therapistId, String name, int sessionsConducted,
                                       double revenueGenerated, int programsHandled) {
            this.therapistId = therapistId;
            this.name = name;
            this.sessionsConducted = sessionsConducted;
            this.revenueGenerated = revenueGenerated;
            this.programsHandled = programsHandled;
        }

        public String getTherapistId() { return therapistId; }
        public String getName() { return name; }
        public int getSessionsConducted() { return sessionsConducted; }
        public double getRevenueGenerated() { return revenueGenerated; }
        public int getProgramsHandled() { return programsHandled; }
    }

    public static class ProgramPopularityDTO {
        private String programId;
        private String name;
        private int totalPatients;
        private int totalSessions;
        private double revenueGenerated;

        public ProgramPopularityDTO(String programId, String name, int totalPatients,
                                    int totalSessions, double revenueGenerated) {
            this.programId = programId;
            this.name = name;
            this.totalPatients = totalPatients;
            this.totalSessions = totalSessions;
            this.revenueGenerated = revenueGenerated;
        }

        public String getProgramId() { return programId; }
        public String getName() { return name; }
        public int getTotalPatients() { return totalPatients; }
        public int getTotalSessions() { return totalSessions; }
        public double getRevenueGenerated() { return revenueGenerated; }
    }
}