
import com.weekmenu.Menu

class BootStrap {

    def init = { servletContext ->
//        if(Menu.count() == 0){
            new Menu(name:'Pasta').save()
            new Menu(name:'Salad').save()
            new Menu(name:'BreakFast').save()
            System.out.println('bootstrap init')
//        }
    }
    def destroy = {
    }
}
