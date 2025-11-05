



INSERT INTO TB_CLIENTE (id, nome, cpf, email, senha, telefone, endereco)
VALUES (1, 'João Silva', '12345678901', 'joao.silva@example.com', '1234', '11987654321', 'Rua das Flores, 123 - São Paulo/SP');

INSERT INTO TB_CLIENTE (id, nome, cpf, email, senha, telefone, endereco)
VALUES (2, 'Maria Souza', '98765432100', 'maria.souza@example.com', 'abcd', '21998765432', 'Av. Paulista, 456 - São Paulo/SP');




INSERT INTO TB_VEICULO (id, modelo, cor, marca, ano, cliente_id, placa)
VALUES (1, 'Civic', 'Preto', 'Honda', 2022, 1, 'ABC1D23');

INSERT INTO TB_VEICULO (id, modelo, cor, marca, ano, cliente_id, placa)
VALUES (2, 'Corolla', 'Branco', 'Toyota', 2020, 2, 'XYZ9E87');



INSERT INTO TB_FUNCIONARIO (id, nome, cpf, email, salario, login, senha, cargo)
VALUES (1, 'Carlos Mendes', '32165498700', 'carlos.mendes@oficina.com', 4500.00, 'carlosm', 'senha123', 'MECANICO');

INSERT INTO TB_FUNCIONARIO (id, nome, cpf, email, salario, login, senha, cargo)
VALUES (2, 'Fernanda Lima', '45698732199', 'fernanda.lima@oficina.com', 6000.00, 'fernandal', 'admin123', 'ADMINISTRADOR');




INSERT INTO TB_PRODUTO (id, nome, preco, quantidade)
VALUES (1, 'Filtro de Óleo', 35.90, 50);

INSERT INTO TB_PRODUTO (id, nome, preco, quantidade)
VALUES (2, 'Pneu Aro 15', 450.00, 20);

INSERT INTO TB_PRODUTO (id, nome, preco, quantidade)
VALUES (3, 'Pastilha de Freio', 180.00, 15);



INSERT INTO TB_ORDEMSERVICO (id, cliente_id, status, descricao, status_Pagamento, valor)
VALUES (1, 1, 'EM_ANDAMENTO', 'Troca de óleo e filtro', 'PENDENTE', 150.00);

INSERT INTO TB_ORDEMSERVICO (id, cliente_id, status, descricao, status_Pagamento, valor)
VALUES (2, 2, 'CONCLUIDA', 'Troca de pastilhas de freio', 'PAGO', 400.00);



INSERT INTO TB_ITEMPRODUTO (id, ordem_servico_id, produto_id, quantidade, valor_Unitario, subtotal)
VALUES (1, 1, 1, 1, 35.90, 35.90);

INSERT INTO TB_ITEMPRODUTO (id, ordem_servico_id, produto_id, quantidade, valor_Unitario, subtotal)
VALUES (2, 2, 3, 2, 180.00, 360.00);



INSERT INTO TB_PAGAMENTO (id, forma_Pagamento, valor, cliente_id)
VALUES (1, 'CARTAO_CREDITO', 150.00, 1);

INSERT INTO TB_PAGAMENTO (id, forma_Pagamento, valor, cliente_id)
VALUES (2, 'PIX', 400.00, 2);