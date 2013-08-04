/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;

/**
 *
 * @author sk
 */
public class MainWindowViewModel {

    private BooleanProperty executing = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return MainWindowViewModel.this;
        }

        @Override
        public String getName() {
            return "executing";
        }
    };
    private BooleanProperty selected = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return MainWindowViewModel.this;
        }

        @Override
        public String getName() {
            return "selected";
        }
    };
    private BooleanProperty onCancelled = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return MainWindowViewModel.this;
        }

        @Override
        public String getName() {
            return "onCancelled";
        }
    };

    public BooleanProperty executingProperty() {
        return executing;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public BooleanProperty onCancelledProperty() {
        return onCancelled;
    }
}
