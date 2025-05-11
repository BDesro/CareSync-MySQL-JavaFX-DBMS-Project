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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import util.AppGlobals;
import util.CareSyncDB;

import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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

        if(!AppGlobals.activeUser.getStaffRole().equals("receptionist") &&
           !AppGlobals.activeUser.getStaffRole().equals("admin"))
            addVisitButton.setVisible(false);
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
            Button editVisitButton = new Button("Edit Visit");
            editVisitButton.setStyle("-fx-font-size: 14px;");
            detailView.getChildren().addAll(symptoms, treatments, prescriptions, editVisitButton);
            editVisitButton.setOnAction(e -> {
                detailView.getChildren().removeAll(symptoms, diagnosis, treatments, prescriptions, editVisitButton);

                HBox symptomBox = new HBox(10);
                Label symptomPrompt = new Label("Symptoms: ");
                TextField editSymptoms = new TextField();
                editSymptoms.setPromptText("Enter Symptoms");
                editSymptoms.setText(visit.getSymptoms());
                symptomBox.getChildren().addAll(symptomPrompt, editSymptoms);

                HBox diagnosisBox = new HBox(10);
                Label diagnosisPrompt = new Label("Diagnosis: ");
                TextField editDiagnosis = new TextField();
                editDiagnosis.setPromptText("Enter Diagnosis");
                editDiagnosis.setText(visit.getDiagnosis());
                diagnosisBox.getChildren().addAll(diagnosisPrompt, editDiagnosis);
                detailView.getChildren().addAll(symptomBox, diagnosisBox);

                HBox treatmentPrescriptionBox = new HBox(10);

                VBox treatmentsBox = new VBox(10);
                VBox selectedTreatmentsBox = new VBox(); // where selected treatments will be shown
                ComboBox<Treatment> treatmentSelector = new ComboBox<>();
                treatmentSelector.getItems().addAll(CareSyncDB.pullTreatmentsFromDB()); // sample treatments
                Button addTButton = new Button("Add Treatment");
                Set<Treatment> selectedTreatments = new HashSet<>();
                addTButton.setOnAction(ev -> {
                    Treatment selected = treatmentSelector.getValue();
                    if (selected != null && !selectedTreatments.contains(selected)) {
                        selectedTreatments.add(selected);

                        // Create a UI node for the selected treatment
                        HBox treatmentItem = new HBox(10);
                        Label treatmentLabel = new Label(selected.getTreatmentName());
                        Button removeButton = new Button("Remove");

                        removeButton.setOnAction(eve -> {
                            selectedTreatmentsBox.getChildren().remove(treatmentItem);
                            selectedTreatments.remove(selected);
                        });

                        treatmentItem.getChildren().addAll(treatmentLabel, removeButton);
                        selectedTreatmentsBox.getChildren().add(treatmentItem);
                    }
                });
                HBox tSelectorBox = new HBox(10, treatmentSelector, addTButton);
                treatmentsBox.getChildren().addAll(new Label("Treatments"), selectedTreatmentsBox, tSelectorBox);

                VBox prescriptionsBox = new VBox(10);
                VBox selectedPrescriptionBox = new VBox(); // where selected treatments will be shown
                ComboBox<Medicine> prescriptionSelector = new ComboBox<>();
                prescriptionSelector.getItems().addAll(CareSyncDB.pullMedicinesFromDB()); // sample treatments
                TextField quantity = new TextField();
                quantity.setPrefWidth(USE_COMPUTED_SIZE);
                quantity.setPromptText("Enter Quantity");
                Button addPButton = new Button("Add Prescription");
                Set<Medicine> selectedPrescriptions = new HashSet<>();
                addPButton.setOnAction(ev -> {
                    Medicine selected = prescriptionSelector.getValue();
                    if (selected != null && !selectedPrescriptions.contains(selected))
                    {
                        selected.setQuantity(Integer.parseInt(quantity.getText()));
                        selectedPrescriptions.add(selected);

                        // Create a UI node for the selected treatment
                        HBox prescriptionItem = new HBox(10);
                        Label prescriptionLabel = new Label(selected.getMedicineName());
                        Label prescriptionQuantity = new Label("q; " + selected.getQuantity());
                        Button removeButton = new Button("Remove");

                        removeButton.setOnAction(eve -> {
                            selectedPrescriptionBox.getChildren().remove(prescriptionItem);
                            selectedPrescriptions.remove(selected);
                        });

                        prescriptionItem.getChildren().addAll(prescriptionLabel, prescriptionQuantity, removeButton);
                        selectedPrescriptionBox.getChildren().add(prescriptionItem);
                    }
                });
                HBox pSelectorBox = new HBox(10, prescriptionSelector, quantity, addPButton);
                prescriptionsBox.getChildren().addAll(new Label("Prescriptions"), selectedPrescriptionBox, pSelectorBox);

                treatmentPrescriptionBox.getChildren().addAll(treatmentsBox, prescriptionsBox);

                HBox actionsBox = new HBox(10);
                Button confirmChanges = new Button("Complete Visit");
                confirmChanges.setOnAction(ev -> {
                    CareSyncDB.updateVisit(visit, symptoms.getText(), diagnosis.getText());
                    CareSyncDB.updateTreatmentsForVisit(visit, selectedTreatments);
                    CareSyncDB.updatePrescriptionsForVisit(visit, selectedPrescriptions);
                    showVisitSummary(CareSyncDB.getVisitByID(visit.getRecordID()));
                });
                Button cancelButton = new Button("Cancel");
                cancelButton.setOnAction(ev -> {
                    showVisitSummary(visit);
                });
                actionsBox.getChildren().addAll(confirmChanges, cancelButton);

                detailView.getChildren().addAll(treatmentPrescriptionBox, actionsBox);
            });
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
