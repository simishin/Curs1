package qwr;

import java.util.List;
import java.util.Scanner;

public interface Item {
	String printLn();//печать строки данных
	String printHd();//печать заголовка таблицы
	String printTitle();

	//	int adding(int jbeg, int jend);
//	int removal(int jbeg, int jend);
	int	idItem();
	Item addID(int id);
	String title();
	void setTitle(String x);
	//создание нулевого элемента для работы меню генерации
//	static void init() { Loger.logs("Этот метод должен быть переопределен в классе"); }

	default void printList(){//для отладки
		for (Item x:list ) { Loger.prnq(x.printLn()); }
		Loger.prnq("---"+list.size());
	}//printList
	List<Item> list = null;

	/**
	 * Вызов метода корректировки значений параметров по умолчанию
	 * вызывается из меню Dialog.editDef()
	 * @param con консоль
	 * @return истина - выход на верхний уровень меню, лож- продолжение редактирования списка
	 */
	default boolean uConsol(Scanner con) {return false;	}
	default void printDefine(){}
}//Item
