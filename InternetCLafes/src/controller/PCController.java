package controller;

import java.util.Vector;

import dao.PCModel;
import model.PC;

public class PCController {
	// PC Controller menggunakan Singleton agar hanya satu instance yang terpakai di app.
	public static volatile PCController instance = null;
	
	private PCController() {
		
	}
	
	public static PCController getInstance() {
		if(instance == null) {
			synchronized (PCController.class) {
				if(instance == null) {
					 instance = new PCController();
				}
			}
		}
		
		return instance;
	}
	
	// Akses model untuk mengakses data PC dari database
	PCModel pcModel = PCModel.getInstance();

	public Vector<PC> getAllPCData() {
		return PCModel.getAllPCData();
	}
	
	
}
