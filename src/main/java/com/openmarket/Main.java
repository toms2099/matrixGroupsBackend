package com.openmarket;

public class Main {

	public static void main(String[] args) {
		
		MatrixClient matrix = new MatrixClient();
		
		String roomId = matrix.createRoom("candyTestRoom2");
		
		String test = roomId + "";
		
	}
}
