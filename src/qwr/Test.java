package qwr;


import qwr.config.AreaZon;
import qwr.config.Room;

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
//		Menu.consol();
//		Dialog<AreaZon>.list=AreaZon.list;
//		Dialog.uConsol(con,AreaZon.list);
		Room.init();
		Room.test();
		Room.list.get(0).uConsol(con);
//		Room.uConsol(con);

//		XFields.uConsol(con,Room.uField,"тестовый запуск");




	    XFields[] z = new XFields[2];

	}//main -----------------------------------------------------------------------
}//class Test======================================================================
