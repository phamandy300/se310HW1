/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import maze.ui.MazeViewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class SimpleMazeGame
{
	/**
	 * Creates a small maze.
	 */
	public static Maze createMaze()
	{
		
		Maze maze = new Maze();
		System.out.println("The maze does not have any rooms yet!");
		return maze;
		

	}

	public static Room createRoom(Map<Integer, Room> roomMap, String[] dataList) {
		int roomNumber = Integer.parseInt(dataList[1]);
		String north = dataList[2];
		String south = dataList[3];
		String east = dataList[4];
		String west = dataList[5];

		Room r = new Room(roomNumber);
		Wall mazeWall = new Wall();

		if (north.equals("wall")) {
			r.setSide(Direction.North, mazeWall);
		}

		if (south.equals("wall")) {
			r.setSide(Direction.South, mazeWall);
		}

		if (east.equals("wall")) {
			r.setSide(Direction.East, mazeWall);
		}

		if (west.equals("wall")) {
			r.setSide(Direction.West, mazeWall);
		}

		roomMap.put(roomNumber, r);

		return r;
	}

	public static void createDoor(Map<Integer, Room> roomMap, Map<String, Door> doorMap, String[] dataList) {
		String doorName = dataList[1];
		int roomNum1 = Integer.parseInt(dataList[2]);
		int roomNum2 = Integer.parseInt(dataList[3]);
		String state = dataList[4];

		Room r1 = roomMap.get(roomNum1);
		Room r2 = roomMap.get(roomNum2);

		Door d = new Door(r1, r2);

		d.setOpen(state.equals("open"));

		doorMap.put(doorName, d);
	}

	public static void connectRoom(Map<Integer, Room> roomMap, Map<String, Door> doorMap, String[] dataList) {
		int roomNumber = Integer.parseInt(dataList[1]);
		String north = dataList[2];
		String south = dataList[3];
		String east = dataList[4];
		String west = dataList[5];

		Room r = roomMap.get(roomNumber);

		if (Pattern.matches("^[0-9]+$", north)) {
			int sideRoomNum = Integer.parseInt(north);
			if (roomMap.containsKey(sideRoomNum)) {
				r.setSide(Direction.North, roomMap.get(sideRoomNum));
			}
		} else if (Pattern.matches("d[0-9]+", north)) {
			r.setSide(Direction.North, doorMap.get(north));
		}

		if (Pattern.matches("^[0-9]+$", south)) {
			int sideRoomNum = Integer.parseInt(south);
			if (roomMap.containsKey(sideRoomNum)) {
				r.setSide(Direction.South, roomMap.get(sideRoomNum));
			}
		} else if (Pattern.matches("d[0-9]+", south)) {
			r.setSide(Direction.South, doorMap.get(south));
		}

		if (Pattern.matches("^[0-9]+$", east)) {
			int sideRoomNum = Integer.parseInt(east);
			if (roomMap.containsKey(sideRoomNum)) {
				r.setSide(Direction.East, roomMap.get(sideRoomNum));
			}
		} else if (Pattern.matches("d[0-9]+", east)) {
			r.setSide(Direction.East, doorMap.get(east));
		}

		if (Pattern.matches("^[0-9]+$", west)) {
			int sideRoomNum = Integer.parseInt(west);
			if (roomMap.containsKey(sideRoomNum)) {
				r.setSide(Direction.West, roomMap.get(sideRoomNum));
			}
		} else if (Pattern.matches("d[0-9]+", west)) {
			r.setSide(Direction.West, doorMap.get(west));
		}

	}

	public static Maze loadMaze(final String path) // NSEW
	{
		Maze maze = new Maze();
		System.out.println("Please load a maze from the file!");

		try {
			Map<Integer, Room> roomMap = new HashMap<>();
			Map<String, Door> doorMap = new HashMap<>();

			File mazeFile = new File(path);
			Scanner myReader = new Scanner(mazeFile);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] dataList = data.split(" ");
				if (dataList[0].equals("room")) {
					Room r = createRoom(roomMap, dataList);

					maze.addRoom(r);

				} else if (dataList[0].equals("door")) {
					createDoor(roomMap, doorMap, dataList);
				}
			}

			myReader.close();

			myReader = new Scanner(mazeFile);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] dataList = data.split(" ");
				if (dataList[0].equals("room")) {
					connectRoom(roomMap, doorMap, dataList);
				} else {
					break;
				}
			}

			myReader.close();

			maze.setCurrentRoom(0);

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred while trying to open the file.");
		}

		return maze;
	}

	public static void main(String[] args)
	{

		Maze maze = loadMaze("large.maze");

	    MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}
