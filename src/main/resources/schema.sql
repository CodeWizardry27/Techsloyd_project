CREATE TABLE IF NOT EXISTS payments (
                          id UUID PRIMARY KEY,
                          order_id UUID NOT NULL,
                          transaction_id VARCHAR(100) UNIQUE,
                          customer_id UUID,
                          amount NUMERIC(10,2) NOT NULL,
                          method VARCHAR(20) CHECK (method IN ('cash','card','digital_wallet')),
                          status VARCHAR(20) CHECK (status IN ('pending','processing','completed','failed','refunded')),
                          processing_fee NUMERIC(10,2) DEFAULT 0,
                          notes TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          processed_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS card_details (
                                            payment_id UUID PRIMARY KEY REFERENCES payments(id) ON DELETE CASCADE,
    cardholder_name VARCHAR(100),
    card_type VARCHAR(20) CHECK (card_type IN ('visa','mastercard','amex')),
    last4 VARCHAR(4),
    expiry_month INT,
    expiry_year INT
    );

CREATE TABLE IF NOT EXISTS cash_details (
                              payment_id UUID PRIMARY KEY REFERENCES payments(id) ON DELETE CASCADE,
                              amount_tendered NUMERIC(10,2),
                              change_given NUMERIC(10,2),
                              drawer_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS digital_wallet_details (
                                        payment_id UUID PRIMARY KEY REFERENCES payments(id) ON DELETE CASCADE,
                                        provider VARCHAR(20) CHECK (provider IN ('apple_pay','google_pay','paypal')),
                                        wallet_transaction_id VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS transactions (
                                            id UUID PRIMARY KEY,
                                            receipt_number VARCHAR(50) UNIQUE NOT NULL,
    customer_id UUID,
    subtotal NUMERIC(10,2) NOT NULL,
    tax_amount NUMERIC(10,2) NOT NULL,
    discount_amount NUMERIC(10,2) DEFAULT 0,
    total_amount NUMERIC(10,2) NOT NULL,
    payment_method VARCHAR(20),
    status VARCHAR(20) CHECK (
                                 status IN ('pending','completed','void','refunded')
    ),
    cashier VARCHAR(100),
    store_id UUID,
    register_id UUID,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    voided_at TIMESTAMP,
    void_reason TEXT
    );
CREATE TABLE IF NOT EXISTS transaction_items (
                                                 id UUID PRIMARY KEY,
                                                 transaction_id UUID REFERENCES transactions(id) ON DELETE CASCADE,
    product_id UUID,
    product_name VARCHAR(200),
    variant_id UUID,
    quantity INT NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL,
    subtotal NUMERIC(10,2) NOT NULL,
    tax_amount NUMERIC(10,2) NOT NULL,
    discount_amount NUMERIC(10,2) DEFAULT 0,
    total NUMERIC(10,2) NOT NULL
    );

