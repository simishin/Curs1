/**
 * Перечень возможных полей в файлах проекта
 * каждое поле может храниться только в одном типе файла проекта
 * поле может быть основным и нести в себе драные или
 * вспомогательным для хранения контрольных значений или описаний - не подгружается
 * каждое поле имеет свою структуру данных в соответствии для какой модели создано
 * Для реализации параллельного выполнения процедур чтения и записи для шинчтво полей
 * ассоциируются с соответствующими структурами данных и содержат флаг и\или счетчики
 * изменений для запуска процессов сохранения данных. А так же хранят время последнего
 * процесса работы с файлом для отслеживания временных промежутков между поиском
 * изменений во внешних файлах и сохранения изменений в локальных файлах. При завершении
 * работы программы генерируется флаг/число -1 в счетчике для выполнения
 * принудительного сохранения результатов работы.
 * Данное перечисление позволяет унифицировать сохранение и чтение внешних данных структур.
 * Поскольку внешних данных много, то данное перечисление ориентирую на локальные данные.
 * Добавляю поля: флаг изменений??????
 *
 * уровень важности элемента определяется не типом файла, а ключём строки в файле !!!!
 * любая запись должна содержать уровень приоритета в старших разрядах поля order
 * 11-Глобальный всеобщий справочник (соглашение между проектами или базовые установки системы)
 * 10-Общий справочник проекта (соглашение внутри проекта)
 * 01-Устаревший элемент (далее не использовать)
 * 00-данные из внешних таблиц, данные пользователя (данные пользователя)
 */
package qwr.SharSystem;

import qwr.Item;
import qwr.Loger;
import qwr.config.AreaZon;
import qwr.config.Room;
import qwr.config.SensoRoom;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static qwr.Loger.prnq;
import static qwr.SharSystem.UtilFile.sepr;

public enum GrRecords {
TITUL {//описание данного файла
    /*
    Титульный заголовок содержит следующую информацию для сбора аналитики:
    Код проекта принадлежности, название проекта и время его создания
     */
    public void writPL(BufferedWriter bw) {
        assert bw!=null:"-- GrRecords:writPL > BufferedWriter==null";
        try { bw.write(this.name()+sepr+    //0)наименование поля
            "Файл сохранения данных настройки охранно-пожарной сигнализации\n");
        }catch(IOException ioException){ioException.printStackTrace();return;}
        return;
    }//writPL
    @Override //---------------------------------------------------------------------
    public boolean readRecord(String[] words,int src) {//разбор строки внеш.файла
        return false;//условие дальнейшего анализа файла
    }//readExst TITUL
}, //============================================================================
ARZON {
    public void writPL(BufferedWriter bw) {
        assert prnq(name()+" : "+ AreaZon.list.size());
        writPL(AreaZon.list.stream(), bw);//вызов обобщенного метода в этом перечислении
        return;
    }//writPL

    /**
     * Вызывается из enum FileType loadLoc() для обработки считанной строки
     * @param words массив слов считанной строки
     * @param src тип источника массива/потока строк
     * @return истина если прекратить обработку потока строк или лож для продолжения
     */
    @Override //---------------------------------------------------------------------
    public boolean readRecord(String[] words,int src) {//разбор строки внеш.файла
        return GrRecords.readRecordPubl(AreaZon.creatExtDbf(words,src));
    }//readExst

},//PRODJ----------------------------------------------------------------------------------------------------
ROOOM{//вспомогательный - количество путей
    @Override
    public void writPL(BufferedWriter bw) {
        assert prnq(name()+" : "+ Room.list.size());
        writPL(Room.list.stream(), bw);//вызов обобщенного метода в этом перечислении
        return;
    }//writPL
    @Override //---------------------------------------------------------------------
    public boolean readRecord(String[] words,int src) {//разбор строки внеш.файла
        return GrRecords.readRecordPubl(Room.creatExtDbf(words,src));
    }//readExst
},//PATHS
//=============================================================================
SENRM{//вспомогательный - количество пользователей
    @Override
    public void writPL(BufferedWriter bw) {
        assert prnq(name()+" : "+ SensoRoom.list.size());
        writPL(SensoRoom.list.stream(), bw);
        return;
    }//writPL
    @Override //---------------------------------------------------------------------
    public boolean readRecord(String[] words,int src) {//разбор строки внеш.файла
        return GrRecords.readRecordPubl(SensoRoom.creatExtDbf(words,src));
    }//readExst
},//URS
//=============================================================================
ENDFL {
    @Override
    public void writPL(BufferedWriter bw) {
        assert bw!=null:"-- GrRecords:writPL > BufferedWriter==null";
        try { bw.write(this.name() + "\n");
              bw.flush();//очистка буффера
        }catch(IOException ioException){ioException.printStackTrace();return;}
        return;
    }//writPL
    @Override //---------------------------------------------------------------------
    public boolean readRecord(String[] words,int src) {//разбор строки внеш.файла
        return true;//условие прекращения дальнейшего анализа файла
    }//readExst
    @Override
    public boolean endLoad(){ return true;}
};//END
    //==============================================================================
    protected int         iadd;//количество добавленных элементов в список для сохранения
    public static final int   lengName = 5;//длинна маркера с разделителем
    public  void        shift(){iadd++;}
    public  void        clrIadd(){iadd=0;}
    public  int         getIadd() { return iadd; }

    //конструктор
    GrRecords()          { iadd=0;}
    //методы
    public abstract void    writPL(BufferedWriter bw);//для переопределения

    /**
     * Флаг нахождения конца списка данных в файле. Переопределяется в ENDFL
     * @return истина, если первое слово обозначает конец списка данных
     */
    public boolean endLoad(){ return false;}
    /**
     * Вызывается из GrRecords.ХХХХХ.writPL(BufferedWriter bw)
     * @param list список требуемого класса объекта записи
     * @param bw буфер для записи, наследуемый при вызове
     * @param <T> тип объекта записи, задается при вызове
     * @return возвращает истина, если запись прошла без сбоев
     */
    public <T> boolean writPL(Stream<T> list, BufferedWriter bw) {
        assert bw!=null:"-- GrRecords:writPL > BufferedWriter==null";
        AtomicBoolean z= new AtomicBoolean(true);
        list.forEach(q-> {
            try { bw.write(this.name()+q.toString()+"\n"); }
            catch (IOException e) { e.printStackTrace(); z.set(false);}
        });
        if (z.get()) this.iadd=0;//сбрасываю счетчик изменений
        return z.get();
    }//writPL

    /**
     * Вызывается из enum FileType loadLoc() для обработки считанной строки
     * @param words массив слов считанной строки
     * @param src тип источника массива/потока строк- 3 младших бита и сдвинутого номера типа файлов
     * @return истина если прекратить обработку потока строк или лож для продолжения
     */
    public abstract boolean readRecord(String[] words,int src);//распознавание записи в строке
    private static boolean readRecordPubl(Item y) {//
        if (y == null) return false;
        y.linkList().add(y);
        return true;
    }//readRecordPubl
}//enum GrRecords
