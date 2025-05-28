CREATE TABLE `pet_categories` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `category_name` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `stock_categories` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `category_name` varchar(255) DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pet_owners` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `address` varchar(255) DEFAULT NULL,
                              `owner_name` varchar(255) DEFAULT NULL,
                              `phone_number` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pet_clinics` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `address` varchar(255) DEFAULT NULL,
                               `clinic_name` varchar(255) DEFAULT NULL,
                               `phone_number` varchar(255) DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `dealers` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `dealer_name` varchar(255) DEFAULT NULL,
                           `email` varchar(255) DEFAULT NULL,
                           `item_name` varchar(255) DEFAULT NULL,
                           `phone_number` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pet_clinic_dealer` (
                                     `dealer_id` bigint NOT NULL,
                                     `pet_clinic_id` bigint NOT NULL,
                                     KEY `FKbmw6sayjesr4gyouqj3mw8njp` (`pet_clinic_id`),
                                     KEY `FKn35r2hrwerdfp3pgbtsjvcnhg` (`dealer_id`),
                                     CONSTRAINT `FKbmw6sayjesr4gyouqj3mw8njp` FOREIGN KEY (`pet_clinic_id`) REFERENCES `pet_clinics` (`id`),
                                     CONSTRAINT `FKn35r2hrwerdfp3pgbtsjvcnhg` FOREIGN KEY (`dealer_id`) REFERENCES `dealers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `stocks` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `availability_status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
                          `description` varchar(255) DEFAULT NULL,
                          `item_code` varchar(255) DEFAULT NULL,
                          `name` varchar(255) DEFAULT NULL,
                          `pet_clinic_id` bigint DEFAULT NULL,
                          `stock_category_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FKmlkl9lvje6shwg93lhucq9m7x` (`pet_clinic_id`),
                          KEY `FK7sjbu50hbp6rld9ek79ctn9fa` (`stock_category_id`),
                          CONSTRAINT `FK7sjbu50hbp6rld9ek79ctn9fa` FOREIGN KEY (`stock_category_id`) REFERENCES `stock_categories` (`id`),
                          CONSTRAINT `FKmlkl9lvje6shwg93lhucq9m7x` FOREIGN KEY (`pet_clinic_id`) REFERENCES `pet_clinics` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pets` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `birth_date` varchar(255) DEFAULT NULL,
                        `gender` varchar(255) DEFAULT NULL,
                        `pet_name` varchar(255) DEFAULT NULL,
                        `doctor_id` bigint DEFAULT NULL,
                        `pet_category_id` bigint DEFAULT NULL,
                        `pet_owner_id` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `FKj2efptrhcptccftxwq55qtn2k` (`doctor_id`),
                        KEY `FKaqralybuj8hb7yck188sf9w5g` (`pet_category_id`),
                        KEY `FKgkcinp9esrflaedshs75y9x1x` (`pet_owner_id`),
                        CONSTRAINT `FKaqralybuj8hb7yck188sf9w5g` FOREIGN KEY (`pet_category_id`) REFERENCES `pet_categories` (`id`),
                        CONSTRAINT `FKgkcinp9esrflaedshs75y9x1x` FOREIGN KEY (`pet_owner_id`) REFERENCES `pet_owners` (`id`),
                        CONSTRAINT `FKj2efptrhcptccftxwq55qtn2k` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `breeds` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `breed_name` varchar(255) DEFAULT NULL,
                          `pet_category_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FKojnylbteaxb0328i0ueisccls` (`pet_category_id`),
                          CONSTRAINT `FKojnylbteaxb0328i0ueisccls` FOREIGN KEY (`pet_category_id`) REFERENCES `pet_categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `doctors` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `doctor_name` varchar(255) DEFAULT NULL,
                           `phone_number` int NOT NULL,
                           `qualifications` varchar(255) DEFAULT NULL,
                           `pet_clinic_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKneaxst0n6wm110p14y8o80o8` (`pet_clinic_id`),
                           CONSTRAINT `FKneaxst0n6wm110p14y8o80o8` FOREIGN KEY (`pet_clinic_id`) REFERENCES `pet_clinics` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `order_name` varchar(255) DEFAULT NULL,
                          `order_status` enum('CANCELLED','COMPLETED','CONFIRMED','DELIVERED','PENDING','PROCESSING') DEFAULT NULL,
                          `quantity` int NOT NULL,
                          `total_amount` double DEFAULT NULL,
                          `dealer_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FKc174cj48olwptv2nlqy2iuiy4` (`dealer_id`),
                          CONSTRAINT `FKc174cj48olwptv2nlqy2iuiy4` FOREIGN KEY (`dealer_id`) REFERENCES `dealers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pet_image_url` (
                                 `pet_id` bigint NOT NULL,
                                 `image_url` varchar(255) DEFAULT NULL,
                                 KEY `FKclym2nwjmrsbsjlumk449xycs` (`pet_id`),
                                 CONSTRAINT `FKclym2nwjmrsbsjlumk449xycs` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

