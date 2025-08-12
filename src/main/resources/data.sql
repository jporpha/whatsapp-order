INSERT INTO settings (store_name, admin_phone, currency, created_at)
VALUES ('CultivArte', '56992760836', 'CLP', CURRENT_TIMESTAMP);

INSERT INTO products (name, description, price, currency, available, position, created_at)
VALUES
    ('La Celeste','Maracuyá + Jengibre', 2500,'CLP', true, 1, CURRENT_TIMESTAMP),
    ('Kombushot','Naranja + Cúrcuma + Jenjibre', 2500,'CLP', true, 2, CURRENT_TIMESTAMP),
    ('Frutos Rojos','Frambuesa + Maqui', 2500,'CLP', true, 3, CURRENT_TIMESTAMP),
    ('HidroKombi','Miel + Cedrón', 2500,'CLP', true, 4, CURRENT_TIMESTAMP),
    ('La la la','Lavanda + Manzanilla', 2500,'CLP', true, 5, CURRENT_TIMESTAMP),
    ('FramBuecha','Frambuesa + Cardamomo', 2500,'CLP', true, 6, CURRENT_TIMESTAMP),
    ('Phombucha','Lemongrass + Cedrón', 2500,'CLP', true, 7, CURRENT_TIMESTAMP);

INSERT INTO message_templates (name, template_text, active, created_at)
VALUES ('Default',
        '*Hola amigos de {storeName}*
Soy: {customerName} ({customerPhone})
Entrega: {deliveryMode}
{addressLine}
--------
{items}
--------
Total: {total} {currency}
Nota: {note}',
true, CURRENT_TIMESTAMP);
