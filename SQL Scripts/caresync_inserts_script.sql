-- ============================================================================================================
-- CARESYNC INSERTS: Production-like sample data
-- ============================================================================================================
USE caresync;

-- Address
INSERT INTO clinic_addresses (street_address, city, state)
VALUES
('123 Wellness Way', 'Springfield', 'il'),
('456 Care Blvd', 'Shelbyville', 'in'),
('789 Health St', 'Ogdenville', 'oh');

-- Clinics
INSERT INTO clinics (clinic_name, phone, address_id)
VALUES
('Downtown Medical Center', '555-123-4567', 1),
('Northside Clinic', '555-987-6543', 2),
('HealthPlus Ogden', '555-111-2222', 3);

-- Rooms
INSERT INTO rooms (room_type, room_cost)
VALUES
('General Checkup', 75.00),
('Emergency', 200.00),
('Surgery', 500.00);

-- Staff
INSERT INTO staff (first_name, last_name, staff_role, clinic_id)
VALUES
('Alice', 'Morgan', 'doctor', 1),
('Bob', 'Thompson', 'nurse', 1),
('Carol', 'Reed', 'receptionist', 2),
('David', 'King', 'admin', 3);

-- Staff Auth
INSERT INTO staff_auth (staff_id, email, hashed_password) -- '1234'
VALUES
(1, 'alice.morgan@clinic.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(2, 'bob.thompson@clinic.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(3, 'carol.reed@clinic.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(4, 'david.king@clinic.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO');

-- Emergency Contacts
INSERT INTO emergency_contacts (first_name, last_name, phone, email)
VALUES
('Emily', 'Morgan', '555-300-4000', 'emily.morgan@example.com'),
('Frank', 'Thompson', '555-300-4001', 'frank.thompson@example.com');

-- Patients
INSERT INTO patients (first_name, middle_init, last_name, gender, phone, email, contact_id)
VALUES
('Grace', 'L', 'Hopper', 'f', '555-111-1111', 'grace.hopper@example.com', 1),
('Henry', 'B', 'Ford', 'm', '555-222-2222', 'henry.ford@example.com', 2);

-- Treatments
INSERT INTO treatments (treatment_name, cost)
VALUES
('X-Ray', 100.00),
('Flu Shot', 40.00),
('Physical Therapy', 75.00);

-- Medications
INSERT INTO medications (med_name, cost, stock_quantity)
VALUES
('Ibuprofen', 10.00, 100),
('Amoxicillin', 25.00, 50),
('Metformin', 30.00, 75);

-- Visit Records
INSERT INTO visit_records (patient_id, clinic_id, room_id, staff_id, reason_for_visit, symptoms, diagnosis, medicine_prescribed)
VALUES
(1, 1, 1, 1, 'Routine Checkup', 'Mild headache', 'Tension headache', TRUE),
(2, 2, 2, 2, 'Flu Symptoms', 'Fever, cough', 'Influenza', TRUE);

-- Record_Treatments
INSERT INTO record_treatments (record_id, treatment_id)
VALUES
(1, 1),
(2, 2);

-- Record_Prescriptions
INSERT INTO record_prescriptions (record_id, med_id, quantity)
VALUES
(1, 1, 2),
(2, 2, 1);

-- Invoices
INSERT INTO invoices (record_id, total_amount, paid)
VALUES
(1, 185.00, TRUE),
(2, 265.00, FALSE);