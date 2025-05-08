package controller;

import Model.Patient;
import Model.Visit;
import core.SceneID;
import core.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import util.AppGlobals;
import util.CareSyncDB;

import java.sql.*;
import java.time.LocalDate;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class VisitsViewController
{
    @FXML private Label lastFirstLabel;
    @FXML private ImageView logo;

    @FXML private TableView<Visit> visitsTable;
    @FXML private TextField searchField;
    @FXML private VBox visitVBox;
    @FXML private Button addVisitButton;

    private ObservableList<Visit> visits = FXCollections.observableArrayList();
    private VBox detailView;
    private VBox formView;

    private boolean formVisible = false;

    @FXML
    public void initialize()
    {
        lastFirstLabel.setText(AppGlobals.activeUser.getLastName() + ", " + AppGlobals.activeUser.getFirstName());
        logo.setImage(AppGlobals.CARESYNCLOGO);

        ObservableList<Visit> loadedVisits = pullVisitsFromDB();
        if(loadedVisits != null)
            visits.setAll(loadedVisits);
        else
            System.err.println("Failed to load visits from DB");

        configureTable();
    }

    private void setupPatientUI() {
        addVisitButton.setOnAction(e -> toggleFormVisibility());

        // Create patient detail panel
        detailView = new VBox(10);
        detailView.setPadding(new Insets(10));
        detailView.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-background-color: #f8f8f8;");
        detailView.setVisible(false); // hidden until user clicks

        // Create patient form panel
        formView = createAddVisitForm();
        formView.setVisible(false);

        visitVBox.getChildren().addAll(detailView, formView);
        VBox.setVgrow(detailView, Priority.ALWAYS);
        VBox.setVgrow(formView, Priority.ALWAYS);

        // Table click handler
        visitsTable.setOnMouseClicked(event -> {
            Visit selected = visitsTable.getSelectionModel().getSelectedItem();
            if (selected != null) showVisitSummary(selected);
        });
    }

    private void showVisitSummary(Visit visit) {
        detailView.getChildren().clear();

        Label header = new Label("Visit Summary");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label name = new Label("Patient: " + visit.getFormattedPatientName());
        name.setStyle("-fx-font-size: 14px;");
        Label date = new Label("Date: " + visit.getVisitDate().toString());
        date.setStyle("-fx-font-size: 14px;");
        Label clinic = new Label("Clinic: " + visit.getClinicName());
        clinic.setStyle("-fx-font-size: 14px;");
        Label room = new Label("Room: " + visit.getRoomType());
        room.setStyle("-fx-font-size: 14px;");
        Label doctor = new Label("Doctor: " + visit.getFormattedStaffName());
        doctor.setStyle("-fx-font-size: 14px;");
        Label symptoms = new Label("Symptoms: " + visit.getSymptoms());
        symptoms.setStyle("-fx-font-size: 14px;");
        Label diagnosis = new Label("Diagnosis: " + visit.getDiagnosis());
        diagnosis.setStyle("-fx-font-size: 14px;");
        Label treatments = new Label("Treatments: " + visit.getTreatments());
        treatments.setStyle("-fx-font-size: 14px;");
        Label prescriptions = new Label("Prescriptions: " + visit.getPrescriptions());
        prescriptions.setStyle("-fx-font-size: 14px;");

        detailView.getChildren().addAll(header, name, date, clinic, room, doctor);
        
        if(AppGlobals.activeUser.getStaffRole().equals("doctor"))
            detailView.getChildren().addAll(symptoms, treatments, prescriptions);

        detailView.setVisible(true);
        detailView.setManaged(true);
        VBox.setVgrow(detailView, Priority.ALWAYS);

        formView.setVisible(false);
        formView.setManaged(false);
        formVisible = false;
    }

    public void configureTable()
    {
        visitsTable.getColumns().clear();

        TableColumn<Visit, String> pNameCol = new TableColumn<>("Patient Name");
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("formattedPatientName"));
        TableColumn<Visit, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        dateCol.setStyle( "-fx-alignment: CENTER;");
        TableColumn<Visit, String> sNameCol = new TableColumn<>("Staff Name");
        sNameCol.setCellValueFactory(new PropertyValueFactory<>("formattedStaffName"));
        sNameCol.setStyle( "-fx-alignment: CENTER;");
        TableColumn<Visit, String> completeCol = new TableColumn<>("Status");
        completeCol.setCellValueFactory(new PropertyValueFactory<>("complete"));

        visitsTable.getColumns().addAll(pNameCol, dateCol);

        FilteredList<Visit> filteredData = new FilteredList<>(visits, p -> true);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(visit -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lower = newValue.toLowerCase();
                return visit.getFormattedPatientName().toLowerCase().contains(lower) ||
                        visit.getVisitDate().toString().contains(lower) ||
                        visit.getFormattedStaffName().toLowerCase().contains(lower) ||
                        (visit.isComplete() ? "Complete" : "Incomplete").contains(lower);
            });
        });

        SortedList<Visit> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(visitsTable.comparatorProperty());

        visitsTable.setItems(sortedData);

        visitsTable.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        visitsTable.setStyle("-fx-font-size: 16px;");
        visitsTable.setFixedCellSize(40);
    }

    public ObservableList<Visit> pullVisitsFromDB()
    {
        ObservableList<Visit> dbVisits = FXCollections.observableArrayList();

        String query = "SELECT * FROM recent_visits WHERE clinic_id = ? ";
        if(AppGlobals.activeUser.getStaffRole().equals("doctor"))
            query += "&& staff_id = ? ";
        query += " ORDER BY visit_date DESC";

        try(Connection conn = CareSyncDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, AppGlobals.activeUser.getClinicID());
            if(AppGlobals.activeUser.getStaffRole().equals("doctor"))
                stmt.setInt(2, AppGlobals.activeUser.getStaffID());
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                int visitID = rs.getInt("record_id");
                int patientID = rs.getInt("patient_id");
                int clinicID = rs.getInt("clinic_id");
                int roomID = rs.getInt("room_id");
                int staffID = rs.getInt("staff_id");
                String reason = rs.getString("reason_for_visit");
                Timestamp timestamp = rs.getTimestamp("visit_date");
                LocalDate date = timestamp.toLocalDateTime().toLocalDate();
                String pFName = rs.getString("patient_first_name");
                char pMInit = rs.getString("patient_middle_init").charAt(0);
                String pLName = rs.getString("patient_last_name");
                String clinicName = rs.getString("clinic_name");
                String roomType = rs.getString("room_type");
                String staffRole = rs.getString("staff_role");
                String staffFirstName = rs.getString("staff_first_name");
                String staffLastName = rs.getString("staff_last_name");
                String symptoms = rs.getString("symptoms");
                String diagnosis = rs.getString("diagnosis");
                String treatments = rs.getString("treatments");
                String prescriptions = rs.getString("prescriptions");
                boolean complete = rs.getBoolean("is_complete");

                dbVisits.add(new Visit(visitID, patientID, clinicID, roomID, staffID, reason, date,
                                       pFName, pMInit, pLName, clinicName, roomType, staffRole, staffFirstName,
                                       staffLastName, symptoms, diagnosis, treatments, prescriptions, complete));
            }
            rs.close();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return dbVisits;
    }

    private void toggleFormVisibility() {
        formVisible = !formVisible;

        if (formVisible) {
            formView.setVisible(true);
            formView.setManaged(true);
            VBox.setVgrow(formView, Priority.ALWAYS);

            detailView.setVisible(false);
            detailView.setManaged(false);
        } else {
            formView.setVisible(false);
            formView.setManaged(false);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    public void onBackClick(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.RECEPTIONIST_DASH);
    }
}
