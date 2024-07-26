-- Connect to the database
\c dbtjv;

-- Insert some sample data
INSERT INTO engineer (email, name, password, surname, role) VALUES
    ('admin@admin.com', 'Admin', 'admin', 'Admin', 'Admin'),
    ('alexey.lunev@example.com', 'Alexey', '1234', 'Lunev', 'Admin'),
    ('john.doe@example.com', 'John', 'password123', 'Doe', 'User'),
    ('jane.smith@example.com', 'Jane', 'password456', 'Smith','User'),
    ('alice.johnson@example.com', 'Alice', 'password789', 'Johnson','User');


INSERT INTO project (price, name, type) VALUES
    (1000000, 'Project A', 'Residential'),
    (2000000, 'Project B', 'Commercial'),
    (1500000, 'Project C', 'Industrial');


INSERT INTO building (in_project_id, building_type, name) VALUES
    (1, 'Apartment', 'Building A1'),
    (2, 'Office', 'Building B1'),
    (3, 'Factory', 'Building C1');


INSERT INTO project_working_by (working_by_id, working_on_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (1, 2),
    (2, 3);
