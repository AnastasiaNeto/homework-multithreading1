import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* 1.Реализуйте обработку каждой строки из массива texts в отдельном потоке.
   2.До цикла создайте List<Thread> threads, для хранения создаваемых потоков.
   3.Поток создавайте через new Thread(...), в конструкторе передайте реализацию лямбдой интерфейса Runnable,
        в котором будет находиться нужное действие.
   4.Не забудьте не только положить созданный объект потока в список потоков, но и запустить поток.
   5.После цикла опишите ожидание запущенных потоков основным потоком программы:
 for (Thread thread : threads) {
     thread.join(); // зависаем, ждём когда поток объект которого лежит в thread завершится
 }
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time
        List<Thread> threads = new ArrayList<>(); // создание List<Thread> threads, для хранения создаваемых потоков
        for (String text : texts) {
            Thread thread = new Thread(() -> { //новый поток
                int maxSize = 0;
                for (int i = 0; i < text.length(); i++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
            });
            threads.add(thread); //положить объект потока
            thread.start(); //запуск потока
        }
        // ожидание запущенных потоков
        for (Thread thread : threads) {
            thread.join(); // зависаем, ждём когда поток объект которого лежит в thread завершится
        }
        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
