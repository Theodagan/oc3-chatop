INSERT INTO USERS (email, name, password, created_at, updated_at) VALUES
('john.doe@email.com', 'John Doe', 'SecureP@$$wOrd1', NOW(), NOW()),
('jane.smith@email.com', 'Jane Smith', 'MyStr0ngP@$$', NOW(), NOW()),
('robert.jones@email.com', 'Robert Jones', 'PasswOrd!123', NOW(), NOW()),
('emily.wilson@email.com', 'Emily Wilson', 'EmiLyPa$$!', NOW(), NOW()),
('david.brown@email.com', 'David Brown', 'D@vidP@$$', NOW(), NOW());

INSERT INTO RENTALS (name, surface, price, picture, description, owner_id, created_at, updated_at) VALUES
('Cozy Apartment', 60.5, 1200.00, 'img1.jpg', 'A comfortable apartment in the city center.', 1, NOW(), NOW()),
('Spacious House', 150.0, 2500.00, 'img2.jpg', 'A large family house with a garden.', 2, NOW(), NOW()),
('Modern Studio', 40.0, 900.00, 'img3.jpg', 'A stylish studio with modern amenities.', 3, NOW(), NOW()),
('Luxury Villa', 300.0, 5000.00, 'img4.jpg', 'A luxurious villa with a private pool.', 1, NOW(), NOW()),
('Charming Cottage', 80.0, 1500.00, 'img5.jpg', 'A cozy cottage in a quiet neighborhood.', 4, NOW(), NOW());

INSERT INTO MESSAGES (rental_id, user_id, message, created_at, updated_at) VALUES
(1, 2, 'Is the apartment available next month?', NOW(), NOW()),
(1, 1, 'Yes, it is. Would you like to schedule a viewing?', NOW(), NOW()),
(2, 3, 'The house looks great! What is the neighborhood like?', NOW(), NOW()),
(2, 2, 'It is very family-friendly and safe.', NOW(), NOW()),
(3, 1, 'I am interested in the studio. Can I come by tomorrow?', NOW(), NOW()),
(3, 3, 'Sure, please contact me to arrange the visit', NOW(), NOW()),
(4, 4, 'Are there extra charges for the pool access?', NOW(), NOW()),
(4, 1, 'No, the pool is included in the rental price.', NOW(), NOW()),
(5, 5, 'Is it possible to have a garden BBQ?', NOW(), NOW()),
(5, 4, 'Yes, but it must be cleaned after', NOW(), NOW());
