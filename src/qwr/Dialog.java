package qwr;
import java.util.List;
import java.util.Scanner;


public class Dialog {
	static int lines=7;//количество строк для единовременного вывода на экран
	static int count=0;//указатель положения
//	static public List<Item> list;

	/**
	 * Редактирование списка элементов с вызовом adding(list,jbeg,jend) для
	 * добавления элементов (+), removal(list,jbeg,jend) для удаления элементов (-),
	 * editing(con,list,jend) для редактирования названия (%),
	 * editDef(con,list, jend) для редактирования шаблона (*).
	 * Вызывается из Menu.action(Scanner con, int x),
	 * где определяется конкретный список при вызове.
	 * @param con консоль
	 * @param list список элементов для редактирования
	 * @return зарезервировано для направления перехода в меню
	 */
	public static int uConsol(Scanner con, List<Item> list){
		Loger.prnq("Создание элементов :"+list.get(0).printTitle());//печать заголовка
//		printFooter(list);
//		printDefine(list);
		String	qs;
		int jbeg, jend;
		label:
		while (true){

			qs=con.nextLine();
			jbeg=0;
			jend=0;
			if (qs.length() !=0) {
				for (int i = 1; i < qs.length(); i++) {
					if (qs.charAt(i) >= '0' && qs.charAt(i) <= '9')
						jend = jend * 10 + (qs.charAt(i) - '0');
					else if (jbeg == 0) {
						jbeg = jend;
						jend = 0;
					} else if (jend != 0) break;
				}
				if (jbeg==0 && jend != 0) {jbeg = jend;	jend = 0;}
				Loger.logs(" "+qs.charAt(0)+"="+jbeg+"-"+jend);
				switch (qs.charAt(0)) {
					case 'q':
					case '/': break label;
					case ' ':
						count=jbeg;
						printNext(list);
//						jbeg=0;
						break;
					case '+':
						printQuant(adding(list,jbeg,jend),jbeg,jend);
//						count=jbeg;
						printNext(list);
						break;
					case '-':  printQuant(removal(list,jbeg,jend),jbeg,jend); break;
					case '%':  if(editTitl(con,list,jbeg)) break label; else break;
					case '=':  if(editDef(con,list, jbeg)) break label; else break;
					case '*':  if(stencil(con,list, jbeg,jend)) break label; else break;
					case '?':	printHelp();	break;
					default:
				}//switch
			} else printNext(list);
			printDefine(list);//Печать шаблоны
			printFooter(list);
//			qs=con.next();// ввода до тех пор, пока не встретится разделитель (по умолчанию это пробел, но вы также можете его изменить)
//			s=inp.nextLine();// сканирует ввод, пока мы не нажмем кнопку ввода, и не вернем все это целиком, и поместит курсор в следующую строку.
		}//while
		return 0;
	}//consol----------------------------------------------------------------


	private static void printHelp(){
		Loger.prnq(
		"Для работы со списком используйте команды: +-для добавления, --удаления, _-просмотра списка, " +
		"%-изменить наименования, =-редактирование, *-по шаблону, /-завершение");
	}//printHelp ------------------------------------------------------------

	private static void printDefine(List<Item> list){
		Loger.prnt("Шаблон :");
		list.get(0).printDefine();
	}//printDefine

	private static void printQuant(int quant, int jbeg, int jend){
		int x= jend==0 ? 1 : jend - jbeg + 1;
		Loger.prnq("Обработано "+quant+" из "+x);
	}//printQuant

	private static void printNext(List<Item> list) {//печать одной странички из списка
		if (list.size()<1) return;
		Loger.prnq("\t"+list.get(0).printHd());//печать заголовка таблицы
		for (int i = 0; i < lines; i++) {
			if (count < 0) count=0;
			count++;
			if (count >= list.size()) { count=0; break; }
			Loger.prnq(count+".\t"+list.get(count).printLn());
		}
		Loger.prnq("= "+count+"/"+(list.size()-1)+" =");
	}//printNext ------------------------------------------------------------
	private static void printFooter(List<Item> list){
		Loger.prnq(
//				"Корректировка списка из "+(list.size()-1)+" элементов\n"+
				"Для работы со списком используйте команды: +-для добавления, --удаления, _-просмотра списка, " +
				"%-изменить наименования, =-редактирование, *-по шаблону, /-завершение");
	}//printFooter

	private static int adding(List<Item> list, int jbeg, int jend) {
		if (jbeg==0) {
			if (addn(list, list.size())) return 1;
			else return 0;
		}
		int z=0;
		if (jend==0) if (addn(list, jbeg)) return 1;
		else return 0;
		else
			for (int i = jbeg; i <= jend ; i++) if (addn(list, i)) z++;
		return z;
	}//adding
	private static boolean addn(List<Item> list, int j){
//		Loger.logs("j:"+j);
		for (Item x:list ) if (x.idItem()==j) return false;
		Item y=list.get(0).addID(j);
		if (y == null) return false;//Если не создалось
		list.add(y);
		return true;
	}//addn

	public static int removal(List<Item> list, int jbeg, int jend) {
		if (jbeg==0) return 0;
		int z=0;
		if (jend==0) if (remov(list, jbeg)) return 1;
		else return 0;
		else
			for (int i = jbeg; i <= jend ; i++) if (remov(list, i)) z++;
		return z;
	}//removal

	private static boolean remov(List<Item> list, int j){
		Item z=null;
		for (Item x:list ) {
			if (x.idItem()==j) {
				z=x;
				break;
			}
		}
		if (z != null) list.remove(z);
		else return false;
		return true;
	}//remov-----------------------------------------------------------------
	private static void printFooterEdit(){
		Loger.prnq("Для редактирования наименований используйте команды:\n"+
				"Ввод для перехода к следующему, +-редактирование " +
				", --переход в меню элементов, *-просмотра списка, /-завершение");
	}//printFooter ---------------------------------------------------------

	/**
	 * Изменение элемента по трафарету.
	 * При jbeg,jend==0 накладывается трафарет на текущий элемент и запрашивается
	 * подтверждение и далее переходим к следующему;
	 * При jend==0: делаю текущим jbeg, запрашиваю подтверждение, перехожу к следующему;
	 * При jbeg,jend !=0, запрашиваю подтверждение и завершаю.
	 * count=0;//указатель положения
	 * @param con консоль
	 * @param list список элементов для обработки
	 * @param jbeg
	 * @param jend
	 * @return Истина-выход на уровень вверх, Лож - работа на текущем уровне
	 */
	private static boolean stencil(Scanner con, List<Item> list, int jbeg, int jend) {
		if (jbeg>=list.size()){
			Loger.prnq("Запись не существует.");
			return false;
		}
		String	qs;
		if (jend == 0){//поэлементно
			if (jbeg !=0 ) count = jbeg;
			Loger.prnq("Подтвердите изменение записи -<+>, пропустить-<->, завершить-</>:");
			while (true) {
				if (count >= list.size()) return false;
				Loger.prnq("" + list.get(count).printLn() + "\n?");
				qs = con.nextLine();
				switch (qs.charAt(0)) {
					case '*':
//						printDefine( list);//печать шаблона
						list.get(0).uConsol(con);
						break;
					case '/':
						return false;
					case '+':
						list.get(count).update();
					case '-': count++;
				}//switch
			}//while
		} else { //изменение группы записей
			Loger.prnq("Подтвердите изменение записей -<+> с "+jbeg+" по "+jend );
			printDefine( list);//печать шаблона
			qs = con.nextLine();
			if (qs.charAt(0) != '+') return false;
			for (int i = jbeg; i <= jend && i < list.size(); i++) {
				list.get(i).update();
			}
			count = jend >= list.size() ? list.size() : jend;
			return false;
		}
	}//stencil -------------------------------------------------------------------
	/**
	 * Редактирование параметров по умолчанию. Вызывается из локальной uConsol()
	 * @param con консоль
	 * @param list список элементов для обработки
	 * @param index номер выбранного элемента или 0 для фильтра
	 * @return Истина-выход на уровень вверх, Лож - работа на текущем уровне
	 */
	private static boolean editDef(Scanner con, List<Item> list, int index) {
		assert list.size()>0 :"Список list не инициализирован до вызова Dialog.editDef()";
		if (index>=list.size()){
			Loger.prnq("Элемент <"+index+"> еще не создан. Создайте элемент.");
			return false;
		}
		if (index==0) Loger.prnq("Редактирование параметров шаблона для элементов\n");
		else Loger.prnq("Редактирование параметров элемента:"+index+" \n");
		Loger.prnq(list.get(index).printTitle());

//		Loger.logs("index:"+index);
		return list.get(index).uConsol(con);
	}//editDef

	/**
	 * Редактирование названия элемента.
	 * Вызывается из Dialog.uConsol(Scanner con, List<Item> list)
	 * @param con консоль
	 * @param list список элементов
	 * @param index номер элемента для которого был сделан вызов метода
	 * @return Истина - выход по 'q' и '%', Лож - выход по '/'
	 */
	private static boolean editTitl(Scanner con, List<Item> list, int index) {
		Loger.prnq("Редактирование наименований : "+list.get(0).printTitle());//печать заголовка
		if (list.size() < 2) {
		Loger.prnq("Записи не созданы. Необходимо сначала создать список.");
			return false;
		}
		printFooterEdit();
		String	qs;

		int jNumber=index<list.size() ? index : 0;//номер элемента в списке
		boolean single=index != 0;//одиночный вывод, если номер элемента или постранично
		if (!single) count=0;//для постраничного вывода начинаю с начала
		while (true){
			if (single) {
				Loger.prnt(jNumber + " /" + list.size());
				Loger.prnq(":\t" + list.get(jNumber).idItem() + "\t<" + list.get(jNumber).title()+">");
			} else printNext(list);
			qs=con.nextLine();
			if (qs.length() !=0) {
				int j=0;
				for (int i = 1; i < qs.length(); i++) {
					if (qs.charAt(i) >= '0' && qs.charAt(i) <= '9')
						j = j * 10 + (qs.charAt(i) - '0');
					else break;
				}//for
				Loger.logs(" count:"+count+" jNumber:"+jNumber+" j:"+j);
				switch (qs.charAt(0)) {
					case 'q':
					case '%':
						return true;
					case '/':
						return false;
					case ' ':
						break;
					case '*':
						single=false;
						count= j==0 ? jNumber: j;
						jNumber=1;
						break;
					case '+':
						jNumber = j==0 ? jNumber+1 : j;
						single=true;
						break;
					case '-':
						jNumber = j==0 ? jNumber-1 : j;
						single=true;
						break;
					default: list.get(jNumber).setTitle(qs);//записываю изменение
						jNumber++;
				}//switch
			} else {//if (qs.length() !=0)
				if (single) jNumber++;
			}
			if (jNumber >= list.size()) jNumber=1;
			else if (jNumber < 1) jNumber=list.size()-1;
			printFooterEdit();
		}//while
	}//editing
}//Dialog
/*
public static void ClearConsole(){
        try{
            String operatingSystem = System.getProperty("os.name") //Check the current operating system

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO.start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO.start();
                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
 */

