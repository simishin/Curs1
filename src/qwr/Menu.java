package qwr;

import qwr.config.AreaZon;
import qwr.config.Room;
import qwr.config.VSensor;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * пользовательский диалог
 */
record Menu(int jItem, int jNext, int level, String uTitle) {
	private static int jSelect = 0;//текущая позиция
	public static int rights = 1; //права
	private static final Menu[] uTreeMenu = new Menu[65];

	public static void consol() {
		Menu.init();
		Main.u.log("--Dialog--");
		Scanner con = new Scanner(System.in);
		Loger.prnq("Вас приветствует Курсовая по JAVA\n текущий уровень доступа: " + rights);
		while (true) {
			Menu.print();
			try {
				int y = action(con, Menu.next(con.nextInt()));
				if (y >= 0) jSelect = y;
				else if (y == -2) previous();
				else if (y == -3) break;
			} catch (InputMismatchException ex) {
				if (con.next().equals("q")) break;
				if (con.next().equals("/")) previous();
				Loger.prne("Error, повторите ввод 'q'\n");
			}//catch
		}//while
	}//consol

	private static void add(int jItem, int jNext, String uTitle) {
		add(jItem, jNext, 0, uTitle);
	}

	private static void add(int jItem, int jNext, int level, String uTitle) {
		assert jSelect < uTreeMenu.length : "Превышение размера массива меню при инициализации. " + uTreeMenu.length;
		uTreeMenu[jSelect] = new Menu(jItem, jNext, level, uTitle);
		jSelect++;
	}//add

	private static void print() {
		for (Menu x : Menu.uTreeMenu)
			if (x.jItem == Menu.jSelect) {
				Loger.prnq("Меню: " + x.jItem + " ---" + x.uTitle + "--- ( Уровень доступа " + rights + ")");
				break;
			}
		for (Menu x : Menu.uTreeMenu) {
			if (x == null) break;
			if (x.jItem == Menu.jSelect) continue;
			if (x.level > rights) continue;//пропускаю если не хватает прав
			if (x.jItem / 10 == Menu.jSelect)
				Loger.prnq(x.jItem % 10 + ". " + x.uTitle);
		}
		Loger.prnq("---\nДля возврата на верхний уровень - 9, а в главное меню - 0 \n?");
	}//print-----------------------------------------------------------------------

	private static void previous() {
		if (Menu.jSelect != 0) Menu.jSelect /= 10;
	}

	private static void init() {
		add(0, 0, "Главное меню");
		add(1, 0, "Конфигурирование системы");
		add(11, 0, "Конфигурирование датчиков");
		add(111, 4, "Конфигурирование типов датчиков");
		add(112, 0, "Конфигурирование уставок отдельных датчиков с привязкой к месту");
		add(113, 0, "Привязка зон к датчикам");
		add(114, 0, "Привязка действий к датчикам");
		add(115, 0, "Привязка визуализации к датчикам");
		add(12, 0, "Конфигурирование зон");
		add(121, 2, "Список зон");
		add(122, 0, "Конфигурирование типов зон");
		add(123, 0, "Установка отдельных зон с привязкой к месту");
		add(124, 0, "Привязка датчиков к зонам");
		add(125, 0, "Привязка действий к зонам");
		add(126, 0, "Привязка визуализации к зонам");
		add(13, 0, "Конфигурирование действий");
		add(131, 0, "Конфигурирование типов исполнительных устройств");
		add(132, 0, "Привязка исполнительных устройств к месту и оборудованию");
		add(133, 0, "Привязка к датчикам исполнительных устройств");
		add(134, 0, "Привязка к зонам исполнительных устройств");
		add(135, 0, "Привязка к визуализации исполнительных устройств");
		add(14, 0, "Чтение из файла");
		add(141, 0, "Повторное Чтение текущего файла проекта");
		add(142, 0, "Чтение нового файла проекта");
		add(143, 0, "Сравнение нового файла проекта с текущем");
		add(15, 0, "Сохранение данных");
		add(151, 0, "Сохранение текущей конфигурации");
		add(152, 0, "Сохранение конфигурации в новый файл проекта");
		add(153, 0, "Создание нового файла проекта");
		add(154, 0, "Сохранение копии конфигурации проекта");
		add(155, 0, "Запись в аппаратный комплекс");
		add(156, 0, "Чтение из аппаратного комплекса");
		add(16, 3, "Список помещений расположения оборудования, мест");
		add(18, 0, "Конфигурирование визуализации");
		add(181, 0, "Конфигурирование экранов визуализации");
		add(182, 0, "Создание планов объектов");
		add(183, 0, "Наложение датчиков на планы объектов");
		add(184, 0, "Наложение зон на планы объектов");
		add(185, 0, "Наложение исполнительных устройств на планы объектов");
		add(186, 0, "Привязка планов к экранам");
		add(187, 0, "Конфигурирование действий тревожного монитора");
		add(2, 0, "Визуализация событий");
		add(21, 0, "Визуализация на общем плане  (АРМ администратора, визуализации)");
		add(22, 0, "Визуализация на тревожном мониторе  (АРМ администратора, визуализации)");
		add(23, 0, "Переключение мониторов (АРМ оператора, администратора)");
		add(3, 0, "Протоколирование событий");
		add(31, 0, "Показ текущего списка событий (АРМ оператора, администратора)");
		add(32, 0, "Показ архива событий (АРМ оператора, администратора)");
		add(33, 0, "Установка параметров архивирования событий (только АРМ администратора)");
		add(34, 0, "Установка фильтров событий (АРМ оператора, администратора)");
		add(35, 0, "Сохранение в текстовом файле списка событий за определенный промежуток времени");
		add(4, 0, "Анализ протокола событий по ложным срабатывания (АРМ администратора)");
		add(41, 0, "Построение графиков событий");
		add(42, 0, "Построение фильтров графиков событий");
		add(43, 0, "Наложение на графики событий внешних факторов, временных вех.");
		add(5, 0, "Действия по событиям и по командам оператора");
		add(51, 0, "Установка прав оператора на отдачу команд (АРМ администратора)");
		add(52, 0, "Отработка команд оператора (АРМ управления)");
		add(53, 0, "Установка автоматизации команд по событиям (АРМ администратора)");
		add(54, 0, "Квитирование событий (АРМ оператора, администратора)");
		add(55, 0, "Квитирование команд автоматизации (АРМ оператора, администратора)");
		add(56, 0, "Установка параметров \"жизни\" оператора (АРМ администратора)");
		add(57, 0, "Квитирование \"жизни\" оператора. (АРМ оператора, администратора)");
		add(8, 2, "ОТЛАДКА кода программы (вызов 1)");
		add(9, 1, "Завершение работы программы");
		Loger.logs(Menu.jSelect + " Количество строк меню");
		Menu.jSelect = 0;
	}//init

	/**
	 * Анализ введенной строки с консоли
	 * Вызывается из consol()
	 * @param j число введённое с консоли
	 * @return номер следующей строки меню или -1=ни чего не делать, -2 =подняться вверх, -3 Завершение работы
	 */
	private static int next(int j) {
		if (j == 0) {
			Menu.jSelect = 0;
			return 0;
		}
		if (j == 9 && Menu.jSelect != 0) {
			Menu.jSelect /= 10;
			return 0;
		}
		int y = Menu.jSelect * 10 + j;
//		Loger.logs("~~"+j+"  "+y);
		for (Menu x : Menu.uTreeMenu) {
			if (x == null) return -2;
			if (x.level > rights) return -3;//пропускаю если не хватает прав
			if (x.jItem == y) {
				if (x.jNext > 0) return x.jNext;
				Menu.jSelect = y;
				return 0;
			}
		}
		return -1;
	}//next---------------------------------------------------------------------------

	/**
	 * Выполнение действий по выбранному пункту меню
	 * @param con консоль для передачи потомкам
	 * @param x выбранный пункт меню
	 * @return Истина- Завершение работы, иначе р=продолжить
	 */
	private static boolean actions(Scanner con, int x) {
		if (x == 0) { jSelect = 0; return false; }
		if (x == 9 && jSelect != 0) { jSelect /= 10;return false; }
		if (x < 0) return false;//игнорирую выбор, жду следующего
		int y = jSelect * 10 + x;
		for (Menu j : uTreeMenu) {
			if (j == null) return false;
			if (j.level > rights) return false;//пропускаю если не хватает прав
			if (j.jItem == y) { jSelect = y; break; }//нашел
		}
		Loger.logs("% " + jSelect);
		int z=0;// 0 на месте, -1 переход по jNext, -2 на уровень выше, -3 завершение работы
		switch (jSelect) {
			case 111: 	z=Dialog.uConsol(con, VSensor.list);break;
			case 16: 	z=Dialog.uConsol(con, Room.list); break;
			case 121:	z=Dialog.uConsol(con, AreaZon.list); break;
			case 9:		if (jSelect == 0) return true;//Завершение работы
			case 8:
		}//switch
		switch (z){
			case -1:	jSelect  = uTreeMenu[x].jNext;
						return false;
			case -2:	if (jSelect != 0) jSelect /= 10;
						return false;
			case -3:	return true;//Завершение работы
		}//switch
		return false;
	}//action
	/**
	 * Выбор действия по меню
	 *
	 * @param con передача консоли для использовании
	 * @param x   номер действия из инициализации меню
	 * @return номер строки меню при возврате, -1 на месте, -2 на уровень выше, -3 Завершение работы
	 */
	private static int action(Scanner con, int x) {
		if (x <= 0) return -1;//игнорирую выбор, жду следующего
		Loger.logs("% " + x);
		switch (x) {
			case 1:
				return -3;//Завершение работы
			case 2:
				Dialog.uConsol(con, AreaZon.list);
				return -1;
			case 3:
				Dialog.uConsol(con, Room.list);
				return -1;
			case 4:
				Dialog.uConsol(con, VSensor.list);
				return -1;
			default:
				Loger.prne("Данный функционал находится в разработке (" + x + ")");
				return -1;
		}//switch
	}//action
}//Menu==================================================================================
