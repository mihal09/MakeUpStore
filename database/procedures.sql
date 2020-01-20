--functions----------------------------------------------------------------------
DELIMITER //
CREATE FUNCTION is_nip_valid(nip CHAR(10)) RETURNS BOOLEAN DETERMINISTIC
BEGIN
  IF MOD(
    6*CAST(SUBSTR(nip, 1, 1) AS UNSIGNED) +
    5*CAST(SUBSTR(nip, 2, 1) AS UNSIGNED) +
    7*CAST(SUBSTR(nip, 3, 1) AS UNSIGNED) +
    2*CAST(SUBSTR(nip, 4, 1) AS UNSIGNED) +
    3*CAST(SUBSTR(nip, 5, 1) AS UNSIGNED) +
    4*CAST(SUBSTR(nip, 6, 1) AS UNSIGNED) +
    5*CAST(SUBSTR(nip, 7, 1) AS UNSIGNED) +
    6*CAST(SUBSTR(nip, 8, 1) AS UNSIGNED) +
    7*CAST(SUBSTR(nip, 9, 1) AS UNSIGNED),
    11) <> SUBSTR(nip, 10, 1)
  THEN
    RETURN FALSE;
  ELSE
    RETURN TRUE;
  END IF;
END //
DELIMITER ;

DELIMITER //
CREATE FUNCTION get_last_id()
RETURNS INT DETERMINISTIC
BEGIN
	RETURN (SELECT MAX(product_id) FROM products);
END //
DELIMITER ;	


DELIMITER //
CREATE FUNCTION lipstick_exists(
	input_name 			varchar(50),
	input_color_id 		int,
	input_formula    	enum ('liquid', 'cream', 'gloss'),
	input_finish     	enum ('matte', 'satin', 'glossy'),
	input_fill_weight 	int UNSIGNED
	) RETURNS BOOLEAN DETERMINISTIC
  BEGIN
    RETURN !ISNULL ((
      SELECT p.product_id
      FROM lipsticks AS l
	  INNER JOIN products p
	  ON p.product_id = l.product_id
      WHERE
		p.name = input_name AND
        l.color_id = input_color_id AND
		l.formula  = input_formula AND
		l.finish = input_finish AND
		l.fill_weight = input_fill_weight
    ));
  END //
DELIMITER ;

DELIMITER //
CREATE FUNCTION concealer_exists(
	input_name 			varchar(50),
	input_color_id 		int,
	input_undertone   	enum ('pink', 'yellow', 'neutral'),
	input_coverage     	enum ('light', 'medium', 'full'),
	input_fill_weight   int UNSIGNED
	) RETURNS BOOLEAN DETERMINISTIC
  BEGIN
    RETURN !ISNULL ((
      SELECT p.product_id
      FROM concealers AS c
	  INNER JOIN products p
	  ON p.product_id = c.product_id
      WHERE
		p.name = input_name AND
        c.color_id = input_color_id AND
		c.undertone  = input_undertone AND
		c.coverage = input_coverage AND
		c.fill_weight = input_fill_weight
    ));
  END //
DELIMITER ;

DELIMITER //
CREATE FUNCTION mascara_exists(
	input_name 			varchar(50),
	input_color_id 	 	int,
	input_brush   		enum ('silicone', 'tradicional'),
	input_effect  		enum ('volume', 'lifting'),
	input_fill_weight   int UNSIGNED
	) RETURNS BOOLEAN DETERMINISTIC
  BEGIN
    RETURN !ISNULL ((
      SELECT p.product_id
      FROM mascaras AS m
	  INNER JOIN products p
	  ON p.product_id = m.product_id
      WHERE
		p.name = name AND
        m.color_id = input_color_id AND
		m.brush  = input_brush AND
		m.effect = input_effect AND
		m.fill_weight = input_fill_weight
    ));
  END //
DELIMITER ;

--procedures-------------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE user_status(OUT status INT)
  BEGIN
	SELECT CURRENT_ROLE() INTO @role;
	SELECT @role AS 'ELUWA1';
	SELECT SUBSTR(@role, 2, LOCATE('@',@role)-3) INTO @role;
	SELECT @role AS 'ELUWA2';
	SELECT 
	IF @role = 'admin' THEN
		SELECT 3 INTO status;
	ELSEIF @role = 'boss' THEN
		SELECT 2 INTO status;
	ELSEIF @role = 'employee' THEN
		SELECT 1 INTO status;
	ELSE 
		SELECT 0 INTO status;
	END IF;
  END //
DELIMITER ;

--admin------------------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE add_user(
IN input_login varchar(30),
IN input_password varchar(100),
IN input_type enum('admin', 'boss', 'employee'))
  BEGIN
	SET @query = CONCAT("CREATE USER IF NOT EXISTS `",input_login,"`@`localhost` IDENTIFIED BY '",input_password,"'");
	PREPARE query FROM @query;
	EXECUTE query;
	DEALLOCATE PREPARE query;
	
	SET @query = CONCAT("GRANT ",input_type," TO `",input_login,"`@`localhost`");
	SELECT @query;
	PREPARE query FROM @query;
	EXECUTE query;
	DEALLOCATE PREPARE query;
	
	SET @query = CONCAT("SET DEFAULT ROLE ",input_type," TO `",input_login,"`@`localhost`");
	SELECT @query;
	PREPARE query FROM @query;
	EXECUTE query;
	DEALLOCATE PREPARE query;
	
	GRANT EXECUTE ON PROCEDURE makeup.user_status TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.add_client TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.plan_delivery TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.plan_sale TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.cancel_delivery TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.cancel_sale TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.update_delivery TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.update_sale TO employee;
	GRANT EXECUTE ON FUNCTION  makeup.amount_on_date TO employee;
	GRANT EXECUTE ON PROCEDURE makeup.user_status TO employee;


	GRANT EXECUTE ON PROCEDURE makeup.add_lipstick TO boss;
	GRANT EXECUTE ON PROCEDURE makeup.add_mascara TO boss;
	GRANT EXECUTE ON PROCEDURE makeup.add_concealer TO boss;
	GRANT EXECUTE ON PROCEDURE makeup.add_brand TO boss;
	

	GRANT EXECUTE ON PROCEDURE makeup.add_user TO admin;
	GRANT EXECUTE ON PROCEDURE makeup.remove_user TO admin;
	GRANT EXECUTE ON PROCEDURE makeup.change_permissions TO admin;
	GRANT EXECUTE ON PROCEDURE makeup.select_user TO admin;
	
	GRANT SELECT, LOCK TABLES ON makeup.* TO admin;
	GRANT DROP, CREATE, ALTER ON makeup.* TO admin;

	
	SET @query = CONCAT("SET DEFAULT ROLE ",input_type," TO `",input_login,"`@`localhost`");
	SELECT @query;
	PREPARE query FROM @query;
	EXECUTE query;
	DEALLOCATE PREPARE query;
	
	
	PREPARE query FROM
	'INSERT INTO users(login, password, type)
    VALUES (?, SHA2(?, 256), ?)';
	
	SET @input_login = input_login;
	SET @input_password = input_password;
	SET @input_type = input_type;
	
	EXECUTE query USING @input_login, @input_password, @input_type;
	DEALLOCATE PREPARE query;
	FLUSH PRIVILEGES;
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE user_status(OUT status INT, IN input_login varchar(30), IN input_password varchar(100))
  BEGIN
	PREPARE query FROM
	'SELECT type INTO @type FROM users WHERE 
	login = ? AND 
	password = SHA2(?, 256)';
	
	SET @input_login = input_login;
	SET @input_password = input_password;

	EXECUTE query USING @input_login, @input_password;
	DEALLOCATE PREPARE query;
	
	IF @type = 'admin' THEN
		SELECT 3 INTO status;
	ELSEIF @type = 'boss' THEN
		SELECT 2 INTO status;
	ELSEIF @type = 'employee' THEN
		SELECT 1 INTO status;
	ELSE 
		SELECT 0 INTO status;
	END IF;
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE remove_user(IN input_user_id INT)
  BEGIN
	PREPARE query FROM
	'SELECT login INTO @login FROM users
    WHERE user_id  = ?';
	SET @input_user_id = input_user_id;
	EXECUTE query USING @input_user_id;
	DEALLOCATE PREPARE query;
	
	
	
	PREPARE query FROM
	'DELETE FROM users
    WHERE user_id  = ?';
	SET @input_user_id = input_user_id;
	EXECUTE query USING @input_user_id;
	DEALLOCATE PREPARE query;
	
	SET @query = CONCAT("DROP USER `",@login,"`@'localhost'");
	PREPARE query FROM @query;
	EXECUTE query;
	DEALLOCATE PREPARE query;

  END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE change_permissions(IN input_user_id INT, IN input_type enum('admin', 'boss', 'employee') )
  BEGIN
	PREPARE query FROM
	'UPDATE users
	SET type = ?
    WHERE user_id  = ?';
	
	SET @input_user_id	= input_user_id;
	SET @input_type 	= input_type;
	
	EXECUTE query USING  @input_type, @input_user_id;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;

--boss---------------------------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE select_user(
input_type enum ('admin', 'boss', 'employee')
)
  BEGIN
	PREPARE query FROM
	'SELECT login FROM users
    WHERE type= ? ';
	SET @input_type = input_type;
	EXECUTE query USING @input_type;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;

/* 
CALL add_lipstick(2, 'cream', 'matte', 99, 1, 'pomadka 4', 44.44);
*/

DELIMITER //
CREATE PROCEDURE add_lipstick(	
IN input_color_id INT,
IN input_formula enum ('liquid', 'cream', 'gloss'),
IN input_finish enum ('matte', 'satin', 'glossy'),
IN input_fill_weight INT UNSIGNED, 
IN input_brand_id INT, 
IN input_name varchar(50),
IN input_price decimal(5, 2) UNSIGNED)
  
BEGIN
  SET autocommit = 0;
  START TRANSACTION;
  CALL add_product(input_brand_id, input_name, 'lipstick', input_price, 0, @id);
  PREPARE query FROM
	'INSERT INTO lipsticks(product_id, color_id, formula, finish, fill_weight)
    VALUES (?, ?, ?, ?, ?)';
	
	SET @input_color_id = input_color_id;
	SET @input_formula = input_formula;
	SET @input_finish =  input_finish;
	SET @input_fill_weight = input_fill_weight;

	EXECUTE query USING @id, @input_color_id, @input_formula, @input_finish, @input_fill_weight;
	DEALLOCATE PREPARE query;
	COMMIT;
    SET autocommit = 1;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE add_concealer(
IN input_color_id INT, 
IN input_undertone enum ('pink', 'yellow', 'neutral'),
IN input_coverage enum ('light', 'medium', 'full'), 
IN input_fill_weight INT UNSIGNED,  
IN input_brand_id INT, 
IN input_name varchar(50), 
IN input_price decimal(5, 2) UNSIGNED)
  
BEGIN
  SET autocommit = 0;
  START TRANSACTION;
  CALL add_product(input_brand_id, input_name, 'concealer', input_price, 0, @id);
  PREPARE query FROM
	'INSERT INTO concealers(product_id, color_id, undertone, coverage,fill_weight)
    VALUES (?, ?, ?, ?, ?)';
	
	SET @input_color_id = input_color_id;
	SET @input_undertone = input_undertone;
	SET @input_coverage =  input_coverage;
	SET @input_fill_weight = input_fill_weight;

	EXECUTE query USING @id, @input_color_id, @input_undertone, @input_coverage, @input_fill_weight;
	DEALLOCATE PREPARE query;
  COMMIT;
  SET autocommit = 1;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE add_mascara(
IN input_color_id INT, 
input_brush enum ('silicone', 'tradicional'),
input_effect enum ('volume', 'lifting'), 
input_fill_weight INT UNSIGNED,  
input_brand_id INT, 
input_name varchar(50), 
input_price decimal(5, 2) UNSIGNED)
  BEGIN
  SET autocommit = 0;
  START TRANSACTION;
  CALL add_product(input_brand_id, input_name, 'mascara', input_price, 0, @id);
  PREPARE query FROM
	'INSERT INTO mascaras(product_id, color_id, brush, effect, fill_weight)
    VALUES (?, ? , ?, ?, ?)';
	
	SET @input_color_id = input_color_id;
	SET @input_brush = input_brush;
	SET @input_effect =  input_effect;
	SET @input_fill_weight = input_fill_weight;

	EXECUTE query USING @id, @input_color_id, @input_brush, @input_effect, @input_fill_weight;
	DEALLOCATE PREPARE query;
  COMMIT;
  SET autocommit = 1;
  END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE add_brand(
  input_name VARCHAR(50),
  input_nip CHAR(10),
  input_address VARCHAR(100))
  BEGIN
	PREPARE query FROM
	'INSERT INTO brands(name, nip, address)
    VALUES (?, ?, ?)';
	
	SET @input_name = input_name;
	SET @input_nip =  input_nip;
	SET @input_address = input_address;

	EXECUTE query USING @input_name, @input_nip, @input_address;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;

--employee-------------------------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE add_client(
  input_name varchar(50),
  input_nip char(10),
  input_address varchar(100))
  BEGIN
	PREPARE query FROM
	'INSERT INTO clients(name, nip, address)
    VALUES (?, ?, ?)';
	
	SET @input_name = input_name;
	SET @input_nip =  input_nip;
	SET @input_address = input_address;

	EXECUTE query USING @input_name, @input_nip, @input_address;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE plan_delivery(
  OUT out_id INT,
  input_delivery_date date,
  input_delivery_details text)
  BEGIN
	
	/*exception handling*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
	BEGIN
      ROLLBACK;
	  SET autocommit = 1;
	END;
	
	SET autocommit = 0;
	START TRANSACTION;
	
	PREPARE query FROM
	'INSERT INTO deliveries(delivery_date, done)
    VALUES (?, 0)';
	
	SET @input_delivery_date = input_delivery_date;
	SET @input_delivery_details =  input_delivery_details;

	EXECUTE query USING @input_delivery_date;
	DEALLOCATE PREPARE query;
	
	SELECT MAX(delivery_id) INTO @delivery_id FROM deliveries;
	SELECT @delivery_id INTO out_id;
	
	productsLoop: LOOP
		IF (LOCATE(';', @input_delivery_details) = 0) THEN
			LEAVE productsLoop;
		END IF;

		SET @product_id = SUBSTRING(@input_delivery_details, 1, LOCATE(',',@input_delivery_details)-1);
		SET @input_delivery_details = SUBSTRING(@input_delivery_details, LOCATE(',',@input_delivery_details) +1);
		SET @product_quantity = SUBSTRING(@input_delivery_details, 1, LOCATE(';',@input_delivery_details)-1);
		SET @input_delivery_details = SUBSTRING(@input_delivery_details, LOCATE(';',@input_delivery_details) +1);
		/*SELECT @delivery_id AS 'delivery)id', @product_id AS 'product_id' , @product_quantity AS 'quantity', @input_delivery_details AS 'details';*/
		PREPARE query FROM
		'INSERT INTO deliveries_details(delivery_id, product_id, quantity)
		VALUES (?, ?, ?)';

		EXECUTE query USING @delivery_id, @product_id, @product_quantity;
		DEALLOCATE PREPARE query;
	END LOOP productsLoop;
	COMMIT;
	SET autocommit = 1;
  END //
DELIMITER ;
 /*call plan_delivery('2020-02-12', '1,10;2,20;3,30;');*/
 

DELIMITER //
CREATE PROCEDURE plan_sale(
  OUT out_success BOOLEAN,
  OUT out_id INT,
  IN input_client_id int,
  IN input_sale_date date,
  IN input_sale_details text)
  BEGIN
	DECLARE success BOOLEAN;
	/*exception handling*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
	BEGIN
      ROLLBACK;
	  SET success = 0;
	  SET autocommit = 1;
	END;
	
	SET success = 1;
	SET autocommit = 0;
	START TRANSACTION;
	
	PREPARE query FROM
	'INSERT INTO sales(client_id, sale_date, done)
    VALUES (?, ?, 0)';
	
	SET @input_client_id = input_client_id;
	SET @input_sale_date = input_sale_date;
	SET @input_sale_details =  input_sale_details;

	EXECUTE query USING @input_client_id,  @input_sale_date;
	DEALLOCATE PREPARE query;
	
	SELECT MAX(sale_id) INTO @sale_id FROM sales;
	SELECT @sale_id INTO out_id;
	
	productsLoop: LOOP
		IF (LOCATE(';', @input_sale_details) = 0) THEN
			LEAVE productsLoop;
		END IF;

		SET @product_id = SUBSTRING(@input_sale_details, 1, LOCATE(',',@input_sale_details)-1);
		SET @input_sale_details = SUBSTRING(@input_sale_details, LOCATE(',',@input_sale_details) +1);
		SET @product_quantity = SUBSTRING(@input_sale_details, 1, LOCATE(';',@input_sale_details)-1);
		SET @input_sale_details = SUBSTRING(@input_sale_details, LOCATE(';',@input_sale_details) +1);

		PREPARE query FROM
		'INSERT INTO sales_details(sale_id, product_id, quantity)
		VALUES (?, ?, ?)';

		EXECUTE query USING @sale_id, @product_id, @product_quantity;
		DEALLOCATE PREPARE query;
		
		IF amount_on_date(@product_id, @input_sale_date) < 0 THEN
			SET success = 0;
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not enough available products for sale';
		END IF;
		
	END LOOP productsLoop;
	IF success = 1 THEN
		COMMIT;
	ELSE
		ROLLBACK;
	END IF;
	SELECT success INTO out_success;

	SET autocommit = 1;
  END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE cancel_delivery(IN input_delivery_id INT)
  BEGIN
  	SET @input_delivery_id = input_delivery_id;
	PREPARE query FROM
	'SELECT COUNT(*) INTO @amount FROM deliveries
	WHERE delivery_id  = ?
	AND done = 0';
	EXECUTE query USING @input_delivery_id;
	DEALLOCATE PREPARE query;
	
	
	IF @amount <> 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot cancel delivery';
	END IF;
	
	PREPARE query FROM
	'DELETE FROM deliveries
    WHERE delivery_id  = ?
	AND done = 0';
	
	
	
	EXECUTE query USING @input_delivery_id;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE cancel_sale(IN input_sale_id INT)
  BEGIN
	SET @input_sale_id = input_sale_id;
	PREPARE query FROM
	'SELECT COUNT(*) INTO @amount FROM sales
	WHERE sale_id  = ?
	AND done = 0';
	EXECUTE query USING @input_sale_id;
	DEALLOCATE PREPARE query;
		
	IF @amount <> 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot cancel sale';
	END IF;
	
	PREPARE query FROM
	'DELETE FROM sales
    WHERE sale_id  = ?
	AND done = 0';
	

	
	EXECUTE query USING @input_sale_id;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE update_delivery(IN input_delivery_id INT)
  BEGIN
	SET @input_delivery_id = input_delivery_id;
	PREPARE query FROM
	'SELECT COUNT(*) INTO @amount FROM deliveries
	WHERE delivery_id  = ?
	AND done = 0';
	EXECUTE query USING @input_delivery_id;
	DEALLOCATE PREPARE query;
	
	
	IF @amount <> 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot update delivery';
	END IF;
	
	
	PREPARE query FROM
	'UPDATE deliveries
	SET done = 1
	WHERE delivery_id  = ?
	AND done = 0';
	
	EXECUTE query USING @input_delivery_id;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_sale(IN input_sale_id INT)
  BEGIN
	SET @input_sale_id = input_sale_id;
	PREPARE query FROM
	'SELECT COUNT(*) INTO @amount FROM sales
	WHERE sale_id  = ?
	AND done = 0';
	EXECUTE query USING @input_sale_id;
	DEALLOCATE PREPARE query;
		
	IF @amount <> 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot update sale';
	END IF;
	
	
	PREPARE query FROM
	'UPDATE sales
	SET done = 1
	WHERE sale_id  = ?
	AND done = 0';
	
	EXECUTE query USING @input_sale_id;
	DEALLOCATE PREPARE query;
  END //
DELIMITER ;


DELIMITER //
CREATE FUNCTION amount_on_date(input_product_id INT, input_date DATE) RETURNS INT DETERMINISTIC
BEGIN
  DECLARE toBeDelivered INT;
  DECLARE toBeSold INT;

  IF input_date < DATE(now()) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Enter future date';
  END IF;

  SELECT SUM(quantity) INTO toBeDelivered
  FROM deliveries_details AS dd
  INNER JOIN deliveries d ON dd.delivery_id = d.delivery_id
  WHERE dd.product_id = input_product_id AND
        delivery_date <= input_date AND
		NOT done;

  SELECT SUM(quantity) INTO toBeSold
  FROM sales_details AS sd
  INNER JOIN sales s ON sd.sale_id = s.sale_id
  WHERE sd.product_id = input_product_id AND
        sale_date <= input_date AND
		NOT done;

  IF toBeDelivered IS NULL THEN
    SET toBeDelivered = 0;
  END IF;
  
   IF toBeSold IS NULL THEN
    SET toBeSold = 0;
  END IF;

  RETURN (SELECT p.quantity + toBeDelivered - toBeSold
  FROM products AS p
  WHERE product_id = input_product_id);
END //
DELIMITER ;

/*select amount_on_date(1,'2020-02-10');*/


 