INSERT INTO jc_street (street_code, street_name) VALUES
(1, 'Vytautas '),
(2, 'Žemaitė '),
(3, 'Dariaus ir Girėnas'),
(4, 'Birutė'),
(5, 'S.Nėris');

INSERT INTO jc_university(university_id, university_name) VALUES
(1, 'Generolo Jono Žemaičio Lietuvos karo akademija'),
(2, 'Kauno medicinos universitetas'),
(3, 'Kauno technologijos universitetas'),
(4, 'Klaipėdos universitetas'),
(5, 'Lietuvos kūno kultūros akademija'),
(6, 'Lietuvos muzikos ir teatro akademija'),
(7, 'Lietuvos veterinarijos akademija'),
(8, 'Lietuvos žemės ūkio universitetas'),
(9, 'Mykolo Romerio universitetas'),
(10, 'Šiaulių universitetas'),
(11, 'Vilniaus dailės akademija'),
(12, 'Vilniaus Gedimino technikos universitetas'),
(13, 'Vilniaus pedagoginis universitetas'),
(14, 'Vilniaus universitetas'),
(15, 'Vytauto Didžiojo universitetas');

INSERT INTO jc_country_struct (area_id, area_name) VALUES
('010000000000', 'Miestas'),
('010010000000', 'Miesto Rajonas 1'),
('010020000000', 'Miesto Rajonas 2'),
('010030000000', 'Miesto Rajonas 3'),
('010040000000', 'Miesto Rajonas 4'),

('020000000000', 'Kraštas'),
('020010000000', 'Kraštas Regionas 1'),
('020010010001', 'Kraštas Regionas 1 Rajonas 1 Gyvenvietės 1'),
('020010010002', 'Kraštas Regionas 1 Rajonas 1 Gyvenvietės 2'),
('020010020000', 'Kraštas Regionas 1 Rajonas 2'),
('020010020001', 'Kraštas Regionas 1 Rajonas 2 Gyvenvietės 1'),
('020010020002', 'Kraštas Regionas 1 Rajonas 2 Gyvenvietės 2'),
('020010020003', 'Kraštas Regionas 1 Rajonas 2 Gyvenvietės 3'),
('020020000000', 'Kraštas Regionas 2'),
('020020010000', 'Kraštas Regionas 2 Rajonas 1'),
('020020010001', 'Kraštas Regionas 2 Rajonas 1 Gyvenvietės 1'),
('020020010002', 'Kraštas Regionas 2 Rajonas 1 Gyvenvietės 2'),
('020020010003', 'Kraštas Regionas 2 Rajonas 1 Gyvenvietės 3'),
('020020020000', 'Kraštas Regionas 2 Rajonas 2'),
('020020020001', 'Kraštas Regionas 2 Rajonas 2 Gyvenvietės 1'),
('020020020002', 'Kraštas Regionas 2 Rajonas 2 Gyvenvietės 2');

INSERT INTO jc_passport_office (p_office_id, p_office_area_id, p_office_name) VALUES
(1, '010010000000', 'Pasų stalas rajonas 1 miestas'),
(2, '010020000000', 'Pasų stalas 1 rajonas 2 miestas'),
(3, '010020000000', 'Pasų stalas 2 rajonas 2 miestas'),
(4, '010010000000', 'Pasų stalas rajonas 3 miestas'),
(5, '020010010001', 'Pasų stalas regionas 1 gyvenvietės 1'),
(6, '020010020002', 'Pasų stalas regionas 1 gyvenvietės 2'),
(7, '020020010000', 'Pasų stalas regionas 2 gyvenvietės 1'),
(8, '020020020000', 'Pasų stalas regionas 2 gyvenvietės 2');

INSERT INTO jc_register_office (r_office_id, r_office_area_id, r_office_name) VALUES
(1, '010010000000', 'santuokos registras 1 rajonas 1 miestas'),
(2, '010010000000', 'santuokos registras 2 rajonas 1 miestas'),
(3, '010020000000', 'santuokos registras rajonas 2 miestas'),
(4, '020010010001', 'santuokos registras regionas 1 gyvenvietė 1'),
(5, '020010020002', 'santuokos registras regionas 1 gyvenvietė 2'),
(6, '020020010000', 'santuokos registras regionas 2 gyvenvietė 1'),
(7, '020020020000', 'santuokos registras regionas 2 gyvenvietė 2');