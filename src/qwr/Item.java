package qwr;

import java.util.List;
import java.util.Scanner;

public interface Item {
	String printLn();//печать строки данных
	String printHd();//печать заголовка таблицы
	String printTitle();
	int	idItem();//номер элемента
	Item addID(int id);//создание элемента с заданным номером
	String title();//показать наименование элемента
	void setTitle(int index, String x);//задать наименование объекта по указанному индексу
	//создание нулевого элемента для работы меню генерации
//	default void printTest(){Loger.logs("Не реализован !");}//для отладки
//	default void printList(){//для отладки
//		for (Item x:list ) { Loger.prnq(x.printLn()); }
//		Loger.prnq("---"+list.size());
//	}//printList
	List<Item> list = null;
	//Возвращение ссылки на список данного класса
	List<Item> linkList();
	/**
	 * Создание элемента нужного типа для помещения в очередь новых элементов
	 * полученных из внешних файлов. Вызывается из GrRecords.readRecordExt
	 * @param words срока из файла внешних данных
	 * @param i номер элемента в enum GrRecords
	 * @return новый элемент
	 */
	static Item creatExtDbf(String[] words, int i) {
		assert Loger.prnq("$ Item.creatExtDbf NOT REALISE $");
		return null;
	}//creatExtDbf


	/**
	 * Вызов метода корректировки значений параметров по умолчанию
	 * вызывается из меню Dialog.editDef()
	 * @param con консоль
	 * @return истина - выход на верхний уровень меню, лож- продолжение редактирования списка
	 */
	default boolean uConsol(int index, Scanner con) {return false;	}
	/**
	 * Выводит на экран установленные фильтры для конкретного списка элементов
	 * Вызывается из Dialog.printDefine(List<Item> list)
	 * и передает в XFields.printDefine(uField) массив данных нулевого элемента
	 */
	default void prnStencil(){Loger.logs("Не реализован !");}
//	default XFields[] uField(){return null;}
	/**
	 * модификация элемента списка по шаблону, находящемся в нулевом элементе
	 * @param index индекс модифицируемого элемента в списке
	 */
	default void update(int index){Loger.logs("Не реализован !");}
}//Item
