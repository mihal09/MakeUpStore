DELIMITER //
CREATE TRIGGER updateQuantitySales AFTER UPDATE ON sales FOR EACH ROW
  BEGIN
    IF NEW.done AND NOT OLD.done THEN
	
		CREATE TEMPORARY TABLE updatedProducts AS (
			SELECT  sd.product_id, sd.quantity FROM sales AS s 
			INNER JOIN sales_details AS sd ON s.sale_id = sd.sale_id 
			WHERE s.sale_id = NEW.sale_id
		);
		
		UPDATE products p
		INNER JOIN updatedProducts ON  p.product_id = updatedProducts.product_id
		SET p.quantity = p.quantity - updatedProducts.quantity;

    END IF;
END //
DELIMITER ;

		

DELIMITER //
CREATE TRIGGER updateQuantityDeliveries AFTER UPDATE ON deliveries FOR EACH ROW
  BEGIN
    IF NEW.done AND NOT OLD.done THEN
		CREATE TEMPORARY TABLE updatedProducts AS (
			SELECT  dd.product_id, dd.quantity FROM deliveries AS d
			INNER JOIN deliveries_details AS dd ON d.delivery_id = dd.delivery_id
			WHERE d.delivery_id = NEW.delivery_id
		);
		
		UPDATE products p
		INNER JOIN updatedProducts ON  p.product_id = updatedProducts.product_id
        SET p.quantity = p.quantity + updatedProducts.quantity;
    END IF;
  END //
DELIMITER ;



DELIMITER //
CREATE TRIGGER insertClients BEFORE INSERT ON clients FOR EACH ROW
  BEGIN
    IF NOT is_nip_valid(new.nip) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'NIP invalid';
    END IF;
  END //
DELIMITER ;

DELIMITER //
CREATE  TRIGGER insertBrands BEFORE INSERT ON brands FOR EACH ROW
  BEGIN
    IF NOT is_nip_valid(new.nip) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'NIP invalid';
    END IF;
  END //
DELIMITER ;

