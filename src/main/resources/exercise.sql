--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.10
-- Dumped by pg_dump version 9.6.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: company_table; Type: TABLE; Schema: public; Owner: mare
--

CREATE TABLE public.company_table (
    id bigint NOT NULL,
    date character varying(255),
    security character varying(255),
    weighting double precision NOT NULL
);


ALTER TABLE public.company_table OWNER TO mare;

--
-- Name: country_table; Type: TABLE; Schema: public; Owner: mare
--

CREATE TABLE public.country_table (
    id bigint NOT NULL,
    name character varying(255),
    weight double precision NOT NULL
);


ALTER TABLE public.country_table OWNER TO mare;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: mare
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO mare;

--
-- Name: message; Type: TABLE; Schema: public; Owner: mare
--

CREATE TABLE public.message (
    id bigint NOT NULL,
    recipient character varying(255),
    text character varying(255)
);


ALTER TABLE public.message OWNER TO mare;

--
-- Name: test; Type: TABLE; Schema: public; Owner: mare
--

CREATE TABLE public.test (
    id integer,
    name character varying(40)
);


ALTER TABLE public.test OWNER TO mare;

--
-- Data for Name: company_table; Type: TABLE DATA; Schema: public; Owner: mare
--

COPY public.company_table (id, date, security, weighting) FROM stdin;
\.


--
-- Data for Name: country_table; Type: TABLE DATA; Schema: public; Owner: mare
--

COPY public.country_table (id, name, weight) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: mare
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: mare
--

COPY public.message (id, recipient, text) FROM stdin;
\.


--
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: mare
--

COPY public.test (id, name) FROM stdin;
1	Max
2	Thomas
3	Flo
4	Alex
5	Ralf
10	Bernd
10	Bernd
10	Bernd
10	Bernd
10	Bernd
10	Bernd
10	Bernd
\.


--
-- Name: company_table company_table_pkey; Type: CONSTRAINT; Schema: public; Owner: mare
--

ALTER TABLE ONLY public.company_table
    ADD CONSTRAINT company_table_pkey PRIMARY KEY (id);


--
-- Name: country_table country_table_pkey; Type: CONSTRAINT; Schema: public; Owner: mare
--

ALTER TABLE ONLY public.country_table
    ADD CONSTRAINT country_table_pkey PRIMARY KEY (id);


--
-- Name: message message_pkey; Type: CONSTRAINT; Schema: public; Owner: mare
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

