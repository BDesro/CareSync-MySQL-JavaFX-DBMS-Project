package controller;

import Model.*;
import core.SceneID;
import core.SceneManager;
import javafx.beans.property.SimpleStringProperty;
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
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

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

        ObservableList<Visit> loadedVisits = CareSyncDB.pullVisitsFromDB();
        if(loadedVisits != null)
            visits.setAll(loadedVisits);
        else
            System.err.println("Failed to load visits from DB");

        configureTable();
        setupVisitUI();
    }

    private void setupVisitUI() {
        if(AppGlobals.activeUser.getStaffRole().equals("receptionist") ||
           AppGlobals.activeUser.getStaffRole().equals("admin"))
            addVisitButton.setOnAction(e -> toggleFormVisibility());

        // Create visit detail panel
        detailView = new VBox(10);
        detailView.setPadding(new Insets(10));
        detailView.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-background-color: #f8f8f8;");
        detailView.setVisible(false); // hidden until user clicks

        // Create visit form panel
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
        Label complete = new Label("Complete?: " + visit.isComplete());
        complete.setStyle("-fx-font-size: 14px;");

        detailView.getChildren().addAll(header, name, date, clinic, room, doctor, complete);

        if(AppGlobals.activeUser.getStaffRole().equals("receptionist") && visit.isComplete())
        {
            Invoice invoice = CareSyncDB.getInvoiceForRecord(visit.getRecordID());
            Label invoiceTotal = new Label("Invoice Total: " + NumberFormat.getCurrencyInstance(Locale.US).format(invoice.getTotalAmount()));
            invoiceTotal.setStyle("-fx-font-size: 14px;");
            Label invoicePaid = new Label("Invoice Paid: " + invoice.isPaid());
            invoicePaid.setStyle("-fx-font-size: 14px;");

            detailView.getChildren().addAll(invoiceTotal, invoicePaid);
        }

        if(AppGlobals.activeUser.getStaffRole().equals("doctor"))
        {
            Label symptoms = new Label("Symptoms: " + visit.getSymptoms());
            symptoms.setStyle("-fx-font-size: 14px;");
            Label diagnosis = new Label("Diagnosis: " + visit.getDiagnosis());
            diagnosis.setStyle("-fx-font-size: 14px;");
            Label treatments = new Label("Treatments: " + visit.getTreatments());
            treatments.setStyle("-fx-font-size: 14px;");
            Label prescriptions = new Label("Prescriptions: " + visit.getPrescriptions());
            prescriptions.setStyle("-fx-font-size: 14px;");
            detailView.getChildren().addAll(symptoms, treatments, prescriptions);
        }

        if(AppGlobals.activeUser.getStaffRole().equals("admin"))
        {
            Button deleteButton = new Button("Delete visit");
            deleteButton.setStyle("-fx-font-size: 14px;");
            deleteButton.setOnAction(e -> {
                if(showDeletionConfirmationAlert())
                {
                    CareSyncDB.deleteVisit(visit);
                    visits.remove(visit);
                }
            });

            detailView.getChildren().addAll(deleteButton);
        }

        detailView.setVisible(true);
        detailView.setManaged(true);
        VBox.setVgrow(detailView, Priority.ALWAYS);

        formView.setVisible(false);
        formView.setManaged(false);
        formVisible = false;
    }

    private VBox createAddVisitForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10));
        form.setStyle("-fx-border-color: darkgray; -fx-background-color: #f0f0ff;");

        Label title = new Label("Add New Visit");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<Patient> patientSelector = new ComboBox<>();
        patientSelector.getItems().addAll(Objects.requireNonNull(CareSyncDB.pullPatientsFromDB()));
        patientSelector.setPromptText("Select Patient");

        ComboBox<Staff> staffSelector = new ComboBox<>();
        staffSelector.getItems().addAll(Objects.requireNonNull(CareSyncDB.pullDoctorsFromDB()));
        staffSelector.setPromptText("Select Doctor");

        TextField clinicField = new TextField(AppGlobals.currentClinic.getClinicName());
        clinicField.setEditable(false);

        ComboBox<Room> roomSelector = new ComboBox<>();
        roomSelector.getItems().addAll(Objects.requireNonNull(CareSyncDB.pullRoomsFromDB()));
        roomSelector.setPromptText("Select Room");

        TextField reasonField = new TextField();
        reasonField.setPromptText("Reason for Visit");

        Button saveButton = new Button("Submit Visit");
        saveButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        saveButton.setOnAction(e -> {
            Patient patient = patientSelector.getSelectionModel().getSelectedItem();
            Staff staff = staffSelector.getSelectionModel().getSelectedItem();
            Clinic clinic = AppGlobals.currentClinic;
            Room room = roomSelector.getSelectionModel().getSelectedItem();
            String reason = reasonField.getText();

            if (patient == null || staff == null || reason == null)
            {
                showAlert("Missing required fields");
                return;
            }

            String call = "{CALL AddNewVisit(?, ?, ?, ?, ?, ?)}";

            try (Connection conn = CareSyncDB.getConnection();
                 CallableStatement stmt = conn.prepareCall(call))
            {
                stmt.setInt(1, patient.getPatientID());
                stmt.setInt(2, clinic.getClinicID());
                stmt.setInt(3, room.getRoomID());
                stmt.setInt(4, staff.getStaffID());
                stmt.setString(5, reason);
                stmt.registerOutParameter(6, Types.INTEGER);

                stmt.execute();

                int newVisitID = stmt.getInt(6);

                Visit visit = CareSyncDB.getVisitByID(newVisitID);
                visits.add(visit);
                showAlert("Successfully added new visit");

                patientSelector.getSelectionModel().clearSelection();
                staffSelector.getSelectionModel().clearSelection();
                clinicField.clear();
                roomSelector.getSelectionModel().clearSelection();
                reasonField.clear();
                formVisible = false;
                form.setVisible(false);
            } catch(SQLException ex) {
                System.out.println(ex.getMessage());
                showAlert("Failed to Submit Visit");
            }

            toggleFormVisibility();
        });

        form.getChildren().addAll(title, patientSelector, staffSelector, clinicField, roomSelector, reasonField, saveButton);
        return form;
    }

    public void configureTable()
    {
        visitsTable.getColumns().clear();

        TableColumn<Visit, String> pNameCol = new TableColumn<>("Patient Name");
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("formattedPatientName"));
        pNameCol.setStyle("-fx-alignment: CENTER;");
        TableColumn<Visit, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        dateCol.setStyle( "-fx-alignment: CENTER;");
        TableColumn<Visit, String> sNameCol = new TableColumn<>("Staff Name");
        sNameCol.setCellValueFactory(new PropertyValueFactory<>("formattedStaffName"));
        sNameCol.setStyle( "-fx-alignment: CENTER;");
        TableColumn<Visit, String> completeCol = new TableColumn<>("Status");
        completeCol.setCellValueFactory(cellData -> {
            boolean complete = cellData.getValue().isComplete();
            String status = complete ? "Complete" : "Incomplete";
            return new SimpleStringProperty(status);
        });
        completeCol.setStyle("-fx-alignment: CENTER;");

        visitsTable.getColumns().addAll(pNameCol, dateCol, sNameCol, completeCol);

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

    public boolean showDeletionConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Visit?");
        alert.setHeaderText("Are you sure you want to delete this visit?");

        ButtonType continueButton = new ButtonType("Continue");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(continueButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == continueButton;
    }

    public void onBackClick(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.DASH);
    }
}
