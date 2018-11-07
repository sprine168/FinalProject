package Program.ControllerPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionController {
    private ObservableList<?> collections;

    public CollectionController(ArrayList newCollection){
        collections = FXCollections.observableArrayList(newCollection);
    }

    public ObservableList<?> getCollections() {
        return collections;
    }
}
