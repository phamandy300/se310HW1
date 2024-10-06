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

	public static Room createRoom(Integer roomNumber, String north, String south, String east, String west) {
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

		return r;
	}

	public static void createDoor(Map<Integer, Room> roomMap, Integer roomNum1, Integer roomNum2, String state) {
		Room r1 = roomMap.get(roomNum1);
		Room r2 = roomMap.get(roomNum2);

		Door d = new Door(r1, r2);

		for (Direction dir : Direction.values()) {
			if (r1.getSide(dir) == null) {
				r1.setSide(dir, d);
				break;
			}
		}

		for (Direction dir : Direction.values()) {
			if (r2.getSide(dir) == null) {
				r2.setSide(dir, d);
				break;
			}
		}

        d.setOpen(state.equals("open"));
	}

	public static Maze loadMaze(final String path) // NSEW
	{
		Maze maze = new Maze();
		System.out.println("Please load a maze from the file!");

		try {
			File mazeFile = new File(path);
			Scanner myReader = new Scanner(mazeFile);
			Map<Integer, Room> roomMap = new HashMap<>();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] dataList = data.split(" ");
				if (dataList[0].equals("room")) {
					Integer roomNumber = Integer.parseInt(dataList[1]);
					String north = dataList[2];
					String south = dataList[3];
					String east = dataList[4];
					String west = dataList[5];
					Room r = createRoom(roomNumber, north, south, east, west);

					maze.addRoom(r);

					roomMap.put(roomNumber, r);

				} else if (dataList[0].equals("door")) {
					Integer roomNum1 = Integer.parseInt(dataList[2]);
					Integer roomNum2 = Integer.parseInt(dataList[3]);
					String state = dataList[4];

					createDoor(roomMap, roomNum1, roomNum2, state);
				}
			}

			maze.setCurrentRoom(0);

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred while trying to open the file.");
		}

		return maze;
	}

	public static void main(String[] args)
	{
//		Maze maze = createMaze();
//
//		Wall mazeWall = new Wall();
//
//		Room r1 = new Room(0);
//		r1.setSide(Direction.South, mazeWall);
//		r1.setSide(Direction.West, mazeWall);
//		r1.setSide(Direction.East, mazeWall);
//		maze.addRoom(r1);
//
//		Room r2 = new Room(1);
//		r2.setSide(Direction.North, mazeWall);
//		r2.setSide(Direction.West, mazeWall);
//		r2.setSide(Direction.East, mazeWall);
//		maze.addRoom(r2);
//
//		Door d1 = new Door(r1, r2);
//		r1.setSide(Direction.North, d1);
//		r2.setSide(Direction.South, d1);
//
//
		Maze maze = loadMaze("large.maze");

	    MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}
