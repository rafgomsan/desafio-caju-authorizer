DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS mccs CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS merchants CASCADE;

CREATE TABLE IF NOT EXISTS public.merchants (
	merchant_id SERIAL4,
	merchant_name varchar(255) NOT NULL,
	mccs varchar(255) NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp,
	PRIMARY KEY(merchant_id)
);
CREATE INDEX IF NOT EXISTS merchant_name_idx ON public.merchants USING btree (merchant_name);

CREATE TABLE IF NOT EXISTS public.accounts (
	account_id SERIAL4,
	merchant_id INT NOT NULL,
	account varchar(25) NOT NULL,
	balance_food decimal NOT NULL,
	balance_meal decimal NOT NULL,
	balance_cash decimal NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp,
	PRIMARY KEY (account_id),
    CONSTRAINT fk_merchant
      FOREIGN KEY(merchant_id)
        REFERENCES merchants(merchant_id)

);
CREATE INDEX IF NOT EXISTS account_idx ON public.accounts USING btree (account);
CREATE INDEX IF NOT EXISTS account_merchant_idx ON public.accounts USING btree (merchant_id);

CREATE TABLE IF NOT EXISTS public.mccs (
	mcc_id SERIAL4,
	mcc varchar(15) NOT NULL,
	assigned_to varchar(15) NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp,
	PRIMARY KEY (mcc_id)
);
CREATE INDEX IF NOT EXISTS mcc_idx ON public.mccs USING btree (mcc);

CREATE TABLE IF NOT EXISTS transactions(
	transaction_id SERIAL4,
	merchant_id INT NULL,
	account_id INT  NULL,
	account varchar(25) NOT NULL,
	total_amount decimal NOT NULL,
	merchant varchar(255) NOT NULL,
	mcc varchar(15) NOT NULL,
	code varchar(15) NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp,
	PRIMARY KEY(transaction_id),
    CONSTRAINT fk_transaction_account
      FOREIGN KEY(account_id)
        REFERENCES accounts(account_id)
);
CREATE INDEX IF NOT EXISTS transaction_account_idx ON public.transactions USING btree (account);

---------------------------------------------------------------------------------
-- INSERT'S MERCHANTS
---------------------------------------------------------------------------------

INSERT INTO public.merchants
(merchant_name, mccs,  created_at, updated_at)
VALUES('gemini systems', '5411|5412|5811|5812', now(), now());

---------------------------------------------------------------------------------
-- INSERT'S ACCOUNTS
---------------------------------------------------------------------------------

INSERT INTO public.accounts
(merchant_id, account, balance_food, balance_meal, balance_cash, created_at, updated_at)
VALUES(1, '123', 100, 100, 100, now(), now());

INSERT INTO public.accounts
(merchant_id, account, balance_food, balance_meal, balance_cash, created_at, updated_at)
VALUES(1, '456', 100, 100, 100, now(), now());

---------------------------------------------------------------------------------
-- INSERT'S MCCS
---------------------------------------------------------------------------------

INSERT INTO public.mccs
(mcc, assigned_to, created_at, updated_at)
VALUES('5411', 'FOOD', now(), now());

INSERT INTO public.mccs
(mcc, assigned_to, created_at, updated_at)
VALUES('5412', 'FOOD', now(), now());

INSERT INTO public.mccs
(mcc, assigned_to, created_at, updated_at)
VALUES('5811', 'MEAL', now(), now());

INSERT INTO public.mccs
(mcc, assigned_to, created_at, updated_at)
VALUES('5812', 'MEAL', now(), now());

---------------------------------------------------------------------------------
-- INSERT'S TRANSACTIONS
---------------------------------------------------------------------------------

INSERT INTO public.transactions
(merchant_id, account_id, account, total_amount, merchant, mcc, code, created_at, updated_at)
VALUES(1, 1, '123', 10, 'gemini systems', '5411', '00', now(), now());
INSERT INTO public.transactions
(merchant_id, account_id, account, total_amount, merchant, mcc, code, created_at, updated_at)
VALUES(1, 1, '123', 10, 'gemini systems', '5412', '00', now(), now());
INSERT INTO public.transactions
(merchant_id, account_id, account, total_amount, merchant, mcc, code, created_at, updated_at)
VALUES(1, 1, '123', 10, 'gemini systems', '5811', '00', now(), now());
INSERT INTO public.transactions
(merchant_id, account_id, account, total_amount, merchant, mcc, code, created_at, updated_at)
VALUES(1, 1, '123', 1, 'gemini systems', '5812', '00', now(), now());
