package qwr;


import qwr.config.AreaZon;

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
//		Menu.consol();
	    Dialog.uConsol(con,AreaZon.list);
	}//main -----------------------------------------------------------------------
}//class Test======================================================================
