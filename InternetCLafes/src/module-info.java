module InternetCLafes {
	opens controller;
	opens database;
	opens dao;
	opens main;
	opens model;
	opens view;

	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
}