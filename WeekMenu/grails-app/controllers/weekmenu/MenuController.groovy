package weekmenu

import com.weekmenu.Menu
import org.springframework.beans.factory.annotation.Autowired

class MenuController {

    
    //    static scaffold = Menu
    
    @Autowired MenuService menuService
    
    def index() {
//        render "Hello World"
        redirect(action: "list")
    }
    
    def create() {
        Menu menu = new Menu()
        println("create : "+params.toMapString())
        [menu:menu]
    }

    def list(){
        def list = Menu.list()
        [menulist:list]
    }
    
    def edit(){
        Menu menu = Menu.get(params.id)
        [menu:menu]
    }
        
    def save() {
        println("save : "+params.toMapString())
        println(params["name"])
        def menu = new Menu(params)
        menu.save()
        redirect(action: "list")
    }
    
    
        
    def update() {
        println("update : "+params.toMapString())

//        Menu menu = params["menu"]
        println(params["name"])
//        def menu = new Menu(params)
//        menu.save()
//        menuService.saveOrUpdate(params)
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
