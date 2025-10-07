package se233.chapter2.controller.draw;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import se233.chapter2.controller.AllEventHandler;
import se233.chapter2.model.Currency;

import java.util.concurrent.Callable;

public class DrawTopAreaTask implements Callable<Pane> {
    private Currency currency;
    private Button watch;
    private Button unwatch;
    private Button delete;

    public DrawTopAreaTask(Currency currency) {
        this.currency = currency;
        this.watch = new Button("Watch");
        this.unwatch = new Button("Unwatch");
        this.delete = new Button("Delete");
        this.watch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandler.onWatch(currency.getShortCode());
            }
        });
        this.unwatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandler.onUnwatch(currency.getShortCode());
            }
        });
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandler.onDelete(currency.getShortCode());
            }
        });
    }

    @Override
    public HBox call() throws Exception {
        HBox topArea = new HBox(10);
        topArea.setPadding(new Insets(5));
        topArea.getChildren().addAll(watch, unwatch, delete);
        ((HBox) topArea).setAlignment(Pos.TOP_RIGHT);
        return topArea;
    }
}