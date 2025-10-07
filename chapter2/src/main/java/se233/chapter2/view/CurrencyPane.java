package se233.chapter2.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.chapter2.controller.draw.DrawCurrencyInfoTask;
import se233.chapter2.controller.draw.DrawGraphTask;
import se233.chapter2.controller.draw.DrawTopAreaTask;
import se233.chapter2.model.Currency;

import java.util.concurrent.*;

public class CurrencyPane extends BorderPane {
    private Currency currency;

    public CurrencyPane(Currency currency) {
        this.setPadding(new Insets(0));
        this.setPrefSize(640,300);
        this.setStyle("-fx-border-color: black");
        try {
            this.refreshPane(currency);
        } catch (ExecutionException e) {
            System.out.println("Encountered an execution exception.");
        } catch (InterruptedException e) {
            System.out.println("Encountered an interrupt exception.");
        }
    }

    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException {
        this.currency = currency;
        Pane currencyInfo = genInfoPane();
        FutureTask futureTask = new FutureTask<VBox>(new DrawGraphTask(currency));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(futureTask);
        VBox currencyGraph = (VBox) futureTask.get();
        Pane topArea = genTopArea();
        this.setTop(topArea);
        this.setLeft(currencyInfo);
        this.setCenter(currencyGraph);
    }

    private Pane genInfoPane() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask<Pane>(new DrawCurrencyInfoTask(currency));
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(futureTask);
        Pane currencyInfoPane = (Pane) futureTask.get();
        return currencyInfoPane;
    }

    private Pane genTopArea() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask<Pane>(new DrawTopAreaTask(currency));
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(futureTask);
        Pane topArea = (Pane) futureTask.get();
        return topArea;
    }
}
