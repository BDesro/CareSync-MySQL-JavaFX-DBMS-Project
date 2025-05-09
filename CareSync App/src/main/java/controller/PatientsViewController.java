package controller;

import Model.Patient;
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

public class PatientsViewController
{
    @FXML private Label userLastFirstName;
    @FXML private ImageView logo;
    @FXML private TableView<Patient> patientsTable;
    @FXML private TextField searchField;
    @FXML private VBox patientVBox;
    @FXML private Button addPatientButton;

    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private VBox detailView;
    private VBox formView;

    private boolean formVisible = false;

    @FXML
    public void initialize()
    {
        userLastFirstName.setText(AppGlobals.activeUser.getLastName() + ", " + AppGlobals.activeUser.getFirstName());
        logo.setImage(AppGlobals.CARESYNCLOGO);

        ObservableList<Patient> loadedPatients = CareSyncDB.pullPatientsFromDB();
        if (loadedPatients != null) {
            patients.setAll(loadedPatients);
        } else {
            System.err.println("Failed to load patients from DB.");
        }

        configureTable();
        setupPatientUI();
    }

    private void setupPatientUI() {
        addPatientButton.setOnAction(e -> toggleFormVisibility());

        // Create patient detail panel
        detailView = new VBox(10);
        detailView.setPadding(new Insets(10));
        detailView.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-background-color: #f8f8f8;");
        detailView.setVisible(false); // hidden until user clicks

        // Create patient form panel
        formView = createAddPatientForm();
        formView.setVisible(false);

        patientVBox.getChildren().addAll(detailView, formView);
        VBox.setVgrow(detailView, Priority.ALWAYS);
        VBox.setVgrow(formView, Priority.ALWAYS);

        // Table click handler
        patientsTable.setOnMouseClicked(event -> {
            Patient selected = patientsTable.getSelectionModel().getSelectedItem();
            if (selected != null) showPatientSummary(selected);
        });
    }

    private void showPatientSummary(Patient patient) {
        detailView.getChildren().clear();

        String cFirstName = "";
        String cLastName = "";
        String cPhone = "";
        String cEmail = "";

        String query = "SELECT * FROM emergency_contacts WHERE contact_id = ?";

        try(Connection conn = CareSyncDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, patient.getContactID());
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                cFirstName = rs.getString("first_name");
                cLastName = rs.getString("last_name");
                cPhone = rs.getString("phone");
                cEmail = rs.getString("email");

            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            showAlert("Couldn't connect to database.");
            return;
        }

        Label header = new Label("Patient Summary");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label name = new Label("Name: " + patient.getFirstName() + " " + patient.getMiddleInitial() + ", " + patient.getLastName());
        name.setStyle("-fx-font-size: 14px;");
        Label dob = new Label("Date of Birth: " + patient.getDateOfBirth().toString());
        dob.setStyle("-fx-font-size: 14px;");
        Label gender = new Label("Gender: " + patient.getGender());
        gender.setStyle("-fx-font-size: 14px;");
        Label phone = new Label("Phone: " + patient.getPhoneNumber());
        phone.setStyle("-fx-font-size: 14px;");
        Label email = new Label("Email: " + patient.getEmail());
        email.setStyle("-fx-font-size: 14px;");

        Label contactHeader = new Label("Emergency Contact");
        contactHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label cNameLabel = new Label("Name: " + cFirstName + " " + cLastName);
        cNameLabel.setStyle("-fx-font-size: 14px;");
        Label cPhoneLabel = new Label("Phone: " + cPhone);
        cPhoneLabel.setStyle("-fx-font-size: 14px;");
        Label cEmailLabel = new Label("Email: " + cEmail);
        cEmailLabel.setStyle("-fx-font-size: 14px;");

        detailView.getChildren().addAll(header, name, dob, gender, phone, email,
                                        contactHeader, cNameLabel, cPhoneLabel, cEmailLabel);

        detailView.setVisible(true);
        detailView.setManaged(true);
        VBox.setVgrow(detailView, Priority.ALWAYS);

        formView.setVisible(false);
        formView.setManaged(false);
        formVisible = false;
    }


    private VBox createAddPatientForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10));
        form.setStyle("-fx-border-color: darkgray; -fx-background-color: #f0f0ff;");

        Label title = new Label("Add New Patient");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField midInitField = new TextField();
        midInitField.setPromptText("Middle Initial");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("Date of Birth");

        TextField genderField = new TextField();
        genderField.setPromptText("Gender");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Label contactPrompt = new Label("Emergency Contact Information");
        contactPrompt.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField contactFNameField = new TextField();
        contactFNameField.setPromptText("Contact First Name");

        TextField contactLNameField = new TextField();
        contactLNameField.setPromptText("Contact Last Name");

        TextField contactPhoneField = new TextField();
        contactPhoneField.setPromptText("Contact Phone");

        TextField contactEmailField = new TextField();
        contactEmailField.setPromptText("Contact Email");

        Button saveButton = new Button("Register Patient");
        saveButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            char middleInit = midInitField.getText().charAt(0);
            String lastName = lastNameField.getText();
            LocalDate dob = dobPicker.getValue();
            char gender = genderField.getText().charAt(0);
            String phone = phoneField.getText();
            String email = emailField.getText();

            String contactFName = contactFNameField.getText();
            String contactLName = contactLNameField.getText();
            String contactPhone = contactPhoneField.getText();
            String contactEmail = contactEmailField.getText();

            if (firstName.isEmpty() || midInitField.getText().isEmpty() || lastName.isEmpty() || dob == null ||
                genderField.getText().isEmpty() || phone.isEmpty() || email.isEmpty() || contactFName.isEmpty() ||
                contactLName.isEmpty() || contactPhone.isEmpty() || contactEmail.isEmpty())
            {
                showAlert("Missing required fields");
                return;
            }

            String call = "{CALL AddPatientWithContact(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            try (Connection conn = CareSyncDB.getConnection();
                 CallableStatement stmt = conn.prepareCall(call))
            {
                stmt.setString(1, firstName);
                stmt.setString(2, String.valueOf(middleInit));
                stmt.setString(3, lastName);
                stmt.setDate(4, Date.valueOf(dob));
                stmt.setString(5, String.valueOf(gender));
                stmt.setString(6, phone);
                stmt.setString(7, email);
                stmt.setString(8, contactFName);
                stmt.setString(9, contactLName);
                stmt.setString(10, contactPhone);
                stmt.setString(11, contactEmail);
                stmt.registerOutParameter(12, Types.INTEGER);
                stmt.registerOutParameter(13, Types.INTEGER);

                stmt.execute();

                int newPatientId = stmt.getInt(12); // retrieved from OUT param
                int newContactId = stmt.getInt(13);

                Patient newPatient = new Patient(newPatientId, firstName, middleInit, lastName, dob, gender, phone, email, newContactId);
                patients.add(newPatient);
                showAlert("Patient Added Successfully!");

                firstNameField.clear();
                midInitField.clear();
                lastNameField.clear();
                dobPicker.setValue(null);
                genderField.clear();
                phoneField.clear();
                emailField.clear();
                contactFNameField.clear();
                contactLNameField.clear();
                contactPhoneField.clear();
                contactEmailField.clear();
                formVisible = false;
                form.setVisible(false);
            } catch(SQLException ex) {
                System.out.println(ex.getMessage());
                showAlert("Failed to Insert Patient.");
            }

            toggleFormVisibility();
        });

        form.getChildren().addAll(title, firstNameField, midInitField, lastNameField, dobPicker, genderField, phoneField, emailField,
                                  contactPrompt, contactFNameField, contactLNameField, contactPhoneField, contactEmailField, saveButton);
        return form;
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

    public void configureTable()
    {
        patientsTable.getColumns().clear();

        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("formattedName"));

        TableColumn<Patient, String> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dobCol.setStyle( "-fx-alignment: CENTER;");
        dobCol.setSortable(false);

        patientsTable.getColumns().addAll(nameCol, dobCol);

        FilteredList<Patient> filteredData = new FilteredList<>(patients, p -> true);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lower = newValue.toLowerCase();
                return patient.getFormattedName().toLowerCase().contains(lower) ||
                       patient.getDateOfBirth().toString().contains(lower);
            });
        });

        SortedList<Patient> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(patientsTable.comparatorProperty());

        patientsTable.setItems(sortedData);

        patientsTable.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        patientsTable.setStyle("-fx-font-size: 16px;"); // Increase font size
        patientsTable.setFixedCellSize(40);             // Increase row height
    }


}
