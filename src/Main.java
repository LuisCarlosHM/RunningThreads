//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){
        System.out.println("Main thread running");
        try {
            System.out.println("Main thread paused for one second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(()->{
           String tname = Thread.currentThread().getName();
           System.out.println(tname + " should take 10 dots to run.");
           for(int i = 0; i < 10; i++){
               System.out.print(". ");
               try{
                   Thread.sleep(500);
               } catch (InterruptedException e){

                   System.out.println("\nWhoops!! " + tname +
                           " interrupted.");
                   Thread.currentThread().interrupt();
                   return;
               }
           }
           System.out.println("\n" + tname + " completed.");
        });
        Thread installledThread = new Thread(()->{
            try {
                for(int i = 0; i < 3; i++){
                    Thread.sleep(250);
                    System.out.println("Instalation Step " + (i + 1)
                    + " is completed.");
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }, "InstalledThread");

        Thread threadMonitor = new Thread(() ->{
            long now = System.currentTimeMillis();
            while(thread.isAlive()){
                System.out.println("\nwating for thread to complete");
                try{
                    Thread.sleep(1000);
                    if(System.currentTimeMillis() - now > 8000){
                        thread.interrupt();
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        System.out.println(thread.getName() + " starting");
        thread.start();
        threadMonitor.start();

        try{
            thread.join();
        } catch (InterruptedException e){
            throw  new RuntimeException(e);
        }

        if(!thread.isInterrupted()){
            installledThread.start();
        } else{
            System.out.println("Previous thread was interrupted, "
                    + installledThread.getName() + "  can't run.");
        }

    }
}