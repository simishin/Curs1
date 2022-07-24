package qwr;


import qwr.config.AreaZon;
import qwr.config.Room;
import qwr.config.VSensor;

import java.util.Scanner;

/**
 * класс для тестирования отдельных частей кода
 */
public class Test {
	public static Loger u= Loger.init();

	public static void main(String[] args) {
		Loger.prnq("Тестирование частей кода");
		Scanner con = new Scanner(System.in);
		AreaZon.init();
		AreaZon.test();
//		Dialog<AreaZon>.list=AreaZon.list;
//		Dialog.uConsol(con,AreaZon.list);
		Room.init();
		Room.test();
		VSensor.init();
//		Room.list.get(0).uConsol(con);
		MainMenu.consol();
	}//main -----------------------------------------------------------------------
}//class Test======================================================================
