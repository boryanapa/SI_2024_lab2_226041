import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class SILab2Test {
    @Test
    public void testCheckCartEveryBranch() {
        RuntimeException thrown_mess = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(null, 500);
        });
        assertEquals("allItems list can't be null!", thrown_mess.getMessage());

        List<Item> itemlist1 = List.of(new Item("", null, 100, 0));
        thrown_mess = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(itemlist1, 500);
        });
        assertEquals("No barcode!", thrown_mess.getMessage());

        List<Item> itemlist2 = List.of(new Item("name1", "A123456789", 100, 0));
        thrown_mess = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(itemlist2, 500);
        });
        assertEquals("Invalid character in item barcode!", thrown_mess.getMessage());

        List<Item> itemlist3 = List.of(new Item("name2", "0123456789", 200, 0));
        assertFalse(SILab2.checkCart(itemlist3, 100));

        List<Item> itemlist4 = List.of(new Item("name3", "0123456789", 500, 5));
        assertTrue(SILab2.checkCart(itemlist4, 2700));
    }

    @Test
    public void testCheckCartMultipleCondition() {
        Item item1 = new Item("name1", "05267893560", 430, 0.2f);  // TTT
        Item item2 = new Item("name2", "1234789560", 310, 0.35f);  // TTF
        Item item3 = new Item("name3", "1234098765", 840, 0.0f);   // TFX
        Item item4 = new Item("name4", "01238945678", 140, 0.1f);  // FXX

        assertTrue(item1.getPrice() > 300, "Item1 price should be > 300.");
        assertTrue(item1.getDiscount() > 0, "Item1 discount should be > 0.");
        assertEquals('0', item1.getBarcode().charAt(0), "Item1 barcode should start with 0.");

        assertTrue(item2.getPrice() > 300, "Item2 price should be > 300.");
        assertTrue(item2.getDiscount() > 0, "Item2 discount should be > 0.");
        assertNotEquals('0', item2.getBarcode().charAt(0), "Item2 barcode should not start with 0.");

        assertTrue(item3.getPrice() > 300, "Item3 price should be > 300.");
        assertFalse(item3.getDiscount() > 0, "Item3 discount should be 0.");

        assertFalse(item4.getPrice() > 300, "Item4 price should be <= 300.");
    }
}
