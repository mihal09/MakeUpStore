USE makeup;

CREATE USER IF NOT EXISTS `admin`@`localhost` IDENTIFIED BY 'adminpassword';
CREATE USER IF NOT EXISTS `boss`@`localhost` IDENTIFIED BY 'bosspassword';
CREATE USER IF NOT EXISTS `employee`@`localhost` IDENTIFIED BY 'employeepassword';

CREATE ROLE IF NOT EXISTS employee;
CREATE ROLE IF NOT EXISTS boss;
CREATE ROLE IF NOT EXISTS admin;

GRANT employee TO boss;
GRANT boss TO admin;

GRANT boss TO `boss`@`localhost`;
GRANT employee TO `employee`@`localhost`;
GRANT admin TO `admin`@`localhost`;

SET DEFAULT ROLE employee TO `employee`@`localhost`;
SET DEFAULT ROLE boss TO `boss`@`localhost`;
SET DEFAULT ROLE admin TO `admin`@`localhost`;

GRANT SELECT ON makeup.lipsticks TO employee;
GRANT SELECT ON makeup.concealers TO employee;
GRANT SELECT ON makeup.mascaras TO employee;
GRANT SELECT ON makeup.products TO employee;
GRANT SELECT ON makeup.brands TO employee;
GRANT SELECT ON makeup.sale_details TO employee;
GRANT SELECT ON makeup.clients TO employee;
GRANT SELECT ON makeup.sales TO employee;
GRANT SELECT ON makeup.deliveries TO employee;
GRANT SELECT ON makeup.deliveries_details TO employee;

GRANT SELECT ON makeup.users TO admin;

GRANT INSERT ON makeup.sale_details TO employee;
GRANT INSERT ON makeup.clients TO employee;
GRANT INSERT ON makeup.sales TO employee;
GRANT INSERT ON makeup.deliveries TO employee;
GRANT INSERT ON makeup.deliveries_details TO employee;

GRANT INSERT ON makeup.colors TO boss;
GRANT INSERT ON makeup.lipsticks TO boss;
GRANT INSERT ON makeup.concealers TO boss;
GRANT INSERT ON makeup.mascaras TO boss;
GRANT INSERT ON makeup.products TO boss;
GRANT INSERT ON makeup.brands TO boss;

GRANT INSERT ON makeup.users TO admin;

GRANT EXECUTE ON PROCEDURE makeup.user_status TO employee;
GRANT EXECUTE ON PROCEDURE makeup.add_client TO employee;
GRANT EXECUTE ON PROCEDURE makeup.plan_delivery TO employee;
GRANT EXECUTE ON PROCEDURE makeup.plan_sale TO employee;
GRANT EXECUTE ON PROCEDURE makeup.cancel_delivery TO employee;
GRANT EXECUTE ON PROCEDURE makeup.cancel_sale TO employee;
GRANT EXECUTE ON PROCEDURE makeup.update_deliveries TO employee;
GRANT EXECUTE ON PROCEDURE makeup.update_sale TO employee;
GRANT EXECUTE ON PROCEDURE makeup.amount_on_date TO employee;
GRANT EXECUTE ON PROCEDURE makeup.user_status TO employee;
GRANT EXECUTE ON FUNCTION makeup.is_nip_valid TO employee;
GRANT EXECUTE ON FUNCTION makeup.is_nip_valid TO employee;


GRANT EXECUTE ON PROCEDURE makeup.add_lipstick TO boss;
GRANT EXECUTE ON PROCEDURE makeup.add_mascara TO boss;
GRANT EXECUTE ON PROCEDURE makeup.add_concealer TO boss;
GRANT EXECUTE ON PROCEDURE makeup.add_brand TO boss;

GRANT EXECUTE ON PROCEDURE makeup.add_user TO admin;
GRANT EXECUTE ON PROCEDURE makeup.remove_user TO admin;
GRANT EXECUTE ON PROCEDURE makeup.change_permissions TO admin;
GRANT EXECUTE ON PROCEDURE makeup.select_user TO admin;


GRANT DELETE ON makeup.users TO admin;


GRANT SELECT ON proc TO employee;

--those are needed for backup & restore functionallity
GRANT REFERENCES, SELECT, LOCK TABLES ON makeup.* TO 'admin'@'localhost';
GRANT DROP, CREATE, ALTER ON makeup.* TO 'admin'@'localhost';
GRANT SUPER ON *.* TO 'admin'@'localhost';