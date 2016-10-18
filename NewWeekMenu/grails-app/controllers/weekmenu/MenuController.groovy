package weekmenu

import com.weekmenu.Menu
import org.springframework.beans.factory.annotation.Autowired

class MenuController {
    
    //    static scaffold = Menu
    
    @Autowired MenuService menuService
    
    def index() {
        redirect(action: "list")
    }
    
    def create() {
        Menu menu = new Menu()
        [menu:menu]
    }

    def list(){
        def menuList = Menu.list()
        
        def weeklist = Menu.findAll{
            isweekMenu
        }
        
//        def weeklist = Menu.findAll { isWeekMenu }
        [menulist:menuList, weeklist:weeklist]
    }
    
    def edit(){
        Menu menu = Menu.get(params.id)
        [menu:menu]
    }
        
    def save() {
        def menu = new Menu(params)
        menu.save()
        redirect(action: "list")
    }
        
    def update() {

        Menu menu = Menu.get(params["id"])
        menu.properties = params
        menu.save()
                
        redirect(action: "list")
    }
    
    def delete(){
        Menu.get(params.id).delete()
        redirect(action: "list")
    }
    
    def addVegetable(){
        menuService.addVegetable()
    }

}
