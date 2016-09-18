package weekmenu

import com.weekmenu.Vegetable

class VegetableController {

    def index() { 
        render 'Hello vedgetable'
    }
    
    def newvegetable(){
        Vegetable veg = new Vegetable()
        [vegetable:veg]
    }
    
//    static scaffold = Vegetable

}
