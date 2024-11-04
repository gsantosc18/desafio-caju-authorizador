INSERT INTO account(active, id, name) VALUES(1, '075aab28-2265-420f-aa5e-253195617e9f', 'John Doel') ON DUPLICATE KEY UPDATE id = '075aab28-2265-420f-aa5e-253195617e9f';

INSERT INTO benefit(balance, id, account_id, `type`)VALUES(1000, '586db90a-61de-47d1-9008-738d1f1501f4', '075aab28-2265-420f-aa5e-253195617e9f', 'FOOD') ON DUPLICATE KEY UPDATE id = '586db90a-61de-47d1-9008-738d1f1501f4';
INSERT INTO benefit(balance, id, account_id, `type`)VALUES(1000, 'e7dc5843-0390-4f3b-8287-5750ed2fe61f', '075aab28-2265-420f-aa5e-253195617e9f', 'MEAL') ON DUPLICATE KEY UPDATE id = 'e7dc5843-0390-4f3b-8287-5750ed2fe61f';

INSERT INTO wallet(id, balance, account_id) VALUES('5b4f9d71-0e54-48b5-b843-ce57830012e6', 10000, '075aab28-2265-420f-aa5e-253195617e9f') ON DUPLICATE KEY UPDATE id = '5b4f9d71-0e54-48b5-b843-ce57830012e6';

INSERT INTO merchants(id, mcc, name) VALUES('4c0064be-ace8-4c1b-adb4-0f796cc60411', '5411', 'UBER EATS                   SAO PAULO BR') ON DUPLICATE KEY UPDATE id = '4c0064be-ace8-4c1b-adb4-0f796cc60411';
INSERT INTO merchants(id, mcc, name) VALUES('3fa605aa-de90-4159-bdff-ec07618bd0c5', '4799', 'UBER TRIP                   SAO PAULO BR') ON DUPLICATE KEY UPDATE id = '3fa605aa-de90-4159-bdff-ec07618bd0c5';
INSERT INTO merchants(id, mcc, name) VALUES('89a073b1-cdaf-4fd4-beb3-8d79f8c690b5', '5599', 'PAG*JoseDaSilva          RIO DE JANEI BR') ON DUPLICATE KEY UPDATE id = '89a073b1-cdaf-4fd4-beb3-8d79f8c690b5';
INSERT INTO merchants(id, mcc, name) VALUES('ff82415b-be85-49ef-89cb-606abfab1c82', '4799', 'PICPAY*BILHETEUNICO           GOIANIA BR') ON DUPLICATE KEY UPDATE id = 'ff82415b-be85-49ef-89cb-606abfab1c82';