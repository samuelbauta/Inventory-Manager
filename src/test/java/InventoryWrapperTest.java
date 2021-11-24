import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryWrapperTest {

    @Test
    void addItem() {
        InventoryWrapper test = new InventoryWrapper();
        Item tempItemOne = new Item("190.00", "A-100-100-100", "Jimmy");
        test.addItem(tempItemOne);

        Item tempItemTwo = test.getData().get(0);
        assertEquals(tempItemTwo, tempItemOne);
    }

    @Test
    void removeItem() {
        InventoryWrapper test = new InventoryWrapper();
        Item tempItemOne = new Item("190.00", "A-100-100-100", "Jimmy");
        test.addItem(tempItemOne);

        Item tempItemTwo = test.getData().get(0);
        assertEquals(tempItemTwo, tempItemOne);

        test.removeItem(tempItemOne);
        assertEquals(test.getData().size(), 0);
    }

    @Test
    void clearList() {
        InventoryWrapper test = new InventoryWrapper();
        Item tempItemOne = new Item("190.00", "A-100-100-100", "Jimmy");
        test.addItem(tempItemOne);

        test.clearList();
        assertEquals(test.getData().size(), 0);
    }

    @Test
    void inventoryCapacity() {
        InventoryWrapper test = new InventoryWrapper();

        for (int i = 0; i < 1024; i++) {
            Item tempItemOne = new Item("190.00", "A-100-100-100", "Jimmy");
            test.addItem(tempItemOne);
        }
        assertEquals(test.getData().size(),1024);
    }
}