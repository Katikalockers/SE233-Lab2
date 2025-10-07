package se233.chapter2.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RefreshTask extends Task<Void> {
    @Override
    protected Void call() throws InterruptedException {
        for (;;) {
            try {
                Thread.sleep((long) (60 * 1e3));
            } catch (InterruptedException e) {
                System.out.println("Encountered an interrupted exception.");
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        FutureTask futureTask = new FutureTask(new WatchTask());
                        Platform.runLater(futureTask);
                        try {
                            futureTask.get();
                        } catch (InterruptedException e) {
                            System.out.println("Encountered an interrupted exception.");
                        } catch (ExecutionException e) {
                            System.out.println("Encountered an execution exception.");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
