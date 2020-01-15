CREATE DATABASE makeup;

USE makeup;

CREATE TABLE brands (
  brand_id 	 		int NOT NULL AUTO_INCREMENT,
  name       		varchar(50),
  nip 		 		char(10),
  address 	 		varchar(100),
  PRIMARY KEY (brand_id)
);

CREATE TABLE products (
  product_id 		int NOT NULL AUTO_INCREMENT,
  type       		enum ('lipstick', 'concealer', 'mascara'),
  name 				VARCHAR(50),
  price      		DECIMAL(5, 2) UNSIGNED,
  quantity   		int UNSIGNED,
  brand_id 			int,
  FOREIGN KEY (brand_id) REFERENCES brands (brand_id),
  CONSTRAINT price_constraint CHECK (price >= 0),
  PRIMARY KEY (product_id)
 );


CREATE TABLE clients (
  client_id 		int NOT NULL AUTO_INCREMENT,
  name 				VARCHAR(60),
  nip 				CHAR(10),
  address 			VARCHAR(100),
  PRIMARY KEY (client_id)
  );

CREATE TABLE sales (
  sale_id 			int NOT NULL AUTO_INCREMENT,
  sale_date 		date,
  client_id 		int,
  done 				boolean,
  PRIMARY KEY (sale_id),
  FOREIGN KEY (client_id) REFERENCES clients (client_id)
);

CREATE TABLE sales_details (
  sale_id    		int,
  product_id 		int,
  quantity   		int UNSIGNED,
  PRIMARY KEY(sale_id, product_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
  FOREIGN KEY (sale_id) REFERENCES sales (sale_id) ON DELETE CASCADE
);

CREATE TABLE deliveries (
  delivery_id   	int NOT NULL AUTO_INCREMENT,
  delivery_date 	date,
  done    			boolean,
  PRIMARY KEY (delivery_id)
);

CREATE TABLE deliveries_details (
  delivery_id 		int,
  product_id 		int,
  quantity 			int UNSIGNED,
  PRIMARY KEY (delivery_id, product_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
  FOREIGN KEY (delivery_id) REFERENCES deliveries (delivery_id) ON DELETE CASCADE
);


CREATE TABLE colors (
  color_id 			int NOT NULL AUTO_INCREMENT,
  name      		varchar(50),
  PRIMARY KEY (color_id)
);

CREATE TABLE lipsticks (
  product_id 		int NOT NULL,
  color_id 			int,
  formula 			enum ('liquid', 'cream', 'gloss'),
  finish 			enum ('matte', 'satin', 'glossy'),
  fill_weight 		int UNSIGNED,
  PRIMARY KEY (product_id),
  FOREIGN KEY (color_id) REFERENCES colors (color_id) ON DELETE CASCADE
);

CREATE TABLE concealers (
  product_id int NOT NULL,
  color_id 			int,
  undertone   		enum ('pink', 'yellow', 'neutral'),
  coverage     		enum ('light', 'medium', 'full'),
  fill_weight   	int UNSIGNED,
  PRIMARY KEY (product_id),
  FOREIGN KEY (color_id) REFERENCES colors (color_id) ON DELETE CASCADE
);

CREATE TABLE mascaras (
  product_id 		int NOT NULL,
  color_id 	 		int,
  brush   			enum ('silicone', 'tradicional'),
  effect  			enum ('volume', 'lifting'),
  fill_weight   	int UNSIGNED,
  PRIMARY KEY (product_id),
  FOREIGN KEY (color_id) REFERENCES colors (color_id) ON DELETE CASCADE
);

CREATE TABLE users (
  user_id int NOT NULL AUTO_INCREMENT,
  login varchar(30),
  password varchar(64),
  type enum('admin', 'boss', 'employee'),
  PRIMARY KEY (user_id)
);


--Indexes on sales
CREATE INDEX salesDoneIndex ON sales(done) USING BTREE;
CREATE INDEX salesDateIndex ON sales(sale_date) USING BTREE;
--Indexes on deliveries
CREATE INDEX deliveriesDoneIndex ON deliveries(done) USING BTREE;		
CREATE INDEX deliveriesDateIndex ON deliveries(delivery_date) USING BTREE;
--Indexes on product name
CREATE FULLTEXT INDEX productNameIndex ON products(name);


