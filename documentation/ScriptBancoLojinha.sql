SET NAMES utf8mb4;
SET time_zone = '+00:00';
SET sql_safe_updates = 0;

DROP DATABASE IF EXISTS pokemon_store;
CREATE DATABASE pokemon_store CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE pokemon_store;

CREATE TABLE Treinador (
  id_trainer  INT PRIMARY KEY AUTO_INCREMENT,
  nome        VARCHAR(80)  NOT NULL,
  pokeyens    DECIMAL(12,2) NOT NULL DEFAULT 0,
  insignias   INT NOT NULL DEFAULT 0
);

CREATE TABLE Bolsa (
  id_trainer INT PRIMARY KEY,
  espaco     INT NOT NULL,
  CONSTRAINT fk_bolsa_tr FOREIGN KEY (id_trainer)
    REFERENCES Treinador(id_trainer) ON DELETE CASCADE
);

CREATE TABLE Loja (
  id_loja  INT PRIMARY KEY AUTO_INCREMENT,
  nome     VARCHAR(80) NOT NULL,
  cidade   VARCHAR(80) NOT NULL,
  tipo     VARCHAR(40) NOT NULL
);

CREATE TABLE Produto (
  id_produto INT PRIMARY KEY AUTO_INCREMENT,
  id_loja    INT NOT NULL,
  nome       VARCHAR(100) NOT NULL,
  descricao  TEXT,
  preco      DECIMAL(10,2) NOT NULL,
  tipo       VARCHAR(40) NOT NULL,
  CONSTRAINT fk_prod_loja FOREIGN KEY (id_loja)
    REFERENCES Loja(id_loja) ON DELETE CASCADE
);

CREATE TABLE Compra (
  id_compra   INT PRIMARY KEY AUTO_INCREMENT,
  id_trainer  INT NOT NULL,
  id_loja     INT NOT NULL,
  data_compra DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  valor_total DECIMAL(12,2) NOT NULL DEFAULT 0,
  CONSTRAINT fk_compra_tr FOREIGN KEY (id_trainer)
    REFERENCES Treinador(id_trainer),
  CONSTRAINT fk_compra_loja FOREIGN KEY (id_loja)
    REFERENCES Loja(id_loja)
);

CREATE TABLE Compra_Produto (
  id_compra   INT NOT NULL,
  id_produto  INT NOT NULL,
  quantidade  INT NOT NULL,
  preco_unit  DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_compra, id_produto),
  CONSTRAINT fk_cp_c FOREIGN KEY (id_compra)
    REFERENCES Compra(id_compra) ON DELETE CASCADE,
  CONSTRAINT fk_cp_p FOREIGN KEY (id_produto)
    REFERENCES Produto(id_produto)
);

CREATE TABLE Acesso_Loja (
  id_trainer  INT NOT NULL,
  id_loja     INT NOT NULL,
  data_acesso DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_trainer, id_loja, data_acesso),
  CONSTRAINT fk_al_tr FOREIGN KEY (id_trainer)
    REFERENCES Treinador(id_trainer) ON DELETE CASCADE,
  CONSTRAINT fk_al_lo FOREIGN KEY (id_loja)
    REFERENCES Loja(id_loja) ON DELETE CASCADE
);

CREATE OR REPLACE VIEW vw_itens_compra AS
SELECT c.id_compra, t.nome AS treinador, l.nome AS loja,
       p.nome AS produto, cp.quantidade, cp.preco_unit,
       (cp.quantidade * cp.preco_unit) AS subtotal
FROM Compra c
JOIN Treinador t ON t.id_trainer = c.id_trainer
JOIN Loja l      ON l.id_loja = c.id_loja
JOIN Compra_Produto cp ON cp.id_compra = c.id_compra
JOIN Produto p    ON p.id_produto = cp.id_produto;

DELIMITER $$
CREATE TRIGGER trg_compra_total_ai
AFTER INSERT ON Compra_Produto
FOR EACH ROW
BEGIN
  UPDATE Compra c
  JOIN (
    SELECT id_compra, SUM(quantidade * preco_unit) AS soma
    FROM Compra_Produto
    WHERE id_compra = NEW.id_compra
    GROUP BY id_compra
  ) x ON x.id_compra = c.id_compra
  SET c.valor_total = x.soma;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE pr_realizar_compra_um_item(
  IN p_id_trainer INT,
  IN p_id_loja INT,
  IN p_id_produto INT,
  IN p_qtd INT
)
BEGIN
  INSERT INTO Compra(id_trainer, id_loja) VALUES (p_id_trainer, p_id_loja);

  INSERT INTO Compra_Produto(id_compra, id_produto, quantidade, preco_unit)
  VALUES (
    LAST_INSERT_ID(),
    p_id_produto,
    p_qtd,
    (SELECT preco FROM Produto WHERE id_produto = p_id_produto)
  );
END$$
DELIMITER ;

INSERT INTO Treinador (nome, pokeyens, insignias) VALUES
  ('Ash',   5000, 6),
  ('Misty', 3200, 4),
  ('Brock', 4100, 5);

INSERT INTO Bolsa (id_trainer, espaco) VALUES
  (1, 30),
  (2, 25),
  (3, 28);

INSERT INTO Loja (nome, cidade, tipo) VALUES
  ('Celadon Mart',  'Celadon',  'Supermercado'),
  ('Pewter Center', 'Pewter',   'Centro'),
  ('Vermilion Mk',  'Vermilion','Mercado');

INSERT INTO Produto (id_loja, nome, descricao, preco, tipo) VALUES
  (1, 'Poké Ball',   'Captura comum', 200.00, 'Ball'),
  (2, 'Great Ball',  'Captura melhor', 600.00, 'Ball'),
  (3, 'Potion',      'Cura 20 HP',    300.00, 'Cura');

INSERT INTO Compra (id_trainer, id_loja) VALUES
  (1, 1),
  (2, 2),
  (3, 3);

INSERT INTO Compra_Produto (id_compra, id_produto, quantidade, preco_unit) VALUES
  (1, 1, 2, 200.00),
  (2, 2, 1, 600.00),
  (3, 3, 3, 300.00);

INSERT INTO Acesso_Loja (id_trainer, id_loja) VALUES
  (1, 1),
  (2, 2),
  (3, 3);

UPDATE Produto SET preco = 550.00 WHERE nome = 'Great Ball';

DELETE FROM Acesso_Loja
WHERE id_trainer = 1 AND id_loja = 1
ORDER BY data_acesso DESC
LIMIT 1;

ALTER TABLE Produto ADD COLUMN obs VARCHAR(60) NOT NULL DEFAULT '—';
ALTER TABLE Produto DROP COLUMN obs;

DROP USER IF EXISTS 'loja_user'@'localhost';
CREATE USER 'loja_user'@'localhost' IDENTIFIED BY 'pokepass';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE, SHOW VIEW
ON pokemon_store.* TO 'loja_user'@'localhost';
FLUSH PRIVILEGES;

CALL pr_realizar_compra_um_item(1, 1, 1, 1);  -- aciona TRIGGER
SELECT * FROM vw_itens_compra ORDER BY id_compra, produto;
SELECT id_compra, valor_total FROM Compra ORDER BY id_compra;
