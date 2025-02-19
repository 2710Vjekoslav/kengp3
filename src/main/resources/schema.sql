CREATE TABLE currency (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   code VARCHAR(32) NOT NULL,
   locale VARCHAR(16) NOT NULL,
   name VARCHAR(255) NOT NULL,
   create_by VARCHAR(255) NOT NULL,
   update_by VARCHAR(255) NOT NULL,
   create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
   update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

   CONSTRAINT unique_code_locale UNIQUE (code, locale)
);

CREATE INDEX idx_locale_name ON currency(locale, name);