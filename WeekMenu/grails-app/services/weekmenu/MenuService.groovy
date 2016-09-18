package weekmenu

import com.weekmenu.Menu
import com.weekmenu.Vegetable
import grails.transaction.Transactional

@Transactional
class MenuService {

//    def addVegetable(Menu menu, Vegetable veg) {
    def addVegetable() {
        menu.vegetable << new Vegetable(name : 'carrot')
    }
    
    void saveOrUpdate(Menu menu) {
        
        if(Menu.get(menu.id)){
            menu.update()
        } else {
            menu.save()
        }
    }
}
