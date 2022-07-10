package qwr;

import java.util.List;

public interface Item {
	String printLn();//печать строки данных
	String printHd();//печать заголовка таблицы
	String printTitul();
//	int adding(int jbeg, int jend);
//	int removal(int jbeg, int jend);
	int	idItem();
	Item addID(int id);
	String titul();
	void setTitul(String x);
	void printList();//для отладки
	List<Item> list = null;
}//Item
