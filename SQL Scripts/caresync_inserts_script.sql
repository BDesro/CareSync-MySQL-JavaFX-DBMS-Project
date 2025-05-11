-- Insert clinic addresses
INSERT INTO clinic_addresses (street_address, city, state) VALUES
('123 Main Street', 'Springfield', 'IL'),
('456 Oak Avenue', 'Chicago', 'IL'),
('789 Pine Road', 'New York', 'NY'),
('101 Elm Boulevard', 'Los Angeles', 'CA'),
('202 Maple Drive', 'Houston', 'TX');

-- Insert clinics
INSERT INTO clinics (clinic_name, phone, address_id) VALUES
('Springfield Medical Center', '217-555-1001', 1),
('Chicago Health Clinic', '312-555-2002', 2),
('NYC Wellness Center', '212-555-3003', 3),
('LA Family Clinic', '213-555-4004', 4),
('Houston Care Facility', '713-555-5005', 5);

-- Insert room types
INSERT INTO rooms (room_type, room_cost) VALUES
('Standard Examination Room', 75.00),
('Emergency Room', 250.00),
('Operating Room', 500.00),
('Pediatric Room', 100.00),
('Physical Therapy Room', 85.00);

-- Insert staff (2 admins, 2 receptionists, and 6 doctors per clinic)
-- Clinic 1 staff
INSERT INTO staff (first_name, last_name, staff_role, clinic_id) VALUES
('John', 'Smith', 'admin', 1),
('Sarah', 'Johnson', 'receptionist', 1),
('Michael', 'Williams', 'doctor', 1),
('Emily', 'Brown', 'doctor', 1),
('David', 'Jones', 'doctor', 1);

-- Clinic 2 staff
INSERT INTO staff (first_name, last_name, staff_role, clinic_id) VALUES
('Robert', 'Miller', 'admin', 2),
('Jennifer', 'Davis', 'receptionist', 2),
('Richard', 'Garcia', 'doctor', 2),
('Jessica', 'Rodriguez', 'doctor', 2),
('Thomas', 'Wilson', 'doctor', 2);

-- Clinic 3 staff
INSERT INTO staff (first_name, last_name, staff_role, clinic_id) VALUES
('Daniel', 'Martinez', 'admin', 3),
('Lisa', 'Anderson', 'receptionist', 3),
('Paul', 'Taylor', 'doctor', 3),
('Amy', 'Thomas', 'doctor', 3),
('Mark', 'Hernandez', 'doctor', 3);

-- Clinic 4 staff
INSERT INTO staff (first_name, last_name, staff_role, clinic_id) VALUES
('James', 'Moore', 'admin', 4),
('Michelle', 'Martin', 'receptionist', 4),
('Andrew', 'Jackson', 'doctor', 4),
('Melissa', 'Thompson', 'doctor', 4),
('Steven', 'White', 'doctor', 4);

-- Clinic 5 staff
INSERT INTO staff (first_name, last_name, staff_role, clinic_id) VALUES
('Kevin', 'Lee', 'admin', 5),
('Amanda', 'Harris', 'receptionist', 5),
('Brian', 'Clark', 'doctor', 5),
('Stephanie', 'Lewis', 'doctor', 5),
('Jason', 'Robinson', 'doctor', 5);

-- Insert staff auth credentials (using bcrypt hashed passwords - all passwords are "1234")
INSERT INTO staff_auth (staff_id, email, hashed_password) VALUES
(1, 'john.smith@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(2, 'sarah.johnson@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(3, 'michael.williams@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(4, 'emily.brown@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(5, 'david.jones@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(6, 'robert.miller@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(7, 'jennifer.davis@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(8, 'richard.garcia@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(9, 'jessica.rodriguez@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(10, 'thomas.wilson@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(11, 'daniel.martinez@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(12, 'lisa.anderson@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(13, 'paul.taylor@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(14, 'amy.thomas@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(15, 'mark.hernandez@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(16, 'james.moore@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(17, 'michelle.martin@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(18, 'andrew.jackson@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(19, 'melissa.thompson@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(20, 'steven.white@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(21, 'kevin.lee@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(22, 'amanda.harris@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(23, 'brian.clark@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(24, 'stephanie.lewis@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO'),
(25, 'jason.robinson@caresync.com', '$2a$10$HTw7hBnCmcY3EzdXNVB0WepnL.FuaCUxMWC6x8ENVBQ6oBFm3JPKO');

-- Insert emergency contacts and patients
-- Patients 1-10
INSERT INTO emergency_contacts (first_name, last_name, phone, email) VALUES
('William', 'Johnson', '555-111-2222', 'william.johnson@example.com'),
('Elizabeth', 'Davis', '555-111-3333', 'elizabeth.davis@example.com'),
('Christopher', 'Wilson', '555-111-4444', 'christopher.wilson@example.com'),
('Sarah', 'Anderson', '555-111-5555', 'sarah.anderson@example.com'),
('Joseph', 'Thomas', '555-111-6666', 'joseph.thomas@example.com'),
('Margaret', 'Jackson', '555-111-7777', 'margaret.jackson@example.com'),
('Thomas', 'White', '555-111-8888', 'thomas.white@example.com'),
('Nancy', 'Harris', '555-111-9999', 'nancy.harris@example.com'),
('Charles', 'Martin', '555-222-1111', 'charles.martin@example.com'),
('Karen', 'Thompson', '555-222-2222', 'karen.thompson@example.com');

INSERT INTO patients (first_name, middle_init, last_name, date_of_birth, gender, phone, email, contact_id) VALUES
('James', 'A', 'Johnson', '1980-05-15', 'M', '555-123-4567', 'james.johnson@example.com', 1),
('Mary', 'B', 'Davis', '1975-08-22', 'F', '555-234-5678', 'mary.davis@example.com', 2),
('Robert', 'C', 'Wilson', '1990-03-10', 'M', '555-345-6789', 'robert.wilson@example.com', 3),
('Jennifer', 'D', 'Anderson', '1985-11-30', 'F', '555-456-7890', 'jennifer.anderson@example.com', 4),
('Michael', 'E', 'Thomas', '1972-07-18', 'M', '555-567-8901', 'michael.thomas@example.com', 5),
('Linda', 'F', 'Jackson', '1988-02-25', 'F', '555-678-9012', 'linda.jackson@example.com', 6),
('William', 'G', 'White', '1995-09-05', 'M', '555-789-0123', 'william.white@example.com', 7),
('Barbara', 'H', 'Harris', '1978-12-12', 'F', '555-890-1234', 'barbara.harris@example.com', 8),
('Richard', 'I', 'Martin', '1983-04-20', 'M', '555-901-2345', 'richard.martin@example.com', 9),
('Susan', 'J', 'Thompson', '1992-06-08', 'F', '555-012-3456', 'susan.thompson@example.com', 10);

-- Patients 11-20
INSERT INTO emergency_contacts (first_name, last_name, phone, email) VALUES
('Daniel', 'Garcia', '555-222-3333', 'daniel.garcia@example.com'),
('Lisa', 'Martinez', '555-222-4444', 'lisa.martinez@example.com'),
('Matthew', 'Robinson', '555-222-5555', 'matthew.robinson@example.com'),
('Betty', 'Clark', '555-222-6666', 'betty.clark@example.com'),
('Anthony', 'Rodriguez', '555-222-7777', 'anthony.rodriguez@example.com'),
('Dorothy', 'Lewis', '555-222-8888', 'dorothy.lewis@example.com'),
('Donald', 'Lee', '555-222-9999', 'donald.lee@example.com'),
('Sandra', 'Walker', '555-333-1111', 'sandra.walker@example.com'),
('Mark', 'Hall', '555-333-2222', 'mark.hall@example.com'),
('Ashley', 'Allen', '555-333-3333', 'ashley.allen@example.com');

INSERT INTO patients (first_name, middle_init, last_name, date_of_birth, gender, phone, email, contact_id) VALUES
('Jessica', 'K', 'Garcia', '1987-01-14', 'F', '555-123-7890', 'jessica.garcia@example.com', 11),
('David', 'L', 'Martinez', '1979-10-03', 'M', '555-234-8901', 'david.martinez@example.com', 12),
('Karen', 'M', 'Robinson', '1993-07-19', 'F', '555-345-9012', 'karen.robinson@example.com', 13),
('Steven', 'N', 'Clark', '1981-04-27', 'M', '555-456-0123', 'steven.clark@example.com', 14),
('Nancy', 'O', 'Rodriguez', '1976-11-08', 'F', '555-567-1234', 'nancy.rodriguez@example.com', 15),
('Paul', 'P', 'Lewis', '1990-08-15', 'M', '555-678-2345', 'paul.lewis@example.com', 16),
('Emily', 'Q', 'Lee', '1984-05-22', 'F', '555-789-3456', 'emily.lee@example.com', 17),
('Andrew', 'R', 'Walker', '1973-02-11', 'M', '555-890-4567', 'andrew.walker@example.com', 18),
('Michelle', 'S', 'Hall', '1989-09-30', 'F', '555-901-5678', 'michelle.hall@example.com', 19),
('Joshua', 'T', 'Allen', '1994-12-25', 'M', '555-012-6789', 'joshua.allen@example.com', 20);

-- Patients 21-30
INSERT INTO emergency_contacts (first_name, last_name, phone, email) VALUES
('Kevin', 'Young', '555-333-4444', 'kevin.young@example.com'),
('Kimberly', 'Hernandez', '555-333-5555', 'kimberly.hernandez@example.com'),
('Brian', 'King', '555-333-6666', 'brian.king@example.com'),
('Amanda', 'Wright', '555-333-7777', 'amanda.wright@example.com'),
('George', 'Lopez', '555-333-8888', 'george.lopez@example.com'),
('Melissa', 'Hill', '555-333-9999', 'melissa.hill@example.com'),
('Edward', 'Scott', '555-444-1111', 'edward.scott@example.com'),
('Rebecca', 'Green', '555-444-2222', 'rebecca.green@example.com'),
('Ronald', 'Adams', '555-444-3333', 'ronald.adams@example.com'),
('Laura', 'Baker', '555-444-4444', 'laura.baker@example.com');

INSERT INTO patients (first_name, middle_init, last_name, date_of_birth, gender, phone, email, contact_id) VALUES
('Daniel', 'U', 'Young', '1982-06-17', 'M', '555-123-8901', 'daniel.young@example.com', 21),
('Sarah', 'V', 'Hernandez', '1977-03-24', 'F', '555-234-9012', 'sarah.hernandez@example.com', 22),
('Jason', 'W', 'King', '1991-10-11', 'M', '555-345-0123', 'jason.king@example.com', 23),
('Heather', 'X', 'Wright', '1986-07-28', 'F', '555-456-1234', 'heather.wright@example.com', 24),
('Eric', 'Y', 'Lopez', '1974-04-05', 'M', '555-567-2345', 'eric.lopez@example.com', 25),
('Nicole', 'Z', 'Hill', '1989-01-22', 'F', '555-678-3456', 'nicole.hill@example.com', 26),
('Jacob', 'A', 'Scott', '1995-08-09', 'M', '555-789-4567', 'jacob.scott@example.com', 27),
('Amy', 'B', 'Green', '1980-05-16', 'F', '555-890-5678', 'amy.green@example.com', 28),
('Nicholas', 'C', 'Adams', '1972-12-03', 'M', '555-901-6789', 'nicholas.adams@example.com', 29),
('Hannah', 'D', 'Baker', '1987-09-20', 'F', '555-012-7890', 'hannah.baker@example.com', 30);

-- Patients 31-40
INSERT INTO emergency_contacts (first_name, last_name, phone, email) VALUES
('Timothy', 'Gonzalez', '555-444-5555', 'timothy.gonzalez@example.com'),
('Angela', 'Nelson', '555-444-6666', 'angela.nelson@example.com'),
('Ryan', 'Carter', '555-444-7777', 'ryan.carter@example.com'),
('Christina', 'Mitchell', '555-444-8888', 'christina.mitchell@example.com'),
('Jeffrey', 'Perez', '555-444-9999', 'jeffrey.perez@example.com'),
('Rachel', 'Roberts', '555-555-1111', 'rachel.roberts@example.com'),
('Gary', 'Turner', '555-555-2222', 'gary.turner@example.com'),
('Megan', 'Phillips', '555-555-3333', 'megan.phillips@example.com'),
('Larry', 'Campbell', '555-555-4444', 'larry.campbell@example.com'),
('Olivia', 'Parker', '555-555-5555', 'olivia.parker@example.com');

INSERT INTO patients (first_name, middle_init, last_name, date_of_birth, gender, phone, email, contact_id) VALUES
('Brandon', 'E', 'Gonzalez', '1983-02-07', 'M', '555-123-9012', 'brandon.gonzalez@example.com', 31),
('Amber', 'F', 'Nelson', '1978-11-14', 'F', '555-234-0123', 'amber.nelson@example.com', 32),
('Adam', 'G', 'Carter', '1992-06-01', 'M', '555-345-1234', 'adam.carter@example.com', 33),
('Brittany', 'H', 'Mitchell', '1985-03-18', 'F', '555-456-2345', 'brittany.mitchell@example.com', 34),
('Stephen', 'I', 'Perez', '1971-10-05', 'M', '555-567-3456', 'stephen.perez@example.com', 35),
('Danielle', 'J', 'Roberts', '1988-07-22', 'F', '555-678-4567', 'danielle.roberts@example.com', 36),
('Patrick', 'K', 'Turner', '1994-04-09', 'M', '555-789-5678', 'patrick.turner@example.com', 37),
('Lauren', 'L', 'Phillips', '1979-01-26', 'F', '555-890-6789', 'lauren.phillips@example.com', 38),
('Justin', 'M', 'Campbell', '1984-08-13', 'M', '555-901-7890', 'justin.campbell@example.com', 39),
('Emma', 'N', 'Parker', '1990-05-30', 'F', '555-012-8901', 'emma.parker@example.com', 40);

-- Patients 41-50
INSERT INTO emergency_contacts (first_name, last_name, phone, email) VALUES
('Scott', 'Evans', '555-555-6666', 'scott.evans@example.com'),
('Victoria', 'Edwards', '555-555-7777', 'victoria.edwards@example.com'),
('Benjamin', 'Collins', '555-555-8888', 'benjamin.collins@example.com'),
('Samantha', 'Stewart', '555-555-9999', 'samantha.stewart@example.com'),
('Samuel', 'Sanchez', '555-666-1111', 'samuel.sanchez@example.com'),
('Christine', 'Morris', '555-666-2222', 'christine.morris@example.com'),
('Gregory', 'Rogers', '555-666-3333', 'gregory.rogers@example.com'),
('Catherine', 'Reed', '555-666-4444', 'catherine.reed@example.com'),
('Jack', 'Cook', '555-666-5555', 'jack.cook@example.com'),
('Alexis', 'Morgan', '555-666-6666', 'alexis.morgan@example.com');

INSERT INTO patients (first_name, middle_init, last_name, date_of_birth, gender, phone, email, contact_id) VALUES
('Nathan', 'O', 'Evans', '1981-12-17', 'M', '555-123-0123', 'nathan.evans@example.com', 41),
('Tiffany', 'P', 'Edwards', '1976-09-04', 'F', '555-234-1234', 'tiffany.edwards@example.com', 42),
('Christian', 'Q', 'Collins', '1993-04-21', 'M', '555-345-2345', 'christian.collins@example.com', 43),
('Madison', 'R', 'Stewart', '1987-01-08', 'F', '555-456-3456', 'madison.stewart@example.com', 44),
('Zachary', 'S', 'Sanchez', '1973-08-25', 'M', '555-567-4567', 'zachary.sanchez@example.com', 45),
('Kayla', 'T', 'Morris', '1988-05-12', 'F', '555-678-5678', 'kayla.morris@example.com', 46),
('Dylan', 'U', 'Rogers', '1995-02-27', 'M', '555-789-6789', 'dylan.rogers@example.com', 47),
('Anna', 'V', 'Reed', '1980-11-14', 'F', '555-890-7890', 'anna.reed@example.com', 48),
('Ethan', 'W', 'Cook', '1985-06-01', 'M', '555-901-8901', 'ethan.cook@example.com', 49),
('Chloe', 'X', 'Morgan', '1991-03-18', 'F', '555-012-9012', 'chloe.morgan@example.com', 50);

-- Insert medications
INSERT INTO medications (med_name, cost, stock_quantity) VALUES
('Amoxicillin', 15.99, 500),
('Ibuprofen', 8.50, 1000),
('Lisinopril', 12.75, 300),
('Metformin', 9.25, 400),
('Atorvastatin', 18.50, 350),
('Albuterol Inhaler', 45.00, 200),
('Omeprazole', 22.99, 250),
('Levothyroxine', 14.50, 400),
('Losartan', 16.25, 300),
('Simvastatin', 11.99, 350),
('Gabapentin', 19.75, 200),
('Hydrochlorothiazide', 7.50, 450),
('Metoprolol', 10.25, 400),
('Tramadol', 24.99, 150),
('Sertraline', 17.50, 300),
('Fluoxetine', 13.25, 350),
('Diazepam', 21.99, 100),
('Prednisone', 6.50, 500),
('Warfarin', 28.75, 150),
('Citalopram', 15.25, 300);

-- Insert treatments
INSERT INTO treatments (treatment_name, cost) VALUES
('Physical Therapy Session', 120.00),
('X-Ray', 150.00),
('MRI Scan', 500.00),
('Blood Test', 75.00),
('Ultrasound', 200.00),
('EKG', 125.00),
('Vaccination', 50.00),
('Minor Surgery', 800.00),
('Dental Cleaning', 90.00),
('Eye Exam', 65.00),
('Chiropractic Adjustment', 85.00),
('Allergy Testing', 180.00),
('Colonoscopy', 1200.00),
('Endoscopy', 950.00),
('Skin Biopsy', 350.00),
('Joint Injection', 275.00),
('Pulmonary Function Test', 225.00),
('Stress Test', 300.00),
('Echocardiogram', 450.00),
('Sleep Study', 750.00);

-- Insert visit records (5-10 visits per clinic)
-- Clinic 1 visits
INSERT INTO visit_records (patient_id, clinic_id, room_id, staff_id, reason_for_visit, symptoms, diagnosis, visit_date, is_complete) VALUES
(1, 1, 1, 3, 'Annual checkup', 'None', 'Healthy', '2023-01-10 09:00:00', TRUE),
(5, 1, 1, 3, 'Cough and fever', 'Cough, fever, fatigue', 'Flu', '2023-01-12 10:30:00', TRUE),
(10, 1, 2, 4, 'Chest pain', 'Sharp chest pain, shortness of breath', 'Anxiety attack', '2023-01-15 14:00:00', TRUE),
(15, 1, 1, 5, 'Vaccination', 'None', 'Administered flu vaccine', '2023-01-18 11:15:00', TRUE),
(20, 1, 3, 4, 'Knee pain', 'Swelling, pain when walking', 'Meniscus tear', '2023-01-20 13:45:00', TRUE),
(25, 1, 1, 5, 'Headache', 'Persistent headache', 'Migraine', '2023-01-25 10:00:00', TRUE),
(30, 1, 2, 3, 'Allergy symptoms', 'Sneezing, itchy eyes', 'Seasonal allergies', '2023-01-28 15:30:00', TRUE);

-- Clinic 2 visits
INSERT INTO visit_records (patient_id, clinic_id, room_id, staff_id, reason_for_visit, symptoms, diagnosis, visit_date, is_complete) VALUES
(2, 2, 1, 8, 'Annual physical', 'None', 'Healthy', '2023-01-11 08:30:00', TRUE),
(7, 2, 2, 9, 'Abdominal pain', 'Nausea, bloating', 'Gastritis', '2023-01-13 11:00:00', TRUE),
(12, 2, 1, 10, 'Blood pressure check', 'None', 'Hypertension', '2023-01-16 14:30:00', TRUE),
(17, 2, 3, 8, 'Back pain', 'Lower back pain', 'Muscle strain', '2023-01-19 10:45:00', TRUE),
(22, 2, 1, 9, 'Skin rash', 'Itchy red patches', 'Contact dermatitis', '2023-01-22 13:15:00', TRUE),
(27, 2, 2, 10, 'Sore throat', 'Pain when swallowing, fever', 'Strep throat', '2023-01-26 09:30:00', TRUE),
(32, 2, 1, 8, 'Diabetes checkup', 'None', 'Type 2 diabetes', '2023-01-29 16:00:00', TRUE);

-- Clinic 3 visits
INSERT INTO visit_records (patient_id, clinic_id, room_id, staff_id, reason_for_visit, symptoms, diagnosis, visit_date, is_complete) VALUES
(3, 3, 1, 13, 'Annual checkup', 'None', 'Healthy', '2023-01-09 10:00:00', TRUE),
(8, 3, 2, 14, 'Ear pain', 'Pain, hearing loss', 'Ear infection', '2023-01-12 13:30:00', TRUE),
(13, 3, 1, 15, 'Vaccination', 'None', 'Administered tetanus shot', '2023-01-14 11:45:00', TRUE),
(18, 3, 3, 13, 'Ankle sprain', 'Swelling, bruising', 'Sprained ankle', '2023-01-17 15:15:00', TRUE),
(23, 3, 1, 14, 'Fatigue', 'Tiredness, lack of energy', 'Iron deficiency', '2023-01-20 09:30:00', TRUE),
(28, 3, 2, 15, 'Dizziness', 'Lightheadedness', 'Vertigo', '2023-01-24 14:00:00', TRUE),
(33, 3, 1, 13, 'Annual physical', 'None', 'Healthy', '2023-01-27 10:30:00', TRUE);

-- Clinic 4 visits
INSERT INTO visit_records (patient_id, clinic_id, room_id, staff_id, reason_for_visit, symptoms, diagnosis, visit_date, is_complete) VALUES
(4, 4, 1, 18, 'Annual checkup', 'None', 'Healthy', '2023-01-10 08:00:00', TRUE),
(9, 4, 2, 19, 'Sinus congestion', 'Headache, pressure', 'Sinusitis', '2023-01-13 11:30:00', TRUE),
(14, 4, 1, 20, 'Joint pain', 'Stiffness in morning', 'Arthritis', '2023-01-15 14:45:00', TRUE),
(19, 4, 3, 18, 'Minor injury', 'Cut on hand', 'Laceration', '2023-01-18 10:15:00', TRUE),
(24, 4, 1, 19, 'Anxiety', 'Nervousness, insomnia', 'Generalized anxiety', '2023-01-21 13:30:00', TRUE),
(29, 4, 2, 20, 'Heartburn', 'Burning sensation', 'GERD', '2023-01-25 09:45:00', TRUE),
(34, 4, 1, 18, 'Annual physical', 'None', 'Healthy', '2023-01-28 15:00:00', TRUE);

-- Clinic 5 visits
INSERT INTO visit_records (patient_id, clinic_id, room_id, staff_id, reason_for_visit, symptoms, diagnosis, visit_date, is_complete) VALUES
(6, 5, 1, 23, 'Annual checkup', 'None', 'Healthy', '2023-01-11 09:15:00', TRUE),
(11, 5, 2, 24, 'Allergic reaction', 'Hives, swelling', 'Allergic reaction', '2023-01-14 12:45:00', TRUE),
(16, 5, 1, 25, 'Blood work', 'None', 'Normal results', '2023-01-16 15:30:00', TRUE),
(21, 5, 3, 23, 'Sports physical', 'None', 'Cleared for sports', '2023-01-19 11:00:00', TRUE),
(26, 5, 1, 24, 'Depression', 'Sadness, lack of interest', 'Depression', '2023-01-22 14:15:00', TRUE),
(31, 5, 2, 25, 'Routine follow-up', 'None', 'Stable condition', '2023-01-26 10:30:00', TRUE),
(36, 5, 1, 23, 'Annual physical', 'None', 'Healthy', '2023-01-29 16:45:00', TRUE);

-- Insert record treatments
-- Clinic 1 treatments
INSERT INTO record_treatments (record_id, treatment_id) VALUES
(1, 1), (1, 4),  -- Annual checkup with blood test
(2, 4), (2, 6),  -- Flu with blood test and EKG
(3, 6),          -- Anxiety attack with EKG
(4, 7),          -- Vaccination
(5, 1), (5, 2),  -- Knee pain with physical therapy and X-Ray
(6, 4),          -- Migraine with blood test
(7, 12);         -- Allergy symptoms with allergy testing

-- Clinic 2 treatments
INSERT INTO record_treatments (record_id, treatment_id) VALUES
(8, 1), (8, 4),   -- Annual physical with blood test
(9, 4), (9, 5),   -- Gastritis with blood test and ultrasound
(10, 6),          -- Hypertension with EKG
(11, 1), (11, 2), -- Back pain with physical therapy and X-Ray
(12, 15),         -- Skin rash with skin biopsy
(13, 4), (13, 6), -- Strep throat with blood test and EKG
(14, 4);          -- Diabetes checkup with blood test

-- Clinic 3 treatments
INSERT INTO record_treatments (record_id, treatment_id) VALUES
(15, 1), (15, 4),  -- Annual checkup with blood test
(16, 5),           -- Ear infection with ultrasound
(17, 7),           -- Vaccination
(18, 2),           -- Sprained ankle with X-Ray
(19, 4), (19, 6),  -- Fatigue with blood test and EKG
(20, 10),          -- Vertigo with eye exam
(21, 1), (21, 4);  -- Annual physical with blood test

-- Clinic 4 treatments
INSERT INTO record_treatments (record_id, treatment_id) VALUES
(22, 1), (22, 4),  -- Annual checkup with blood test
(23, 5),           -- Sinusitis with ultrasound
(24, 4), (24, 6),  -- Arthritis with blood test and EKG
(25, 8),           -- Laceration with minor surgery
(26, 10),          -- Anxiety with eye exam
(27, 4),           -- GERD with blood test
(28, 1), (28, 4);  -- Annual physical with blood test

-- Clinic 5 treatments
INSERT INTO record_treatments (record_id, treatment_id) VALUES
(29, 1), (29, 4),  -- Annual checkup with blood test
(30, 12),          -- Allergic reaction with allergy testing
(31, 4),           -- Blood work
(32, 1),           -- Sports physical with physical therapy
(33, 10),          -- Depression with eye exam
(34, 4),           -- Routine follow-up with blood test
(35, 1), (35, 4);  -- Annual physical with blood test

-- Insert record prescriptions
-- Clinic 1 prescriptions
INSERT INTO record_prescriptions (record_id, med_id, quantity) VALUES
(2, 1, 1), (2, 2, 1),  -- Flu: Amoxicillin and Ibuprofen
(3, 14, 1),            -- Anxiety attack: Diazepam
(5, 2, 1), (5, 16, 1), -- Knee pain: Ibuprofen and Prednisone
(6, 14, 1),            -- Migraine: Tramadol
(7, 19, 1);            -- Allergies: Citalopram

-- Clinic 2 prescriptions
INSERT INTO record_prescriptions (record_id, med_id, quantity) VALUES
(9, 3, 1), (9, 7, 1),   -- Gastritis: Lisinopril and Omeprazole
(10, 3, 1),             -- Hypertension: Lisinopril
(11, 2, 1), (11, 11, 1),-- Back pain: Ibuprofen and Gabapentin
(12, 19, 1),            -- Skin rash: Hydrocortisone cream
(13, 1, 1),             -- Strep throat: Amoxicillin
(14, 4, 1);             -- Diabetes: Metformin

-- Clinic 3 prescriptions
INSERT INTO record_prescriptions (record_id, med_id, quantity) VALUES
(16, 1, 1),             -- Ear infection: Amoxicillin
(18, 2, 1), (18, 16, 1),-- Sprained ankle: Ibuprofen and Prednisone
(19, 5, 1),             -- Iron deficiency: Iron supplement
(20, 17, 1);            -- Vertigo: Meclizine

-- Clinic 4 prescriptions
INSERT INTO record_prescriptions (record_id, med_id, quantity) VALUES
(23, 7, 1),             -- Sinusitis: Omeprazole
(24, 2, 1), (24, 11, 1),-- Arthritis: Ibuprofen and Gabapentin
(25, 1, 1),             -- Laceration: Amoxicillin
(26, 15, 1),            -- Anxiety: Sertraline
(27, 7, 1);             -- GERD: Omeprazole

-- Clinic 5 prescriptions
INSERT INTO record_prescriptions (record_id, med_id, quantity) VALUES
(30, 19, 1),           -- Allergic reaction: Diphenhydramine
(33, 15, 1);           -- Depression: Sertraline

-- Invoices will be automatically generated by the trigger after visit records are marked as complete
UPDATE visit_records 
SET is_complete = FALSE
WHERE is_complete = TRUE;

UPDATE visit_records 
SET is_complete = TRUE 
WHERE is_complete = FALSE;