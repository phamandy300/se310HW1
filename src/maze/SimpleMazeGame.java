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

	public static Maze loadMaze(final String path) // NSEW
	{
		Maze maze = new Maze();
		System.out.println("Please load a maze from the file!");

		try {
			File mazeFile = new File(path);
			Scanner myReader = new Scanner(mazeFile);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
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

		Maze maze = loadMaze("../../small.maze");

	    MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}
