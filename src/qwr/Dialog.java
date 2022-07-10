package qwr;

import qwr.config.AreaZon;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Dialog<T> {
	static int lines=7;//количество строк для единовременного вывода на экран
	static int count=0;//указатель положения
//	static public List<Item> list;

	public static int consol(Scanner con, List<Item> list){
		Loger.prnq(list.get(0).printTitul());//печать заголовка
		printFooter(list);
		String	qs;
		int jbeg, jend, quant;
		label:
		while (true){
			qs=con.nextLine();
			jbeg=0;
			jend=0;
//			Loger.prnt(qs.length()+"*");
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
					case '*':
						count=jbeg;
						printNext(list);
						jbeg=0;
						break;
					case '+':  printQuant(adding(list,jbeg,jend),jbeg,jend); break;
					case '-':  printQuant(removal(list,jbeg,jend),jbeg,jend); break;
					case '%':  if(editing(con,list,jbeg,jend)) break label; else break;
					default:
				}//switch
			} else printNext(list);
			printFooter(list);
//			qs=con.next();// ввода до тех пор, пока не встретится разделитель (по умолчанию это пробел, но вы также можете его изменить)
//			s=inp.nextLine();// сканирует ввод, пока мы не нажмем кнопку ввода, и не вернем все это целиком, и поместит курсор в следующую строку.
		}//while
		return 0;
	}//consol

	private static void printQuant(int quant, int jbeg, int jend){
		int x= jend==0 ? 1 : jend - jbeg + 1;
		Loger.prnq("Обработано "+quant+" из "+x);
	}//printQuant

	private static void printNext(List<Item> list) {//печать одной странички из списка
		if (list.size()<1) return;
//		if (lines==0 || lines>list.size()) lines=list.size();
		Loger.prnq("\t"+list.get(0).printHd());//печать заголовка таблицы
		for (int i = 0; i < lines; i++) {
			if (count < 0) count=0;
			count++;
			if (count >= list.size()) { count=0; break; }
			Loger.prnq(count+".\t"+list.get(count).printLn());
		}
		Loger.prnq("---"+count+"/"+list.size());
//		list.get(0).printList();
	}//print
	private static void printFooter(List<Item> list){
		Loger.prnq("В базе находится "+(list.size()-1)+" элементов\n"+
				"Введите команду: +-для добавления, --удаления, *-просмотра списка, /-завершение");
	}//printFooter

	private static int adding(List<Item> list, int jbeg, int jend) {
		if (jbeg==0) return 0;
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
		Loger.prnq("Редактирование наименований элементов\n"+
				"Команды: Ввод для перехода к следующему, +-редактирование " +
				", --выход в главное меню, *-просмотра списка, /-завершение");
	}//printFooter

	private static boolean editing(Scanner con, List<Item> list, int jbeg, int jend) {
		printFooterEdit();
		return false;
	}//editing
}//Dialog

