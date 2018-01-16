package com.osiris.test.core.data.common;

import com.osiris.sample.entity.Drink;
import com.osiris.sample.entity.DrinkHasMaterial;
import com.osiris.sample.repository.DrinkRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class EntityTest {

    @Test
    public void manyToOne() {
        DrinkRepository dRepo = new DrinkRepository();
        Drink cappuccino = dRepo.findById(5)
                .orElse(null);
        assertNotNull(cappuccino);

        List<DrinkHasMaterial> materialList = cappuccino.getMaterialList();
        assertNotNull(materialList);

        for (DrinkHasMaterial drinkHasMaterial : materialList) {
            Drink drinkFromList = drinkHasMaterial.getDrink();
            assertEquals(cappuccino.getId(), drinkFromList.getId());
            assertEquals(cappuccino.getDrinkName(), drinkFromList.getDrinkName());
            assertEquals(0, Double.compare(cappuccino.getPrice(), drinkFromList.getPrice()));
            assertEquals(cappuccino.getCreatedDate(), drinkFromList.getCreatedDate());
            assertEquals(cappuccino.getDeletedDate(), drinkFromList.getDeletedDate());
        }
    }

    @Test
    public void oneToMany() {
        DrinkRepository dRepo = new DrinkRepository();
        Drink milkCoffee = dRepo.findById(2)
                .orElse(null);
        assertNotNull(milkCoffee);

        List<DrinkHasMaterial> materialList = milkCoffee.getMaterialList();
        assertNotNull(materialList);

        List<DrinkHasMaterial> expectedList = new ArrayList<>();
        expectedList.add(new DrinkHasMaterial(2, 1));
        expectedList.add(new DrinkHasMaterial(2, 2));
        expectedList.add(new DrinkHasMaterial(2, 3));

        assertEquals(expectedList.size(), materialList.size());
        for (int i = 0; i < materialList.size(); i++) {
            assertEquals(expectedList.get(i).getDrinkId(), materialList.get(i).getDrinkId());
            assertEquals(expectedList.get(i).getMaterialId(), materialList.get(i).getMaterialId());
        }
    }
}