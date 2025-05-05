DROP DATABASE IF EXISTS caresync;
CREATE DATABASE caresync;
USE caresync;

-- TABLES
-- =================================================================================================================================

-- ===========================================
-- Address Table
-- ===========================================
DROP TABLE IF EXISTS clinic_addresses;
CREATE TABLE clinic_addresses
(
	address_id INT PRIMARY KEY AUTO_INCREMENT,
    street_address VARCHAR(50) NOT NULL,
    city VARCHAR(25) NOT NULL,
    state CHAR(2) NOT NULL
) AUTO_INCREMENT = 1;

-- Trigger to make sure state abbreviation is capitalized
DROP TRIGGER IF EXISTS addresses_before_insert
DELIMITER //
CREATE TRIGGER addresses_before_insert
BEFORE INSERT ON clinic_addresses
FOR EACH ROW
BEGIN
	SET NEW.state = UPPER(NEW.state);
END //
DELIMITER ;

-- ===========================================
-- Clinics Table
-- ===========================================
DROP TABLE IF EXISTS clinics;
CREATE TABLE clinics
(
	clinic_id INT PRIMARY KEY AUTO_INCREMENT,
    clinic_name VARCHAR(45) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    address_id INT NOT NULL,
    
    CONSTRAINT clinics_address_id_fk FOREIGN KEY (address_id) REFERENCES clinic_addresses (address_id)
		ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- ===========================================
-- Rooms Table
-- ===========================================
DROP TABLE IF EXISTS rooms;
CREATE TABLE rooms
(
	room_id INT PRIMARY KEY AUTO_INCREMENT,
    room_type VARCHAR(45) NOT NULL UNIQUE,
    room_cost DECIMAL(10, 2) NOT NULL
) AUTO_INCREMENT = 1;

-- ===========================================
-- Staff Table
-- ===========================================
DROP TABLE IF EXISTS staff;
CREATE TABLE staff
(
	staff_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    staff_role ENUM('nurse', 'doctor', 'receptionist', 'admin') NOT NULL,
    clinic_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    
    CONSTRAINT fk_staff_clinic_id_clinics FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id)
		ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- ===========================================
-- Emergency Contacts Table
-- ===========================================
DROP TABLE IF EXISTS emergency_contacts;
CREATE TABLE emergency_contacts
(
	contact_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL
) AUTO_INCREMENT = 1;

-- ===========================================
-- Patients Table
-- ===========================================
DROP TABLE IF EXISTS patients;
CREATE TABLE patients
(
	patient_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(25) NOT NULL,
    middle_init CHAR(1) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    contact_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    
    CONSTRAINT patients_contact_id_contacts_fk FOREIGN KEY (contact_id) REFERENCES emergency_contacts (contact_id)
		ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- Log table to track changes to Patients table
CREATE TABLE patient_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    action_type ENUM('INSERT', 'UPDATE', 'DELETE'),
    timestamp DATETIME DEFAULT NOW()
);

-- Trigger to update the patient log after an insertion
DROP TRIGGER IF EXISTS log_patient_insert;
DELIMITER //
CREATE TRIGGER log_patient_insert
AFTER INSERT ON patients
FOR EACH ROW
BEGIN
    INSERT INTO patient_log (patient_id, action_type)
    VALUES (NEW.patient_id, 'INSERT');
END //
DELIMITER ;

-- Trigger to update the patient log after changes are made
DROP TRIGGER IF EXISTS log_patient_update;
DELIMITER //
CREATE TRIGGER log_patient_update
AFTER UPDATE ON patients
FOR EACH ROW
BEGIN
    INSERT INTO patient_log (patient_id, action_type)
    VALUES (OLD.patient_id, 'UPDATE');
END //
DELIMITER ;

-- Trigger to update the patient log after changes are made
DROP TRIGGER IF EXISTS log_patient_delete;
DELIMITER //
CREATE TRIGGER log_patient_delete
AFTER DELETE ON patients
FOR EACH ROW
BEGIN
    INSERT INTO patient_log (patient_id, action_type)
    VALUES (OLD.patient_id, 'DELETE');
END //
DELIMITER ;

-- ===========================================
-- Treatments Table
-- ===========================================
DROP TABLE IF EXISTS treatments;
CREATE TABLE treatments
(
	treatment_id INT PRIMARY KEY AUTO_INCREMENT,
    treatment_name VARCHAR(100) NOT NULL UNIQUE,
    cost DECIMAL(8, 2) NOT NULL
) AUTO_INCREMENT = 1;

-- ===========================================
-- Medications Table
-- ===========================================
DROP TABLE IF EXISTS medications;
CREATE TABLE medications
(
	med_id INT PRIMARY KEY AUTO_INCREMENT,
    med_name VARCHAR(50) NOT NULL UNIQUE,
    cost DECIMAL(6, 2) NOT NULL,
    stock_quantity INT NOT NULL
) AUTO_INCREMENT = 1;

-- ===========================================
-- Visit Records Table
-- ===========================================
DROP TABLE IF EXISTS visit_records;
CREATE TABLE visit_records
(
	record_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    clinic_id INT NOT NULL,
    room_id INT NOT NULL,
    staff_id INT NOT NULL,
    visit_date DATETIME NOT NULL DEFAULT NOW(),
    reason_for_visit VARCHAR(255) NOT NULL DEFAULT "None Specified",
    symptoms VARCHAR(255) NOT NULL DEFAULT "None Specified",
    diagnosis VARCHAR(255) NOT NULL DEFAULT "None Specified",
    medicine_prescribed BOOL NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW(),
    
    CONSTRAINT patient_id_med_rec_fk FOREIGN KEY (patient_id) REFERENCES patients (patient_id)
		ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT clinic_id_med_rec_fk FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id)
		ON UPDATE CASCADE,
	CONSTRAINT room_id_med_rec_fk FOREIGN KEY (room_id) REFERENCES rooms (room_id)
		ON UPDATE CASCADE,
	CONSTRAINT staff_id_med_rec_fk FOREIGN KEY (staff_id) REFERENCES staff (staff_id)
		ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- ===========================================
-- Record/Treatments Join Table
-- ===========================================
DROP TABLE IF EXISTS record_treatments;
CREATE TABLE record_treatments
(
	record_id INT NOT NULL,
    treatment_id INT NOT NULL,
    
    CONSTRAINT record_treatments_pk PRIMARY KEY (record_id, treatment_id),
    CONSTRAINT record_id_r_t_fk FOREIGN KEY (record_id) REFERENCES visit_records (record_id)
		ON UPDATE CASCADE,
	CONSTRAINT treatment_id_fk FOREIGN KEY (treatment_id) REFERENCES treatments (treatment_id)
		ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- ===========================================
-- Record/Prescriptions Join Table
-- ===========================================
DROP TABLE IF EXISTS record_prescriptions;
CREATE TABLE record_prescriptions
(
	record_id INT NOT NULL,
    med_id INT NOT NULL,
    quantity INT NOT NULL,
    
    CONSTRAINT record_prescriptions_pk PRIMARY KEY (record_id, med_id),
    CONSTRAINT record_id_fk FOREIGN KEY (record_id) REFERENCES visit_records (record_id)
		ON UPDATE CASCADE,
	CONSTRAINT med_id_fk FOREIGN KEY (med_id) REFERENCES medications (med_id)
		ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- ===========================================
-- Invoices Table
-- ===========================================
DROP TABLE IF EXISTS invoices;
CREATE TABLE invoices
(
	invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    record_id INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    issue_date DATETIME NOT NULL DEFAULT NOW(),
    paid BOOL NOT NULL DEFAULT FALSE,
    
    CONSTRAINT record_id_invoice_fk FOREIGN KEY (record_id) REFERENCES visit_records (record_id)
) AUTO_INCREMENT = 1;
-- ==================================================================================================================================


-- VIEWS
-- ==================================================================================================================================

-- Creates the Recent Visits View
CREATE OR REPLACE VIEW recent_visits AS
	SELECT vr.record_id, p.first_name, p.middle_init, p.last_name, vr.visit_date, 
		   c.clinic_name, r.room_type, s.staff_role, s.first_name AS staff_fname, s.last_name AS staff_lname, 
           vr.diagnosis, t.treatment_name, m.med_name AS prescription_name
	FROM visit_records vr JOIN patients p 
		   ON vr.patient_id = p.patient_id
						  JOIN clinics c 
		   ON vr.clinic_id = c.clinic_id
						  JOIN rooms r 
		   ON vr.room_id = r.room_id
						  JOIN staff s
		   ON vr.staff_id = s.staff_id
						  JOIN record_treatments rt
		   ON vr.record_id = rt.record_id
						  JOIN treatments t 
		   ON rt.treatment_id = t.treatment_id
						  JOIN record_prescriptions rp
		   ON vr.record_id = rp.record_id
						  JOIN medications m
		   ON rp.med_id = m.med_id
	ORDER BY vr.visit_date DESC;

-- Creates the Unpaid Invoices View
CREATE OR REPLACE VIEW unpaid_invoices AS
	SELECT i.invoice_id, CONCAT(p.first_name, " ", p.last_name) AS patient_name,
		   i.total_amount, i.issue_date
	FROM invoices i JOIN visit_records vr 
		 ON i.record_id = vr.record_id
				    JOIN patients p 
		 ON vr.patient_id = p.patient_id
	WHERE i.paid = FALSE;

-- Creates the Staff Workload View (Read-Only)
CREATE OR REPLACE VIEW staff_workload AS
SELECT s.staff_id,
       CONCAT(s.first_name, ' ', s.last_name) AS staff_name,
       s.staff_role,
       COUNT(vr.record_id) AS patients_seen
FROM staff s LEFT JOIN visit_records vr 
	 ON s.staff_id = vr.staff_id
GROUP BY s.staff_id;
-- ==================================================================================================================================


-- STORED PROCEDURES
-- ==================================================================================================================================

-- Procedure to add a new patient with an emergency contact
DROP PROCEDURE IF EXISTS addPatientWithContact;
DELIMITER //
CREATE PROCEDURE addPatientWithContact(
	IN patient_fname VARCHAR(25), IN patient_mi CHAR(1), IN patient_lname VARCHAR(25), IN patient_phone VARCHAR(15), IN patient_email VARCHAR(50),
    IN contact_fname VARCHAR(25), IN contact_lname VARCHAR(25), IN contact_phone VARCHAR(15), IN contact_email VARCHAR(50)
)
BEGIN
	DECLARE new_contact_id INT;
    
	INSERT INTO emergency_contacts (first_name, last_name, phone, email)
    VALUES (contact_fname, contact_lname, contact_phone, contact_email);
    
    SET new_contact_id = LAST_INSERT_ID();
    
    INSERT INTO patients (first_name, middle_init, last_name, phone, email, contact_id)
    VALUES (patient_fname, patient_mi, patient_lname, patient_phone, patient_email, new_contact_id);
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getDoctorVisits;
DELIMITER //
CREATE PROCEDURE getDoctorVisits(IN first_name VARCHAR(25), IN last_name VARCHAR(50))
BEGIN
	SELECT * FROM recent_visits
    WHERE staff_name = CONCAT(first_name, " ", last_name);
END //
DELIMITER ;

-- Procedure to generate a new visit invoice
DROP PROCEDURE IF EXISTS generateInvoice;
DELIMITER //
CREATE PROCEDURE generateInvoice(IN p_record_id INT)
BEGIN
	DECLARE total_cost DECIMAL(10, 2);
    
    SELECT r.cost + IFNULL(t.cost, 0) + IFNULL(SUM(m.cost * rp.quantity), 0) INTO total_cost
	FROM visit_records vr JOIN record_treatments rt
		 ON vr.record_id = rt.record_id
						  JOIN treatments t
		 ON rt.treatment_id = t.treatment_id
						  JOIN record_prescriptions rp
		 ON vr.record_id = rp.record_id
						  JOIN medications m
		 ON rp.med_id = m.med_id
						  JOIN rooms r 
		 ON vr.room_id = r.room_id
	WHERE vr.record_id = p_record_id;
    
    INSERT INTO invoices (record_id, total_amount)
    VALUES (p_record_id, total_cost);
END //
DELIMITER ;

-- Procedure to add a new prescription to the record_prescriptions table
DROP PROCEDURE IF EXISTS addPrescription;
DELIMITER //
CREATE PROCEDURE addPrescription(
	IN p_record_id INT,
    IN p_med_id INT,
    IN p_quantity INT
)
BEGIN
	DECLARE med_stock INT;
    
    SELECT stock_quantity INTO med_stock
    FROM medications
    WHERE med_id = p_med_id;
    
    IF med_stock >= p_quantity THEN
		INSERT INTO record_prescriptions (record_id, med_id, quantity)
        VALUES (p_record_id, p_med_id, p_quantity);
        
        UPDATE medications
        SET stock_quantity = stock_quantity - p_quantity
        WHERE med_id = p_med_id;
	ELSE
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = "Not enough medication stock to fill the prescription.";
	END IF;
END //
DELIMITER ;

-- Procedure to add a new visit and update the proper tables
DROP PROCEDURE IF EXISTS addNewVisit;
DELIMITER //
CREATE PROCEDURE addNewVisit(
	IN in_patient_id INT, IN in_clinic_id INT, IN in_room_id INT, IN in_staff_id INT,
    IN in_reason_for_visit VARCHAR(255), IN in_symptoms VARCHAR(255), IN in_diagnosis VARCHAR(255), 
    IN in_treatment_id INT, IN in_med_id INT, IN in_med_quantity INT
)
BEGIN
	DECLARE new_record_id INT;
    
	INSERT INTO visit_records (patient_id, clinic_id, room_id, staff_id, reason_for_visit, 
							   symptoms, diagnosis, medicine_prescribed)
	VALUES (in_patient_id, in_clinic_id, in_room_id, in_staff_id, in_reason_for_visit,
			in_symptoms, in_diagnosis, IF(in_med_id IS NOT NULL, TRUE, FALSE));
	
    SET new_record_id = LAST_INSERT_ID();
    
    IF in_treatment_id IS NOT NULL THEN
		INSERT INTO record_treatments (record_id, treatment_id)
        VALUES (new_record_id, in_treatment_id);
	END IF;
    
    IF in_med_id IS NOT NULL AND in_med_quantity IS NOT NULL THEN
		CALL addPrescription(new_record_id, in_med_id, in_med_quantity);
	END IF;
    
    CALL generateInvoice(new_record_id);
END //
DELIMITER ;
-- ==================================================================================================================================


-- DATABASE USERS
-- ==================================================================================================================================

-- DROP USERS FIRST
DROP USER IF EXISTS 'doctor_user'@'localhost';
DROP USER IF EXISTS 'nurse_user'@'localhost';
DROP USER IF EXISTS 'receptionist_user'@'localhost';
DROP USER IF EXISTS 'admin_user'@'localhost';

-- CREATE USERS
CREATE USER 'doctor_user'@'localhost' IDENTIFIED BY 'StrongAppPass!';
CREATE USER 'nurse_user'@'localhost' IDENTIFIED BY 'ReadOnlyPass!';
CREATE USER 'receptionist_user'@'localhost' IDENTIFIED BY 'AuditReadPass!';
CREATE USER 'admin_user'@'localhost' IDENTIFIED BY 'AdminPass!';

-- GRANT PRIVILEGES

-- Admin has full access
GRANT ALL PRIVILEGES ON caresync.* TO 'admin_user'@'localhost';

-- Doctor can access their visits and can view patients
GRANT EXECUTE ON PROCEDURE getDoctorVisits TO 'doctor_user'@'localhost';
GRANT SELECT ON caresync.patients TO 'doctor_user'@'localhost';
GRANT SELECT, INSERT ON caresync.treatments TO 'doctor_user'@'localhost';
GRANT SELECT ON caresync.medications TO 'doctor_user'@'localhost';

-- Read-only user for analytics
GRANT EXECUTE ON PROCEDURE addPatientWithContact TO 'nurse_user'@'localhost';

GRANT EXECUTE ON PROCEDURE addNewVisit TO 'receptionist_user'@'localhost';

FLUSH PRIVILEGES;