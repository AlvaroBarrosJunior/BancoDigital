DELETE FROM public.tb004_cliente;

DELETE FROM public.tb002_agencia;

ALTER SEQUENCE public.seq_id_cliente RESTART;

ALTER SEQUENCE public.seq_id_agencia RESTART;
