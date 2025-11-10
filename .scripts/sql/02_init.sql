USE `irsi`;

INSERT INTO `dim_currencies`
    (`code`, `name`, `symbol`)
VALUES
    ('EUR', 'Euro', '€');

INSERT INTO `dim_suppliers`
    (`id`, `name`, `url`)
VALUES
    (1, 'Property Services Regulatory Authority', 'https://www.psr.ie');
