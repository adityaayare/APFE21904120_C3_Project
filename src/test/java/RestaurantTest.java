import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    List<Item> listOfItems = new ArrayList<Item>();

    @BeforeEach
    public void create_dummy_restaurant_for_testing() {
        restaurant = new Restaurant("Cafe Coffee Day", "Mumbai", LocalTime.parse("10:30:00"), LocalTime.parse("22:00:00"));
        restaurant.addToMenu("Burger", 320);
        restaurant.addToMenu("Fries", 70);
        listOfItems = restaurant.getMenu();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime ten_minutes_after_current_time = LocalTime.now().plusMinutes(10);
        restaurant.setClosingTime(ten_minutes_after_current_time);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime ten_minutes_before_current_time = LocalTime.now().minusMinutes(10);
        restaurant.setClosingTime(ten_minutes_before_current_time);
        assertFalse(restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Burger");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("Vegetable lasagne"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<TOTAL ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void order_value_should_return_total_cost_of_items_selected() {
        assertEquals(390, restaurant.getTotalOrderValue(listOfItems));
    }

    @Test
    public void order_value_should_return_reduced_total_cost_when_an_item_removed() {
        int totalCost = restaurant.getTotalOrderValue(listOfItems);
        int costOfRemovedItem = listOfItems.get(1).getPrice();
        listOfItems.remove(1);
        assertEquals(totalCost - costOfRemovedItem, restaurant.getTotalOrderValue(listOfItems));
    }

    @Test
    public void order_value_should_return_zero_when_all_items_removed(){
        listOfItems.removeAll(listOfItems);
        assertEquals(0,restaurant.getTotalOrderValue(listOfItems));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<TOTAL ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}